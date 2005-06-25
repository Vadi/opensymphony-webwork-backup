/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.dispatcher.ApplicationMap;
import com.opensymphony.webwork.dispatcher.DispatcherUtils;
import com.opensymphony.webwork.dispatcher.RequestMap;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import java.util.HashMap;
import java.util.Map;


/**
 * ActionTag enables developers to call Actions directly from a JSP page by specifying the Action name and an optional
 * namespace.  The body content of the tag is used to render the results from the Action.  Any Result processor defined
 * for this Action in xwork.xml will be ignored.
 *
 * @author <a href="mailto:plightbo@hotmail.com">Pat Lightbody</a>
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 * @version $Id$
 */
public class ActionTag extends ParameterizedTagSupport implements WebWorkStatics {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(ActionTag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    ActionProxy proxy;
    String name;
    String namespaceAttr;
    boolean executeResult;
    boolean ignoreContextParams;

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * If set to true the result of an action will be executed.
     */
    public void setExecuteResult(boolean executeResult) {
        this.executeResult = executeResult;
    }

    /**
     * If set to true, the PARAMETERS map from the original context will be ignored
     */
    public void setIgnoreContextParams(boolean ignoreContextParams) {
        this.ignoreContextParams = ignoreContextParams;
    }

    /**
     * Sets the name of the action to be invoked
     *
     * @param name the name of the Action as defined in the xwork.xml file
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets the namespace for the action.  If null, this will default to "default"
     */
    public void setNamespace(String namespaceAttr) {
        this.namespaceAttr = namespaceAttr;
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

    private Map createExtraContext() {
        Map parentParams = null;

        if (!ignoreContextParams) {
            parentParams = new ActionContext(getStack().getContext()).getParameters();
        }

        Map newParams = (parentParams != null) ? new HashMap(parentParams) : new HashMap();

        if (params != null) {
            newParams.putAll(params);
        }

        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        HttpServletResponse response = (HttpServletResponse) pageContext.getResponse();
        ServletContext servletContext = pageContext.getServletContext();

        DispatcherUtils.initialize(servletContext);
        DispatcherUtils du = DispatcherUtils.getInstance();
        Map extraContext = du.createContextMap(new RequestMap(request),
                newParams,
                new SessionMap(request),
                new ApplicationMap(servletContext),
                request,
                response,
                servletContext);
        extraContext.put(PAGE_CONTEXT, pageContext);

        OgnlValueStack vs = getStack();
        OgnlValueStack newStack = new OgnlValueStack(vs);
        extraContext.put(ActionContext.VALUE_STACK, newStack);

        return extraContext;
    }

    /**
     * Execute the requested action.  If no namespace is provided, we'll
     * attempt to derive a namespace using buildNamespace().  The ActionProxy
     * and the namespace will be saved into the instance variables proxy and
     * namespace respectively.
     *
     * @see com.opensymphony.webwork.views.jsp.TagUtils#buildNamespace
     */
    private void executeAction() throws JspException {
        String actualName = findString(name);

        if (actualName == null) {
            throw new JspException("Unable to find value for name " + name);
        }

        String namespace;

        final ServletRequest request = pageContext.getRequest();
        if (namespaceAttr == null) {
            namespace = TagUtils.buildNamespace(getStack(), (HttpServletRequest) request);
        } else {
            namespace = findString(namespaceAttr);
        }

        // get the old value stack from the request
        OgnlValueStack stack = getStack();
        // execute at this point, after params have been set
        try {
            proxy = ActionProxyFactory.getFactory().createActionProxy(namespace, actualName, createExtraContext(), executeResult);
            // set the new stack into the request for the taglib to use
            request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, proxy.getInvocation().getStack());
            proxy.execute();

        } catch (Exception e) {
            log.error("Could not execute action: " + namespace + "/" + actualName, e);
        } finally {
            // set the old stack back on the request
            request.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, stack);
        }

        if (getId() != null) {
            final Map context = stack.getContext();
            context.put(getId(), proxy.getAction());
        }
    }
}
