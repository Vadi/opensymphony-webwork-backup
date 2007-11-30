/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.WebWorkResultSupport;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * An abstract class that will auto populate the http response header of the current http request.
 * Subclass would want to override {@link #afterHttpHeadersPopulatedExecute(String, com.opensymphony.xwork.ActionInvocation)}
 * which would be called, after the http headers are being populated into {@link javax.servlet.http.HttpServletResponse}.
 * Normally, we'd do
 * <pre>
 *    ActionContext.getContext().get(ServletActionContext.HTTP_RESPONSE);
 * </pre>
 * or
 * <pre>
 *    ServetActionContext.getResponse();
 * </pre>
 * to get hold of {@link javax.servlet.http.HttpServletResponse}.
 *
 *
 * @see HttpHeaderResult
 * @see JasperReportsResult
 *
 * @author tmjee
 * @version $Date$ $Id$
 */
public abstract class AbstractHttpHeaderPopulatingResult extends WebWorkResultSupport {

    private Map headers;
    
    /**
     * Returns a Map of all HTTP headers.
     *
     * @return a Map of all HTTP headers.
     */
    public Map getHeaders() {
        if (headers == null) {
            headers = new HashMap();
        }

        return headers;
    }

    /**
     * Sets the optional HTTP response status code and also re-sets HTTP headers after they've
     * been optionally evaluated against the ValueStack.
     *
     * @param finalLocation 
     * @param invocation an encapsulation of the action execution state.
     * @throws Exception if an error occurs when re-setting the headers.
     */
    public void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
        HttpServletResponse response = ServletActionContext.getResponse();

        if (headers != null) {
            OgnlValueStack stack = ActionContext.getContext().getValueStack();

            for (Iterator iterator = headers.entrySet().iterator();
                 iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String value = (String) entry.getValue();
                String finalValue = conditionalParse(value, invocation);
                response.addHeader((String) entry.getKey(), finalValue);
            }
        }

        afterHttpHeadersPopulatedExecute(finalLocation, invocation);
    }

    /**
     * This method is meant for subclass to override, it is called after the http headers
     * have been set into the current http response.
     *
     * @param finalLocation
     * @param invocation
     * @throws Exception
     */
   protected abstract void afterHttpHeadersPopulatedExecute(String finalLocation, ActionInvocation invocation) throws Exception;
}
