package com.opensymphony.webwork.dispatcher;

import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.util.FileManager;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;
import com.opensymphony.webwork.dispatcher.multipart.WebWorkRequestWrapper;
import com.opensymphony.webwork.util.AttributeMap;
import com.opensymphony.webwork.util.ObjectFactoryInitializable;
import com.opensymphony.xwork.*;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.interceptor.component.ComponentInterceptor;
import com.opensymphony.xwork.interceptor.component.ComponentManager;
import com.opensymphony.xwork.util.LocalizedTextUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * User: patrick
 * Date: Jun 24, 2005
 * Time: 2:11:59 PM
 */
public class DispatcherUtils {
    private static final Log LOG = LogFactory.getLog(DispatcherUtils.class);

    private static DispatcherUtils instance;

    public static void initialize(ServletContext servletContext) {
        synchronized (DispatcherUtils.class) {
            if (instance == null) {
                instance = new DispatcherUtils(servletContext);
            }
        }
    }

    public static DispatcherUtils getInstance() {
        return instance;
    }

    public static void setInstance(DispatcherUtils instance) {
        DispatcherUtils.instance = instance;
    }

    // set from the webwork.i18n.encoding property in webwork.properties
    protected String encoding = null;

    // set from webwork.locale property in webwork.properties
    protected Locale locale = null;

    protected boolean devMode = false;

    // used to get WebLogic to play nice
    protected boolean paramsWorkaroundEnabled = false;

    protected DispatcherUtils(ServletContext servletContext) {
        boolean reloadi18n = Boolean.valueOf((String) Configuration.get("webwork.i18n.reload")).booleanValue();
        LocalizedTextUtil.setReloadBundles(reloadi18n);

        if (Configuration.isSet("webwork.objectFactory")) {
            String className = (String) Configuration.get("webwork.objectFactory");
            try {
                Class clazz = ClassLoaderUtil.loadClass(className, DispatcherUtils.class);
                ObjectFactory objectFactory = (ObjectFactory) clazz.newInstance();
                if (objectFactory instanceof ObjectFactoryInitializable) {
                    ((ObjectFactoryInitializable) objectFactory).init(servletContext);
                }
                ObjectFactory.setObjectFactory(objectFactory);
            } catch (Exception e) {
                LOG.error("Could not load ObjectFactory named " + className + ". Using default ObjectFactory.", e);
            }
        }

        LocalizedTextUtil.addDefaultResourceBundle("com/opensymphony/webwork/webwork-messages");

        if ("true".equals(Configuration.get("webwork.devMode"))) {
            devMode = true;
            Configuration.set("webwork.i18n.reload", "true");
            Configuration.set("webwork.configuration.xml.reload", "true");
        }

        //check for configuration reloading
        if ("true".equalsIgnoreCase(Configuration.getString("webwork.configuration.xml.reload"))) {
            FileManager.setReloadingConfigs(true);
        }

        if (Configuration.isSet("webwork.i18n.encoding")) {
            encoding = Configuration.getString("webwork.i18n.encoding");
        }

        if (Configuration.isSet("webwork.locale")) {
            locale = localeFromString(Configuration.getString("webwork.locale"));
        }

        // test wether param-access workaround needs to be enabled
        if (servletContext.getServerInfo().indexOf("WebLogic") >= 0) {
            LOG.info("WebLogic server detected. Enabling WebWork parameter access work-around.");
            paramsWorkaroundEnabled = true;
        } else if (Configuration.isSet("webwork.dispatcher.parametersWorkaround")) {
            paramsWorkaroundEnabled = "true".equals(Configuration.get("webwork.dispatcher.parametersWorkaround"));
        } else {
            LOG.debug("Parameter access work-around disabled.");
        }
    }

