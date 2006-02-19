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
 * @author tm_jee
 */
public abstract class AbstractUITag extends ComponentTagSupport {
    protected String cssClass;
    protected String cssStyle;
    protected String title;
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
    
    // tooltip attributes
    protected String tooltip;
    protected String tooltipIcon;
    protected String tooltipAboveMousePointer;
    protected String tooltipBgColor;
    protected String tooltipBgImg;
    protected String tooltipBorderWidth;
    protected String tooltipBorderColor;
    protected String tooltipDelay;
    protected String tooltipFixCoordinate;
    protected String tooltipFontColor;
    protected String tooltipFontFace;
    protected String tooltipFontSize;
    protected String tooltipFontWeight;
    protected String tooltipLeftOfMousePointer;
    protected String tooltipOffsetX;
    protected String tooltipOffsetY;
    protected String tooltipOpacity;
    protected String tooltipPadding;
    protected String tooltipShadowColor;
    protected String tooltipShadowWidth;
    protected String tooltipStatic;
    protected String tooltipSticky;
    protected String tooltipStayAppearTime;
    protected String tooltipTextAlign;
    protected String tooltipTitle;
    protected String tooltipTitleColor;
    protected String tooltipWidth;


    protected void populateParams() {
        super.populateParams();

        UIBean uiBean = (UIBean) component;
        uiBean.setCssClass(cssClass);
        uiBean.setCssClass(cssClass);
        uiBean.setCssStyle(cssStyle);
        uiBean.setTitle(title);
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
        uiBean.setTooltip(tooltip);
        uiBean.setTooltipIcon(tooltipIcon);
        uiBean.setTooltipAboveMousePointer(tooltipAboveMousePointer);
        uiBean.setTooltipBgColor(tooltipBgColor);
        uiBean.setTooltipBgImg(tooltipBgImg);
        uiBean.setTooltipBorderWidth(tooltipBorderWidth);
        uiBean.setTooltipBorderColor(tooltipBorderColor);
        uiBean.setTooltipDelay(tooltipDelay);
        uiBean.setTooltipFixCoordinate(tooltipFixCoordinate);
        uiBean.setTooltipFontColor(tooltipFontColor);
        uiBean.setTooltipFontFace(tooltipFontFace);
        uiBean.setTooltipFontSize(tooltipFontSize);
        uiBean.setTooltipFontWeight(tooltipFontWeight);
        uiBean.setTooltipLeftOfMousePointer(tooltipLeftOfMousePointer);
        uiBean.setTooltipOffsetX(tooltipOffsetX);
        uiBean.setTooltipOffsetY(tooltipOffsetY);
        uiBean.setTooltipOpacity(tooltipOpacity);
        uiBean.setTooltipPadding(tooltipPadding);
        uiBean.setTooltipShadowColor(tooltipShadowColor);
        uiBean.setTooltipShadowWidth(tooltipShadowWidth);
        uiBean.setTooltipStatic(tooltipStatic);
        uiBean.setTooltipSticky(tooltipSticky);
        uiBean.setTooltipStayAppearTime(tooltipStayAppearTime);
        uiBean.setTooltipTextAlign(tooltipTextAlign);
        uiBean.setTooltipTitle(tooltipTitle);
        uiBean.setTooltipTitleColor(tooltipTitleColor);
        uiBean.setTooltipWidth(tooltipWidth);
    }

    public void setCssClass(String cssClass) {
        this.cssClass = cssClass;
    }

    public void setCssStyle(String cssStyle) {
        this.cssStyle = cssStyle;
    }

    public void setTitle(String title) {
        this.title = title;
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
     * @deprecated please use {@link #setLabelPosition} instead
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
    
    public void setTooltip(String tooltip) {
    	this.tooltip = tooltip;
    }

	public void setTooltipAboveMousePointer(String tooltipAboveMousePointer) {
		this.tooltipAboveMousePointer = tooltipAboveMousePointer;
	}

	public void setTooltipBgColor(String tooltipBgColor) {
		this.tooltipBgColor = tooltipBgColor;
	}

	public void setTooltipBgImg(String tooltipBgImg) {
		this.tooltipBgImg = tooltipBgImg;
	}

	public void setTooltipBorderColor(String tooltipBorderColor) {
		this.tooltipBorderColor = tooltipBorderColor;
	}

	public void setTooltipBorderWidth(String tooltipBorderWidth) {
		this.tooltipBorderWidth = tooltipBorderWidth;
	}

	public void setTooltipDelay(String tooltipDelay) {
		this.tooltipDelay = tooltipDelay;
	}

	public void setTooltipFixCoordinate(String tooltipFixCoordinate) {
		this.tooltipFixCoordinate = tooltipFixCoordinate;
	}

	public void setTooltipFontColor(String tooltipFontColor) {
		this.tooltipFontColor = tooltipFontColor;
	}

	public void setTooltipFontFace(String tooltipFontFace) {
		this.tooltipFontFace = tooltipFontFace;
	}

	public void setTooltipFontSize(String tooltipFontSize) {
		this.tooltipFontSize = tooltipFontSize;
	}

	public void setTooltipFontWeight(String tooltipFontWeight) {
		this.tooltipFontWeight = tooltipFontWeight;
	}

	public void setTooltipIcon(String tooltipIcon) {
		this.tooltipIcon = tooltipIcon;
	}

	public void setTooltipLeftOfMousePointer(String tooltipLeftOfMousePointer) {
		this.tooltipLeftOfMousePointer = tooltipLeftOfMousePointer;
	}

	public void setTooltipOffsetX(String tooltipOffsetX) {
		this.tooltipOffsetX = tooltipOffsetX;
	}

	public void setTooltipOffsetY(String tooltipOffsetY) {
		this.tooltipOffsetY = tooltipOffsetY;
	}

	public void setTooltipOpacity(String tooltipOpacity) {
		this.tooltipOpacity = tooltipOpacity;
	}

	public void setTooltipPadding(String tooltipPadding) {
		this.tooltipPadding = tooltipPadding;
	}

	public void setTooltipShadowColor(String tooltipShadowColor) {
		this.tooltipShadowColor = tooltipShadowColor;
	}

	public void setTooltipShadowWidth(String tooltipShadowWidth) {
		this.tooltipShadowWidth = tooltipShadowWidth;
	}

	public void setTooltipStatic(String tooltipStatic) {
		this.tooltipStatic = tooltipStatic;
	}

	public void setTooltipStayAppearTime(String tooltipStayAppearTime) {
		this.tooltipStayAppearTime = tooltipStayAppearTime;
	}

	public void setTooltipSticky(String tooltipSticky) {
		this.tooltipSticky = tooltipSticky;
	}

	public void setTooltipTextAlign(String tooltipTextAlign) {
		this.tooltipTextAlign = tooltipTextAlign;
	}

	public void setTooltipTitle(String tooltipTitle) {
		this.tooltipTitle = tooltipTitle;
	}

	public void setTooltipTitleColor(String tooltipTitleColor) {
		this.tooltipTitleColor = tooltipTitleColor;
	}

	public void setTooltipWidth(String tooltipWidth) {
		this.tooltipWidth = tooltipWidth;
	}
}
