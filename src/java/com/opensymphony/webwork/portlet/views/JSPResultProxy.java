/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.views;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn"> Henry Hu </a>
 * @since 2005-7-6
 */
public class JSPResultProxy extends AbstractResult {

    public JSPResultProxy() {
        super();
    }

    protected void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String location = (String) ActionContext.getContext().get("template");

        OgnlValueStack stack = ActionContext.getContext().getValueStack();

        RequestDispatcher dispatcher = request.getRequestDispatcher(location);

        // if the view doesn't exist, let's do a 404
        if (dispatcher == null) {
            response.sendError(404, "result '" + location + "' not found");
            return;
        }

        dispatcher.include(request, response);

    }

}