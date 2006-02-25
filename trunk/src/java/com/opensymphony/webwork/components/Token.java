/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components;

import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * <!-- START SNIPPET: javadoc -->
 * Stop double-submission of forms.</p>
 *
 * The token tag is used to help with the "double click" submission problem. It is needed if you are using the
 * TokenInterceptor or the TokenSessionInterceptor. The ww:token tag merely places a hidden element that contains
 * the unique token.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;ww:token /&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Rene Gielen
 * @author Rainer Hermanns
 * @version $Date$ $Id$
 * @since 2.2
 *
 * @see com.opensymphony.webwork.interceptor.TokenInterceptor
 * @see com.opensymphony.webwork.interceptor.TokenSessionStoreInterceptor
 *
 * @ww.tag name="token" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.TokenTag"
 * description="Stop double-submission of forms"
 */
public class Token extends UIBean {
    
    public static final String TEMPLATE = "token";

    public Token(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    /**
     * First looks for the token in the PageContext using the supplied name (or {@link com.opensymphony.webwork.util.TokenHelper#DEFAULT_TOKEN_NAME}
     * if no name is provided) so that the same token can be re-used for the scope of a request for the same name. If
     * the token is not in the PageContext, a new Token is created and set into the Session and the PageContext with
     * the name.
     */
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        String tokenName;
        Map parameters = getParameters();

        if (parameters.containsKey("name")) {
            tokenName = (String) parameters.get("name");
        } else {
            if (name == null) {
                tokenName = TokenHelper.DEFAULT_TOKEN_NAME;
            } else {
                tokenName = findString(name);

                if (tokenName == null) {
                    tokenName = name;
                }
            }

            addParameter("name", tokenName);
        }

        String token = buildToken(tokenName);
        addParameter("token", token);
        addParameter("tokenNameField", TokenHelper.TOKEN_NAME_FIELD);
    }

    /**
     * @deprecated Templates should use $parameters from now on, not $tag.
     * This will be removed in a future version of WebWork.
     */
    public String getTokenNameField() {
        return TokenHelper.TOKEN_NAME_FIELD;
    }

    private String buildToken(String name) {
        Map context = stack.getContext();
        Object myToken = context.get(name);

        if (myToken == null) {
            myToken = TokenHelper.setToken(name);
            context.put(name, myToken);
        }

        return myToken.toString();
    }
}
