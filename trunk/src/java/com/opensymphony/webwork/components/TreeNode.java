/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Renders a tree node within a tree widget with AJAX support.<p/>
 *
 * Either of the following combinations should be used depending on if the tree 
 * is to be constrcted dynamically or statically. <p/>
 * 
 * <b>Dynamically</b>
 * <ul>
 * 		<li>id - id of this tree node</li>
 * 		<li>title - label to be displayed for this tree node</li>
 * </ul>
 * 
 * <b>Statically</b>
 * <ul>
 * 		<li>rootNode - the parent node of which this tree is derived from</li>
 * 		<li>nodeIdProperty - property to obtained this current tree node's id</li>
 * 		<li>nodeTitleProperty - property to obtained this current tree node's title</li>
 * 		<li>childCollectionProperty - property that returnds this current tree node's children</li>
 * </ul>
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 * &lt-- statically --&gt;
 * &lt;ww:tree id="..." label="..."&gt;
 *    &lt;ww:treenode id="..." label="..." /&gt;
 *    &lt;ww:treenode id="..." label="..."&gt;
 *        &lt;ww:treenode id="..." label="..." /&gt;
 *        &lt;ww:treenode id="..." label="..." /&gt;
 *    &;lt;/ww:treenode&gt;
 *    &lt;ww:treenode id="..." label="..." /&gt;
 * &lt;/ww:tree&gt;
 * 
 * &lt;-- dynamically --&gt;
 * &lt;ww:tree 
 *          id="..."
 *          rootNode="..."
 *          nodeIdProperty="..."
 *          nodeTitleProperty="..."
 *          childCollectionProperty="..." /&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * Created : Dec 12, 2005 3:53:40 PM
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 * @author tm_jee
 *
 * @ww.tag name="treenode" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.TreeNodeTag"
 * description="Render a tree node within a tree widget."
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

    /**
     * Label expression used for rendering tree node label.
     * @ww.tagattribute required="true"
     */
    public void setLabel(String label) {
        super.setLabel(label);
    }
}
