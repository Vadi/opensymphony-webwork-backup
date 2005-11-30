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
 * &lt;ww:file name="anUploadFile" accept="text/*" /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="file" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.FileTag"
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
     * @ww.tagattribute required="false"
     * description="HTML accept attribute to indicate accepted file mimetypes"
     */
    public void setAccept(String accept) {
        this.accept = accept;
    }

    /**
     * @ww.tagattribute required="false" type="Integer"
     * description="HTML size attribute"
     */
    public void setSize(String size) {
        this.size = size;
    }
}
