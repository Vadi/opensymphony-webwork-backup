package com.opensymphony.webwork.components;

import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;

/**
 * User: plightbo
 * Date: Jul 18, 2005
 * Time: 7:55:59 PM
 */
public abstract class ClosingUIBean extends UIBean {
    private static final Log LOG = LogFactory.getLog(ClosingUIBean.class);

    protected ClosingUIBean(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    String openTemplate;

    public abstract String getDefaultOpenTemplate();

    public void setOpenTemplate(String openTemplate) {
        this.openTemplate = openTemplate;
    }

    public void start(Writer writer) {
        super.start(writer);
        try {
            evaluateParams();

            mergeTemplate(writer, buildTemplateName(openTemplate, getDefaultOpenTemplate()));
        } catch (Exception e) {
            LOG.error("Could not open template", e);
            e.printStackTrace();
        }
    }
}
