package com.opensymphony.webwork.dispatcher;

import junit.framework.TestCase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mockobjects.dynamic.Mock;
import com.mockobjects.dynamic.C;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.ServletActionContext;

import java.util.Map;
import java.util.HashMap;

/**
 * HttpHeaderResultTest
 * @author Jason Carreira
 * Date: Nov 16, 2003 1:08:20 AM
 */
public class HttpHeaderResultTest extends TestCase {
    Mock responseMock;
    HttpServletResponse response;
    ActionInvocation invocation;
    HttpHeaderResult result;

    protected void setUp() throws Exception {
        super.setUp();
        result = new HttpHeaderResult();
        responseMock = new Mock(HttpServletResponse.class);
        response = (HttpServletResponse) responseMock.proxy();
        invocation = (ActionInvocation) new Mock(ActionInvocation.class).proxy();
        ServletActionContext.setResponse(response);
    }

    public void testStatusIsSet() throws Exception {
        responseMock.expect("setStatus", C.eq(123));
        result.setStatus(123);
        result.execute(invocation);
        responseMock.verify();
    }

    public void testHeaderValuesAreParsedAndSet() throws Exception {
        Map params = new HashMap();
        params.put("headers.foo", "${bar}");
        params.put("headers.baz", "baz");

        Map values = new HashMap();
        values.put("bar", "abc");
        ActionContext.getContext().getValueStack().push(values);

        OgnlUtil.setProperties(params, result);

        responseMock.expect("addHeader", C.args(C.eq("foo"), C.eq("abc")));
        responseMock.expect("addHeader", C.args(C.eq("baz"), C.eq("baz")));
        result.execute(invocation);
        responseMock.verify();
    }

    public void testHeaderValuesAreNotParsedWhenParseIsFalse() throws Exception {
        Map params = new HashMap();
        params.put("headers.foo", "${bar}");
        params.put("headers.baz", "baz");

        Map values = new HashMap();
        values.put("bar", "abc");
        ActionContext.getContext().getValueStack().push(values);

        OgnlUtil.setProperties(params, result);

        responseMock.expect("addHeader", C.args(C.eq("foo"), C.eq("${bar}")));
        responseMock.expect("addHeader", C.args(C.eq("baz"), C.eq("baz")));
        result.setParse(false);
        result.execute(invocation);
        responseMock.verify();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
        ServletActionContext.setResponse(null);
        ActionContext.getContext().setValueStack(new OgnlValueStack());
    }
}