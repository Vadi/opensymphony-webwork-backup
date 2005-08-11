package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.UIBean;
import com.opensymphony.webwork.components.ajax.TopicScope;
import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This is a body tag that surrounds other Ajax components/tags that need to generate javascript on
 * a per-component (class) or per-instance basis.  This will be common for components/tags that
 * utilize the dojo event framework.
 *
 * @author Ian Roughley
 * @version $Id$
 */
public class TopicScopeTag extends AbstractUITag {

    public UIBean getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new TopicScope(stack, req, res);
    }
}
