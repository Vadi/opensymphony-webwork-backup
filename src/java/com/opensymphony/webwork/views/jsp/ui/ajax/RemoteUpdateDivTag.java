package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;

/**
 * A tag that creates a HTML &gt;DIV /&lt; that obtains it's content via a remote XMLHttpRequest call
 * via the dojo framework.
 * <p/>
 * If a "triggerTopics" is supplied, it will listen to that topic and refresh it's content when any message
 * is received.  If utilizing the topic/event elements, then this tag needs to be contained within
 * a &gt;ww:topicScope /&lt; tag.
 *
 * @see TopicScopeTag
 *
 * @author		Ian Roughley
 * @version		$Id$
 */
public class RemoteUpdateDivTag extends AbstractClosingUITag implements JavascriptEmitter, Cloneable {

    private static final String TEMPLATE = "div";
    private static final String TEMPLATE_CLOSE = "div-close";
    final private static String COMPONENT_NAME = RemoteUpdateDivTag.class.getName();

    private String href;
    private String updateFreq;
    private String delay;
    private String loadingText;
    private String errorText;
    private boolean showErrorTransportText = false;
    protected String triggerTopics;


    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag#getDefaultTemplate()
     */
    public String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag#getDefaultOpenTemplate()
     */
    public String getDefaultOpenTemplate() {
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
     * @return the href being called.  If the href starts with "/" the context path is appended
     */
    public String getHref() {
        return href;
    }

    /**
     * @param href the href being called.  If the href starts with "/" the context path is appended
     */
    public void setHref(String href) {
        String stackUrl = findString(href);
        String contextPath = ((HttpServletRequest)pageContext.getRequest()).getContextPath();
        if( stackUrl.startsWith("/") && stackUrl.startsWith(contextPath) )
            contextPath = "";
        this.href = contextPath + stackUrl;
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
     * @return the delay before loading the content
     */
    public String getDelay() {
        if( null!=delay && !"".equals(delay) ) {
            return findString(delay);
        }
        return "0";
    }

    /**
     * @param delay the delay before loading the content
     */
    public void setDelay(String delay) {
        this.delay = delay;
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
    public String getTriggerTopics() {
        return triggerTopics;
    }

    /**
     * @param triggerTopics the topic name to subscribe to
     */
    public void setTriggerTopics(String triggerTopics) {
        this.triggerTopics = findString(triggerTopics);
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
    public void emittInstanceConfigurationJavascript( PageContext page ) throws JspException {
        // nothing to emitt
    }
}
