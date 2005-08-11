package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.TopicScope;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ian Roughley
 * @version $Id$
 */
public class TopicScopeModel extends TagModel {
    public TopicScopeModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack, req, res);
    }

    protected Component getBean() {
        return new TopicScope(stack, req, res);
    }
}
