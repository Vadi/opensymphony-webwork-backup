/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import javax.servlet.jsp.JspException;


/**
 * @author Rick Salsa (rsal@mb.sympatico.ca)
 * @version $Revision$
 */
public class ElseTag extends WebWorkTagSupport {
    //~ Methods ////////////////////////////////////////////////////////////////

    public int doStartTag() throws JspException {
        Boolean ifResult = (Boolean) pageContext.getAttribute(IfTag.ANSWER);

        pageContext.removeAttribute(IfTag.ANSWER);

        if ((ifResult == null) || (ifResult.booleanValue() == true)) {
            return SKIP_BODY;
        } else {
            return EVAL_BODY_INCLUDE;
        }
    }
}
