/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.util.GUID;

import com.opensymphony.webwork.ServletActionContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;


/**
 * TokenHelper
 * @author Jason Carreira
 * Created Apr 3, 2003 9:21:53 AM
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
    * @return the token string
    */
    public static String setToken() {
        return setToken(DEFAULT_TOKEN_NAME);
    }

    /**
    * Sets a transaction token into the session using the provided token name.
    * @param tokenName the name to store into the session with the token as the value
    * @return the token string
    */
    public static String setToken(String tokenName) {
        Map session = ServletActionContext.getContext().getSession();
        String token = GUID.generateGUID();
        session.put(tokenName, token);

        return token;
    }

    /**
    * Gets the Token value from the params in the ServletActionContext using the given name
    * @param tokenName the name of the parameter which holds the token value
    * @return the token String or null, if the token could not be found
    */
    public static String getToken(String tokenName) {
        Map params = ServletActionContext.getContext().getParameters();
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
    * @return the token name found in the params, or null if it could not be found
    */
    public static String getTokenName() {
        Map params = ServletActionContext.getContext().getParameters();

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
    * @return true if there was no token set into the params (check by looking for {@link #TOKEN_NAME_FIELD})
    *
    */
    public static boolean validToken() {
        String tokenName = getTokenName();

        if (tokenName == null) {
            return true;
        }

        String token = getToken(tokenName);

        if (token == null) {
            return false;
        }

        Map session = ServletActionContext.getContext().getSession();
        String sessionToken = (String) session.get(tokenName);

        if (!token.equals(sessionToken)) {
            LOG.warn("Form token " + token + " does not match the session token " + sessionToken);

            return false;
        }

        // remove the token so it won't be used again
        session.remove(tokenName);

        return true;
    }
}
