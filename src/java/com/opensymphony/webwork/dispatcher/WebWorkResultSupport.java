/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.webwork.WebWorkStatics;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;

import java.io.IOException;

import javax.servlet.ServletException;


/**
 * WebWorkResultSupport
 * @author Jason Carreira
 * Created Oct 1, 2003 10:53:57 AM
 */
public abstract class WebWorkResultSupport implements Result, WebWorkStatics {
    //~ Static fields/initializers /////////////////////////////////////////////

    public static final String DEFAULT_PARAM = "location";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected boolean parse = true;
    private String location;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setLocation(String location) {
        this.location = location;
    }

    public void setParse(boolean parse) {
        this.parse = parse;
    }

    public void execute(ActionInvocation invocation) throws Exception {
        String finalLocation = location;

        if (parse) {
            OgnlValueStack stack = ActionContext.getContext().getValueStack();
            finalLocation = TextParseUtil.translateVariables(location, stack);
        }

        doExecute(finalLocation, invocation);
    }

    protected abstract void doExecute(String finalLocation, ActionInvocation invocation) throws Exception;
}
