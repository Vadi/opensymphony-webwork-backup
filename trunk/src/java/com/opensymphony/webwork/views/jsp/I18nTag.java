/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.TextProviderSupport;
import com.opensymphony.xwork.LocaleProvider;
import com.opensymphony.xwork.util.LocalizedTextUtil;

import org.apache.commons.logging.*;

import java.util.Locale;
import java.util.ResourceBundle;
import java.util.List;

import javax.servlet.jsp.JspException;


/**
 * Gets a resource bundle and place it on the value stack. This allows
 * the text tag to access messages from any bundle, and not just the bundle
 * associated with the current action.
 *
 * @author Rickard Öberg (rickard@dreambean.com)
 * @version $Revision$
 */
public class I18nTag extends WebWorkTagSupport {
    //~ Instance fields ////////////////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    protected String nameAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    // Public --------------------------------------------------------
    public void setName(String aName) {
        nameAttr = aName;
    }

    public int doEndTag() throws JspException {
        getStack().pop();

        return EVAL_PAGE;
    }

    // BodyTag implementation ----------------------------------------
    public int doStartTag() throws JspException {
        // Get bundle
        try {
            String name = this.findString(nameAttr);
            ResourceBundle bundle = (ResourceBundle) findValue("texts('" + name + "')");

            if (bundle == null) {
                bundle = LocalizedTextUtil.findResourceBundle(name, (Locale) getStack().getContext().get(ActionContext.LOCALE));
            }

            if (bundle != null) {
                final Locale locale = (Locale) getStack().getContext().get(ActionContext.LOCALE);
                getStack().push(new TextProviderSupport(bundle, new LocaleProvider() {
                    public Locale getLocale() {
                        return locale;
                    }
                }));
            }
        } catch (Exception e) {
            LogFactory.getLog(getClass()).error("Could not find the bundle " + nameAttr, e);
            throw new JspException("Could not find the bundle " + nameAttr);
        }

        return EVAL_BODY_INCLUDE;
    }
}
