/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 * TokenTag
 *
 * @author Jason Carreira
 *         Created Apr 2, 2003 10:44:58 PM
 */
public class TokenTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    final public static String TEMPLATE = "token";

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getTokenNameField() {
        return TokenHelper.TOKEN_NAME_FIELD;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    /**
     * First looks for the token in the PageContext using the supplied name (or {@link TokenHelper#DEFAULT_TOKEN_NAME}
     * if no name is provided) so that the same token can be re-used for the scope of a request for the same name. If
     * the token is not in the PageContext, a new Token is created and set into the Session and the PageContext with
     * the name.
     */
    protected void evaluateExtraParams(OgnlValueStack stack) {
        super.evaluateExtraParams(stack);

        String tokenName = null;
        Map parameters = getParameters();

        if (parameters.containsKey("name")) {
            tokenName = (String) parameters.get("name");
        } else {
            if (nameAttr == null) {
                tokenName = TokenHelper.DEFAULT_TOKEN_NAME;
            } else {
                tokenName = (String) findString(nameAttr);

                if (tokenName == null) {
                    tokenName = nameAttr;
                }
            }

            addParameter("name", tokenName);
        }

        String token = buildToken(tokenName);
        addParameter("token", token);
    }

    private String buildToken(String name) {
        Object myToken = pageContext.getAttribute(name);

        if (myToken == null) {
            myToken = TokenHelper.setToken(name, (HttpServletRequest) pageContext.getRequest());
            pageContext.setAttribute(name, myToken);
        }

        return myToken.toString();
    }
}
