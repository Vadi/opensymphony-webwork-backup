/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.util.FileManager;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequest;
import com.opensymphony.webwork.dispatcher.multipart.MultiPartRequestWrapper;
import com.opensymphony.webwork.util.AttributeMap;
import com.opensymphony.webwork.views.velocity.VelocityManager;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.interceptor.component.ComponentInterceptor;
import com.opensymphony.xwork.interceptor.component.ComponentManager;
import com.opensymphony.xwork.util.LocalizedTextUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/*
 * TODO: QUESTIONS:
 *
 * 1) What unit is maxSize of attachments in? (assuming bytes for now)
 * 2) Isn't error message wrong in catch of try/catch in  service() method?
 * 3) Why is getActionName(String) not declared public? (The fix would not be an API addition so this could be
 *      done for pre 2.1)
 * 4) Why does createContextMap(...) return a HashMap and not a Map? (2.1 api change)
 * 5) Why doesn't getNameSpace(request) get the servlet path in the same way that getActionName(request) does?
 * 6) Why does getParameterMap throw an IOException? Can't see a reason for that. (2.1 api change)
 */

/**
 * Main dispatcher servlet in WebWork2 which acts as the controller in the MVC paradigm. <p>
 * <p/>
 * When a request enters the servlet the following things will happen: <ol>
 * <p/>
 * <li>The action name is parsed from the servlet path (i.e., /foo/bar/MyAction.action -> MyAction).</li>
 * <li>A context consisting of the request, response, parameters, session and application
 * properties is created.</li>
 * <li>An XWork <tt>ActionProxy</tt> object is instantiated (wraps an <tt>Action</tt>) using the action name, path,
 * and context then executed.</li>
 * <li>Action output will channel back through the response to the user.</li></ol>
 * <p/>
 * Any errors occurring during the action execution will result in a
 * {@link javax.servlet.http.HttpServletResponse#SC_INTERNAL_SERVER_ERROR} error and any resource errors
 * (i.e., invalid action name or missing JSP page) will result in a
 * {@link javax.servlet.http.HttpServletResponse#SC_NOT_FOUND} error. <p>
 * <p/>
 * Instead of traditional servlet init params this servlet will initialize itself using WebWork2 properties.
 * The following properties are used upon initialization: <ul>
 * <p/>
 * <li><tt>webwork.configuration.xml.reload</tt>: if and only if set to <tt>true</tt> then the xml configuration
 * files (action definitions, interceptor definitions, etc) will be reloaded for each request. This is
 * useful for development but should be disabled for production deployment.</li>
 * <li><tt>webwork.multipart.saveDir</tt>: The path used for temporarily uploaded files. Defaults to the
 * temp path specified by the app server.</li>
 * <li><tt>webwork.multipart.maxSize</tt>: sets the maximum allowable multipart request size
 * in bytes. If the size was not specified then {@link java.lang.Integer#MAX_VALUE} will be used
 * (essentially unlimited so be careful).</li></ul>
 * <p/>
 * Developers who want to subclass this servlet may be interested in the following protected methods: <ul>
 * <p/>
 * <li>{@link #getParameterMap(HttpServletRequest)}</li>
 * <li>{@link #getRequestMap(HttpServletRequest)}</li>
 * <li>{@link #getSessionMap(HttpServletRequest)}</li>
 * <li>{@link #getNameSpace(HttpServletRequest)}</li></ul>
 *
 * @author <a href="mailto:rickard@middleware-company.com">Rickard Öberg</a>
 * @author <a href="mailto:matt@smallleap.com">Matt Baldree</a>
 * @author Jason Carreira
 * @author <a href="mailto:cameron@datacodex.net">Cameron Braid</a>
 * @author Bill Lynch
 * @see ServletDispatcherResult
 */
