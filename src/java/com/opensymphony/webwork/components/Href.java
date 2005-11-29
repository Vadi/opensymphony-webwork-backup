package com.opensymphony.webwork.components;

import com.opensymphony.util.TextUtils;
import com.opensymphony.webwork.portlet.context.PortletContext;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * A tag that creates a HTML &gt;a href='' /&lt; that when clicked calls a URL remote XMLHttpRequest call
 * via the dojo framework.  The result from the URL is executed as JavaScript.<p/>
 *
 * If a "listenTopics" is supplied, it will publish a 'click' message to that topic when the result is
 * returned.  If utilizing the topic/event elements, then this tag needs to be contained within
 * a &gt;ww:topicScope /&lt; tag.<p/>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:href ... /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Ian Roughley
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="href" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.HrefTag"
 * description="Render a HTML href element that when clicked calls a URL via remote XMLHttpRequest"
  */
public class Href extends RemoteCallUIBean {
    final public static String OPEN_TEMPLATE = "a";
    final public static String TEMPLATE = "a-close";
    final public static String COMPONENT_NAME = Href.class.getName();

    protected String notifyTopics;
    protected String preInvokeJS;

    public Href(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        /*
        TODO: This code was added to help with Portlet suppoort, but it is not correct.Instead, it should consult with ActionMapper
        TODO: When fixing todo above, check whether solution is to be applied to RemoteCallUIBean (rgielen)
        if (href != null) {
            String hrefValue = findString(href);

            if (!TextUtils.stringSet(PortletContext.getContext().getActionURL())) {
                addParameter("href", UrlHelper.buildUrl(hrefValue, request, response, null));
            } else {

                String actionExtension = (String) Configuration.get("webwork.action.extension");

                if (actionExtension == null || "".equals(actionExtension)) {
                    actionExtension = ".action";
                } else {
                    actionExtension = "." + actionExtension;
                }

                boolean isWebWorkAction = hrefValue.indexOf(actionExtension) >= 0;

                if (isWebWorkAction) {
                    sb.append(actionUrl).append("?wwXAction=.").append(hrefValue);
                } else {
                    sb.append(actionUrl).append("?wwLink=").append(hrefValue);
                }

                addParameter("href", sb.toString());
            }
        }
        */

        if (notifyTopics != null) {
            addParameter("notifyTopics", findString(notifyTopics));
        }

        if (preInvokeJS != null) {
            addParameter("preInvokeJS", findString(preInvokeJS));
        }
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set notifyTopics attribute"
     */
    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set preInvokeJS attribute"
     */
    public void setPreInvokeJS(String preInvokeJS) {
        this.preInvokeJS = preInvokeJS;
    }
}