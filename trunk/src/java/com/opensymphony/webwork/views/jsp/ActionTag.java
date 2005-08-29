/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.webwork.components.ActionComponent;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @see ActionComponent
 */
public class ActionTag extends ParameterizedTagSupport implements WebWorkStatics {
    protected ActionComponent component;

    protected String name;
    protected String namespace;
    protected boolean executeResult;
    protected boolean ignoreContextParams;

    public int doEndTag() throws JspException {
        component.addAllParameters(getParameters());
        component.end(pageContext.getOut());
        pageContext.setAttribute(getId(), component.getProxy().getAction());

        return SKIP_BODY;
    }

    public int doStartTag() throws JspException {
        component = new ActionComponent(getStack(),
                (HttpServletRequest) pageContext.getRequest(),
                (HttpServletResponse) pageContext.getResponse());
        component.setId(id);
        component.setName(name);
        component.setNamespace(namespace);
        component.setExecuteResult(executeResult);
        component.setIgnoreContextParams(ignoreContextParams);
        component.start(pageContext.getOut());

        return EVAL_BODY_INCLUDE;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setExecuteResult(boolean executeResult) {
        this.executeResult = executeResult;
    }

    public void setIgnoreContextParams(boolean ignoreContextParams) {
        this.ignoreContextParams = ignoreContextParams;
    }
}
