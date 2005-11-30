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
 * via the dojo framework. The result from the URL is executed as JavaScript.<p/>
 *
 * If a "listenTopics" is supplied, it will publish a 'click' message to that topic when the result is
 * returned.  If utilizing the topic/event elements, then this tag needs to be contained within
 * a &gt;ww:topicScope /&lt; tag.<p/>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example1 -->
 * &lt;ww:a id="link1" theme="ajax" href="/DoIt.action" errorText="'An error ocurred'" showErrorTransportText="true"&gt;&lt;img border="none" src="&lt;%=request.getContextPath()%&gt;/images/delete.gif"/&gt;
 *     &lt;ww:param name="id" value="1"/&gt;
 * &lt;/ww:a&gt;
 * <!-- END SNIPPET: example1 -->
 * </pre>
 * </p>
 * <!-- START SNIPPET: exampledescription1 -->
 * Results in
 * <!-- END SNIPPET: exampledescription1 -->
 * </p>
 *
 * <pre>
 * <!-- START SNIPPET: example2 -->
 * &lt;a dojoType="BindAnchor" evalResult="true" id="link1" href="/DoIt.action?id=1" errorHtml="An error ocurred" showTransportError="true"&gt;&lt;/a&gt;
 * <!-- END SNIPPET: example2 -->
 * </pre>
 * </p>
 * <!-- START SNIPPET: exampledescription2 -->
 * Here is an example that uses the postInvokeJS. This example is in altSyntax=true:
 * <!-- END SNIPPET: exampledescription2 -->
 * </p>
 *
 * <pre>
 * <!-- START SNIPPET: example3 -->
 * &lt;ww:a id="test" theme="ajax" href="/simpeResult.action" preInvokeJS="confirm(\'You sure\')"&gt;A&lt;/ww:a&gt;
 * <!-- END SNIPPET: example3 -->
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
     * @ww.tagattribute required="true" type="String"
     * description="The id to assign the component"
     */
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * @ww.tagattribute required="false"
     * description="Topic names to post an event to after the remote call has been made"
     */
    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="A javascript snippet that will be invoked prior to the execution of the target href. If provided must return true or false. True indicates to continue executing target, false says do not execute link target. Possible uses are for confirm dialogs."
     */
    public void setPreInvokeJS(String preInvokeJS) {
        this.preInvokeJS = preInvokeJS;
    }
}