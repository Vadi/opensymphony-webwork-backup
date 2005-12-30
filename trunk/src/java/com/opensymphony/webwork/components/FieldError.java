/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import com.opensymphony.webwork.components.Param.UnnamedParametric;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Create a list ( &lt;ul&gt; and &lt;li&gt; ) of action messages if they exists.
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 *    &lt;!-- example 1 --&gt;
 *    &lt;ww:fielderror /&gt;
 *
 *    &lt;!-- example 2 --&gt;
 *    &lt;ww:fielderror&gt;
 *         &lt;ww:param&gt;field1&lt;/ww:param&gt;
 *         &lt;ww:param&gt;field2&lt;/ww:param&gt;
 *    &lt;/ww:fielderror&gt;
 *
 *    OR
 *
 *    &lt;ww:fielderror&gt;
 *    		&lt;ww:param value="%{'field1'}" /&gt;
 *    		&lt;ww:param value="%{'field2'}" /&gt;
 *    &lt;/ww:fielderror&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 *
 * <p/> <b>Description</b><p/>
 *
 *
 * <!-- START SNIPPET: description -->
 *
 * Example 1: display all field errors<p/> Example 2: display field errors only for 'field1' and 'field2'<p/>
 *
 * <!-- END SNIPPET: description -->
 *
 * @author tm_jee
 * @version $Date$ $Id$
 * @ww.tag name="fielderror" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.FieldErrorTag"
 * description="Render field error (all or partial depending on param tag nested)if they exists"
 */
public class FieldError extends UIBean implements UnnamedParametric {

    private List errorFieldNames = new ArrayList();

    public FieldError(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    private static final String TEMPLATE = "fielderror";

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void addParameter(Object value) {
        if (value != null) {
            errorFieldNames.add(value.toString());
        }
    }

    public List getFieldErrorFieldNames() {
        return errorFieldNames;
    }
}

