/*
 * Copyright 2004 BEKK Consulting
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.opensymphony.webwork.portlet.dispatcher;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.util.ClassLoaderUtil;
import com.opensymphony.util.FileManager;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.dispatcher.ApplicationMap;
import com.opensymphony.webwork.dispatcher.RequestMap;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.portlet.PortletActionConstants;
import com.opensymphony.webwork.portlet.PortletApplicationMap;
import com.opensymphony.webwork.portlet.PortletRequestMap;
import com.opensymphony.webwork.portlet.PortletSessionMap;
import com.opensymphony.webwork.portlet.context.PortletActionContext;
import com.opensymphony.webwork.portlet.context.ServletContextHolderListener;
import com.opensymphony.webwork.util.AttributeMap;
import com.opensymphony.webwork.util.ObjectFactoryInitializable;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.config.ConfigurationException;
import com.opensymphony.xwork.util.LocalizedTextUtil;

/**
 * WebWork2 JSR-168 portlet dispatcher. Similar to the WW2 Servlet dispatcher,
 * but adjusted to a portal environment. For information about WebWork2, go to
 * <a
 * href="http://www.opensymphony.com/webwork2">http://www.opensymphony.com/webwork2
 * </a>
 * 
 * @author <a href="nils-helge.garli@bekk.no">Nils-Helge Garli </a>
 *  
 */

