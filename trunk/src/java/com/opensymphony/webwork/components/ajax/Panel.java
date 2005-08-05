package com.opensymphony.webwork.components.ajax;

import com.opensymphony.webwork.components.ClosingUIBean;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:19:23 AM
 */
public class Panel extends ClosingUIBean implements JavascriptEmitter, ContentPane {
    public static final String TEMPLATE = "tab";
    public static final String TEMPLATE_CLOSE = "tab-close";
    final private static String COMPONENT_NAME = Panel.class.getName();

    protected String tabName;
    protected String subscribeTopicName;

    public Panel(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    public void end(Writer writer) {
        TabbedPanel tabbedPanel = ((TabbedPanel) findAncestor(TabbedPanel.class));
        subscribeTopicName = tabbedPanel.getTopicName();
        tabbedPanel.addTab(this);
        ((TopicScope) findAncestor(TopicScope.class)).addEmitter(this);

        super.end(writer);
    }

    public void emittJavascript(Writer writer) {
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }

    public void emittInstanceConfigurationJavascript(Writer writer) {
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("var tabpanel_").append(id).append(" = new TabContent( \"").append(id).append("\", false );\n");
            sb.append("dojo.event.topic.subscribe( \"").append(subscribeTopicName).append("\"");
            sb.append(", tabpanel_").append(id).append(", ");
            sb.append("\"updateVisibility\"").append(" );");
            sb.append("\n");
            writer.write(sb.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getTabName() {
        return tabName;
    }

    public void setTabName(String tabName) {
        this.tabName = tabName;
    }
}
