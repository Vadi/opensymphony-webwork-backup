package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag;
import com.opensymphony.webwork.views.jsp.ui.AbstractUITag;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;

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
public class RemoteLinkTag extends AbstractUITag {

    final public static String OPEN_TEMPLATE = "a";
    final public static String TEMPLATE = "a-close";

    final private static String COMPONENT_NAME = RemoteLinkTag.class.getName();

    private String href;
    private String errorText;
    private String showErrorTransportText;
    private String notifyTopics;
    private String afterLoading;


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
     * @see com.opensymphony.webwork.components.ajax.JavascriptEmitter#getComponentName()
     */
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * @param href the href being called.  If the href starts with "/" the context path is appended
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @param errorText the text to display if there is an error
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * @param showErrorTransportText whether to show the error message from dojo
     */
    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = showErrorTransportText;
    }

    /**
     * @param notifyTopics the topic name to subscribe to
     */
    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    /**
      * @param afterLoading JS code to execute after loading the content of the div remotely
      */
     public void setAfterLoading(String afterLoading) {
         this.afterLoading = afterLoading;
     }

    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractUITag#evaluateExtraParams(com.opensymphony.xwork.util.OgnlValueStack)
     */
    protected void evaluateExtraParams(OgnlValueStack stack) {

        super.evaluateExtraParams(stack);

        if (href != null) {
            String stackUrl = findString(href);
            String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
            if (stackUrl.startsWith("/") && stackUrl.startsWith(contextPath)) {
                contextPath = "";
            }
            addParameter("href", contextPath + stackUrl );
        }

        if (showErrorTransportText != null) {
            addParameter("showErrorTransportText", findValue(showErrorTransportText, Boolean.class));
        }

        if (errorText != null) {
            addParameter("errorText", findString(errorText));
        }

        if (notifyTopics != null) {
            addParameter("notifyTopics", findString(notifyTopics));
        }

        if (afterLoading != null) {
            addParameter("afterLoading", findString(afterLoading));
        }
    }

}
