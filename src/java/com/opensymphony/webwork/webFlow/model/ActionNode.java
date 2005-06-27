package com.opensymphony.webwork.webFlow.model;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 4:49:49 PM
 */
public class ActionNode extends WebFlowNode {
    public ActionNode(String name) {
        super(name);
    }

    public String getExt() {
        return "action";
    }

    public String getColor() {
        return "coral1";
    }
}