    /**
     * Loads the action and executes it. This method first creates the action context from the given
     * parameters then loads an <tt>ActionProxy</tt> from the given action name and namespace. After that,
     * the action is executed and output channels throught the response object. Errors are also
     * sent back to the user via the {@link DispatcherUtils#sendError} method.
     *
     * @param request  the HttpServletRequest object
     * @param response the HttpServletResponse object
     * @param mapping  the action mapping object
     */
    public void serviceAction(HttpServletRequest request, HttpServletResponse response, ServletContext context, ActionMapping mapping) {
        // request map wrapping the http request objects
        Map requestMap = new RequestMap(request);

        // parameters map wrapping the http paraneters.
        Map params = mapping.getParams();
        Map requestParams = new HashMap(request.getParameterMap());
        if (params != null) {
            params.putAll(requestParams);
        } else {
            params = requestParams;
        }

        // session map wrapping the http session
        Map session = new SessionMap(request);

        // application map wrapping the ServletContext
        Map application = new ApplicationMap(context);

        HashMap extraContext = createContextMap(requestMap, params, session, application, request, response, context);

        // If there was a previous value stack, then create a new copy and pass it in to be used by the new Action
        OgnlValueStack stack = (OgnlValueStack) request.getAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY);
        if (stack != null) {
            extraContext.put(ActionContext.VALUE_STACK, new OgnlValueStack(stack));
        }

        try {
            String namespace = mapping.getNamespace();
            String name = mapping.getName();
            String method = mapping.getMethod();

            ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(namespace, name, extraContext);
            proxy.setMethod(method);
            request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, proxy.getInvocation().getStack());

            // if the ActionMapping says to go straight to a result, do it!
            if (mapping.getResult() != null) {
                Result result = mapping.getResult();
                result.execute(proxy.getInvocation());
            } else {
                proxy.execute();
            }

