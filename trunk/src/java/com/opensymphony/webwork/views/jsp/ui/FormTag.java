/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.webwork.views.velocity.IterationRenderer;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.velocity.Template;
import org.apache.velocity.context.Context;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * FormTag
 * @author Jason Carreira
 * Created Apr 1, 2003 8:19:47 PM
 */
public class FormTag extends AbstractClosingUITag implements IterationRenderer {
    //~ Static fields/initializers /////////////////////////////////////////////

    final public static String OPEN_TEMPLATE = "form.vm";
    final public static String TEMPLATE = "form-close.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    String action;
    String enctype;
    String method;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAction(String action) {
        /**
         * If called from a JSP, pageContext will not be null.  otherwise, we'll get request and response from the
         * ServletActionContext.
         *
         * todo - determine if there's any reason we can't just always use ServletActionContext
         */
        HttpServletResponse response = null;
        HttpServletRequest request = null;

        if (pageContext != null) {
            response = (HttpServletResponse) pageContext.getResponse();
            request = (HttpServletRequest) pageContext.getRequest();
        } else {
            request = ServletActionContext.getRequest();
            response = ServletActionContext.getResponse();
        }

        String result = UrlHelper.buildUrl(action, request, response, null);
        this.action = result;
    }

    public String getAction() {
        return action;
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    public String getEnctype() {
        return enctype;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }

    public int doAfterRender(Context context, Writer writer) {
        return IterationRenderer.RENDER_DONE;
    }

    public void doBeforeRender(Context context, Writer writer) {
        this.evaluateActualValue();

        try {
            String openTemplate = this.getOpenTemplate();

            if (openTemplate == null) {
                openTemplate = OPEN_TEMPLATE;
            }

            String templateName = buildTemplateName(getTemplate(), openTemplate);

            Template template = velocityEngine.getTemplate(templateName);
            template.merge(context, writer);
        } catch (Exception e) {
            try {
                writer.write("<pre>");
                e.printStackTrace(new PrintWriter(writer));
                writer.write("</pre>");
            } catch (IOException e1) {
                // ok
            }
        }
    }

    /**
     * Clears all the instance variables to allow this instance to be reused.
     */
    public void release() {
        super.release();
        method = null;
        enctype = null;
        action = null;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }
}
