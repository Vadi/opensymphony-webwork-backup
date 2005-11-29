package com.opensymphony.webwork.components;

import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * RemoteCallUIBean is superclass for all components dealing with remote calls.
 *
 * @author Rene Gielen
 * @author Ian Roughley
 * @version $Revision$
 * @since 2.2
 */

public abstract class RemoteCallUIBean extends ClosingUIBean {

    protected String href;
    protected String errorText;
    protected String showErrorTransportText;
    protected String afterLoading;

    public RemoteCallUIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (href != null) {
            addParameter("href", UrlHelper.buildUrl(findString(href), request, response, null));
        }

        if (showErrorTransportText != null) {
            addParameter("showErrorTransportText", findValue(showErrorTransportText, Boolean.class));
        }

        if (errorText != null) {
            addParameter("errorText", findString(errorText));
        }

        if (afterLoading != null) {
            addParameter("afterLoading", findString(afterLoading));
        }
    }

    /**
     * @ww.tagattribute required="true"
     * description="The URL to call to obtain the content"
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @ww.tagattribute required="false" rtexprvalue="true"
     * description="The text to display to the user if the is an error fetching the content"
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * @ww.tagattribute required="false"
     * description="true/false - when to show the error message as content when the URL had problems"
     */
    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = showErrorTransportText;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Javascript code that will be executed after the content has been fetched"
     */
    public void setAfterLoading(String afterLoading) {
        this.afterLoading = afterLoading;
    }
}
