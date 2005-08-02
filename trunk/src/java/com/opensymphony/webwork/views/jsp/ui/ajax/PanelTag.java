package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.ajax.Panel;
import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * PanelTag
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 * @author <a href="ian@fdar.com">Ian Roughley</a>
 */
public class PanelTag extends AbstractUITag {
    protected String tabName;

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Panel(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        ((Panel) bean).setTabName(tabName);
    }

    public void setTabName(String tabName) {
        this.tabName = findString(tabName);
    }
}
