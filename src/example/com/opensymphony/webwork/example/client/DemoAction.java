/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.example.client;

import java.io.Serializable;

import com.opensymphony.webwork.dispatcher.client.ClientRequest;
import com.opensymphony.webwork.dispatcher.client.ClientRequestInvocation;
import com.opensymphony.xwork.Action;


/**
 * WebWork Action used by the {@link Demo Demo} class.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class DemoAction implements Action, ClientRequest {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * Variable solely to increase object size.
     */
    static Acl acl = new Acl();

    /**
     * Variable solely to increase object size.
     */
    static Bcl bcl = new Bcl(acl);

    //~ Instance fields ////////////////////////////////////////////////////////

    Bcl param2;
    int param1;
    int result1;

    /**
     * Variable solely to increase object size.
     */
    private Bcl junkHolder = null;

    /**
     * Required for {@link ClientRequest ClientRequest} interface.
     */
    private ClientRequestInvocation clientRequestInvocation;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setClientRequestInvocation(ClientRequestInvocation clientRequestInvocation) {
        this.clientRequestInvocation = clientRequestInvocation;
    }

    public ClientRequestInvocation getClientRequestInvocation() {
        return clientRequestInvocation;
    }

    /**
     * Set the number we would like squared.
     */
    public void setParam1(int param1) {
        this.param1 = param1;
    }

    /**
     * Getter for property param1.
     * @return the number we would like squared
     */
    public int getParam1() {
        return param1;
    }

    /**
     * Set an object array to increase the size of the request or reply.
     */
    public void setParam2(Bcl param2) {
        this.param2 = param2;
    }

    /**
     * Get the result of squaring <code>param1</code>. You must call
     * {@link DemoAction#execute() DemoAction.execute()} first, after first
     * building a {@link ClientRequest ClientRequest} proxy using the
     * {@link TransportFactory#createClientRequestProxy(ClientRequest)
     * TransportFactory.createClientRequestProxy(ClientRequest)} method.
     */
    public int getResult1() {
        return result1;
    }

    /**
     * Execute method. We return an <code>ERROR</code> if number 7 is the
     * <code>param1</code>, to demonstrate the operation of
     * {@link ClientRequestInvocation#getResultCode()
     * ClientRequestInvocation.getResultCode()}. We return <code>SUCCESS</code>
     * if any number other than 7 was provided.
     */
    public String execute() throws Exception {
        junkHolder = new Bcl(acl); // make return object bigger

        if (param1 == 7) {
            result1 = 7;

            return ERROR;
        } else {
            result1 = param1 * param1;

            return SUCCESS;
        }
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    /**
     * Class that is used solely to increase the object's serialized size.
     */
    private static class Acl {
        static String[][][] aclvar = new String[4][366][999];
    }

    /**
     * Class that is used solely to increase the object's serialized size.
     */
    private static class Bcl implements Serializable {
        String[][][] bclv = new String[4][366][999];

        Bcl(Acl acl) {
            for (int a = 0; a < 999; a++) {
                for (int l = 0; l < 366; l++) {
                    for (int w = 0; w < 4; w++) {
                        bclv[w][l][a] = Acl.aclvar[w][l][a];
                    }
                }
            }
        }
    }
}
