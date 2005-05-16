package com.opensymphony.webwork.views.util;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.webwork.views.jsp.ui.OgnlTool;
import com.opensymphony.webwork.util.WebWorkUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * User: plightbo
 * Date: May 15, 2005
 * Time: 6:02:38 PM
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
        map.put(SESSION, req.getSession());
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
}