public class Jsr168Dispatcher extends GenericPortlet implements WebWorkStatics,
        PortletActionConstants {

    private static final Log LOG = LogFactory.getLog(Jsr168Dispatcher.class);

    private ActionProxyFactory factory = null;

    private Map modeMap = new HashMap(3);

    private Map actionMap = new HashMap(3);

    private String portletNamespace = null;

    private String encoding = null;

    private Locale locale = null;

    private static final int NO_ACTION_YET_PERFORMED = 1;

    private static final int RE_RENDER = 2;

    private static final int NEW_REQUEST = 3;

    private static final int EVENT = 4;

    public void init(PortletConfig cfg) throws PortletException {
        super.init(cfg);
        LOG.debug("Creating portlet instance with hashcode = " + hashCode());
        // For testability
        if (factory == null) {
            factory = ActionProxyFactory.getFactory();
        }
        portletNamespace = cfg.getInitParameter("portletNamespace");
        LOG.debug("PortletNamespace: " + portletNamespace);
        parseModeConfig(cfg, PortletMode.VIEW, "viewNamespace",
                "defaultViewAction");
        parseModeConfig(cfg, PortletMode.EDIT, "editNamespace",
                "defaultEditAction");
        parseModeConfig(cfg, PortletMode.HELP, "helpNamespace",
                "defaultHelpAction");
        parseModeConfig(cfg, new PortletMode("config"), "configNamespace",
                "defaultConfigAction");
        parseModeConfig(cfg, new PortletMode("about"), "aboutNamespace",
                "defaultAboutAction");
        parseModeConfig(cfg, new PortletMode("print"), "printNamespace",
                "defaultPrintAction");
        parseModeConfig(cfg, new PortletMode("preview"), "previewNamespace",
                "defaultPreviewAction");
        parseModeConfig(cfg, new PortletMode("edit_defaults"),
                "editDefaultsNamespace", "defaultEditDefaultsAction");
        if (StringUtils.isEmpty(portletNamespace)) {
            portletNamespace = "";
        }
        LocalizedTextUtil
                .addDefaultResourceBundle("com/opensymphony/webwork/webwork-messages");

        //check for configuration reloading
        if ("true".equalsIgnoreCase(Configuration
                .getString("webwork.configuration.xml.reload"))) {
            FileManager.setReloadingConfigs(true);
        }

        if (Configuration.isSet("webwork.i18n.encoding")) {
            encoding = Configuration.getString("webwork.i18n.encoding");
        }

        if (Configuration.isSet("webwork.locale")) {
            locale = localeFromString(Configuration.getString("webwork.locale"));
        }
        if (Configuration.isSet("webwork.objectFactory")) {
            String className = (String) Configuration
                    .get("webwork.objectFactory");
            if (className.equals("spring")) {
                // note: this class name needs to be in string form so we don't
                // put hard
                //       dependencies on spring, since it isn't technically required.
                className = "com.opensymphony.webwork.spring.WebWorkSpringObjectFactory";
            }

            try {
                Class clazz = ClassLoaderUtil.loadClass(className,
                        Jsr168Dispatcher.class);
                ObjectFactory objectFactory = (ObjectFactory) clazz
                        .newInstance();
                if (objectFactory instanceof ObjectFactoryInitializable) {
                    ((ObjectFactoryInitializable) objectFactory)
                            .init(ServletContextHolderListener
                                    .getServletContext());
                }
                ObjectFactory.setObjectFactory(objectFactory);
            } catch (Exception e) {
                LOG.error("Could not load ObjectFactory named " + className
                        + ". Using default ObjectFactory.", e);
            }
        }
        LOG.debug("Init complete. The maps has hashcodes " + modeMap.hashCode()
                + ", " + actionMap.hashCode());
    }

    private void parseModeConfig(PortletConfig portletConfig,
            PortletMode portletMode, String nameSpaceParam,
            String defaultActionParam) {
        String namespace = portletConfig.getInitParameter(nameSpaceParam);
        if (StringUtils.isEmpty(namespace)) {
            namespace = "";
        }
        modeMap.put(portletMode, namespace);
        String defaultAction = portletConfig
                .getInitParameter(defaultActionParam);
        if (StringUtils.isEmpty(defaultAction)) {
            defaultAction = DEFAULT_ACTION_NAME;
        }
        StringBuffer fullPath = new StringBuffer();
        if (StringUtils.isNotEmpty(portletNamespace)) {
            fullPath.append(portletNamespace + "/");
        }
        if (StringUtils.isNotEmpty(namespace)) {
            fullPath.append(namespace + "/");
        }
        fullPath.append(defaultAction);
        ActionMapping mapping = new ActionMapping();
        mapping.setName(getActionName(fullPath.toString()));
        mapping.setNamespace(getNamespace(fullPath.toString()));
        actionMap.put(portletMode, mapping);
    }

    /**
     * @see javax.portlet.Portlet#processAction(javax.portlet.ActionRequest,
     *      javax.portlet.ActionResponse)
     * @see #serviceAction(PortletRequest, PortletResponse, String, String, Map,
     *      Map, Map, Map, Integer)
     */
    public void processAction(ActionRequest request, ActionResponse response)
            throws PortletException, IOException {
        LOG.debug("Entering processAction");
        resetActionContext();
        try {
            serviceAction(request, response, getActionMapping(request),
                    getRequestMap(request), getParameterMap(request),
                    getSessionMap(request), getApplicationMap(),
                    portletNamespace, EVENT_PHASE);
            LOG.debug("Leaving processAction");
        } finally {
            ActionContext.setContext(null);
        }
    }

    /**
     * @see javax.portlet.Portlet#render(javax.portlet.RenderRequest,
     *      javax.portlet.RenderResponse)
     * @see #serviceAction(PortletRequest, PortletResponse, String, String, Map,
     *      Map, Map, Map, Integer)
     */
    public void render(RenderRequest request, RenderResponse response)
            throws PortletException, IOException {

        LOG.debug("Entering render");
        LOG.debug("Namespace for portlet is: " + response.getNamespace());
        resetActionContext();
        try {
            // Check to see if an event set the render to be included directly
            serviceAction(request, response, getActionMapping(request),
                    getRequestMap(request), getParameterMap(request),
                    getSessionMap(request), getApplicationMap(),
                    portletNamespace, RENDER_PHASE);
            LOG.debug("Leaving render");
        } finally {
            resetActionContext();
        }
    }

    /**
     *  
     */
    private void resetActionContext() {
        ActionContext.setContext(null);
    }

    /**
     * Merges all application and portlet attributes into a single
     * <tt>HashMap</tt> to represent the entire <tt>Action</tt> context.
     * 
     * @param requestMap a Map of all request attributes.
     * @param parameterMap a Map of all request parameters.
     * @param sessionMap a Map of all session attributes.
     * @param applicationMap a Map of all servlet context attributes.
     * @param request the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param servletConfig the ServletConfig object.
     * @param phase The portlet phase (render or action, see
     *        {@link PortletActionConstants})
     * @return a HashMap representing the <tt>Action</tt> context.
     */
    public HashMap createContextMap(Map requestMap, Map parameterMap,
            Map sessionMap, Map applicationMap, PortletRequest request,
            PortletResponse response, PortletConfig portletConfig, Integer phase) {

        // TODO Must put http request/response objects into map for use with
        // ServletActionContext
        HashMap extraContext = new HashMap();
        extraContext.put(ActionContext.PARAMETERS, parameterMap);
        extraContext.put(ActionContext.SESSION, sessionMap);
        extraContext.put(ActionContext.APPLICATION, applicationMap);
        extraContext.put(ActionContext.LOCALE, request.getLocale());

        extraContext.put(REQUEST, request);
        extraContext.put(RESPONSE, response);
        extraContext.put(PORTLET_CONFIG, portletConfig);
        extraContext.put(PORTLET_NAMESPACE, portletNamespace);
        extraContext.put(DEFAULT_ACTION_FOR_MODE, actionMap.get(request.getPortletMode()));
        // helpers to get access to request/session/application scope
        extraContext.put("request", requestMap);
        extraContext.put("session", sessionMap);
        extraContext.put("application", applicationMap);
        extraContext.put("parameters", parameterMap);
        extraContext.put(MODE_NAMESPACE_MAP, modeMap);

        extraContext.put(PHASE, phase);

        AttributeMap attrMap = new AttributeMap(extraContext);
        extraContext.put("attr", attrMap);

        return extraContext;
    }

    /**
     * Loads the action and executes it. This method first creates the action
     * context from the given parameters then loads an <tt>ActionProxy</tt>
     * from the given action name and namespace. After that, the action is
     * executed and output channels throught the response object. Errors are
     * also sent back to the user via the
     * {@link #sendError(HttpServletRequest,HttpServletResponse,int,Exception)}
     * method.
     * 
     * @param request the HttpServletRequest object.
     * @param response the HttpServletResponse object.
     * @param namespace the namespace or context of the action.
     * @param actionName the name of the action to execute.
     * @param requestMap a Map of request attributes.
     * @param parameterMap a Map of request parameters.
     * @param sessionMap a Map of all session attributes.
     * @param phase The portlet phase (render or action, see
     *        {@link PortletActionConstants})
     * @param applicationMap a Map of all application attributes.
     */
    public void serviceAction(PortletRequest request, PortletResponse response,
            ActionMapping mapping, Map requestMap, Map parameterMap,
            Map sessionMap, Map applicationMap, String portletNamespace,
            Integer phase) throws PortletException {
        LOG.debug("serviceAction");
        HashMap extraContext = createContextMap(requestMap, parameterMap,
                sessionMap, applicationMap, request, response,
                getPortletConfig(), phase);
        PortletMode mode = request.getPortletMode();
        LOG.debug("Thread serving action is "
                + Thread.currentThread().getName());
        LOG.debug("ActionContext in use is " + ActionContext.getContext());
        String actionName = mapping.getName();
        String namespace = mapping.getNamespace();
        try {
            LOG.debug("Creating action proxy for name = " + actionName
                    + ", namespace = " + namespace);
            ActionProxy proxy = factory.createActionProxy(namespace,
                    actionName, extraContext);
            request.setAttribute("webwork.valueStack", proxy.getInvocation()
                    .getStack());
            if (PortletActionConstants.RENDER_PHASE.equals(phase)
                    && StringUtils.isNotEmpty(request
                            .getParameter(EVENT_ACTION))) {

                ActionProxy action = (ActionProxy) request.getPortletSession()
                        .getAttribute(EVENT_ACTION);
                if (action != null) {
                    proxy.getInvocation().getStack().push(
                            action.getInvocation().getAction());
                }
            }
            LOG.debug("Executing action proxy");
            proxy.execute();
            if (PortletActionConstants.EVENT_PHASE.equals(phase)) {
                // Store the executed action in the session for retrieval in the
                // render
                // phase.
                ActionResponse actionResp = (ActionResponse) response;
                request.getPortletSession().setAttribute(EVENT_ACTION, proxy);
                actionResp.setRenderParameter(EVENT_ACTION, "true");
            }
        } catch (ConfigurationException e) {
            LOG.error("Could not find action", e);
            throw new PortletException("Could not find action " + actionName, e);
        } catch (Exception e) {
            LOG.error("Could not execute action", e);
            throw new PortletException("Error executing action " + actionName,
                    e);
        }
    }

    /**
     * @return
     */
    private String generateRequestUID() {
        return Long.toString(System.currentTimeMillis());
    }

    /**
     * Returns a Map of all application attributes. Copies all attributes from
     * the {@link PortletActionContext}into an {@link ApplicationMap}.
     * 
     * @return a Map of all application attributes.
     */
    protected Map getApplicationMap() {
        return new PortletApplicationMap(getPortletContext());
    }

    /**
     * Gets the namespace of the action from the request. The namespace is the
     * same as the portlet mode. E.g, view mode is mapped to namespace
     * <code>view</code>, and edit mode is mapped to the namespace
     * <code>edit</code>
     * 
     * @param request the PortletRequest object.
     * @return the namespace of the action.
     */
    protected ActionMapping getActionMapping(PortletRequest request) {
        ActionMapping mapping = new ActionMapping();
        if (resetAction(request)) {
            mapping = (ActionMapping) actionMap.get(request.getPortletMode());
        } else {
            String actionPath = request.getParameter("action");
            if (StringUtils.isEmpty(actionPath)) {
                mapping = (ActionMapping) actionMap.get(request
                        .getPortletMode());
            } else {
                String namespace = "";
                String action = actionPath;
                int idx = actionPath.lastIndexOf('/');
                if (idx >= 0) {
                    namespace = actionPath.substring(0, idx);
                    action = actionPath.substring(idx + 1);
                }
                LOG.debug("Action: " + action + ", Namespace: " + namespace);
                mapping.setName(action);
                mapping.setNamespace(namespace);
                LOG.debug("Action (from mapping): " + mapping.getName()
                        + ", Namespace: " + mapping.getNamespace());
            }
        }
        return mapping;
    }

    String getNamespace(String actionPath) {
        int idx = actionPath.lastIndexOf('/');
        String namespace = "";
        if (idx >= 0) {
            namespace = actionPath.substring(0, idx);
        }
        return namespace;
    }

    String getActionName(String actionPath) {
        int idx = actionPath.lastIndexOf('/');
        String action = actionPath;
        if (idx >= 0) {
            action = actionPath.substring(idx + 1);
        }
        return action;
    }

    /**
     * Returns a Map of all request parameters. This implementation just calls
     * {@link PortletRequest#getParameterMap()}.
     * 
     * @param request the PortletRequest object.
     * @return a Map of all request parameters.
     * @throws IOException if an exception occurs while retrieving the parameter
     *         map.
     */
    protected Map getParameterMap(PortletRequest request) throws IOException {
        return new HashMap(request.getParameterMap());
    }

    /**
     * Returns a Map of all request attributes. The default implementation is to
     * wrap the request in a {@link RequestMap}. Override this method to
     * customize how request attributes are mapped.
     * 
     * @param request the PortletRequest object.
     * @return a Map of all request attributes.
     */
    protected Map getRequestMap(PortletRequest request) {
        return new PortletRequestMap(request);
    }

    /**
     * Returns a Map of all session attributes. The default implementation is to
     * wrap the reqeust in a {@link SessionMap}. Override this method to
     * customize how session attributes are mapped.
     * 
     * @param request the PortletRequest object.
     * @return a Map of all session attributes.
     */
    protected Map getSessionMap(PortletRequest request) {
        return new PortletSessionMap(request);
    }

    protected void setActionProxyFactory(ActionProxyFactory factory) {
        this.factory = factory;
    }

    /**
     * Builds a {@link java.util.Locale}from a String of the form en_US_foo
     * into a Locale with language "en", country "US" and variant "foo". This
     * will parse the output of {@link java.util.Locale#toString()}. todo move
     * this to LocalizedTextUtil in xwork 1.0.6
     */
    public static Locale localeFromString(String localeStr) {
        if ((localeStr == null) || (localeStr.trim().length() == 0)
                || (localeStr.equals("_"))) {
            return null;
        }
        int index = localeStr.indexOf('_');
        if (index < 0) {
            return new Locale(localeStr);
        }
        String language = localeStr.substring(0, index);
        if (index == localeStr.length()) {
            return new Locale(language);
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

    private boolean resetAction(PortletRequest request) {
        boolean reset = false;
        Map paramMap = request.getParameterMap();
        String[] modeParam = (String[]) paramMap.get("portletwork.mode");
        if (modeParam != null && modeParam.length == 1) {
            String originatingMode = modeParam[0];
            String currentMode = request.getPortletMode().toString();
            if (!currentMode.equals(originatingMode)) {
                reset = true;
            }
        }
        return reset;
    }
}