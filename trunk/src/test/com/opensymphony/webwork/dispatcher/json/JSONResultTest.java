/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.json;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.mock.MockActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.springframework.mock.web.MockHttpServletResponse;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class JSONResultTest extends WebWorkTestCase {

    public void test1() throws Exception {
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final JSONObject jsonObject = new JSONObject();
        JSONResult result = new JSONResult() {

            protected HttpServletResponse getServletResponse(ActionInvocation invocation) {
                return response;
            }

            protected JSONObject getJSONObject(ActionInvocation invocation) throws JSONException {
                jsonObject.put("name", "tmjee");
                jsonObject.put("age", "22");
                return jsonObject;
            }
        };

        result.execute(new MockActionInvocation());


        assertEquals(response.getContentType(), "application/json");
        assertEquals(new String(response.getContentAsByteArray()), jsonObject.toString());
    }

    public void test2() throws Exception {
        final MockHttpServletResponse response = new MockHttpServletResponse();
        final JSONObject jsonObject = new JSONObject();
        JSONResult result = new JSONResult() {

            protected HttpServletResponse getServletResponse(ActionInvocation invocation) {
                return response;
            }

            protected JSONObject getJSONObject(ActionInvocation invocation) throws JSONException {
                jsonObject.put("name", "tmjee");
                jsonObject.put("age", "22");
                return jsonObject;
            }
        };

        result.setContentType("xapplication/json");
        result.execute(new MockActionInvocation());


        assertEquals(response.getContentType(), "xapplication/json");
        assertEquals(new String(response.getContentAsByteArray()), jsonObject.toString());
    }

    public void test3() throws Exception {

        final JSONObject jsonObject = new JSONObject();


        Action action = new ActionSupport() {
            public JSONObject getMyJsonObject() throws JSONException {
                jsonObject.put("name", "tmjee");
                jsonObject.put("age", "22");
                return jsonObject;
            }
        };

        OgnlValueStack valueStack = new OgnlValueStack();
        valueStack.push(action);

        ActionContext actionContext = new ActionContext(new HashMap());
        actionContext.setValueStack(valueStack);

        MockActionInvocation invocation = new MockActionInvocation();
        invocation.setInvocationContext(actionContext);


        final MockHttpServletResponse response = new MockHttpServletResponse();
        JSONResult result = new JSONResult() {

            protected HttpServletResponse getServletResponse(ActionInvocation invocation) {
                return response;
            }
        };
        result.setJSONObjectProperty("myJsonObject");
        result.execute(invocation);


        assertEquals(response.getContentType(), "application/json");
        assertEquals(new String(response.getContentAsByteArray()), jsonObject.toString());
    }

}
