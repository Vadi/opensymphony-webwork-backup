/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.util.GUID;
import com.opensymphony.xwork.util.LocalizedTextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;


/**
 * TokenHelper
 *
 * @author Jason Carreira
 *         Created Apr 3, 2003 9:21:53 AM
 */
public class TokenHelper {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The default name to map the token value
     */
    final public static String DEFAULT_TOKEN_NAME = "webwork.token";

    /**
     * The name of the field which will hold the token name
     */
    final public static String TOKEN_NAME_FIELD = "webwork.token.name";
    private static final Log LOG = LogFactory.getLog(TokenHelper.class);

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Sets a transaction token into the session using the default token name.
     *
     * @return the token string
     */
    public static String setToken(HttpServletRequest request) {
        return setToken(DEFAULT_TOKEN_NAME, request);
    }

    /**
     * Sets a transaction token into the session using the provided token name.
     *
     * @param tokenName the name to store into the session with the token as the value
     * @return the token string
     */
    public static String setToken(String tokenName, HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String token = GUID.generateGUID();
        session.setAttribute(tokenName, token);

        return token;
    }

    /**
     * Gets the Token value from the params in the ServletActionContext using the given name
     *
     * @param tokenName the name of the parameter which holds the token value
     * @return the token String or null, if the token could not be found
     */
    public static String getToken(String tokenName, HttpServletRequest request) {
        Map params = request.getParameterMap();
        String[] tokens = (String[]) params.get(tokenName);
        String token;

        if ((tokens == null) || (tokens.length < 1)) {
            LOG.warn("Could not find token mapped to token name " + tokenName);

            return null;
        }

        token = tokens[0];

        return token;
    }

    /**
     * Gets the token name from the Parameters in the ServletActionContext
     *
     * @return the token name found in the params, or null if it could not be found
     */
    public static String getTokenName(HttpServletRequest request) {
        Map params = request.getParameterMap();

        if (!params.containsKey(TOKEN_NAME_FIELD)) {
            LOG.warn("Could not find token name in params.");

            return null;
        }

        String[] tokenNames = (String[]) params.get(TOKEN_NAME_FIELD);
        String tokenName;

        if ((tokenNames == null) || (tokenNames.length < 1)) {
            LOG.warn("Got a null or empty token name.");

            return null;
        }

        tokenName = tokenNames[0];

        return tokenName;
    }

    /**
     * Checks for a valid transaction token in the current request params. If a valid token is found, it is
     * removed so the it is not valid again.
     *
     * @return false if there was no token set into the params (check by looking for {@link #TOKEN_NAME_FIELD}), true if a valid token is found
     */
    public static boolean validToken(HttpServletRequest request) {
        String tokenName = getTokenName(request);

        if (tokenName == null) {
            return false;
        }

        String token = getToken(tokenName, request);

        if (token == null) {
            return false;
        }

        HttpSession session = request.getSession(true);
        String sessionToken = (String) session.getAttribute(tokenName);

        if (!token.equals(sessionToken)) {
            LOG.warn(LocalizedTextUtil.findText(TokenHelper.class, "webwork.invalid.token", request.getLocale(), "Form token {0} does not match the session token {1}.", new Object[]{
                token, sessionToken
            }));

            return false;
        }

        // remove the token so it won't be used again
        session.removeAttribute(tokenName);

        return true;
    }
}
