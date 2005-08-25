package com.opensymphony.webwork.components;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:22:58 AM
 */
public class Href extends ClosingUIBean {
    final public static String OPEN_TEMPLATE = "a";
    final public static String TEMPLATE = "a-close";
    final public static String COMPONENT_NAME = Href.class.getName();

    protected String href;
    protected String errorText;
    protected String showErrorTransportText;
    protected String notifyTopics;
    protected String afterLoading;

    public Href(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
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

        //Fix for wwportlet Support -- Added by Henry Hu
        if (href != null) {

            String actionURL = com.opensymphony.webwork.portlet.context.PortletContext.getContext().getActionURL();
            String hrefValue = href;
            String contextPath = request.getContextPath();

            String hrefTemp = "";
            StringBuffer sb = new StringBuffer();

            if (hrefValue != null && !"".equals(hrefValue)) {

                if (!hrefValue.startsWith("/"))
                    hrefValue = "/" + hrefValue;

                if (hrefValue.startsWith(contextPath)) {
                    contextPath = "";
                }

                if (actionURL == null || "".equals(actionURL)) {
                    sb.append(contextPath).append(hrefValue);
                    hrefTemp = sb.toString();

                } else {

                    String actionExtension = (String) Configuration.get("webwork.action.extension");

                    if (actionExtension == null || "".equals(actionExtension)) {
                        actionExtension = ".action";
                    } else {
                        actionExtension = "." + actionExtension;
                    }

                    boolean isWebWorkAction = hrefValue.indexOf(actionExtension) >= 0;

                    if (isWebWorkAction) {
                        sb.append(actionURL).append("?wwXAction=.").append(hrefValue);
                    } else {
                        sb.append(actionURL).append("?wwLink=").append(hrefValue);
                    }

                    hrefTemp = sb.toString();//actionURL+"?wwXAction=./"+pageValue;

                }

                addParameter("href", hrefTemp);
            }

        }
        /////////////Fix End ////////////////

/*        if (href != null) {
            String stackUrl = findString(href);
            String contextPath = request.getContextPath();
            if (stackUrl.startsWith("/") && stackUrl.startsWith(contextPath)) {
                contextPath = "";
            }
            addParameter("href", contextPath + stackUrl );
        }*/

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
