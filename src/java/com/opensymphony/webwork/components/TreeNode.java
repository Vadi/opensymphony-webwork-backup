/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TreeNode
 * <p/>
 * Created : Dec 12, 2005 3:53:40 PM
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class TreeNode extends ClosingUIBean {
    private static final String TEMPLATE = "treenode-close";
    private static final String OPEN_TEMPLATE = "treenode";

    public TreeNode(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}