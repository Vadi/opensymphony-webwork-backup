/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import com.opensymphony.xwork.Action;

import java.io.Serializable;


/**
 * Holds information related to the outcome of a server action execution.
 * Upon receipt on the client, the contents of the <code>RemoteResult</code>
 * are copied into the {@link ClientRequestInvocation ClientRequestInvocation}
 * and are made available through
 * {@link ClientRequestInvocation#getResultAction()
 * ClientRequestInvocation.getResultAction()} and
 * {@link ClientRequestInvocation#getResultCode()()
 * ClientRequestInvocation.getResultCode()}.<BR><BR>
 *
 * This class should never be used directly by end users.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class RemoteResult implements Serializable {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Action action;
    private String resultCode;
    private String sessionId;

    //~ Constructors ///////////////////////////////////////////////////////////

    public RemoteResult(Action action, String resultCode, String sessionId) {
        this.action = action;
        this.resultCode = resultCode;
        this.sessionId = sessionId;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Action getAction() {
        return action;
    }

    public String getResultCode() {
        return resultCode;
    }

    public String getSessionId() {
        return sessionId;
    }
}
