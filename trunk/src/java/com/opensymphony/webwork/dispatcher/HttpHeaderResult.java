/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import com.opensymphony.xwork.Result;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;
import com.opensymphony.webwork.ServletActionContext;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

/**
 * HttpHeaderResult
 * @author Jason Carreira
 * Date: Nov 16, 2003 12:12:44 AM
 */
public class HttpHeaderResult implements Result {
    public static final String DEFAULT_PARAM = "status";

    protected boolean parse = true;
    private int status = -1;
    private Map headers;

    /**
     * If parse is true (the default) the header values will be evaluated against the ValueStack
     * @param parse
     */
    public void setParse(boolean parse) {
        this.parse = parse;
    }

    /**
     * If set this int value will be set using HttpServletResponse.setStatus(int status)
     * @param status the Http status code
     */
    public void setStatus(int status) {
        this.status = status;
    }

    public Map getHeaders() {
        if (headers == null) {
            headers = new HashMap();
        }
        return headers;
    }

    /**
     * Represents a generic interface for all action execution results, whether that be displaying a webpage, generating
     * an email, sending a JMS message, etc.
     */
    public void execute(ActionInvocation invocation) throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();
        if (status != -1) {
            response.setStatus(status);
        }
        if (headers != null) {
            OgnlValueStack stack = ActionContext.getContext().getValueStack();
            for (Iterator iterator = headers.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String value = (String)entry.getValue();
                String finalValue = parse ? TextParseUtil.translateVariables(value, stack) : value;
                response.addHeader((String)entry.getKey(),finalValue);
            }
        }
    }


}
