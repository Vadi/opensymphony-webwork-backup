/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;


/**
 * This class represents an interceptor added to {@link ClientRequest
 * ClientRequest} proxy objects. This class is added via the
 * {@link TransportFactory#createClientRequestProxy(ClientRequest)
 * TransportFactory.createClientRequestProxy(ClientRequest)}
 * method.<BR><BR>
 *
 * This class is responsible for ensuring the {@link ClientRequestInvocation
 * ClientRequestInvocation} associated with a given {@link ClientRequest
 * ClientRequest} proxy object is properly notified whenever calls are made to
 * <code>set</code> methods and the <code>execute</code> method.<BR><BR>
 *
 * Users should not need to interact directly with this class.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class ClientRequestProxy implements MethodInterceptor {
    //~ Methods ////////////////////////////////////////////////////////////////

    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        ClientRequest clientAction = (ClientRequest) obj;
        Object result = null;

        if (method.getName().equals("execute")) {
            RemoteResult car = clientAction.getClientRequestInvocation().execute();
            clientAction.getClientRequestInvocation().setResultAction(car.getAction());
            clientAction.getClientRequestInvocation().setResultCode(car.getResultCode());
            result = car.getResultCode();
        } else if (method.getName().startsWith("set")) {
            String propertyName = method.getName().substring(3, method.getName().length());
            result = proxy.invokeSuper(obj, args);
            clientAction.getClientRequestInvocation().setParameter(propertyName, args[0]);
        }

        return result;
    }
}
