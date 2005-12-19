/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortalContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletResponse;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServlet;

import org.apache.pluto.core.impl.PortletConfigImpl;

import com.opensymphony.util.FileManager;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.portlet.context.PortletContext;
import com.opensymphony.webwork.portlet.util.PortalContainer;
import com.opensymphony.webwork.portlet.util.PortletMessaging;
import com.opensymphony.webwork.util.AttributeMap;
import com.opensymphony.webwork.views.JspSupportServlet;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionGlobalContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public class WebWorkPortlet extends GenericPortlet implements WebWorkPortletStatics {

    private static final String NAME_RESULT = "result";

    private static final String NAME_ACTION = "wwAction";

    private static final String NAME_XACTION = "wwXAction";

    private static final String NAME_LINK = "wwLink";

    private static final String NAME_DEFAULT_VIEW_FILE = "defaultViewFile";

    private static final String NAME_DEFAULT_EDIT_FILE = "defaultEditFile";

    private static final String NAME_DEFAULT_HELP_FILE = "defaultHelpFile";

    private String helpFileName = "";

    private String mockHelpFileName = "";

    private Map cheatRequestMap = null;

    public void init() throws PortletException {
        if ("true".equalsIgnoreCase(Configuration.getString(WebWorkConstants.WEBWORK_CONFIGURATION_XML_RELOAD))) {
            FileManager.setReloadingConfigs(true);
        }

        helpFileName = getInitParameter(NAME_DEFAULT_HELP_FILE);

        /*
         * Add JSP support, change the dispatch name from *.jsp to *.jspt, so
         * our WebWorkJSPServlvet can catch this request.
         */
        int jspIndex = helpFileName.lastIndexOf(".jsp");
        if (jspIndex >= 0) {
            mockHelpFileName = helpFileName.substring(0, jspIndex) + ".jspt";
        } else {
            int index = helpFileName.lastIndexOf(".");
            mockHelpFileName = helpFileName.substring(0, index) + ".vm";
        }

    }

    protected void doHelp(RenderRequest request, RenderResponse response) throws PortletException, IOException {

        response.setContentType("text/html");
        ActionContext.getContext().put("template", helpFileName);
        PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(mockHelpFileName);
        try {
            rd.include(request, response);
        } catch (PortletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void doEdit(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        doService(request, response, NAME_DEFAULT_EDIT_FILE);
    }

    protected void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
        doService(request, response, NAME_DEFAULT_VIEW_FILE);
    }

    private void doService(RenderRequest request, RenderResponse response, String defalutFile) throws IOException, PortletException {

        try {

            response.setContentType("text/html");

            PortletContext portletContext = (PortletContext) PortletMessaging.receive(request, "PortletContext");
            PortletContext.setContext(portletContext);

            ActionContext actionContext = (ActionContext) PortletMessaging.receive(request, "ActionContext");
            ActionContext.setContext(actionContext);

            PortletURL portletURL = response.createActionURL();
            String actionURL = portletURL.toString();
            PortletContext.getContext().setActionURL(actionURL);

            String template = (String) request.getPortletSession().getAttribute(RENDER_TEMPLATE);
            if (template == null || "".equals(template)) {
                template = getInitParameter(defalutFile);
            }

            if (!template.startsWith("/"))
                template = "/" + template;

            /*
             * Store the template fileName in the thread, this will be retrieved
             * in WebWorkVelocityServlet for the real rendering.
             */
            ActionContext.getContext().put("template", template);

            /*
             * Add JSP support, change the dispatch name from *.jsp to *.jspt,
             * so our WebWorkJSPServlvet can catch this request.
             */
            int jspIndex = template.lastIndexOf(".jsp");
            String dispatchTemplate = template;
            if (jspIndex >= 0)
                dispatchTemplate = template.substring(0, jspIndex) + ".jspt";

            /*
             * Must put the stack into HttpServletRequest, because the WebWork
             * JSP Taglib will use it to judge whether there are some errors in
             * stack.
             */
            OgnlValueStack stack = ActionContext.getContext().getValueStack();
            request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, stack);

            PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(dispatchTemplate);
            rd.include(request, response);

            ActionContext.setContext(null);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {

        try {

            ///////////////////////////For OpenSessionInXWorkInterceptor in
            // WebWork Interceptor/////////////////////
            try {
                // javax.servlet.http.HttpServletRequestWrapper requestWrapper =
                // (javax.servlet.http.HttpServletRequestWrapper) request;
                // PortletContext.getContext().setServletContext(requestWrapper.getSession().getServletContext());

                HttpServlet servlet = JspSupportServlet.jspSupportServlet;
                if (servlet != null)
                    PortletContext.getContext().setServletContext(servlet.getServletContext());

            } catch (Exception e) {
                PortletContext.getContext().setServletConfig(((PortletConfigImpl) getPortletConfig()).getServletConfig());
            }

            /*
             * Retrieve the "wwAction" parameter from Portlet request, and parse
             * it to nameSpace & actionName for XWork ActionProxy.
             */
            String nameAction = request.getParameter(NAME_ACTION);
            String nameXAction = request.getParameter(NAME_XACTION);
            String templateFileName = request.getParameter(NAME_LINK);

            String cheatKey = "";
            String cheatValue = "";

            if (nameXAction != null && !"".equals(nameXAction)) {
                int beginIdx = nameXAction.indexOf("./");
                nameAction = nameXAction.substring(((beginIdx == -1) ? 0 : (beginIdx + 1)), nameXAction.length());

                int beginPIdx = nameAction.indexOf("?");
                int beginPEIdx = nameAction.indexOf("=");

                // TODO: Problem, right now only support one parameter after
                // xxx.action?????
                // actionXURL=actionURL+"?wwXAction=./" +
                // "xxxx.action?a=a&b=b&c=c
                // Right now only support xxxx.action?a=a
                if (beginPIdx >= 0 && beginPEIdx > 0) {
                    cheatKey = nameAction.substring(beginPIdx + 1, beginPEIdx);
                    cheatValue = nameAction.substring(beginPEIdx + 1, nameAction.length());
                }
            }

            if (nameAction == null || "".equals(nameAction)) {
                if (templateFileName == null)
                    templateFileName = "";
                request.getPortletSession().setAttribute(RENDER_TEMPLATE, templateFileName);
                //response.setRenderParameter(RENDER_TEMPLATE,
                // templateFileName);

                PortletMessaging.publish(request, "ActionContext", ActionContext.getContext());
                PortletMessaging.publish(request, "PortletContext", PortletContext.getContext());

                return;
            }

            String nameSpace = "";
            int lastIndex = nameAction.lastIndexOf("/");
            if (lastIndex > 0)
                nameSpace = nameAction.substring(0, lastIndex);
            String actionName = getActionName(nameAction);

            //Create ContextMap from Portlet request for XWork.
            Map contextMap = createContextMap(getRequestMap(request), request.getParameterMap(), getSessionMap(request),
                    getApplicationMap(request), request, response, cheatKey, cheatValue);

            contextMap.put(PORTLET_DISPATCHER, this);

            try {
                ActionProxy proxy = ActionProxyFactory.getFactory().createActionProxy(nameSpace, actionName, contextMap);
                proxy.execute();

                ActionGlobalContext.setContext(null);

            } catch (Throwable e) {
                e.printStackTrace();
            }

            PortletMessaging.publish(request, "ActionContext", ActionContext.getContext());
            PortletMessaging.publish(request, "PortletContext", PortletContext.getContext());

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private HashMap createContextMap(Map requestMap, Map parameterMap, Map sessionMap, Map applicationMap, PortletRequest request,
            PortletResponse response, String cheatKey, String cheatValue) {
        HashMap extraContext = new HashMap();

        String[] cheatValues = { cheatValue };
        if (!"".equals(cheatKey) && !"".equals(cheatValue))
            parameterMap.put(cheatKey, cheatValues);

        extraContext.put(ActionContext.PARAMETERS, parameterMap);
        extraContext.put(ActionContext.SESSION, sessionMap);
        extraContext.put(ActionContext.APPLICATION, applicationMap);
        extraContext.put(ActionContext.LOCALE, request.getLocale());

        extraContext.put(PORTLET_REQUEST, request);
        extraContext.put(PORTLET_RESPONSE, response);
        extraContext.put(PORTLET_CONTEXT, getPortletContext());

        //        extraContext.put(ComponentInterceptor.COMPONENT_MANAGER,
        // request.getAttribute("DefaultComponentManager"));

        // helpers to get access to request/session/application scope
        extraContext.put("request", requestMap);
        extraContext.put("session", sessionMap);
        extraContext.put("application", applicationMap);
        extraContext.put("parameters", parameterMap);

        AttributeMap attrMap = new AttributeMap(extraContext);
        extraContext.put("attr", attrMap);

        return extraContext;
    }

    private Map getSessionMap(PortletRequest request) {
        return new com.opensymphony.webwork.portlet.SessionMap(request);
    }

    private Map getApplicationMap(PortletRequest request) {

        Map result = new HashMap();
        PortalContext context = request.getPortalContext();
        if (context == null) {
            return result;
        }

        boolean isJetSpeed = (PortalContainer.JETSPEED_PORTAL == PortalContainer.get());
        Enumeration propNames = context.getPropertyNames();
        while (propNames.hasMoreElements()) {
            String key = (String) propNames.nextElement();
            if (key == null)
                continue;

            /*
             * Fixed for JetSpeed 2.0 (It's JetSpeed's bug!!)
             * supported.portletmode & supported.windowstate in
             * PortalContext.getProperty(key) are not the string object, but in
             * PortalContext.getProperty(key) method will cast the object to
             * string, and will throw ClassCastException
             */
            if (isJetSpeed & key != null & key.startsWith("supported.")) {
                continue;
            }

            Object value = request.getPortalContext().getProperty(key);
            result.put(key, value);
        }
        return result;
    }

    private Map getRequestMap(PortletRequest request) {
        Enumeration attNames = request.getAttributeNames();
        Map result = new HashMap();
        while (attNames.hasMoreElements()) {
            String key = (String) attNames.nextElement();
            Object value = request.getAttribute(key);
            result.put(key, value);
        }
        return result;
    }

    private String getActionName(String name) {
        // Get action name ("Foo.action" -> "Foo" action)
        int beginIdx = name.lastIndexOf("/");
        name = name.substring(((beginIdx == -1) ? 0 : (beginIdx + 1)), name.length());
        int endIdx = name.indexOf(".");

        return name.substring(0, (endIdx == -1) ? name.length() : endIdx);
    }
}