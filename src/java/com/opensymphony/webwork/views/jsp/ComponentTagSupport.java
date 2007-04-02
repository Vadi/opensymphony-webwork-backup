package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.JspException;

/**
 * @auhtor plightbo
 * @author tmjee
 * @version $Date$ $Id$
 */
public abstract class ComponentTagSupport extends WebWorkBodyTagSupport {
    protected Component component;

    public abstract Component getBean(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res);

    public int doEndTag() throws JspException {
        boolean evalBodyAgain = component.end(pageContext.getOut(), getBody());
        if (evalBodyAgain) {
        	return EVAL_BODY_AGAIN;
        }
        else {
            component = null;
        	return EVAL_PAGE;
        }
    }

    public int doStartTag() throws JspException {
        component = getBean(getStack(), (HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.getResponse());
        populateParams();
        boolean evalBody = component.start(pageContext.getOut());

        if (evalBody) {
            return component.usesBody() ? EVAL_BODY_BUFFERED : EVAL_BODY_INCLUDE;
        } else {
            return SKIP_BODY;
        }
    }

    protected void populateParams() {
        component.setId(id);
    }

    public Component getComponent() {
        return component;
    }
}
