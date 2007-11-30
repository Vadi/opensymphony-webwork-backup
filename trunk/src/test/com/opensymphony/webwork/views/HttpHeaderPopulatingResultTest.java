/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.mock.MockActionInvocation;
import org.springframework.mock.web.MockHttpServletResponse;

import java.util.HashMap;

/**
 * A test case for {@link com.opensymphony.webwork.views.AbstractHttpHeaderPopulatingResult}.
 *
 * @author tmjee
 * @version $Date$ $Id$
 */
public class HttpHeaderPopulatingResultTest extends WebWorkTestCase {

    public void test() throws Exception {
        AbstractHttpHeaderPopulatingResult result = new AbstractHttpHeaderPopulatingResult() {
            protected void afterHttpHeadersPopulatedExecute(String finalLocation, ActionInvocation invocation) throws Exception {
                // do nothing
            }
        };


        MockHttpServletResponse response = new MockHttpServletResponse();
        MockActionInvocation actionInvocation = new MockActionInvocation();
        actionInvocation.setInvocationContext(ActionContext.getContext());
        actionInvocation.setStack(ActionContext.getContext().getValueStack());
        actionInvocation.getInvocationContext().put(ServletActionContext.HTTP_RESPONSE, response);


        result.getHeaders().put("header1", "header_value_1");
        result.getHeaders().put("Content-Type", "text/html");
        result.getHeaders().put("Cache-Control", "cache, must-revalidate");
        result.getHeaders().put("Pragma", "public");
        result.execute(actionInvocation);

        assertEquals(response.getHeader("header1"), "header_value_1");
        assertEquals(response.getHeader("Content-Type"), "text/html");
        assertEquals(response.getHeader("Cache-Control"), "cache, must-revalidate");
        assertEquals(response.getHeader("Pragma"), "public");
        assertEquals(response.getHeader("no-such-header"), null);
    }


}
