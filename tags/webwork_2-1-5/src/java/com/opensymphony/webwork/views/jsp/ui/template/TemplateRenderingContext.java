package com.opensymphony.webwork.views.jsp.ui.template;

import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.PageContext;
import java.util.Map;

/**
 * TemplateRenderingContext
 * Date: Sep 28, 2004 2:30:28 PM
 *
 * @author jcarreira
 */
public class TemplateRenderingContext {
    String templateName;
    PageContext pageContext;
    OgnlValueStack stack;
    Map parameters;
    AbstractUITag tag;

    public TemplateRenderingContext(String templateName, PageContext pageContext, OgnlValueStack stack, Map params, AbstractUITag tag) {
        this.templateName = templateName;
        this.pageContext = pageContext;
        this.stack = stack;
        this.parameters = params;
        this.tag = tag;
    }

    public String getTemplateName() {
        return templateName;
    }

    public PageContext getPageContext() {
        return pageContext;
    }

    public OgnlValueStack getStack() {
        return stack;
    }

    public Map getParameters() {
        return parameters;
    }

    public AbstractUITag getTag() {
        return tag;
    }
}
