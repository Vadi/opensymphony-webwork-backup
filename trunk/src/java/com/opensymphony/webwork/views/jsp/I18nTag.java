package com.opensymphony.webwork.views.jsp;

import org.apache.commons.logging.*;

import javax.servlet.jsp.JspException;
import java.util.ResourceBundle;
import java.util.Locale;

import com.opensymphony.xwork.util.LocalizedTextUtil;
import com.opensymphony.xwork.ActionContext;

/**
 * Gets a resource bundle and place it on the value stack. This allows
 * the text tag to access messages from any bundle, and not just the bundle
 * associated with the current action.
 *
 * @author Rickard Öberg (rickard@dreambean.com)
 * @version $Revision$
 */
public class I18nTag
        extends WebWorkTagSupport {
    // Attributes ----------------------------------------------------
    protected String nameAttr;

    // Public --------------------------------------------------------
    public void setName(String aName) {
        nameAttr = aName;
    }

    // BodyTag implementation ----------------------------------------
    public int doStartTag() throws JspException {
        // Get bundle
        try {
            String name = this.findString(nameAttr);
            ResourceBundle bundle = (ResourceBundle) findValue("texts('" + name + "')");

            if (bundle == null) {
                LocalizedTextUtil.findResourceBundle(name, (Locale) getStack().getContext().get(ActionContext.LOCALE));
            }

            getStack().push(new BundleAccessor(bundle));
        } catch (Exception e) {
            LogFactory.getLog(getClass()).error("Could not find the bundle " + nameAttr, e);
            throw new JspException("Could not find the bundle " + nameAttr);
        }

        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        getStack().pop();
        return EVAL_PAGE;
    }

    // This is what the text tag will use to access the bundle
    public static class BundleAccessor {
        ResourceBundle bundle;

        public BundleAccessor(ResourceBundle aBundle) {
            bundle = aBundle;
        }

        public String getText(String aName) {
            return bundle.getString(aName);
        }
    }
}


