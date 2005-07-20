package com.opensymphony.webwork.views.jsp.ui.ajax;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;
import java.io.IOException;

import com.opensymphony.webwork.components.ajax.ContentPane;
import com.opensymphony.webwork.components.ajax.JavascriptEmitter;

/**
 * @author		Ian Roughley
 * @version		$Id$
 */
public class RemotePanelTag extends RemoteUpdateDivTag implements ContentPane, JavascriptEmitter {

    public static final String TEMPLATE = "remotetab";
    public static final String TEMPLATE_CLOSE = "remotetab-close";
    final private static String COMPONENT_NAME = RemotePanelTag.class.getName();

    private String tabName;
    private String subscribeTopicName;
    private String openTemplate;

    private static final Log LOG = LogFactory.getLog(RemotePanelTag.class);

    /**
     * Sets the name of the tab to display to the user.
     *
     * @param tabName the name of the tab to display to the user
     */
    public void setTabName(String tabName) {
        this.tabName = findString(tabName);;
    }

    /**
     * Returns the name of the tab to display to the user.
     *
     * @return the name of the tab to display to the user
     */
    public String getTabName() {
        return tabName;
    }

    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag#getDefaultTemplate()
     */
    public String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    public void setOpenTemplate(String openTemplate) {
        this.openTemplate = openTemplate;
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doStartTag()
     */
    public int doStartTag() throws JspException {
        try {
            evaluateParams(getStack());
            String openTemplateName = buildTemplateName( openTemplate, TEMPLATE );
            mergeTemplate(openTemplateName);
        } catch (Exception e) {
            LOG.error("Could not open template", e);

            return SKIP_PAGE;
        }
        return super.doStartTag();
    }

    /**
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
     */
    public int doEndTag() throws JspException {
        subscribeTopicName = ((TabbedPanelTag)getParent()).getTopicName();
        try {
            ((TabbedPanelTag)getParent()).addTab((ContentPane)this.clone());
        } catch (CloneNotSupportedException e) {
            throw new JspException(e);
        }
        return super.doEndTag();
    }

    /**
     * @see JavascriptEmitter#getComponentName()
     */
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * @see com.opensymphony.webwork.components.ajax.JavascriptEmitter#emittJavascript(javax.servlet.jsp.PageContext)
     */
    public void emittJavascript( PageContext page ) {
        // nothing to emitt
    }


    /**
     * Create JS to subscribe this instance to the topics requested.
     *       todo:  fix this for new remoteupdatedivtag
     * @see com.opensymphony.webwork.components.ajax.JavascriptEmitter#emittInstanceConfigurationJavascript(javax.servlet.jsp.PageContext)
     */
    public void emittInstanceConfigurationJavascript( PageContext page ) {

//        super.emittInstanceConfigurationJavascript( pageContext );

        JspWriter out = page.getOut();
        StringBuffer sb = new StringBuffer();

        // create the remote div object
        sb.append("var remotediv_").append(id);
        sb.append(" = new RemoteUpdateComponent( '");
        sb.append( getParameters().get("href")).append("', 'tab_contents_update_").append(id).append("', '");
//        sb.append(getReloadingText()).append("', '");
        sb.append(getParameters().get("errorText")).append("', '").append(getParameters().get("showErrorTransportText")).append("' );\n");

        // subscribe to the tab selection topics
        sb.append("var tabpanel_").append(id).append(" = new TabContent( \"").append(id).append("\" );\n");
        sb.append("dojo.event.topic.subscribe( \"").append( subscribeTopicName ).append("\"");
        sb.append( ", tabpanel_" ).append(id).append( ", " );
        sb.append("\"updateVisibility\"").append(" );");
        sb.append("\n");

        // code to update the panel when it is selected
        sb.append("dojo.event.topic.subscribe( \"").append( subscribeTopicName ).append("\"");
        sb.append( ", remotediv_" ).append(id).append( ", " );
        sb.append("\"tabbedElementIdRefresh\"").append(" );");
        sb.append("\n");

        try {
            out.println(sb.toString());
            out.flush();
        } catch (IOException e) {
            LOG.error( "Error writting JS to pageContext.out", e );
        }

    }
}
