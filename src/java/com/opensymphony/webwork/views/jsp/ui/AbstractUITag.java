/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.views.jsp.ComponentTagSupport;


/**
 * Abstract base class for all UI tags.
 *
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public abstract class AbstractUITag extends ComponentTagSupport {
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


    protected void populateParams() {
        super.populateParams();

        UIBean uiBean = (UIBean) component;
        uiBean.setCssClass(cssClass);
        uiBean.setCssClass(cssClass);
        uiBean.setCssStyle(cssStyle);
        uiBean.setDisabled(disabled);
        uiBean.setLabel(label);
        uiBean.setLabelPosition(labelPosition);
        uiBean.setName(name);
        uiBean.setRequired(required);
        uiBean.setTabindex(tabindex);
        uiBean.setValue(value);
        uiBean.setTemplate(template);
        uiBean.setTheme(theme);
        uiBean.setOnclick(onclick);
        uiBean.setOndblclick(ondblclick);
        uiBean.setOnmousedown(onmousedown);
        uiBean.setOnmouseup(onmouseup);
        uiBean.setOnmouseover(onmouseover);
        uiBean.setOnmousemove(onmousemove);
        uiBean.setOnmouseout(onmouseout);
        uiBean.setOnfocus(onfocus);
        uiBean.setOnblur(onblur);
        uiBean.setOnkeypress(onkeypress);
        uiBean.setOnkeydown(onkeydown);
        uiBean.setOnkeyup(onkeyup);
        uiBean.setOnselect(onselect);
        uiBean.setOnchange(onchange);
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setLabelPosition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * @deprecated please use setLabelPosition instead
     */
    public void setLabelposition(String labelPosition) {
        this.labelPosition = labelPosition;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setRequired(String required) {
        this.required = required;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setTabindex(String tabindex) {
        this.tabindex = tabindex;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setTheme(String theme) {
        this.theme = theme;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnclick(String onclick) {
        this.onclick = onclick;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOndblclick(String ondblclick) {
        this.ondblclick = ondblclick;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnmousedown(String onmousedown) {
        this.onmousedown = onmousedown;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnmouseup(String onmouseup) {
        this.onmouseup = onmouseup;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnmouseover(String onmouseover) {
        this.onmouseover = onmouseover;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnmousemove(String onmousemove) {
        this.onmousemove = onmousemove;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnmouseout(String onmouseout) {
        this.onmouseout = onmouseout;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnfocus(String onfocus) {
        this.onfocus = onfocus;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnblur(String onblur) {
        this.onblur = onblur;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnkeypress(String onkeypress) {
        this.onkeypress = onkeypress;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnkeydown(String onkeydown) {
        this.onkeydown = onkeydown;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnkeyup(String onkeyup) {
        this.onkeyup = onkeyup;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnselect(String onselect) {
        this.onselect = onselect;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setOnchange(String onchange) {
        this.onchange = onchange;
    }
}