            // If there was a previous value stack then set it back onto the request
            if (stack != null) {
                request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, stack);
            }
        } catch (ConfigurationException e) {
            LOG.error("Could not find action", e);
            sendError(request, response, HttpServletResponse.SC_NOT_FOUND, e);
        } catch (Exception e) {
            LOG.error("Could not execute action", e);
            sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Merges all application and servlet attributes into a single <tt>HashMap</tt> to represent the entire
     * <tt>Action</tt> context.
     *
     * @param requestMap     a Map of all request attributes.
     * @param parameterMap   a Map of all request parameters.
     * @param sessionMap     a Map of all session attributes.
     * @param applicationMap a Map of all servlet context attributes.
     * @param request        the HttpServletRequest object.
     * @param response       the HttpServletResponse object.
     * @param servletContext the ServletContext object.
     * @return a HashMap representing the <tt>Action</tt> context.
     */
    public HashMap createContextMap(Map requestMap,
                                    Map parameterMap,
                                    Map sessionMap,
                                    Map applicationMap,
                                    HttpServletRequest request,
                                    HttpServletResponse response,
                                    ServletContext servletContext) {
        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, new HashMap(parameterMap));
        extraContext.put(ActionContext.SESSION, sessionMap);
        extraContext.put(ActionContext.APPLICATION, applicationMap);
        extraContext.put(ActionContext.LOCALE, (locale == null) ? request.getLocale() : locale);
        extraContext.put(ActionContext.DEV_MODE, Boolean.valueOf(devMode));

        extraContext.put(WebWorkStatics.HTTP_REQUEST, request);
        extraContext.put(WebWorkStatics.HTTP_RESPONSE, response);
        extraContext.put(WebWorkStatics.SERVLET_CONTEXT, servletContext);
        extraContext.put(ComponentInterceptor.COMPONENT_MANAGER, request.getAttribute(ComponentManager.COMPONENT_MANAGER_KEY));

        // helpers to get access to request/session/application scope
        extraContext.put("request", requestMap);
        extraContext.put("session", sessionMap);
        extraContext.put("application", applicationMap);
        extraContext.put("parameters", parameterMap);

        AttributeMap attrMap = new AttributeMap(extraContext);
        extraContext.put("attr", attrMap);

        return extraContext;
    }

    /**
     * Builds a {@link java.util.Locale} from a String of the form en_US_foo into a Locale
     * with language "en", country "US" and variant "foo". This will parse the output of
     * {@link java.util.Locale#toString()}.
     * todo move this to LocalizedTextUtil in xwork 1.0.6
     */
    public Locale localeFromString(String localeStr) {
        if ((localeStr == null) || (localeStr.trim().length() == 0) || (localeStr.equals("_"))) {
            return null;
        }
        int index = localeStr.indexOf('_');
        if (index < 0) {
            return new Locale(localeStr, "");
        }
        String language = localeStr.substring(0, index);
        if (index == localeStr.length()) {
            return new Locale(language, "");
        }
        localeStr = localeStr.substring(index + 1);
        index = localeStr.indexOf('_');
        if (index < 0) {
            return new Locale(language, localeStr);
        }
        String country = localeStr.substring(0, index);
        if (index == localeStr.length()) {
            return new Locale(language, country);
        }
        localeStr = localeStr.substring(index + 1);
        return new Locale(language, country, localeStr);
    }

    /**
     * Returns the maximum upload size allowed for multipart requests (this is configurable).
     *
     * @return the maximum upload size allowed for multipart requests
     */
    public static int getMaxSize() {
        Integer maxSize = new Integer(Integer.MAX_VALUE);
        try {
            String maxSizeStr = Configuration.getString("webwork.multipart.maxSize");

            if (maxSizeStr != null) {
                try {
                    maxSize = new Integer(maxSizeStr);
                } catch (NumberFormatException e) {
                    LOG.warn("Unable to format 'webwork.multipart.maxSize' property setting. Defaulting to Integer.MAX_VALUE");
                }
            } else {
                LOG.warn("Unable to format 'webwork.multipart.maxSize' property setting. Defaulting to Integer.MAX_VALUE");
            }
        } catch (IllegalArgumentException e1) {
            LOG.warn("Unable to format 'webwork.multipart.maxSize' property setting. Defaulting to Integer.MAX_VALUE");
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("maxSize=" + maxSize);
        }

        return maxSize.intValue();
    }

    /**
     * Returns the path to save uploaded files to (this is configurable).
     *
     * @return the path to save uploaded files to
     */
    public String getSaveDir(ServletContext servletContext) {
        String saveDir = Configuration.getString("webwork.multipart.saveDir").trim();

        if (saveDir.equals("")) {
            File tempdir = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            LOG.info("Unable to find 'webwork.multipart.saveDir' property setting. Defaulting to javax.servlet.context.tempdir");

            if (tempdir != null) {
                saveDir = tempdir.toString();
            }
        } else {
            File multipartSaveDir = new File(saveDir);

            if (!multipartSaveDir.exists()) {
                multipartSaveDir.mkdir();
            }
        }

        if (LOG.isDebugEnabled()) {
            LOG.debug("saveDir=" + saveDir);
        }

        return saveDir;
    }

    public void prepare(HttpServletRequest request, HttpServletResponse response) {
        if (encoding != null) {
            try {
                request.setCharacterEncoding(encoding);
            } catch (Exception e) {
                LOG.error("Error setting character encoding to '" + encoding + "' - ignoring.", e);
            }
        }

        if (locale != null) {
            response.setLocale(locale);
        }

        if (paramsWorkaroundEnabled) {
            request.getParameter("foo"); // simply read any parameter (existing or not) to "prime" the request
        }
    }

    /**
     * Wraps and returns the given response or returns the original response object. This is used to transparently
     * handle multipart data as a wrapped class around the given request. Override this method to handle multipart
     * requests in a special way or to handle other types of requests. Note, {@link com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper} is
     * flexible - you should look to that first before overriding this method to handle multipart data.
     *
     * @param request the HttpServletRequest object.
     * @return a wrapped request or original request.
     * @see com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper
     */
    public HttpServletRequest wrapRequest(HttpServletRequest request, ServletContext servletContext) throws IOException {
        // don't wrap more than once
        if (request instanceof WebWorkRequestWrapper) {
            return request;
        }

        if (MultiPartRequest.isMultiPart(request)) {
            request = new MultiPartRequestWrapper(request, getSaveDir(servletContext), getMaxSize());
        } else {
            request = new WebWorkRequestWrapper(request);
        }

        return request;
    }

    /**
     * Sends an HTTP error response code.
     *
     * @param request  the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param code     the HttpServletResponse error code (see {@link javax.servlet.http.HttpServletResponse} for possible error codes).
     * @param e        the Exception that is reported.
     */
    public void sendError(HttpServletRequest request, HttpServletResponse response, int code, Exception e) {
        try {
            // send a http error response to use the servlet defined error handler
            // make the exception availible to the web.xml defined error page
            request.setAttribute("javax.servlet.error.exception", e);

            // for compatibility
            request.setAttribute("javax.servlet.jsp.jspException", e);

            // send the error response
            response.sendError(code, e.getMessage());
        } catch (IOException e1) {
        }
    }
}
