/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity.components;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.TreeNode;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <code>TreeNodeDirective</code>
 *
 * @author Rainer Hermanns
 * @version $Id$
 */
public class TreeNodeDirective  extends AbstractDirective {
    public String getBeanName() {
        return "treenode";
    }

    protected Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new TreeNode(stack, req, res);
    }
}

