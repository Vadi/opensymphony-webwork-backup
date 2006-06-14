/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.TreeNode;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @see TreeNode
 * @author Jason Carreira <jcarreira@eplus.com>
 * @author tm_jee
 */
public class TreeNodeTag extends AbstractClosingTag {

	private static final long serialVersionUID = -230856889989903794L;

	public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new TreeNode(stack,req,res);
    }

    public void setLabel(String label) {
        this.label = label;
    }

    // NOTE: not necessary, label property is inherited, will be populated 
    // by super-class
    /*protected void populateParams() {
        if (label != null) {
            TreeNode treeNode = (TreeNode)component;
            treeNode.setLabel(label);
        }
        super.populateParams();
    }*/
}
