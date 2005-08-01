package com.opensymphony.webwork.components.ajax;

import com.opensymphony.webwork.components.ClosingUIBean;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:23:11 AM
 */
public class RemoteUpdateDiv extends ClosingUIBean {
    public static final String TEMPLATE = "div";
    public static final String TEMPLATE_CLOSE = "div-close";
    public static final String COMPONENT_NAME = RemoteUpdateDiv.class.getName();

    protected String href;
    protected String updateFreq;
    protected String delay;
    protected String loadingText;
    protected String errorText;
    protected String showErrorTransportText;
    protected String listenTopics;
    protected String afterLoading;

    public RemoteUpdateDiv(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate() {
        return TEMPLATE_CLOSE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (href != null) {
            String stackUrl = findString(href);
            String contextPath = request.getContextPath();
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

    public void setHref(String href) {
        this.href = href;
    }

    public void setUpdateFreq(String updateFreq) {
        this.updateFreq = updateFreq;
    }

    public void setDelay(String delay) {
        this.delay = delay;
    }

    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = showErrorTransportText;
    }

    public void setListenTopics(String listenTopics) {
        this.listenTopics = listenTopics;
    }

    public void setAfterLoading(String afterLoading) {
        this.afterLoading = afterLoading;
    }
}
