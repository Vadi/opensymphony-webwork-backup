/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.dispatcher.ApplicationMap;
import com.opensymphony.webwork.dispatcher.ServletDispatcher;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.webwork.views.velocity.Renderer;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import ognl.Ognl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.context.Context;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;


/**
 * ActionTag enables developers to call Actions directly from a JSP page by specifying the Action name and an optional
 * namespace.  The body content of the tag is used to render the results from the Action.  Any Result processor defined
 * for this Action in xwork.xml will be ignored.
 *
 * @version $Id$
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 */
public class ActionTag extends TagSupport implements WebWorkStatics, ParameterizedTag, Renderer {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(ActionTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    ActionProxy proxy;
    Map params;
    String name;
    String namespace;
    boolean executeResult;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Sets the name of the action to be invoked
     * @param name the name of the Action as defined in the xwork.xml file
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the namespace for the action.  If null, this will default to "default"
     * @param namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * If set to true the result of an action will be executed.
     * @param executeResult
     */
    public void setExecuteResult(boolean executeResult) {
        this.executeResult = executeResult;
    }

    public Map getParams() {
        return params;
    }

    public void addParam(String key, Object value) {
        if (params == null) {
            params = new HashMap();
        }

        params.put(key, value);
    }

    public int doEndTag() throws JspException {
        // execute the action and save the proxy (and the namespace) as instance variables
        executeAction();

        pageContext.setAttribute(getId(), proxy.getAction());

        return EVAL_PAGE;
    }

    public int doStartTag() throws JspException {
        /**
         * Migrated instantiation of the params HashMap to here from the constructor to facilitate implementation of the
         * release() method.
         */
        this.params = new HashMap();

        return EVAL_BODY_INCLUDE;
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        this.proxy = null;
        this.params = null;
        this.name = null;
        this.namespace = null;
    }

    /**
     * executes the action under Velocity
     * <p>
     *   <i>com.opensymphony.webwork.views.velocity.Renderer implementation</i>
     * </p>
     *
     * @param context
     * @param writer
     * @throws Exception
     */
    public void render(Context context, Writer writer) throws Exception {
        executeAction();
    }

    String buildNamespace() {
        String namespace = "";
        ActionInvocation invocation = null;
        ActionContext context = ActionContext.getContext();

        if (context != null) {
            invocation = context.getActionInvocation();
        }

        if (invocation == null) {
            // Path is always original path, even if it is included in page with another path
            HttpServletRequest request = ServletActionContext.getRequest();

            if ((request == null) && (pageContext != null)) {
                request = (HttpServletRequest) pageContext.getRequest();
            }

            String actionPath = request.getServletPath();
            namespace = ServletDispatcher.getNamespaceFromServletPath(actionPath);
        } else {
            namespace = invocation.getProxy().getNamespace();
        }

        return namespace;
    }

    private Map createExtraContext() {
        Map extraContext = Ognl.createDefaultContext(this);

        // Leave the ValueStack out -- We're not processing inside the tag
        //        OgnlValueStack vs = ActionContext.getContext().getValueStack();
        //        extraContext.put(ActionContext.VALUE_STACK, vs);
        Map parentParams = ActionContext.getContext().getParameters();
        Map newParams = (parentParams != null) ? new HashMap(parentParams) : new HashMap();

        if (params != null) {
            newParams.putAll(params);
        }

        extraContext.put(ActionContext.PARAMETERS, newParams);

        HttpServletRequest request = null;
        HttpServletResponse response = null;
        ServletConfig servletConfig = null;
        ServletContext servletContext = null;

        if (pageContext != null) {
            request = (HttpServletRequest) pageContext.getRequest();
            response = (HttpServletResponse) pageContext.getResponse();
            servletConfig = pageContext.getServletConfig();
            servletContext = pageContext.getServletContext();
        } else {
            request = ServletActionContext.getRequest();
            response = ServletActionContext.getResponse();
            servletConfig = ServletActionContext.getServletConfig();
            servletContext = ServletActionContext.getServletContext();
        }

        extraContext.put(ActionContext.APPLICATION, new ApplicationMap(servletContext));
        extraContext.put(ActionContext.SESSION, new SessionMap(request.getSession()));
        extraContext.put(HTTP_REQUEST, request);
        extraContext.put(HTTP_RESPONSE, response);
        extraContext.put(SERVLET_CONFIG, servletConfig);
        extraContext.put(COMPONENT_MANAGER, request.getAttribute("DefaultComponentManager"));

        return extraContext;
    }

    /**
     * execute the requested action.  if no
     * namespace is provided, we'll attempt to derive a namespace using buildNamespace().  the ActionProxy and the
     * namespace will be saved into the instance variables proxy and namespace respectively.
     * @see #buildNamespace
     */
    private void executeAction() {
        if (namespace == null) {
            namespace = buildNamespace();
        }

        // execute at this point, after params have been set
        try {
            proxy = ActionProxyFactory.getFactory().createActionProxy(namespace, name, createExtraContext(), executeResult);
            proxy.execute();
        } catch (Exception e) {
            log.error("Could not execute action: " + namespace + "/" + name, e);
        }

        if (getId() != null) {
            ActionContext.getContext().put(getId(), proxy.getAction());
        }
    }
}
