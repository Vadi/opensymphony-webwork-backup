/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;
import net.sf.cglib.transform.MethodFilter;

import java.util.Properties;
import java.util.Random;


/**
 * Provides an abstract implementation of a {@link TransportFactory
 * TransportFactory}.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public abstract class TransportFactoryBase implements TransportFactory {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected String sessionId = SESSION_UNDEFINED;
    private ProgressConsumer progressConsumer = new ProgressConsumerNull();
    private Properties properties;
    private Random random = new Random();

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setProperties(Properties properties) throws ClientException {
        this.properties = properties;
    }

    public Properties getProperties() {
        return this.properties;
    }

    public Object createClientRequestProxy(ClientRequest clientAction, String namespace) {
        Class clazz = clientAction.getClass();

        Object enhanced = Enhancer.create(clazz, new Class[] {ClientRequest.class}, new CallbackFilter() {
                public int accept(java.lang.reflect.Method member) {
                    if (member.getName().startsWith("set") && !member.getName().startsWith("setClientRequestInvocation")) {
                        return 0;
                    } else if (member.getName().equals("execute")) {
                        return 0;
                    } else {
                        return 1;
                    }
                }
            }, new Callback[] {new ClientRequestProxy(), NoOp.INSTANCE});

        ClientRequest returnObject = (ClientRequest) enhanced;
        String actionName = null;

        if (namespace != null) {
            actionName = "/" + namespace + "/" + clientAction.getClass().getName() + ".action";
        } else {
            actionName = clientAction.getClass().getName();
        }

        returnObject.setClientRequestInvocation(new ClientRequestInvocation(this, actionName));

        return returnObject;
    }

    public abstract RemoteResult execute(ClientRequestInvocation invocation) throws ClientException;

    public void setProgressConsumer(ProgressConsumer progressConsumer) {
        this.progressConsumer = progressConsumer;
    }

    public ProgressConsumer getProgressConsumer() {
        return this.progressConsumer;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    /**
 * Generates a new request ID. This is simply a random number.
 * Used for the <code>id</code> in
 * {@link ProgressNotification#ProgressNotification(String)
 * ProgressNotification.ProgressNotification(String id)} creations.
 */
    public String generateRequestId() {
        return new Double(random.nextDouble() * 10).toString().replace('.', '0');
    }

    public void resetSessionId() {
        this.sessionId = SESSION_UNDEFINED;
    }
}
