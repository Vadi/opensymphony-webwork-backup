package com.opensymphony.webwork.components;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.util.TextUtils;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.portlet.context.PortletContext;
import com.opensymphony.webwork.portlet.util.PortalContainer;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.xwork.util.OgnlValueStack;

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

            // Fix: This code was added to help with Portlet suppoort.
            //Modified by Henry Hu @12/9/2005 mail: hu_pengfei@yahoo.com.cn
            //Original Code is:
            //addParameter("href", UrlHelper.buildUrl(findString(href), request, response, null));

            String hrefValue = findString(href);
            String actionUrl = PortletContext.getContext().getActionURL();

            if (!TextUtils.stringSet(actionUrl)) {
                addParameter("href", UrlHelper.buildUrl(hrefValue, request, response, null));
            } else {

                String actionExtension = (String) Configuration.get("webwork.action.extension");

                if (actionExtension == null || "".equals(actionExtension)) {
                    actionExtension = ".action";
                } else {
                    actionExtension = "." + actionExtension;
                }

                boolean isWebWorkAction = hrefValue.indexOf(actionExtension) >= 0;
                StringBuffer sb = new StringBuffer();
                if (isWebWorkAction) {

                    if (PortalContainer.LIFERAY_PORTAL == PortalContainer.get()) {
                        int catIndex = actionUrl.lastIndexOf("&#p_");
                        String liferay_actionUrl = actionUrl.substring(0, (catIndex == -1) ? actionUrl.length() : catIndex);
                        sb.append(liferay_actionUrl).append("&wwXAction=").append(hrefValue);
                    }else {
                        sb.append(actionUrl).append("?wwXAction=").append(hrefValue);
                    }

                } else {
                    if (PortalContainer.LIFERAY_PORTAL == PortalContainer.get()) {
                        int catIndex = actionUrl.lastIndexOf("&#p_");
                        String liferay_actionUrl = actionUrl.substring(0, (catIndex == -1) ? actionUrl.length() : catIndex);
                        sb.append(liferay_actionUrl).append("&wwLink=").append(hrefValue);
                    }else {
                        sb.append(actionUrl).append("?wwLink=").append(hrefValue);
                    }
                }

                addParameter("href", sb.toString());
            }


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
     * @ww.tagattribute required="false" type="String"
     * description="The theme to use for the element. <b>This tag will usually use the ajax theme.</b>"
     */
    public void setTheme(String theme) {
        super.setTheme(theme);
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="The URL to call to obtain the content"
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="The text to display to the user if the is an error fetching the content"
     */
    public void setErrorText(String errorText) {
        this.errorText = errorText;
    }

    /**
     * @ww.tagattribute required="false" type="Boolean" default="false"
     * description="when to show the error message as content when the URL had problems"
     */
    public void setShowErrorTransportText(String showErrorTransportText) {
        this.showErrorTransportText = showErrorTransportText;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="Javascript code that will be executed after the content has been fetched"
     */
    public void setAfterLoading(String afterLoading) {
        this.afterLoading = afterLoading;
    }
}
