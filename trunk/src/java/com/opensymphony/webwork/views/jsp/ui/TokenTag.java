/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.webwork.util.TokenHelper;

import org.apache.velocity.context.Context;

import java.io.Writer;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;


/**
 * TokenTag
 * @author Jason Carreira
 * Created Apr 2, 2003 10:44:58 PM
 */
public class TokenTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    final public static String TEMPLATE = "token.vm";

    //~ Instance fields ////////////////////////////////////////////////////////

    String token;

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getToken() {
        return token;
    }

    public String getTokenNameField() {
        return TokenHelper.TOKEN_NAME_FIELD;
    }

    /**
* First looks for the token in the PageContext using the supplied name (or {@link TokenHelper#DEFAULT_TOKEN_NAME}
* if no name is provided) so that the same token can be re-used for the scope of a request for the same name. If
* the token is not in the PageContext, a new Token is created and set into the Session and the PageContext with
* the name.
* @return
* @throws JspException
*/
    public int doEndTag() throws JspException {
        if (name == null) {
            name = TokenHelper.DEFAULT_TOKEN_NAME;
        }

        token = buildToken(name.toString());

        return super.doEndTag();
    }

    /**
* Clears all the instance variables to allow this instance to be reused.
*/
    public void release() {
        super.release();
        token = null;
        name = null;
    }

    public void render(Context context, Writer writer) throws Exception {
        if (name == null) {
            name = TokenHelper.DEFAULT_TOKEN_NAME;
        }

        token = buildTokenForVelocity(context, name.toString());

        super.render(context, writer);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    private String buildToken(String name) {
        Object myToken = pageContext.getAttribute(name);

        if (myToken == null) {
            // verify the session exists before passing control to the TokenHelper
            verifySession();
            myToken = TokenHelper.setToken(name.toString());
            pageContext.setAttribute(name, myToken);
        }

        return myToken.toString();
    }

    private String buildTokenForVelocity(Context context, String name) {
        Object myToken = context.get(name);

        if (myToken == null) {
            verifySession();
            ;
            myToken = TokenHelper.setToken(name.toString());
            context.put(name, myToken);
        }

        return myToken.toString();
    }

    /**
* This method checks to see if a HttpSession object exists in the context. If a session
* doesn't exist, it creates a new one and adds it to the context.
*/
    private void verifySession() {
        Map session = ServletActionContext.getContext().getSession();

        // if the session is null, add a new HttpSession to the context
        if (session == null) {
            HttpServletRequest request = ServletActionContext.getRequest();

            if (pageContext != null) {
                request = (HttpServletRequest) pageContext.getRequest();
            } else {
                request = ServletActionContext.getRequest();
            }

            ServletActionContext.getContext().setSession(new SessionMap(request.getSession()));
        }
    }
}
