/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.views.jsp.ParameterizedTagSupport;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;


/**
 * Abstract base class for all UI tags.
 *
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public abstract class AbstractUITag extends ParameterizedTagSupport {

    protected UIBean bean;

    protected String cssClass;
    protected String cssStyle;
    protected String disabled;
    protected String label;
    protected String labelPosition;
    protected String name;
    protected String required;
    protected String tabindex;
    protected String value;
    protected String template;
    protected String theme;
    protected String onclick;
    protected String ondblclick;
    protected String onmousedown;
    protected String onmouseup;
    protected String onmouseover;
    protected String onmousemove;
    protected String onmouseout;
    protected String onfocus;
    protected String onblur;
    protected String onkeypress;
    protected String onkeydown;
    protected String onkeyup;
    protected String onselect;
    protected String onchange;

    public abstract UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res);

    public int doEndTag() throws JspException {
        bean.end(pageContext.getOut());
        bean = null;

        return EVAL_BODY_INCLUDE;
    }

    public int doStartTag() throws JspException {
        bean = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        populateParams();
        bean.addAllParameters(getParameters());
        bean.start(pageContext.getOut());

        return EVAL_PAGE;
    }

    protected void populateParams() {
        bean.setId(id);
        bean.setCssClass(cssClass);
        bean.setCssClass(cssClass);
        bean.setCssStyle(cssStyle);
        bean.setDisabled(disabled);
        bean.setLabel(label);
        bean.setLabelPosition(labelPosition);
        bean.setName(name);
        bean.setRequired(required);
        bean.setTabindex(tabindex);
        bean.setValue(value);
        bean.setTemplate(template);
        bean.setTheme(theme);
        bean.setOnclick(onclick);
        bean.setOndblclick(ondblclick);
        bean.setOnmousedown(onmousedown);
        bean.setOnmouseup(onmouseup);
        bean.setOnmouseover(onmouseover);
        bean.setOnmousemove(onmousemove);
        bean.setOnmouseout(onmouseout);
        bean.setOnfocus(onfocus);
        bean.setOnblur(onblur);
        bean.setOnkeypress(onkeypress);
        bean.setOnkeydown(onkeydown);
        bean.setOnkeyup(onkeyup);
        bean.setOnselect(onselect);
        bean.setOnchange(onchange);
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    /**
     * @deprecated please use setLabelPosition instead
     */
    public void setLabelposition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRequired(String required) {
        this.required = required;
    }

    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }
}
