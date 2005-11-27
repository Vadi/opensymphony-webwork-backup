package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.webwork.views.util.UrlHelper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * The div tag is primarily an AJAX tag, providing a remote call from the current page to update a section
 * of content without having to refresh the entire page.<p/>
 *
 * It creates a HTML &lt;DIV /&gt; that obtains it's content via a remote XMLHttpRequest call
 * via the dojo framework.<p/>
 *
 * If a "listenTopics" is supplied, it will listen to that topic and refresh it's content when any message
 * is received.<p/>
 * <!-- END SNIPPET: javadoc -->
 *
 * <b>Important:</b> Be sure to setup the page containing this tag to be Configured for AJAX</p>
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:div ... /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Ian Roughley
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @jsp.tag name="div" body-content="JSP"
 * description="Render HTML div providing content from remote call via AJAX"
  */
public class Div extends RemoteCallUIBean {
    public static final String TEMPLATE = "div";
    public static final String TEMPLATE_CLOSE = "div-close";
    public static final String COMPONENT_NAME = Div.class.getName();

    protected String updateFreq;
    protected String delay;
    protected String loadingText;
    protected String listenTopics;

    public Div(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    public String getDefaultOpenTemplate() {
        return TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE_CLOSE;
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (null != updateFreq && !"".equals(updateFreq)) {
            addParameter("updateFreq", findString(updateFreq));
        } else {
            addParameter("updateFreq", "0");
        }

        if (null != delay && !"".equals(delay)) {
            addParameter("delay", findString(delay));
        } else {
            addParameter("delay", "0");
        }

        if (loadingText != null) {
            addParameter("loadingText", findString(loadingText));
        }

        if (listenTopics != null) {
            addParameter("listenTopics", findString(listenTopics));
        }
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="How often to re-fetch the content (in milliseconds)"
     */
    public void setUpdateFreq(String updateFreq) {
        this.updateFreq = updateFreq;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="How long to wait before fetching the content (in milliseconds)"
     */
    public void setDelay(String delay) {
        this.delay = delay;
    }

    /**
     * @jsp.attribute required="false" rtexprvalue="true"
     * description=" The text to display to the user while the new content is being fetched (especially good if the content will take awhile)"
     */
    public void setLoadingText(String loadingText) {
        this.loadingText = loadingText;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Topic name to listen to (comma delimited), that will cause the DIV's content to be re-fetched"
     */
    public void setListenTopics(String listenTopics) {
        this.listenTopics = listenTopics;
    }

}
