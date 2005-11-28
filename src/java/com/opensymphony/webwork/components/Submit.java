package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Render a submit button.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:submit value="'Submit'" /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @jsp.tag name="submit" body-content="JSP" tag-class="com.opensymphony.webwork.views.jsp.ui.SubmitTag"
 * description="Render a submit button"
 */
public class Submit extends UIBean {
    final public static String TEMPLATE = "submit";

    protected String action;
    protected String method;
    protected String align;
    protected String resultDivId;
    protected String onLoadJS;
    protected String notifyTopics;
    protected String listenTopics;
    protected String preInvokeJS;

    public Submit(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateParams() {
        if (align == null) {
            align = "right";
        }

        if (value == null) {
            value = "Submit";
        }

        super.evaluateParams();

        if (action != null || method != null) {
            String name;

            if (action != null) {
                name = "action:" + findString(action);

                if (method != null) {
                    name += findString(method);
                }
            } else {
                name = "method:" + findString(method);
            }
            
            addParameter("name", name);
        }

        addParameter("align", findString(align));

        if (null != resultDivId) {
            addParameter("resultDivId", findString(resultDivId));
        }

        if (null != onLoadJS) {
            addParameter("onLoadJS", findString(onLoadJS));
        }

        if (null != notifyTopics) {
            addParameter("notifyTopics", findString(notifyTopics));
        }

        if (null != listenTopics) {
            addParameter("listenTopics", findString(listenTopics));
        }

        if (preInvokeJS != null) {
            addParameter("preInvokeJS", findString(preInvokeJS));
        }
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set action attribute"
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set method attribute"
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="HTML align attribute"
     */
    public void setAlign(String align) {
        this.align = align;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set resultDivId attribute"
     */
    public void setResultDivId(String resultDivId) {
        this.resultDivId = resultDivId;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set onLoadJS attribute"
     */
    public void setOnLoadJS(String onLoadJS) {
        this.onLoadJS = onLoadJS;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set notifyTopics attribute"
     */
    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set listenTopics attribute"
     */
    public void setListenTopics(String listenTopics) {
        this.listenTopics = listenTopics;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Set preInvokeJS attribute"
     */
    public void setPreInvokeJS(String preInvokeJS) {
        this.preInvokeJS = preInvokeJS;
    }
}
