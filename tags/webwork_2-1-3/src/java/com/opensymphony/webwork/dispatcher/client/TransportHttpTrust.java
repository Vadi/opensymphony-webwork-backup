/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import java.util.Properties;

import javax.net.ssl.SSLSocketFactory;


/**
 * Interface that enables the delegation of <code>SSLSocketFactory</code>
 * creation to an external class. Used by {@link TransportHttp TransportHttp}
 * for its <code>trustManager</code> <code>Properties</code> setting.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public interface TransportHttpTrust {
    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Returns true if the connection is encrypted.
     * Used by the factory to set the
     * {@link ProgressNotification#SECURITY_ENCRYPTED
     * ProgressNotification.SECURITY_ENCRYPTED}.
     */
    public boolean isEncrypted();

    /**
     * Returns true if the connection is properly identified by a public key.
     * Used by the factory to set the
     * {@link ProgressNotification#SECURITY_IDENTIFIED
     * ProgressNotification.SECURITY_IDENTIFIED}.
     */
    public boolean isIdentified();

    /**
     * Returns information about the service. This is added to the factory
     * information provided to
     * {@link ProgressNotification#setFactoryInformation(Properties)
     * ProgressNotification.setFactoryInformation(Properties)}.
     */
    public Properties serviceInformation();

    public SSLSocketFactory start(Properties properties) throws ClientException;
}
