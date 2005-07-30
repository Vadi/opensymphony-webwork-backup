package com.opensymphony.webwork.components.template;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.PageContext;
import java.util.Map;
import java.io.Writer;

/**
 * TemplateRenderingContext
 * Date: Sep 28, 2004 2:30:28 PM
 *
 * @author jcarreira
 */
public class TemplateRenderingContext {
    String templateName;
    OgnlValueStack stack;
    Map parameters;
    UIBean tag;
    Writer writer;

    public TemplateRenderingContext(String templateName, Writer writer, OgnlValueStack stack, Map params, UIBean tag) {
        this.templateName = templateName;
        this.writer = writer;
        this.stack = stack;
        this.parameters = params;
        this.tag = tag;
    }

    public String getTemplateName() {
        return templateName;
    }

    public OgnlValueStack getStack() {
        return stack;
    }

    public Map getParameters() {
        return parameters;
    }

    public UIBean getTag() {
        return tag;
    }

    public Writer getWriter() {
        return writer;
    }
}
