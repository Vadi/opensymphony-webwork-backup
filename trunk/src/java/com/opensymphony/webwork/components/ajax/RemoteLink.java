package com.opensymphony.webwork.components.ajax;

import com.opensymphony.webwork.components.ClosingUIBean;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:22:58 AM
 */
public class RemoteLink extends ClosingUIBean {
    final public static String OPEN_TEMPLATE = "a";
    final public static String TEMPLATE = "a-close";
    final public static String COMPONENT_NAME = RemoteLink.class.getName();

    protected String href;
    protected String errorText;
    protected String showErrorTransportText;
    protected String notifyTopics;
    protected String afterLoading;

    public RemoteLink(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public String getComponentName() {
        return COMPONENT_NAME; // todo: is this needed? if not, remove it
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (href != null) {
            String stackUrl = findString(href);
            String contextPath = request.getContextPath();
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

    public void setHref(String href) {
        this.href = href;
    }

    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = showErrorTransportText;
    }

    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    public void setAfterLoading(String afterLoading) {
        this.afterLoading = afterLoading;
    }
}
