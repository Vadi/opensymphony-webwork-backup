package com.opensymphony.webwork.views.jsp.ui.ajax;

import com.opensymphony.webwork.views.jsp.ui.AbstractClosingUITag;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;

/**
 * A tag that creates a HTML &gt;DIV /&lt; that obtains it's content via a remote XMLHttpRequest call
 * via the dojo framework.
 * <p/>
 * If a "listenTopics" is supplied, it will listen to that topic and refresh it's content when any message
 * is received.
 *
 * @author		Ian Roughley
 * @version		$Id$
 */
public class RemoteUpdateDivTag extends AbstractClosingUITag {

    private static final String TEMPLATE = "div";
    private static final String TEMPLATE_CLOSE = "div-close";
    final private static String COMPONENT_NAME = RemoteUpdateDivTag.class.getName();

    private String href;
    private String updateFreq;
    private String delay;
    private String loadingText;
    private String errorText;
    private String showErrorTransportText;
    private String listenTopics;
    private String afterLoading;


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
     * @param href the href being called.  If the href starts with "/" the context path is appended
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @param updateFreq the frequence which the component will be updated in seconds
     */
    public void setUpdateFreq(String updateFreq) {
        this.updateFreq = updateFreq;
    }

   /**
     * @param delay the delay before loading the content
     */
    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     * @param loadingText the text to display while the component is being loaded
     */
    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

    /**
     * @param errorText the text to display when an error ocurrs
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * @param showErrorTransportText whether to display the error from the transport is displayed along
     *                               with the errorText, if true the transport error is displayed
     */
    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = showErrorTransportText;
    }

    /**
     * @param listenTopics the topic name to subscribe to
     */
    public void setListenTopics(String listenTopics) {
        this.listenTopics = listenTopics;
    }

    /**
     * @param afterLoading JS code to execute after loading the content of the div remotely
     */
    public void setAfterLoading(String afterLoading) {
        this.afterLoading = afterLoading;
    }

    /**
     * @see JavascriptEmitter#getComponentName()
     */
    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * @see com.opensymphony.webwork.views.jsp.ui.AbstractUITag#evaluateExtraParams(com.opensymphony.xwork.util.OgnlValueStack)
     */
    public void evaluateExtraParams(OgnlValueStack stack) {

        super.evaluateExtraParams(stack);

        if (href != null) {
            String stackUrl = findString(href);
            String contextPath = ((HttpServletRequest) pageContext.getRequest()).getContextPath();
            if (stackUrl.startsWith("/") && stackUrl.startsWith(contextPath)) {
                contextPath = "";
            }
            addParameter("href", contextPath + stackUrl );
        }

        if (null != updateFreq && !"".equals(updateFreq)) {
            addParameter("updateFreq", findString(updateFreq));
        } else {
            addParameter("updateFreq", "0");
        }

        if (showErrorTransportText != null) {
            addParameter("showErrorTransportText", findValue(showErrorTransportText, Boolean.class));
        }

        if (null != delay && !"".equals(delay)) {
            addParameter("delay", findString(delay));
        } else {
            addParameter("delay", "0");
        }

        if (loadingText != null) {
            addParameter("loadingText", findString(loadingText));
        }

        if (errorText != null) {
            addParameter("errorText", findString(errorText));
        }

        if (listenTopics != null) {
            addParameter("listenTopics", findString(listenTopics));
        }

        if (afterLoading != null) {
            addParameter("afterLoading", findString(afterLoading));
        }

    }
}
