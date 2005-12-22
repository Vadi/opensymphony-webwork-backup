package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Renders parts of the HEAD section for an HTML file. This is useful as some themes require certain CSS and JavaScript
 * includes.
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:head/&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @ww.tag name="head" tld-body-content="empty" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.HeadTag"
 * description="Render a chunk of HEAD for your HTML file"
 * @since 2.2
 */
public class Head extends UIBean {
    final public static String TEMPLATE = "head";

    public Head(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}
