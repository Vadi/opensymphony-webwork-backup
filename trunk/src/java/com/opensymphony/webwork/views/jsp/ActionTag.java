/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.dispatcher.ApplicationMap;
import com.opensymphony.webwork.dispatcher.RequestMap;
import com.opensymphony.webwork.dispatcher.ServletDispatcher;
import com.opensymphony.webwork.dispatcher.SessionMap;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;


/**
 * ActionTag enables developers to call Actions directly from a JSP page by specifying the Action name and an optional
 * namespace.  The body content of the tag is used to render the results from the Action.  Any Result processor defined
 * for this Action in xwork.xml will be ignored.
 *
 * @version $Id$
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 */
public class ActionTag extends ParameterizedTagSupport implements WebWorkStatics {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(ActionTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    ActionProxy proxy;
    String name;
    String namespace;
    boolean executeResult;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * If set to true the result of an action will be executed.
     * @param executeResult
     */
    public void setExecuteResult(boolean executeResult) {
        this.executeResult = executeResult;
    }

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

    public int doEndTag() throws JspException {
        try {
            // execute the action and save the proxy (and the namespace) as instance variables
            executeAction();

            if (getId() != null) {
                pageContext.setAttribute(getId(), proxy.getAction());
            }

            return SKIP_BODY;
        } finally {
            // clean up after ourselves to allow this tag to be reused
            this.reset();
        }
    }

    public int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

   String buildNamespace() {
        String namespace = "";
        ActionInvocation invocation = null;
        ActionContext context = new ActionContext(getStack().getContext());

        if (context != null) {
            invocation = context.getActionInvocation();
        }

        if (invocation == null) {
            // Path is always original path, even if it is included in page with another path
            HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();

            String actionPath = request.getServletPath();
            namespace = ServletDispatcher.getNamespaceFromServletPath(actionPath);
        } else {
            namespace = invocation.getProxy().getNamespace();
        }

        return namespace;
    }

    private Map createExtraContext() {
        Map parentParams = new ActionContext(getStack().getContext()).getParameters();
        Map newParams = (parentParams != null) ? new HashMap(parentParams) : new HashMap();

        if (params != null) {
            newParams.putAll(params);
        }

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        ServletConfig servletConfig = pageContext.getServletConfig();
        ServletContext servletContext = pageContext.getServletContext();

        Map extraContext = ServletDispatcher.createContextMap(new RequestMap(request), newParams, new SessionMap(request.getSession()), new ApplicationMap(servletContext), request, response, servletConfig);
        extraContext.put(PAGE_CONTEXT, pageContext);

        OgnlValueStack vs = ActionContext.getContext().getValueStack();
        extraContext.put(ActionContext.VALUE_STACK, vs);

        return extraContext;
    }

    /**
     * execute the requested action.  if no
     * namespace is provided, we'll attempt to derive a namespace using buildNamespace().  the ActionProxy and the
     * namespace will be saved into the instance variables proxy and namespace respectively.
     * @see #buildNamespace
     */
    private void executeAction() throws JspException {
        if (namespace == null) {
            namespace = buildNamespace();
        }

        OgnlValueStack stack = getStack();
        String actualName = findString(name);

        if (actualName == null) {
            throw new JspException("Unable to find value for name " + name);
        }

        // execute at this point, after params have been set
        try {
            Object top = null;
            ActionContext actionContext = ActionContext.getContext();

            if ((stack != null) && (stack.size() > 0)) {
                top = stack.peek();
            }

            proxy = ActionProxyFactory.getFactory().createActionProxy(namespace, actualName, createExtraContext(), executeResult);
            proxy.execute();

            if (actionContext != null) {
                if ((stack != null) && (stack.size() > 1)) {
                    Object newTop = stack.peek();

                    while (!(newTop == null) && !newTop.equals(top)) {
                        stack.pop();
                        newTop = stack.peek();
                    }
                }
            }
        } catch (Exception e) {
            log.error("Could not execute action: " + namespace + "/" + actualName, e);
        }

        if (getId() != null) {
            getStack().getContext().put(getId(), proxy.getAction());
        }
    }
}
