/*
 * Copyright (c) 2005 ePlus Corporation. All Rights Reserved.
 */
package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Renders a tree widget with AJAX support.<p/>
 * 
 * The id attribute is normally specified, such that it could be looked up using
 * javascript if necessary.<p/>
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
 * Created : Oct 27, 2005 3:56:23 PM
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 * @author tm_jee
 *
 * @ww.tag name="tree" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.TreeTag"
 * description="Render a tree widget."
 */
public class Tree extends ClosingUIBean {
    private static final String TEMPLATE = "tree-close";
    private static final String OPEN_TEMPLATE = "tree";
    private String toggle = "fade";
    private String treeSelectedTopic;
    private String treeExpandedTopic;
    private String treeCollapsedTopic;
    protected String rootNodeAttr;
    protected String childCollectionProperty;
    protected String nodeTitleProperty;
    protected String nodeIdProperty;
    private String showRootGrid;
    
    private String showGrid;
    private String blankIconSrc;
	private String gridIconSrcL;
	private String gridIconSrcV;
	private String gridIconSrcP;
	private String gridIconSrcC;
	private String gridIconSrcX;
	private String gridIconSrcY;
	private String expandIconSrcPlus;
	private String expandIconSrcMinus;
	private String iconWidth;
	private String iconHeight;
	private String toggleDuration;
    private String templateCssPath;

