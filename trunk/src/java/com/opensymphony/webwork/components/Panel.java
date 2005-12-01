package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * <!-- START SNIPPET: javadoc -->
 * Render a panel for tabbedPanel.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 * <p/>
 * <!-- START SNIPPET: exampledescription -->
 * The following is an example of a tabbedpanel and panel tag utilizing local and remote content.<p/>
 * <!-- END SNIPPET: exampledescription -->
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:tabbedPanel id="test2" theme="simple" &gt;
 *     &lt;ww:panel id="left" tabName="left" theme="ajax"&gt;
 *         This is the left pane&lt;br/&gt;
 *         &lt;ww:form &gt;
 *             &lt;ww:textfield name="tt" label="Test Text" /&gt;  &lt;br/&gt;
 *             &lt;ww:textfield name="tt2" label="Test Text2" /&gt;
 *         &lt;/ww:form&gt;
 *     &lt;/ww:panel&gt;
 *     &lt;ww:panel remote="true" href="/AjaxTest.action" id="ryh1" theme="ajax" tabName="remote one" /&gt;
 *     &lt;ww:panel id="middle" tabName="middle" theme="ajax"&gt;
 *         middle tab&lt;br/&gt;
 *         &lt;ww:form &gt;
 *             &lt;ww:textfield name="tt" label="Test Text44" /&gt;  &lt;br/&gt;
 *             &lt;ww:textfield name="tt2" label="Test Text442" /&gt;
 *         &lt;/ww:form&gt;
 *     &lt;/ww:panel&gt;
 *     &lt;ww:panel remote="true" href="/AjaxTest.action"  id="ryh21" theme="ajax" tabName="remote right" /&gt;
 * &lt;/ww:tabbedPanel&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * <p/> <b>Additional Configuration</b>
 *
 * <!-- START SNIPPET: exampledescription2 -->
 * If you are looking for the "nifty" rounded corner look, there is additional configuration. This assumes
 * that the background color of the tabs is white. If you are using a different color, please modify the
 * parameter in the Rounded() method.<p/>
 * <!-- END SNIPPET: exampledescription2 -->
 *
 * <pre>
 * <!-- START SNIPPET: example2 -->
 * &lt;link rel="stylesheet" type="text/css" href="&lt;ww:url value="/webwork/tabs.css"/&gt;"&gt;
 * &lt;link rel="stylesheet" type="text/css" href="&lt;ww:url value="/webwork/niftycorners/niftyCorners.css"/&gt;"&gt;
 * &lt;link rel="stylesheet" type="text/css" href="&lt;ww:url value="/webwork/niftycorners/niftyPrint.css"/&gt;" media="print"&gt;
 * &lt;script type="text/javascript" src="&lt;ww:url value="/webwork/niftycorners/nifty.js"/&gt;"&gt;&lt;/script&gt;
 * &lt;script type="text/javascript"&gt;
 *     dojo.event.connect(window, "onload", function() {
 *         if (!NiftyCheck())
 *             return;
 *         Rounded("li.tab_selected", "top", "white", "transparent", "border #ffffffS");
 *         Rounded("li.tab_unselected", "top", "white", "transparent", "border #ffffffS");
 *         // "white" needs to be replaced with the background color
 *     });
 * &lt;/script&gt;
 * <!-- END SNIPPET: example2 -->
 * </pre>
 *
 * @author Ian Roughley
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @see TabbedPanel
 *
 * @ww.tag name="panel" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.PanelTag"
 * description="Render a panel for tabbedPanel"
 */
public class Panel extends Div {
    private static final Log LOG = LogFactory.getLog(Panel.class);

    public static final String TEMPLATE = "tab";
    public static final String TEMPLATE_CLOSE = "tab-close";
    public static final String COMPONENT_NAME = Panel.class.getName();

    protected String tabName;
    protected String subscribeTopicName;
    protected String remote;

    public Panel(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    public void end(Writer writer, String body) {
        TabbedPanel tabbedPanel = ((TabbedPanel) findAncestor(TabbedPanel.class));
        subscribeTopicName = tabbedPanel.getTopicName();
        tabbedPanel.addTab(this);

        super.end(writer, body);
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (tabName != null) {
            addParameter("tabName", findString(tabName));
        }

        if (subscribeTopicName != null) {
            addParameter("subscribeTopicName", subscribeTopicName);
        }

        if (remote != null && "true".equalsIgnoreCase(remote)) {
            addParameter("remote", "true");
        } else {
            addParameter("remote", "false");
        }
    }

    public String getTabName() {
        return findString(tabName);
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * @ww.tagattribute required="true"
     * description="The text of the tab to display in the header tab list"
     */
    public void setTabName(String tabName) {
        this.tabName = tabName;
    }

    /**
     * @ww.tagattribute required="false"
     * description="Set subscribeTopicName attribute"
     */
    public void setSubscribeTopicName(String subscribeTopicName) {
        this.subscribeTopicName = subscribeTopicName;
    }

    /**
     * @ww.tagattribute required="false" type="Boolean" default="false"
     * description="determines whether this is a remote panel (ajax) or a local panel (content loaded into visible/hidden containers)"
     */
    public void setRemote(String remote) {
        this.remote = remote;
    }
}
