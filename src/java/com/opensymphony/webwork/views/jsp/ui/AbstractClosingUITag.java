/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;


/**
 * AbstractClosingUITag
 * @author Jason Carreira
 * Created Apr 1, 2003 10:03:50 PM
 */
public abstract class AbstractClosingUITag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Log log = LogFactory.getLog(AbstractClosingUITag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    String openTemplate;

    //~ Methods ////////////////////////////////////////////////////////////////

    public abstract String getDefaultOpenTemplate();

    public void setOpenTemplate(String openTemplate) {
        this.openTemplate = openTemplate;
    }

    public String getOpenTemplate() {
        return openTemplate;
    }

    public int doStartTag() throws JspException {
        try {
            String openTemplateName = buildTemplateName(getOpenTemplate(), getDefaultOpenTemplate());
            mergeTemplate(openTemplateName);
        } catch (Exception e) {
            log.error("Could not open template", e);

            return SKIP_PAGE;
        }

        return super.doStartTag();
    }
}
