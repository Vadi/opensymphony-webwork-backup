/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import java.util.Properties;


/**
 * Interface that is implemented by connection factories.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public interface TransportFactory {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
 * Constant that indicates the session ID allocated to this factory
 * by the remote server is either unknown, not supported, or has been
 * reset by a call to the {@link TransportFactory#resetSessionId()
 * TransportFactory.resetSessionId()} method.
 */
    public static final String SESSION_UNDEFINED = new String("Session ID undefined");

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
 * Sets an object that will consume progress notifications during
 * {@link TransportFactory#execute(ClientRequestInvocation)
 * TransportFactory.execute(ClientRequestInvocation)} operations.
 */
    public void setProgressConsumer(ProgressConsumer progressConsumer);

    /**
 * Sets the <code>Properties</code> used by the factory and all of its
 * created {@link ClientRequest ClientRequest} proxy objects.
 */
    public void setProperties(Properties properties) throws ClientException;

    /**
 * Returns the <code>Properties</code> used by the factory and all of
 * its created {@link ClientRequest ClientRequest} proxy objects.
 */
    public Properties getProperties();

    /**
 * Returns the current session ID used by this factory. A factory will
 * start using a new session ID if any reply from the server contains a new
 * session ID.
 */
    public String getSessionId();

    /**
 * Returns an enhanced {@link ClientRequest ClientRequest} object
 * that contains an interceptor as defined by {@link ClientRequestProxy
 * ClientRequestProxy}. The enhanced class is referred to as a
 * "{@link ClientRequest ClientRequest} proxy object".
 * @param namespace webwork namespace that the remote action is in. Set to null if the remote
 * action is in the default namespace.
 */
    public Object createClientRequestProxy(ClientRequest clientAction, String namespace);

    /**
 * Should not be called directly. Normal users should only use the
 * <code>execute()</code> method of their {@link ClientRequest ClientRequest}
 * proxy object. This method is called by the
 * {@link ClientRequestInvocation#execute()
 * ClientRequestInvocation.execute()} method.<BR><BR>
 */
    public RemoteResult execute(ClientRequestInvocation invocation) throws ClientException;

    /**
 * Instructs the factory to no longer present the current session ID.
 * The factory is not required to notify the server of this cancellation.
 * It merely prevents the factory from presenting the current session ID
 * again.
 */
    public void resetSessionId();
}
