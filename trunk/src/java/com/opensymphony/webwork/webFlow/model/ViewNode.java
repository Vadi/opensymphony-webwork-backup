package com.opensymphony.webwork.webFlow.model;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 4:52:39 PM
 */
public class ViewNode extends WebFlowNode {
    public ViewNode(String name) {
        super(name);
    }

    public String getExt() {
        return "";
    }

    public String getColor() {
        return "darkseagreen2";
    }
}
