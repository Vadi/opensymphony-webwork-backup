package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * PanelTag
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 * @author <a href="ian@fdar.com">Ian Roughley</a>
 */
public class PanelTag extends AbstractClosingUITag implements JavascriptEmitter, Cloneable, ContentPane {

    public static final String TEMPLATE = "tab";
    public static final String TEMPLATE_CLOSE = "tab-close";
    final private static String COMPONENT_NAME = PanelTag.class.getName();
    private String tabName;
    private String subscribeTopicName;


    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag#getDefaultOpenTemplate()
     */
    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag#getDefaultTemplate()
     */
    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    /**
     * Set the displayable name for the tab.
     *
     * @param tabName the displayable name for the tab
     */
    public void setTabName(String tabName) {
        this.tabName = findString(tabName);
    }

    /**
     * Note: no idea why this.clone() needs to be used, but when it wasn't the same
     * tag instance was re-used for each tag cause incorrect javascript to be generated.
     *
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        subscribeTopicName = ((TabbedPanelTag)getParent()).getTopicName();
        try {
            ((TabbedPanelTag)getParent()).addTab((ContentPane)this.clone());
            ((TopicScopeTag)findAncestorWithClass( this, TopicScopeTag.class )).addEmitter((JavascriptEmitter)this.clone());
        } catch (CloneNotSupportedException e) {
            throw new JspException(e);
        }
        return super.doEndTag();
    }

    /**
     * @see JavascriptEmitter#emittJavascript(javax.servlet.jsp.PageContext)
     */
    public void emittJavascript(PageContext page) {
        // nothing to emitt
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
        JspWriter out = page.getOut();
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("var tabpanel_").append(id).append(" = new TabContent( \"").append(id).append("\" );\n");
            sb.append("dojo.event.topic.subscribe( \"").append( subscribeTopicName ).append("\"");
            sb.append( ", tabpanel_" ).append(id).append( ", " );
            sb.append("\"updateVisibility\"").append(" );");
            sb.append("\n");
            out.println(sb.toString());
        } catch (IOException e) {
            throw new JspException(e);
        }
    }
    /**
     * @see ContentPane#getId()
     */
    public String getId() {
        return id;
    }

    /**
     * @see ContentPane#getTabName()
     */
    public String getTabName() {
        return tabName;
    }

}
