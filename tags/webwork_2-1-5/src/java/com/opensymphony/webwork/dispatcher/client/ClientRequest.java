/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;


/**
 * Interface that must be implemented by any client-side object that needs to
 * be processed by the Client Dispatcher. Generally, normal XWork Action
 * objects will implement this interface and thus enable them to be used on
 * both the client and the server tiers.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public interface ClientRequest {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Sets the {@link ClientRequestInvocation ClientRequestInvocation}
     * that is specific to this <code>ClientRequest</code> proxy object. This
     * method should only be called by
     * {@link TransportFactory#createClientRequestProxy(ClientRequest)
     * TransportFactory.createClientRequestProxy(ClientRequest)}.
     */
    public void setClientRequestInvocation(ClientRequestInvocation clientRequestInvocation);

    /**
     * Provides the {@link ClientRequestInvocation ClientRequestInvocation}
     * that is specific to this <code>ClientRequest</code> proxy object.
     */
    public ClientRequestInvocation getClientRequestInvocation();
}
