/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.DefaultActionProxyFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Map;


/**
 * This class is intended for use in conjunction with {@link HTTPClientObjectFactory HTTPClientObjectFactory}
 * to allow the remote action invocation to be placed on the value stack on the client rather
 * than the local one.
 * @author Peter Kelley
 */
public class HTTPClientActionProxyFactory extends DefaultActionProxyFactory {
    //~ Static fields/initializers /////////////////////////////////////////////

    /** logger for this class */
    private static final Log log = LogFactory.getLog(HTTPClientActionProxyFactory.class);

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Default constructor
     */
    public HTTPClientActionProxyFactory() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * This method adds a pre result listener to allow replacement of the object at the top of the
     * value stack with the one returned from the server..
     *
     * @param actionProxy The action proxy to create the invocation for
     * @return a {@link com.opensymphony.xwork.DefaultActionInvocation DefaultActionInvocation} with
     * a {link HTTPClientPreResultListener HTTPClientPreResultListener} added.
     * @throws Exception if something goes wrong
     */
    public ActionInvocation createActionInvocation(ActionProxy actionProxy) throws Exception {
        ActionInvocation result = super.createActionInvocation(actionProxy);

        if (log.isDebugEnabled()) {
            log.debug("Adding pre result listener");
        }

        result.addPreResultListener(new HTTPClientPreResultListener());

        return result;
    }

    /**
     * This method adds a pre result listener to allow replacement of the object at the top of the
     * value stack with the one returned from the server..
     *
     * @param actionProxy The action proxy to create the invocation for
     * @param extraContext Extra information needed for this action invocation
     * @return a {@link com.opensymphony.xwork.DefaultActionInvocation DefaultActionInvocation}
     *   with a {link HTTPClientPreResultListener HTTPClientPreResultListener} added.
     * @throws Exception if something goes wrong
     */
    public ActionInvocation createActionInvocation(ActionProxy actionProxy, Map extraContext) throws Exception {
        ActionInvocation result = super.createActionInvocation(actionProxy, extraContext);

        if (log.isDebugEnabled()) {
            log.debug("Adding pre result listener");
        }

        result.addPreResultListener(new HTTPClientPreResultListener());

        return result;
    }

    /**
     * This method adds a pre result listener to allow replacement of the object at the top of the
     * value stack with the one returned from the server..
     *
     * @param actionProxy The action proxy to create the invocation for
     * @param extraContext Extra information needed for this action invocation
     * @param pushAction whether to oput this action on the value stack after invocation
     * @return a {@link com.opensymphony.xwork.DefaultActionInvocation DefaultActionInvocation}
     *   with a {link HTTPClientPreResultListener HTTPClientPreResultListener} added.
     * @throws Exception if something goes wrong
     */
    public ActionInvocation createActionInvocation(ActionProxy actionProxy, Map extraContext, boolean pushAction) throws Exception {
        ActionInvocation result = super.createActionInvocation(actionProxy, extraContext, pushAction);

        if (pushAction) {
            result.addPreResultListener(new HTTPClientPreResultListener());
            log.debug("Adding pre result listener");
        } else {
            log.debug("Not adding pre result listener as action is not on the stack");
        }

        return result;
    }
}
