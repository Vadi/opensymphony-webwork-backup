package com.opensymphony.webwork.components.ajax;

import com.opensymphony.webwork.components.ClosingUIBean;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:23:18 AM
 */
public class TabbedPanel extends ClosingUIBean implements JavascriptEmitter {
    public static final String TEMPLATE_CLOSE = "tabbedpanel-close";
    public static final String COMPONENT_JS = "tabbedpanel-js.vm";
    final private static String COMPONENT_NAME = TabbedPanel.class.getName();

    protected List tabs = new ArrayList();

    protected TabbedPanel(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public void addTab(ContentPane pane) {
        tabs.add(pane);
    }

    public List getTabs() {
        return tabs;
    }

    public String getTopicName() {
        return "topic_tab_" + id + "_selected";
    }

    public String getDefaultOpenTemplate() {
        return null;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    public void end(Writer writer) {
        ((TopicScope) findAncestor(TopicScope.class)).addEmitter(this);
        super.end(writer);
    }

    public void emittJavascript(Writer writer) {
        //String template = buildTemplateName( null, COMPONENT_JS );
        try {
            mergeTemplate(writer, "/template/simple/tabbedpanel-js.vm");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }

    public void emittInstanceConfigurationJavascript(Writer writer) {
        if (tabs.size() == 0 || null == tabs.get(0)) {
            return;
        }

        ContentPane initialPane = (ContentPane) tabs.get(0);
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("dojo.event.topic.publish('").append(getTopicName()).append("', '").append(initialPane.getId()).append("');\n");
            writer.write(sb.toString() + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