public class ServletDispatcher extends HttpServlet implements WebWorkStatics {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * Logger for this class.
     */
    protected static final Log log = LogFactory.getLog(ServletDispatcher.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    // Max upload size allowed for multipart request (this is configurable).
    Integer maxSize;

    // Path to save uploaded files to (this is configurable).
    String saveDir;
    
    boolean paramsWorkaroundEnabled = false;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Returns the namespace (the context path) of the action. I.e., "/foo/bar/MyAction.action" -&gt;
     * "/foo/bar" and "MyAction.action" -&gt; "".
     *
     * @param servletPath the servlet URL path.
     * @return the namespace (context path) of the action.
     */
    public static String getNamespaceFromServletPath(String servletPath) {
        servletPath = servletPath.substring(0, servletPath.lastIndexOf("/"));

        return servletPath;
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
     * @param servletConfig  the ServletConfig object.
     * @return a HashMap representing the <tt>Action</tt> context.
     */
    public static HashMap createContextMap(Map requestMap, Map parameterMap, Map sessionMap, Map applicationMap, HttpServletRequest request, HttpServletResponse response, ServletConfig servletConfig) {
        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, parameterMap);
        extraContext.put(ActionContext.SESSION, sessionMap);
        extraContext.put(ActionContext.APPLICATION, applicationMap);
        extraContext.put(ActionContext.LOCALE, request.getLocale());

        extraContext.put(HTTP_REQUEST, request);
        extraContext.put(HTTP_RESPONSE, response);
        extraContext.put(SERVLET_CONFIG, servletConfig);
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
     * Initalizes the servlet. Please read the {@link ServletDispatcher class documentation} for more
     * detail. <p>
     * <p/>
     * Note, the <a href="http://jakarta.apache.org/velocity/" target="_blank">Velocity</a> compontent is also
     * initialized in this method - it is used in many of the WebWork2 JSP tags.
     *
     * @param config the ServletConfig object.
     * @throws ServletException if an error occurs during initialization.
     */
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        // initialize the VelocityEngine
        VelocityManager.getInstance().init(config.getServletContext());

        LocalizedTextUtil.addDefaultResourceBundle("com/opensymphony/webwork/webwork-messages");

        //check for configuration reloading
        if ("true".equalsIgnoreCase(Configuration.getString("webwork.configuration.xml.reload"))) {
            FileManager.setReloadingConfigs(true);
        }

        //load multipart configuration
        //saveDir
        saveDir = Configuration.getString("webwork.multipart.saveDir").trim();

        if (saveDir.equals("")) {
            File tempdir = (File) config.getServletContext().getAttribute("javax.servlet.context.tempdir");
            log.warn("Unable to find 'webwork.multipart.saveDir' property setting. Defaulting to javax.servlet.context.tempdir");

            if (tempdir != null) {
                saveDir = tempdir.toString();
            }
        } else {
            File multipartSaveDir = new File(saveDir);

            if (!multipartSaveDir.exists()) {
                multipartSaveDir.mkdir();
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("saveDir=" + saveDir);
        }

        //maxSize
        try {
            String maxSizeStr = Configuration.getString("webwork.multipart.maxSize");

            if (maxSizeStr != null) {
                try {
                    maxSize = new Integer(maxSizeStr);
                } catch (NumberFormatException e) {
                    maxSize = new Integer(Integer.MAX_VALUE);
                    log.warn("Unable to format 'webwork.multipart.maxSize' property setting. Defaulting to Integer.MAX_VALUE");
                }
            } else {
                maxSize = new Integer(Integer.MAX_VALUE);
                log.warn("Unable to format 'webwork.multipart.maxSize' property setting. Defaulting to Integer.MAX_VALUE");
            }
        } catch (IllegalArgumentException e1) {
            maxSize = new Integer(Integer.MAX_VALUE);
            log.warn("Unable to format 'webwork.multipart.maxSize' property setting. Defaulting to Integer.MAX_VALUE");
        }

        if (log.isDebugEnabled()) {
            log.debug("maxSize=" + maxSize);
        }

        // store a reference to ourself into the SessionContext so that we can generate a PageContext
        config.getServletContext().setAttribute("webwork.servlet", this);
        
        // test wether param-access workaround needs to be enabled
        if(config.getServletContext().getServerInfo().indexOf("WebLogic") >= 0) {
            log.info("WebLogic server detected. Enabling parameter access work-around.");
            paramsWorkaroundEnabled = true;
        }
        else {
            log.debug("Parameter access work-around disabled.");
        }
    }

    /**
     * Services the request by determining the desired action to load, building the action context and
     * then executing the action. This handles all servlet requests including GETs and POSTs. <p>
     * <p/>
     * This method also transparently handles multipart requests.
     *
     * @param request  the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @throws ServletException if an error occurs while loading or executing the action.
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            if(paramsWorkaroundEnabled)
                request.getParameter("foo"); // simply read any parameter (existing or not) to "prime" the request
	   
            request = wrapRequest(request);
            serviceAction(request, response, getNameSpace(request), getActionName(request), getRequestMap(request), getParameterMap(request), getSessionMap(request), getApplicationMap());
        } catch (IOException e) {
            String message = "Could not wrap servlet request with MultipartRequestWrapper!";
            log.error(message, e);
            sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, new ServletException(message, e));
        }
    }

    /**
     * Loads the action and executes it. This method first creates the action context from the given
     * parameters then loads an <tt>ActionProxy</tt> from the given action name and namespace. After that,
     * the action is executed and output channels throught the response object. Errors are also
     * sent back to the user via the {@link #sendError(HttpServletRequest,HttpServletResponse,int,Exception)} method.
     *
     * @param request        the HttpServletRequest object.
     * @param response       the HttpServletResponse object.
     * @param namespace      the namespace or context of the action.
     * @param actionName     the name of the action to execute.
     * @param requestMap     a Map of request attributes.
     * @param parameterMap   a Map of request parameters.
     * @param sessionMap     a Map of all session attributes.
     * @param applicationMap a Map of all application attributes.
     */
    public void serviceAction(HttpServletRequest request, HttpServletResponse response, String namespace, String actionName, Map requestMap, Map parameterMap, Map sessionMap, Map applicationMap) {
        HashMap extraContext = createContextMap(requestMap, parameterMap, sessionMap, applicationMap, request, response, getServletConfig());
        extraContext.put(SERLVET_DISPATCHER, this);

        try {
            ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(namespace, actionName, extraContext);
            request.setAttribute("webwork.valueStack", proxy.getInvocation().getStack());
            proxy.execute();
        } catch (ConfigurationException e) {
            log.error("Could not find action", e);
            sendError(request, response, HttpServletResponse.SC_NOT_FOUND, e);
        } catch (Exception e) {
            log.error("Could not execute action", e);
            sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e);
        }
    }

    /**
     * Build the name of the action from the request. Override this method to customize the request to action
     * name mapping. Default implementation is to call the getActionName(String) method with the
     * servlet path as the parameter.
     *
     * @param request the HttpServletRequest object.
     * @return the name or alias of the action to execute.
     */
    protected String getActionName(HttpServletRequest request) {
        String servletPath = (String) request.getAttribute("javax.servlet.include.servlet_path");

        if (servletPath == null) {
            servletPath = request.getServletPath();
        }

        return getActionName(servletPath);
    }

    /**
     * Returns a Map of all application attributes. The default implementation is to wrap the ServletContext
     * in an {@link ApplicationMap}. Override this method to customize how application attributes are mapped.
     *
     * @return a Map of all application attributes.
     */
    protected Map getApplicationMap() {
        return new ApplicationMap(getServletContext());
    }

    /**
     * Gets the namespace of the action from the request. Override this method to customize the request to action
     * namespace mapping. Default implementation is to call {@link #getNamespaceFromServletPath(String)} method
     * with the servlet path as the parameter.
     *
     * @param request the HttpServletRequest object.
     * @return the namespace (context path) of the action.
     */
    protected String getNameSpace(HttpServletRequest request) {
        // Path is always original path, even if it is included in page with another path
        String servletPath = request.getServletPath();

        return getNamespaceFromServletPath(servletPath);
    }

    /**
     * Returns a Map of all request parameters. The default implementation just calls
     * {@link HttpServletRequest#getParameterMap()}. Override this method to customize how application parameters
     * are mapped.
     *
     * @param request the HttpServletRequest object.
     * @return a Map of all request parameters.
     * @throws IOException if an exception occurs while retrieving the parameter map.
     */
    protected Map getParameterMap(HttpServletRequest request) throws IOException {
        return request.getParameterMap();
    }

    /**
     * Returns a Map of all request attributes. The default implementation is to wrap the request in a
     * {@link RequestMap}. Override this method to customize how request attributes are mapped.
     *
     * @param request the HttpServletRequest object.
     * @return a Map of all request attributes.
     */
    protected Map getRequestMap(HttpServletRequest request) {
        return new RequestMap(request);
    }

    /**
     * Returns a Map of all session attributes. The default implementation is to wrap the reqeust
     * in a {@link SessionMap}. Override this method to customize how session attributes are mapped.
     *
     * @param request the HttpServletRequest object.
     * @return a Map of all session attributes.
     */
    protected Map getSessionMap(HttpServletRequest request) {
        return new SessionMap(request);
    }

    /**
     * Sends an HTTP error response code.
     *
     * @param request  the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param code     the HttpServletResponse error code (see {@link HttpServletResponse} for possible error codes).
     * @param e        the Exception that is reported.
     */
    protected void sendError(HttpServletRequest request, HttpServletResponse response, int code, Exception e) {
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

    /**
     * Wraps and returns the given response or returns the original response object. This is used to transparently
     * handle multipart data as a wrapped class around the given request. Override this method to handle multipart
     * requests in a special way or to handle other types of requests. Note, {@link MultiPartRequestWrapper} is
     * flexible - you should look to that first before overriding this method to handle multipart data.
     *
     * @param request the HttpServletRequest object.
     * @return a wrapped request or original request.
     * @see MultiPartRequestWrapper
     */
    protected HttpServletRequest wrapRequest(HttpServletRequest request) throws IOException {
        // don't wrap more than once
        if (request instanceof MultiPartRequestWrapper) {
            return request;
        }

        if (MultiPartRequest.isMultiPart(request)) {
            request = new MultiPartRequestWrapper(request, saveDir, maxSize.intValue());
        }

        return request;
    }

    /**
     * Determine action name by extracting last string and removing extension (i.e., /.../.../Foo.action -> Foo).
     *
     * @param name the full action path.
     * @return the action name stripped of path/context info.
     */
    String getActionName(String name) {
        // Get action name ("Foo.action" -> "Foo" action)
        int beginIdx = name.lastIndexOf("/");
        int endIdx = name.lastIndexOf(".");

        return name.substring(((beginIdx == -1) ? 0 : (beginIdx + 1)), (endIdx == -1) ? name.length() : endIdx);
    }
}
