/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;

import com.opensymphony.xwork.Action;

import java.io.Serializable;

import java.util.HashMap;
import java.util.Map;


/**
 * Holds information specific to a unique {@link ClientRequest
 * ClientRequest} proxy object.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 * @author Philipp Meier (meier@meisterbohne.de)
 */
public class ClientRequestInvocation implements Serializable {
    //~ Instance fields ////////////////////////////////////////////////////////

    private transient TransportFactory transportFactory;
    private Action resultAction;
    private Map parameters;
    private String remoteActionName;
    private String resultCode;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     * Should not be called directly by users. A
     * <code>ClientRequestInvocation</code> will be generated for a given
     * {@link ClientRequest ClientRequest} whilst executing
     * {@link TransportFactory#createClientRequestProxy(ClientRequest)
     * TransportFactory.createClientRequestProxy(ClientRequest)}.
     */
    public ClientRequestInvocation(TransportFactory transportFactory, String remoteActionName) {
        this.transportFactory = transportFactory;
        this.remoteActionName = remoteActionName;
        this.parameters = new HashMap();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * Should not be called directly by users. The
     * {@link ClientRequestProxy ClientRequestProxy} will record each parameter
     * <code>set</code> performed against the associated
     * {@link ClientRequest ClientRequest} proxy object. These are ultimately
     * transferred to the server side of the Client Dispatcher.
     */
    public void setParameter(Object key, Object value) {
        parameters.put(key, value);
    }

    /**
     * Returns the list of parameters <code>set</code> against a
     * {@link ClientRequest ClientRequest} proxy object.
     * This method is not normally used by users, as the
     * {@link TransportFactory#execute(ClientRequestInvocation)
     * TransportFactory.execute(ClientRequestInvocation)}
     * method will serialize this information to the remote side of the
     * Client Dispatcher.
     */
    public Map getParameters() {
        return parameters;
    }

    /**
     * Sets the action name that will be executed on the remote side of the
     * Client Dispatcher. This remote name must match an action defined in
     * <code>xwork.xml</code> through a
     * <code>&lt;action name="remoteActionName"
     * class="com.some.company.class"&gt;</code>
     * setting.<BR><BR>
     *
     * {@link TransportFactory TransportFactory} implementations usually set
     * a remote action name to be equal to the class name of the
     * {@link ClientRequest ClientRequest}.<BR><BR>
     *
     * Users should ensure this method is used to correctly set the intended
     * remote action.
     */
    public void setRemoteActionName(String remoteActionName) {
        this.remoteActionName = remoteActionName;
    }

    /**
     * Return the action name that will be executed on the server side of the
     * Client Dispatcher.
     */
    public String getRemoteActionName() {
        return remoteActionName;
    }

    /**
     * Should not be called by users. This sets the final remote action that
     * was executed on the XWork Value Stack. This method is called by the
     * {@link ClientRequestProxy#execute ClientRequestProxy.execute} method.
     */
    public void setResultAction(Action resultAction) {
        this.resultAction = resultAction;
    }

    /**
     * After the {@link ClientRequestInvocation#execute()
     * ClientRequestInvocation.execute()} method has been called via the
     * {@link ClientRequest ClientRequest} proxy object, this method returns
     * the final action that was in the server's XWork Value Stack.
     * This is a serialized copy of that Action and is therefore limited
     * to normal serialization behaviour (eg objects marked
     * <code>transient</code> or that do not implement <code>
     * java.io.Serializable</code> will not be serialized).
     */
    public Action getResultAction() {
        return resultAction;
    }

    /**
     * Should not be called by users. This sets the final remote
     * <code>String</code> result code. This method is called by the
     * {@link ClientRequestProxy#execute ClientRequestProxy.execute} method.
     */
    public void setResultCode(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * After the {@link ClientRequestInvocation#execute()
     * ClientRequestInvocation.execute()} method has been called via the
     * {@link ClientRequest ClientRequest} proxy object, this method returns
     * the <code>String</code> result code from the remote action's
     * <code>execute()</code> method. This is typically <code>SUCCESS</code>,
     * <code>INPUT</code>, <code>ERROR</code> etc.
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * Should not be called directly by users. The
     * {@link ClientRequestProxy ClientRequestProxy} will call this
     * method as part of its interception of the <code>execute</code> method
     * of the {@link ClientRequest ClientRequest} proxy object.
     */
    public RemoteResult execute() throws ClientException {
        return transportFactory.execute(this);
    }
}
