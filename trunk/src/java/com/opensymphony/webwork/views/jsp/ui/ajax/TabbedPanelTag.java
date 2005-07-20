package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag;
import com.opensymphony.webwork.components.ajax.ContentPane;
import com.opensymphony.webwork.components.ajax.JavascriptEmitter;

import java.util.List;
import java.util.ArrayList;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyContent;
import java.io.IOException;

/**
 * TabbedPanelTag
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 * @author <a href="ian@fdar.com">Ian Roughley</a>
 */
public class TabbedPanelTag extends AbstractClosingUITag implements BodyTag, JavascriptEmitter, Cloneable {

    public static final String TEMPLATE_CLOSE = "tabbedpanel-close";
    public static final String COMPONENT_JS = "tabbedpanel-js.vm";
    final private static String COMPONENT_NAME = TabbedPanelTag.class.getName();

    private List tabs = new ArrayList();
    private String innerBodyContent;
    private JspWriter innerBodyWriter;
    private BodyContent bodyContent;

    /**
     * Constructor.
     */
    public TabbedPanelTag() {
        tabs = new ArrayList();
    }

    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag#getDefaultOpenTemplate()
     */
    public String getDefaultOpenTemplate() {
        return null;  // the opening tab is not used
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTag#setBodyContent(javax.servlet.jsp.tagext.BodyContent)
     */
    public void setBodyContent(BodyContent bodyContent) {
        this.bodyContent = bodyContent;
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTag#doInitBody()
     */
    public void doInitBody() throws JspException {
        // do nothing
    }

    /**
     * @see javax.servlet.jsp.tagext.BodyTag#doStartTag()
     */
    public int doStartTag() throws JspException {
        return BodyTag.EVAL_BODY_BUFFERED;
    }

    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag#getDefaultTemplate()
     */
    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    /**
     * Get the list of {@link ContentPane} tabs for this tab panel.
     * @return the list of {@link ContentPane} tabs for this tab panel
     */
    public List getTabs() {
        return tabs;
    }

    /**
     * Add a new {@link com.opensymphony.webwork.components.ajax.ContentPane} to be rendered.
     *
     * @param pane a new {@link com.opensymphony.webwork.components.ajax.ContentPane} to be rendered
     */
    public void addTab( ContentPane pane ) {
        tabs.add(pane);
    }

    /**
     * Returns the name of the topic that selections of the tab will be made.
     *
     * @return the name of the topic that selections of the tab will be made
     */
    public String getTopicName() {
        return "topic_tab_" + id + "_selected";
    }

    /**
     * Get the body of this tag and store it away to be rendered after the tab headers.
     *
     * @see javax.servlet.jsp.tagext.BodyTag#doAfterBody()
     */
    public int doAfterBody() throws JspException {
        BodyContent body = bodyContent;
        innerBodyWriter = body.getEnclosingWriter();
        innerBodyContent = body.getString();
        body.clearBody();
        return SKIP_BODY;
    }

    /**
     * Note: no idea why this.clone() needs to be used, but when it wasn't the same
     * tag instance was re-used for each tag cause incorrect javascript to be generated.
     * <p/>
     *
     * Write the closing template (for the header tabs), and then write the contents of
     * the body tag, which will be the div's for each tab's contents.
     *
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        try {
            try {
                ((TopicScopeTag)findAncestorWithClass( this, TopicScopeTag.class )).addEmitter((JavascriptEmitter)this.clone());
            } catch (CloneNotSupportedException e) {
                throw new JspException(e);
            }

            evaluateParams(getStack());

            try {
                mergeTemplate(getTemplateName());
                innerBodyWriter.println(innerBodyContent);
                return BodyTag.EVAL_BODY_AGAIN;

            } catch (Exception e) {
                throw new JspException("Fatal exception caught in " + this.getClass().getName() + " tag class, doEndTag: " + e.getMessage(), e);
            }
        } finally {
            tabs = new ArrayList();
            innerBodyContent = null;
            innerBodyWriter = null;
        }
    }

    /**
     * @see JavascriptEmitter#emittJavascript(javax.servlet.jsp.PageContext)
     */
    public void emittJavascript(PageContext page) throws JspException {
        try {
//            String template = buildTemplateName( null, COMPONENT_JS );
            mergeTemplate( "/template/simple/tabbedpanel-js.vm" );
        } catch (Exception e) {
            throw new JspException(e);
        }
    }

    /**
     * @see JavascriptEmitter#getComponentName()
     */
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * @see JavascriptEmitter#emittInstanceConfigurationJavascript(javax.servlet.jsp.PageContext)
     */
    public void emittInstanceConfigurationJavascript(PageContext page) throws JspException {
        if( tabs.size()==0 || null==(ContentPane)tabs.get(0) )
            return;
        ContentPane initialPane = (ContentPane)tabs.get(0);
        JspWriter out = page.getOut();
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("dojo.event.topic.publish('").append(getTopicName()).append("', '").append(initialPane.getId()).append("');\n");
            out.println(sb.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
    }

}
