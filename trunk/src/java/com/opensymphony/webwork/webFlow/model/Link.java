package com.opensymphony.webwork.webFlow.model;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 4:50:33 PM
 */
public class Link {
    public static final int TYPE_FORM = 0;
    public static final int TYPE_ACTION = 1;
    public static final int TYPE_HREF = 2;
    public static final int TYPE_RESULT = 3;
    public static final int TYPE_REDIRECT = 4;

    private int type;
    private WebFlowNode node;
    private String label;

    public Link(int type, WebFlowNode node, String label) {
        this.type = type;
        this.node = node;
        this.label = label;
    }

    public int getType() {
        return type;
    }

    public WebFlowNode getNode() {
        return node;
    }

    public String getLabel() {
        return label;
    }
}
