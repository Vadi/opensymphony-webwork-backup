package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.webwork.components.Href;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @jsp.tag name="href" bodycontent="JSP"
 * @see Href
 */
public class HrefTag extends AbstractClosingTag {
    protected String href;
    protected String errorText;
    protected String showErrorTransportText;
    protected String notifyTopics;
    protected String afterLoading;
    protected String preInvokeJS;

    public Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        return new Href(stack, req, res);
    }

    protected void populateParams() {
        super.populateParams();

        Href link = (Href) component;
        link.setHref(href);
        link.setErrorText(errorText);
        link.setShowErrorTransportText(showErrorTransportText);
        link.setNotifyTopics(notifyTopics);
        link.setAfterLoading(afterLoading);
        link.setPreInvokeJS(preInvokeJS);
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = showErrorTransportText;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setAfterLoading(String afterLoading) {
        this.afterLoading = afterLoading;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     */
    public void setPreInvokeJS(String preInvokeJS) {
        this.preInvokeJS = preInvokeJS;
    }
}

