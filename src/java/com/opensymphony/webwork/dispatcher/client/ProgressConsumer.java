/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;


/**
 * Interface this is implemented by classes wishing to receive
 * progress notifications whilst
 * {@link TransportFactory#execute(ClientRequestInvocation)
 * TransportFactory.execute(ClientRequestInvocation)} is running.
 *
 * Users should use the {@link
 * TransportFactory#setProgressConsumer(ProgressConsumer)
 * TransportFactory.setProgressConsumer(ProgressConsumer)} method to denote
 * which <code>ProgressConsumer</code> should be used.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public interface ProgressConsumer {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Notifies the <code>ProgressConsumer</code> implementation that another
     * stage of request procesing has occurred. The details of the progress
     * are contained in the {@link ProgressNotification ProgressNotification}.
     */
    public void notify(ProgressNotification progressNotification);
}
