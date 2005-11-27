package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * <!-- START SNIPPET: javadoc -->
 * Render a panel for tabbedPanel.</p>
 *
 * The tabbedpanel component is primarily an AJAX component, where each tab can either be local content or remote
 * content (refreshed each time the user selects that tab).</p>
 *
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
 * <b>Important:</b> Be sure to setup the page containing this tag to be Configured for AJAX
 *
 * @author Ian Roughley
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @see Panel
 *
 * @jsp.tag name="tabbedPanel" body-content="JSP"
 * description="Render a tabbedPanel widget."
 */
public class TabbedPanel extends ClosingUIBean {
    public static final String TEMPLATE = "tabbedpanel";
    public static final String TEMPLATE_CLOSE = "tabbedpanel-close";
    final private static String COMPONENT_NAME = TabbedPanel.class.getName();

    protected List tabs = new ArrayList();

    public TabbedPanel(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    /**
     * Add a new panel to be rendered.
     *
     * @param pane a new panel to be rendered
     */
    public void addTab(Panel pane) {
        tabs.add(pane);
    }

    /**
     * Get the list of panel tabs for this tab panel.
     *
     * @return the list of panel tabs for this tab panel
     */
    public List getTabs() {
        return tabs;
    }

    public String getTopicName() {
        return "topic_tab_" + id + "_selected";
    }

    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        addParameter("topicName", "topic_tab_" + id + "_selected");
        addParameter("tabs", tabs);

    }

    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    public String getComponentName() {
        return COMPONENT_NAME;
    }

    /**
     * @jsp.attribute required="true" rtexprvalue="true" description="HTML id attribute"
     */
    public void setId(String id) {
        // This is required to override tld generation attributes to required=true
        super.setId(id);
    }
}
