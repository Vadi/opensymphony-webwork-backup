/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.config.entities.ActionConfig;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class HTTPClientObjectFactory extends ObjectFactory {
    //~ Instance fields ////////////////////////////////////////////////////////

    /** transport factory used to talk to the server */
    private TransportFactory transportFactory;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
 * Default constructor
 * @param transport The transport factory implementation to use to communicate with the remote
 * server
 */
    public HTTPClientObjectFactory(TransportFactory transport) {
        if (transport == null) {
            throw new IllegalArgumentException("Transport factory must not be null");
        }

        transportFactory = transport;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
 * Build an action from the configuration
 *
 * @param config The action configuration to use to find the action. The action referenced
 * must implement {@link ClientRequest ClientRequest} for this to work.
 * @throws Exception If there is a problem creating the action. In particular the action must
 * implement {@link ClientRequest ClientRequest} or a class cast execption will occur.
 * @return An action set up to be called remotely
 */
    public Action buildAction(ActionConfig config) throws Exception {
        Action localAction = (Action) buildBean(config.getClassName());
        String namespace = config.getPackageName();
        Action remoteAction = (Action) transportFactory.createClientRequestProxy((ClientRequest) localAction, namespace);

        return remoteAction;
    }
}
