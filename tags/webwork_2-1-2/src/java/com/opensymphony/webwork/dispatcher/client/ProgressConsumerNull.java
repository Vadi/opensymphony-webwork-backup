/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;


/**
 * Consumes process notifications, but does nothing with them.
 * A {@link TransportFactory TransportFactory} should
 * use this class as its default {@link ProgressConsumer ProgressConsumer},
 * in case the user has not set one via the
 * {@link TransportFactory#setProgressConsumer(ProgressConsumer)
 * TransportFactory.setProgressConsumer(ProgressConsumer)} method.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class ProgressConsumerNull implements ProgressConsumer {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void notify(ProgressNotification progressNotification) {
    }
}
