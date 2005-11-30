/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.util;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.webwork.views.jsp.ui.OgnlTool;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.WebWorkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Value Stack's Context related Utilities.
 * 
 * @author plightbo
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class ContextUtil {
    public static final String REQUEST = "req";
    public static final String REQUEST2 = "request";
    public static final String RESPONSE = "res";
    public static final String RESPONSE2 = "response";
    public static final String SESSION = "session";
    public static final String BASE = "base";
    public static final String STACK = "stack";
    public static final String OGNL = "ognl";
    public static final String WEBWORK = "webwork";
    public static final String ACTION = "action";

    public static Map getStandardContext(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        HashMap map = new HashMap();
        map.put(REQUEST, req);
        map.put(REQUEST2, req);
        map.put(RESPONSE, res);
        map.put(RESPONSE2, res);
        map.put(SESSION, req.getSession(false));
        map.put(BASE, req.getContextPath());
        map.put(STACK, stack);
        map.put(OGNL, OgnlTool.getInstance());
        map.put(WEBWORK, new WebWorkUtil(stack, req, res));

        ActionInvocation invocation = (ActionInvocation) stack.getContext().get(ActionContext.ACTION_INVOCATION);
        if (invocation != null) {
            map.put(ACTION, invocation.getAction());
        }
        return map;
    }
    
    public static boolean isUseAltSyntax(Map context) {
        // We didn't make altSyntax static cause, if so, webwork.configuration.xml.reload will not work
        // plus the Configuration implementation should cache the properties, which WW's
        // configuration implementation does
        boolean altSyntax = "true".equals(Configuration.getString("webwork.tag.altSyntax"));
        return altSyntax ||(
                (context.containsKey("useAltSyntax") && 
                        context.get("useAltSyntax") != null &&
                        "true".equals(context.get("useAltSyntax").toString())? true : false));
    }
}