    public Tree(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public boolean start(Writer writer) {
        boolean result = super.start(writer);

        if (this.label == null) {
            if ((rootNodeAttr == null)
                    || (childCollectionProperty == null)
                    || (nodeTitleProperty == null)
                    || (nodeIdProperty == null)) {
                fieldError("label","The TreeTag requires either a value for 'label' or ALL of 'rootNode', " +
                        "'childCollectionProperty', 'nodeTitleProperty', and 'nodeIdProperty'", null);
            }
        }
        return result;
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (toggle != null) {
            addParameter("toggle", findString(toggle));
        }

        if (treeSelectedTopic != null) {
            addParameter("treeSelectedTopic", findString(treeSelectedTopic));
        }

        if (treeExpandedTopic != null) {
            addParameter("treeExpandedTopic", findString(treeExpandedTopic));
        }

        if (treeCollapsedTopic != null) {
            addParameter("treeCollapsedTopic", findString(treeCollapsedTopic));
        }

        if (rootNodeAttr != null) {
            addParameter("rootNode", findValue(rootNodeAttr));
        }

        if (childCollectionProperty != null) {
            addParameter("childCollectionProperty", findString(childCollectionProperty));
        }

        if (nodeTitleProperty != null) {
            addParameter("nodeTitleProperty", findString(nodeTitleProperty));
        }

        if (nodeIdProperty != null) {
            addParameter("nodeIdProperty", findString(nodeIdProperty));
        }
        
        if (showRootGrid != null) {
        	addParameter("showRootGrid", findValue(showRootGrid, Boolean.class));
        }
        
        
        if (showGrid != null) {
        	addParameter("showGrid", findValue(showGrid, Boolean.class));
        }
        
        if (blankIconSrc != null) {
        	addParameter("blankIconSrc", findString(blankIconSrc));
        }
        
    	if (gridIconSrcL != null) {
    		addParameter("gridIconSrcL", findString(gridIconSrcL));
    	}
    	
    	if (gridIconSrcV != null) {
    		addParameter("gridIconSrcV", findString(gridIconSrcV));
    	}
    	
    	if (gridIconSrcP != null)  {
    		addParameter("gridIconSrcP", findString(gridIconSrcP));
    	}
    	
    	if (gridIconSrcC != null) {
    		addParameter("gridIconSrcC", findString(gridIconSrcC));
    	}
    	
    	if (gridIconSrcX != null) {
    		addParameter("gridIconSrcX", findString(gridIconSrcX));
    	}
    	
    	if (gridIconSrcY != null) {
    		addParameter("gridIconSrcY", findString(gridIconSrcY));
    	}
    	
    	if (expandIconSrcPlus != null) {
    		addParameter("expandIconSrcPlus", findString(expandIconSrcPlus));
    	}
    	
    	if (expandIconSrcMinus != null) {
    		addParameter("expandIconSrcMinus", findString(expandIconSrcMinus));
    	}
    	
    	if (iconWidth != null) {
    		addParameter("iconWidth", findValue(iconWidth, Integer.class));
    	}
    	if (iconHeight != null) {
    		addParameter("iconHeight", findValue(iconHeight, Integer.class));
    	}
    	if (toggleDuration != null) {
    		addParameter("toggleDuration", findValue(toggleDuration, Integer.class));
    	}
        if (templateCssPath != null) {
        	addParameter("templateCssPath", findString(templateCssPath));
        }
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public String getToggle() {
        return toggle;
    }

    /**
     * The toggle property.
     * @ww.tagattribute required="false"
     */
    public void setToggle(String toggle) {
        this.toggle = toggle;
    }

    public String getTreeSelectedTopic() {
        return treeSelectedTopic;
    }

    /**
     * The treeSelectedTopic property.
     * @ww.tagattribute required="false"
     */
    public void setTreeSelectedTopic(String treeSelectedTopic) {
        this.treeSelectedTopic = treeSelectedTopic;
    }

    public String getTreeExpandedTopic() {
        return treeExpandedTopic;
    }

    /**
     * The treeExpandedTopic property.
     * @ww.tagattribute required="false"
     */
    public void setTreeExpandedTopic(String treeExpandedTopic) {
        this.treeExpandedTopic = treeExpandedTopic;
    }

    public String getTreeCollapsedTopic() {
        return treeCollapsedTopic;
    }

    /**
     * The treeCollapsedTopic property.
     * @ww.tagattribute required="false"
     */
    public void setTreeCollapsedTopic(String treeCollapsedTopic) {
        this.treeCollapsedTopic = treeCollapsedTopic;
    }

    public String getRootNode() {
        return rootNodeAttr;
    }

    /**
     * The rootNode property.
     * @ww.tagattribute required="false"
     */
    public void setRootNode(String rootNode) {
        this.rootNodeAttr = rootNode;
    }

    public String getChildCollectionProperty() {
        return childCollectionProperty;
    }

    /**
     * The childCollectionProperty property.
     * @ww.tagattribute required="false"
     */
    public void setChildCollectionProperty(String childCollectionProperty) {
        this.childCollectionProperty = childCollectionProperty;
    }

    public String getNodeTitleProperty() {
        return nodeTitleProperty;
    }

    /**
     * The nodeTitleProperty property.
     * @ww.tagattribute required="false"
     */
    public void setNodeTitleProperty(String nodeTitleProperty) {
        this.nodeTitleProperty = nodeTitleProperty;
    }

    public String getNodeIdProperty() {
        return nodeIdProperty;
    }

    /**
     * The nodeIdProperty property.
     * @ww.tagattribute required="false"
     */
    public void setNodeIdProperty(String nodeIdProperty) {
        this.nodeIdProperty = nodeIdProperty;
    }
    
    /**
     * The showRootGrid property (default true).
     * @ww.tagattribute required="false"
     */
    public void setShowRootGrid(String showRootGrid) {
    	this.showRootGrid = showRootGrid;
    }
    
    public String getShowRootGrid() {
    	return showRootGrid;
    }

	public String getBlankIconSrc() {
		return blankIconSrc;
	}

	/**
	 * Blank icon image source.
	 * @ww.tagattribute required="false"
	 */
	public void setBlankIconSrc(String blankIconSrc) {
		this.blankIconSrc = blankIconSrc;
	}

	public String getExpandIconSrcMinus() {
		return expandIconSrcMinus;
	}

	/**
	 * Expand icon (-) image source.
	 * @ww.tagattribute required="false"
	 */
	public void setExpandIconSrcMinus(String expandIconSrcMinus) {
		this.expandIconSrcMinus = expandIconSrcMinus;
	}

	public String getExpandIconSrcPlus() {
		return expandIconSrcPlus;
	}

	/**
	 * Expand Icon (+) image source.
	 * @ww.tagattribute required="false"
	 */
	public void setExpandIconSrcPlus(String expandIconSrcPlus) {
		this.expandIconSrcPlus = expandIconSrcPlus;
	}

	public String getGridIconSrcC() {
		return gridIconSrcC;
	}

	/**
	 * Image source for under child item child icons.
	 * @ww.tagattribute required="false"
	 */
	public void setGridIconSrcC(String gridIconSrcC) {
		this.gridIconSrcC = gridIconSrcC;
	}

	public String getGridIconSrcL() {
		return gridIconSrcL;
	}

	
	/**
	 * Image source for last child grid.
	 * @ww.tagattribute required="false"
	 */
	public void setGridIconSrcL(String gridIconSrcL) {
		this.gridIconSrcL = gridIconSrcL;
	}

	public String getGridIconSrcP() {
		return gridIconSrcP;
	}

	/**
	 * Image source for under parent item child icons.
	 * @ww.tagattribute required="false"
	 */
	public void setGridIconSrcP(String gridIconSrcP) {
		this.gridIconSrcP = gridIconSrcP;
	}

	public String getGridIconSrcV() {
		return gridIconSrcV;
	}

	/**
	 * Image source for vertical line.
	 * @ww.tagattribute required="false"
	 */
	public void setGridIconSrcV(String gridIconSrcV) {
		this.gridIconSrcV = gridIconSrcV;
	}

	public String getGridIconSrcX() {
		return gridIconSrcX;
	}

	/**
	 * Image source for grid for sole root item.
	 * @ww.tagattribute required="false"
	 */
	public void setGridIconSrcX(String gridIconSrcX) {
		this.gridIconSrcX = gridIconSrcX;
	}

	public String getGridIconSrcY() {
		return gridIconSrcY;
	}

	/**
	 * Image source for grid for last root item.
	 * @ww.tagattribute required="false"
	 */
	public void setGridIconSrcY(String gridIconSrcY) {
		this.gridIconSrcY = gridIconSrcY;
	}

	public String getIconHeight() {
		return iconHeight;
	}

	
	/**
	 * Icon height (default 18 pixels).
	 * @ww.tagattribute required="false"
	 */
	public void setIconHeight(String iconHeight) {
		this.iconHeight = iconHeight;
	}

	public String getIconWidth() {
		return iconWidth;
	}

	/**
	 * Icon width (default 19 pixels).
	 * @ww.tagattribute required="false"
	 */
	public void setIconWidth(String iconWidth) {
		this.iconWidth = iconWidth;
	}

	

	public String getTemplateCssPath() {
		return templateCssPath;
	}

	/**
	 * Template css path (default {contextPath}/struts/tree.css.
	 * @ww.tagattribute required="false"
	 */
	public void setTemplateCssPath(String templateCssPath) {
		this.templateCssPath = templateCssPath;
	}

	public String getToggleDuration() {
		return toggleDuration;
	}

	/**
	 * Toggle duration (default 150 ms)
	 * @ww.tagattribute required="false"
	 */
	public void setToggleDuration(String toggleDuration) {
		this.toggleDuration = toggleDuration;
	}

	public String getShowGrid() {
		return showGrid;
	}

	/**
	 * Show grid (default true).
	 * @ww.tagattribute required="false"
	 */
	public void setShowGrid(String showGrid) {
		this.showGrid = showGrid;
	}
}

