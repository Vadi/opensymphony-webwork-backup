/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.vui;

import com.opensymphony.webwork.components.Include;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.ContainUtil;
import com.opensymphony.webwork.views.jsp.ParamTag;
import com.opensymphony.webwork.views.jsp.WebWorkBodyTagSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.JspWriter;
import java.util.HashMap;
import java.util.Map;


/**
 * Abstract VUI tag
 *
 * @author Jeff Haynie (jhaynie@vocalocity.net)
 * @version $Revision$
 */
public abstract class AbstractVUITag extends WebWorkBodyTagSupport implements ParamTag.Parametric {
    //~ Static fields/initializers /////////////////////////////////////////////

    // Attributes ----------------------------------------------------
    private static Log log = LogFactory.getLog(AbstractVUITag.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    protected Map params = new HashMap();
    protected String templateFooterAttr;
    protected String templateHeaderAttr;
    protected String theme;
    protected String themeAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public abstract String getFooterTemplate();

    // Public --------------------------------------------------------
    public abstract String getHeaderTemplate();

    public String getBrowserUserAgent() {
        HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
        String ua = request.getHeader("User-Agent");

        if (ua == null) {
            ua = request.getHeader("user-agent");
        }

        return ((ua == null) ? "" : ua);
    }

    public Map getParameters() {
        return params;
    }

    public void setTemplateFooter(String aName) {
        templateFooterAttr = aName;
    }

    public void setTemplateHeader(String aName) {
        templateHeaderAttr = aName;
    }

    public void setTheme(String aName) {
        themeAttr = aName;
    }

    public String getTheme() {
        // If theme set is not explicitly given,
        // try to find attribute which states the theme set to use
        if ((theme == null) || (theme == "")) {
            theme = (String) pageContext.findAttribute("theme");
        }

        // Default template set
        if ((theme == null) || (theme == "")) {
            theme = Configuration.getString("webwork.ui.theme");

            if (!theme.endsWith("/")) {
                theme += "/";
            }
        }

        return theme;
    }

    public void addParameter(String name, Object value) {
        addParameterInternal(name, value);
    }

    public int doAfterBody() throws JspException {
        if (bodyContent != null) {
            try {
                JspWriter out = getPreviousOut();
                out.print(bodyContent.getString());
                bodyContent.clearBody();
            } catch (Exception ex) {
                ex.printStackTrace();
                throw new JspTagException("Exception:: " + toString(ex));
            }

            return (EVAL_BODY_BUFFERED);
        }

        return (SKIP_BODY);
    }

    public int doEndTag() throws JspException {
        if (themeAttr != null) {
            theme = (String) findValue(themeAttr);
        }

        getStack().push(this);

        try {
            // footer
            String template = templateFooterAttr;

            if (template == null) {
                template = getFooterTemplate();
            }

            Include.include(getTemplateDirectory() + template, pageContext.getOut(),
                    pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new JspTagException("Exception including footer: " + toString(ex));
        } finally {
            getStack().pop();
            params = new HashMap();
        }

        return EVAL_PAGE;
    }

    // FieldTag overrides ------------------------------------------
    public int doStartTag() {
        if (themeAttr != null) {
            theme = (String) findValue(themeAttr);
        }

        initializeAttributes();

        getStack().push(this);

        try {
            // header
            String template = templateHeaderAttr;

            if (template == null) {
                template = getHeaderTemplate();
            }

            Include.include(getTemplateDirectory() + template, pageContext.getOut(),
                    pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        } catch (Exception ex) {
            ex.printStackTrace();

            return SKIP_BODY;
        } finally {
            getStack().pop();
        }

        return EVAL_BODY_INCLUDE;
    }

    public boolean memberOf(Object obj1, Object obj2) {
        return ContainUtil.contains(obj1, obj2);
    }

    protected void getSetParameter(String a, String n) {
        if (a != null) {
            Object value = findValue(a);

            if (value != null) {
                addParameterInternal(n, value);
            }
        }
    }

    protected abstract void initializeAttributes();

    /**
     * get the template directory for a specific voice browser
     */
    protected String getTemplateDirectory() {
        String ua = getBrowserUserAgent();

        return BrowserSupport.getBrowserTemplateDirectory(ua);
    }

    private void addParameterInternal(String name, Object value) {
        params.put(name, value);
    }
}
