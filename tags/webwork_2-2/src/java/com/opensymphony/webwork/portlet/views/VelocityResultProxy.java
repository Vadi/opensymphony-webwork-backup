/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.views;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn"> Henry Hu </a>
 * @since 2005-7-6
 */
public class VelocityResultProxy extends AbstractResult {

    private com.opensymphony.webwork.dispatcher.VelocityResult vr;

    public VelocityResultProxy() {
        super();
        vr = new com.opensymphony.webwork.dispatcher.VelocityResult();
    }

    // ////////////////////////////////////////////////////////////////

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // save the old PageContext
        PageContext oldPageContext = ServletActionContext.getPageContext();
        ServletActionContext.setRequest(request);
        ServletActionContext.setResponse(response);

        String location = (String) ActionContext.getContext().get("template");
        ActionInvocation invocation = ActionContext.getContext().getActionInvocation();
        try {
            vr.doExecute(location, invocation);
        } catch (Exception e) {
        }

        //restore the old PageContext
        ActionContext.getContext().put(ServletActionContext.PAGE_CONTEXT, oldPageContext);

    }

}