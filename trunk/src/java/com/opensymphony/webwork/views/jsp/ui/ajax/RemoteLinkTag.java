package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

/**
 * A tag that creates a HTML &gt;a href='' /&lt; that when clicked calls a URL remote XMLHttpRequest call
 * via the dojo framework.  The result from the URL is executed as JavaScript.
 * <p/>
 * If a "listenTopics" is supplied, it will publish a 'click' message to that topic when the result is
 * returned.  If utilizing the topic/event elements, then this tag needs to be contained within
 * a &gt;ww:topicScope /&lt; tag.
 *
 * @see TopicScopeTag
 *
 * @author		Ian Roughley
 * @version		$Id$
 */
public class RemoteLinkTag extends AbstractClosingUITag implements JavascriptEmitter, Cloneable {

    final public static String OPEN_TEMPLATE = "a";
    final public static String TEMPLATE = "a-close";

    final private static String COMPONENT_NAME = RemoteLinkTag.class.getName();

    private String href;
    private String errorText;
    private boolean showErrorTransportText;
    private String notifyTopics;


    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag#getDefaultTemplate()
     */
    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag#getDefaultOpenTemplate()
     */
    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
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
     * Do nothing here, the topic is published to in the Javascript function "evalAfterRemoteCall()"
     * which is used in combination with this class.  It needs to be done this way, as there is no
     * way to distinguish between a click on the &gt;a href='' /&lt; tag and any other browser onClick
     * event.
     * <p/>
     * i.e. this code (the original idea) trapped any onClick event in the browser and published to the topic
     *  <code>dojo.event.topic.getTopic("t").registerPublisher( document.getElementById("a"), "onclick" );</code>
     *
     * @see JavascriptEmitter#emittInstanceConfigurationJavascript(javax.servlet.jsp.PageContext)
     */
    public void emittInstanceConfigurationJavascript( PageContext page ) {
        // nothing to emitt
    }

    /**
     * @return the href being called
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
     * @return the text to display if there is an error
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * @param errorText the text to display if there is an error
     */
    public void setErrorText(String errorText) {
        this.errorText = findString(errorText);
    }

    /**
     * @return whether to show the error message from dojo
     */
    public boolean getShowErrorTransportText() {
        return showErrorTransportText;
    }

    /**
     * @param showErrorTransportText whether to show the error message from dojo
     */
    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = "true".equals(findValue(showErrorTransportText,String.class)) ? true : false;
    }

    /**
     * @return the topic name to subscribe to
     */
    public String getNotifyTopics() {
        if( null==notifyTopics )
            return "";
        return findString(notifyTopics);
    }

    /**
     * @param notifyTopics the topic name to subscribe to
     */
    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    /**
     * Note: no idea why this.clone() needs to be used, but when it wasn't the same
     * tag instance was re-used for each tag cause incorrect javascript to be generated.
     *
     * @see javax.servlet.jsp.tagext.TagSupport#doEndTag()
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

}
