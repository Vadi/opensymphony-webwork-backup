/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

import java.util.HashMap;
import java.util.Map;


/**
 * InvocationSessionStore
 * @author Jason Carreira
 * Created Apr 12, 2003 9:53:19 PM
 */
public class InvocationSessionStore {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final String INVOCATION_MAP_KEY = "com.opensymphony.webwork.util.InvocationSessionStore.invocationMap";

    //~ Constructors ///////////////////////////////////////////////////////////

    private InvocationSessionStore() {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
    * Checks the Map in the Session for the key and the token. If the DefaultActionInvocation is saved in the Session, it
    * gets the DefaultActionInvocation and ActionContext saved in the Session and sets the ActionContext into the
    * ActionContext ThreadLocal and returns the DefaultActionInvocation.
    * @param key the name the DefaultActionInvocation and ActionContext were saved as
    * @return the DefaultActionInvocation saved using the key, or null if none was found
    */
    public static ActionInvocation loadInvocation(String key, String token) {
        InvocationContext invocationContext = (InvocationContext) getInvocationMap().get(key);

        if ((invocationContext == null) || !invocationContext.token.equals(token)) {
            return null;
        }

        ActionContext.setContext(invocationContext.context);

        return invocationContext.invocation;
    }

    /**
    * Stores the DefaultActionInvocation and ActionContext into the Session using the provided key for loading later using
    * {@link #loadInvocation}
    * @param key
    * @param invocation
    */
    public static void storeInvocation(String key, String token, ActionInvocation invocation) {
        ActionContext context = ActionContext.getContext();
        InvocationContext invocationContext = new InvocationContext(invocation, context, token);
        Map invocationMap = getInvocationMap();
        invocationMap.put(key, invocationContext);
        setInvocationMap(invocationMap);
    }

    static void setInvocationMap(Map invocationMap) {
        Map session = ActionContext.getContext().getSession();

        if (session == null) {
            throw new IllegalStateException("Unable to access the session.");
        }

        session.put(INVOCATION_MAP_KEY, invocationMap);
    }

    static Map getInvocationMap() {
        Map session = ActionContext.getContext().getSession();

        if (session == null) {
            throw new IllegalStateException("Unable to access the session.");
        }

        Map invocationMap = (Map) session.get(INVOCATION_MAP_KEY);

        if (invocationMap == null) {
            invocationMap = new HashMap();
            setInvocationMap(invocationMap);
        }

        return invocationMap;
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    private static class InvocationContext {
        ActionContext context;
        ActionInvocation invocation;
        String token;

        public InvocationContext(ActionInvocation invocation, ActionContext context, String token) {
            this.invocation = invocation;
            this.context = context;
            this.token = token;
        }
    }
}
