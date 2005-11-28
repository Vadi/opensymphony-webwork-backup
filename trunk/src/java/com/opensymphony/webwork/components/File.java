package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <!-- START SNIPPET: javadoc -->
 * Renders an HTML file input element.
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:file ... /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @jsp.tag name="file" body-content="JSP" tag-class="com.opensymphony.webwork.views.jsp.ui.FileTag"
 * description="Render a file input field"
  */
public class File extends UIBean {
    final public static String TEMPLATE = "file";

    protected String accept;
    protected String size;

    public File(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    public void evaluateParams() {
        super.evaluateParams();

        if (accept != null) {
            addParameter("accept", findString(accept));
        }

        if (size != null) {
            addParameter("size", findString(size));
        }
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="Accept attribute to indicate accepted filetypes"
     */
    public void setAccept(String accept) {
        this.accept = accept;
    }

    /**
     * @jsp.attribute required="false"  rtexprvalue="true"
     * description="HTML size attribute"
     */
    public void setSize(String size) {
        this.size = size;
    }
}
