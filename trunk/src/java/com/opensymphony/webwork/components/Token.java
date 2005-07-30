package com.opensymphony.webwork.components;

import com.opensymphony.webwork.util.TokenHelper;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * User: plightbo
 * Date: Jul 20, 2005
 * Time: 7:02:04 AM
 */
public class Token extends UIBean {
    final public static String TEMPLATE = "token";

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
            myToken = TokenHelper.setToken(name, request);
            context.put(name, myToken);
        }

        return myToken.toString();
    }
}
