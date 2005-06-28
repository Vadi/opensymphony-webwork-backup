package com.opensymphony.webwork.webFlow.model;

import java.io.IOException;

/**
 * User: plightbo
 * Date: Jun 26, 2005
 * Time: 4:50:33 PM
 */
public class Link implements Render {
    public static final int TYPE_FORM = 0;
    public static final int TYPE_ACTION = 1;
    public static final int TYPE_HREF = 2;
    public static final int TYPE_RESULT = 3;
    public static final int TYPE_REDIRECT = 4;

    private WebFlowNode from;
    private WebFlowNode to;
    private int type;
    private String label;

    public Link(WebFlowNode from, WebFlowNode to, int type, String label) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.label = label;
    }

    public void render(IndentWriter writer) throws IOException {
        writer.write(from.getFullName() + " -> " + to.getFullName() + " [label=\"" + getRealLabel() + "\"" + getColor() + "];");
    }

    private String getRealLabel() {
        switch (type) {
            case TYPE_ACTION:
                return "action" + label;
            case TYPE_FORM:
                return "form" + label;
            case TYPE_HREF:
                return "href" + label;
            case TYPE_REDIRECT:
                return "redirect: " + label;
            case TYPE_RESULT:
                return label;
        }

        return "";
    }

    private String getColor() {
        if (type == TYPE_RESULT || type == TYPE_ACTION) {
            return ",color=\"darkseagreen2\"";
        } else {
            return "";
        }
    }
}
