package com.opensymphony.webwork.components;

import com.opensymphony.webwork.components.ClosingUIBean;
import com.opensymphony.webwork.components.ContentPane;
import com.opensymphony.webwork.components.JavascriptEmitter;
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
    public static final String TEMPLATE = "tabbedpanel";
    public static final String TEMPLATE_CLOSE = "tabbedpanel-close";
    final private static String COMPONENT_NAME = TabbedPanel.class.getName();

    protected List tabs = new ArrayList();

    public TabbedPanel(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
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

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        addParameter("topicName", "topic_tab_" + id + "_selected");
        addParameter("tabs", tabs);

    }

    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    public void end(Writer writer) {
        ((TopicScope) findAncestor(TopicScope.class)).addEmitter(this);

//        private String innerBodyContent;
//        private JspWriter innerBodyWriter;
//        private BodyContent bodyContent;


//        public void end(Writer writer) {
//
//         ((TabbedPanel) bean).
//         super.end( writer );
//
//        try {
//            try {
//                ((TopicScopeTag)findAncestorWithClass( this, TopicScopeTag.class )).addEmitter((JavascriptEmitter)this.clone());
//            } catch (CloneNotSupportedException e) {
//                throw new JspException(e);
//            }
//
//            evaluateParams(getStack());
//
//            try {
//                mergeTemplate(getTemplateName());
//                innerBodyWriter.println(innerBodyContent);
//                return BodyTag.EVAL_BODY_AGAIN;
//
//            } catch (Exception e) {
//                throw new JspException("Fatal exception caught in " + this.getClass().getName() + " tag class, doEndTag: " + e.getMessage(), e);
//            }
//        } finally {
//            tabs = new ArrayList();
//            innerBodyContent = null;
//            innerBodyWriter = null;
//        }
//    }
        super.end(writer);
    }

    public void emittJavascript(Writer writer) {
        // do nothing
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
