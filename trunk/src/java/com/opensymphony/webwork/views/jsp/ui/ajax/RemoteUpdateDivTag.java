package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * A tag that creates a HTML &gt;DIV /&lt; that obtains it's content via a remote XMLHttpRequest call
 * via the dojo framework.
 * <p/>
 * If a "topicName" is supplied, it will listen to that topic and refresh it's content when any message
 * is received.  If utilizing the topic/event elements, then this tag needs to be contained within
 * a &gt;ww:topicScope /&lt; tag.
 *
 * @see TopicScopeTag
 *
 * @author		Ian Roughley
 * @version		$Id$
 */
public class RemoteUpdateDivTag extends AbstractUITag implements JavascriptEmitter, Cloneable {

    private static final String TEMPLATE = "remotediv";
    final private static String COMPONENT_NAME = RemoteUpdateDivTag.class.getName();

    private String url;
    private String updateFreq;
    private String loadingText;
    private String reloadingText;
    private String errorText;
    private boolean showErrorTransportText = false;
    protected String topicName;

    private static final Log LOG = LogFactory.getLog(RemoteUpdateDivTag.class);


    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractUITag#getDefaultTemplate()
     */
    public String getDefaultTemplate() {
        return TEMPLATE;
    }

    /**
     * Note: no idea why this.clone() needs to be used, but when it wasn't the same
     * tag instance was re-used for each tag cause incorrect javascript to be generated.
     *
     * @see javax.servlet.jsp.tagext.Tag#doEndTag()
     */
    public int doEndTag() throws JspException {
        try {
            TopicScopeTag topicScope = (TopicScopeTag)findAncestorWithClass( this, TopicScopeTag.class );
            if( null!=topicScope )
                topicScope.addEmitter((JavascriptEmitter)this.clone());
        } catch (CloneNotSupportedException e) {
            throw new JspException(e);
        }
        return super.doEndTag();
    }

    /**
     * @return the url being called.  If the url starts with "/" the context path is appended
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url the url being called.  If the url starts with "/" the context path is appended
     */
    public void setUrl(String url) {
        String stackUrl = findString(url);
        if( stackUrl.startsWith("/") )
            this.url = ((HttpServletRequest)pageContext.getRequest()).getContextPath() + stackUrl;
    }

    /**
     * @return the frequence which the component will be updated in seconds
     */
    public String getUpdateFreq() {
        if( null!=updateFreq && !"".equals(updateFreq) ) {
            return findString(updateFreq);
        }
        return "0";
    }

    /**
     * @param updateFreq the frequence which the component will be updated in seconds
     */
    public void setUpdateFreq(String updateFreq) {
        this.updateFreq = updateFreq;
    }

    /**
     * @return the text to display while the component is being loaded
     */
    public String getLoadingText() {
        return loadingText;
    }

    /**
     * @param loadingText the text to display while the component is being loaded
     */
    public void setLoadingText(String loadingText) {
        this.loadingText = findString(loadingText);
    }

    /**
     * @return the text to display while the component is being reloaded
     */
    public String getReloadingText() {
        return reloadingText;
    }

    /**
     * @param reloadingText the text to display while the component is being reloaded
     */
    public void setReloadingText(String reloadingText) {
        this.reloadingText = findString(reloadingText);
    }

    /**
     * @return the text to display when an error ocurrs
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * @param errorText the text to display when an error ocurrs
     */
    public void setErrorText(String errorText) {
        this.errorText = findString(errorText);
    }

    /**
     * @return whether to display the error from the transport is displayed along with the errorText
     */
    public boolean getShowErrorTransportText() {
        return showErrorTransportText;
    }

    /**
     * @param showErrorTransportText whether to display the error from the transport is displayed along
     *              with the errorText, if true the transport error is displayed
     */
    public void setShowErrorTransportText(String showErrorTransportText) {
            this.showErrorTransportText = "true".equals(findString(showErrorTransportText)) ? true : false;
    }

    /**
     * @return the topic name to subscribe to
     */
    public String getTopicName() {
        return topicName;
    }

    /**
     * @param topicName the topic name to subscribe to
     */
    public void setTopicName(String topicName) {
        this.topicName = findString(topicName);
    }

    /**
     * @see JavascriptEmitter#emittJavascript(javax.servlet.jsp.PageContext)
     */
    public void emittJavascript( PageContext page ) {
        // nothing to emitt
    }

    /**
     * @see JavascriptEmitter#getComponentName()
     */
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * Create JS to subscribe this instance to the topics requested.
     *
     * @see JavascriptEmitter#emittInstanceConfigurationJavascript(javax.servlet.jsp.PageContext)
     */
    public void emittInstanceConfigurationJavascript( PageContext page ) {

        if( null==topicName || "".equals(topicName) )
            return;

        JspWriter out = page.getOut();
        try {
            StringBuffer sb = new StringBuffer();
            StringTokenizer st = new StringTokenizer( topicName, "," );
            while( st.hasMoreElements() ) {
                sb.append("dojo.event.topic.subscribe( " ).append("\"topic_").append(st.nextElement()).append("\"");
                sb.append( ", remotediv_" ).append(id).append( ", " );
                sb.append("\"refresh\"").append(" );");
                sb.append("\n");
            }
            out.println(sb.toString());
        } catch (IOException e) {
            LOG.error( "Error writting JS to pageContext.out", e );
        }
    }
}
