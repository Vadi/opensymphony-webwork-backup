package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Render HTML textarea tag.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:textarea label="Comments" name="comments" cols="30" rows="8"/&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @see TabbedPanel
 *
 * @ww.tag name="textarea" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.TextareaTag"
 * description="Render HTML textarea tag."
 */
public class TextArea extends UIBean {
    final public static String TEMPLATE = "textarea";

    protected String cols;
    protected String readonly;
    protected String rows;
    protected String wrap;

    public TextArea(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateExtraParams() {
        super.evaluateExtraParams();

        if (readonly != null) {
            addParameter("readonly", findValue(readonly, Boolean.class));
        }

        if (cols != null) {
            addParameter("cols", findString(cols));
        }

        if (rows != null) {
            addParameter("rows", findString(rows));
        }

        if (wrap != null) {
            addParameter("wrap", findString(wrap));
        }
    }

    /**
     * @ww.tagattribute required="false" type="Integer"
     * description="HTML cols attribute"
     */
    public void setCols(String cols) {
        this.cols = cols;
    }

    /**
     * @ww.tagattribute required="false" type="Boolean" default="false"
     * description="Whether the textarea is readonly"
     */
    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    /**
     * @ww.tagattribute required="false" type="Integer"
     * description="HTML rows attribute"
     */
    public void setRows(String rows) {
        this.rows = rows;
    }

    /**
     * @ww.tagattribute required="false" type="String"
     * description="HTML wrap attribute"
     */
    public void setWrap(String wrap) {
        this.wrap = wrap;
    }
}
