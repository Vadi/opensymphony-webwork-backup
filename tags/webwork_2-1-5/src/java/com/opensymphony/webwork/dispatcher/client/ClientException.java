/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.client;


/**
 * Generic exception used by classes in the
 * <code>com.opensymphony.webwork.dispatcher.client</code> package.
 *
 * @version $Id$
 * @author Ben Alex (<a href="mailto:ben.alex@acegi.com.au">ben.alex@acegi.com.au</a>)
 */
public class ClientException extends Exception {
    //~ Instance fields ////////////////////////////////////////////////////////

    private String text;
    private Throwable cause;

    //~ Constructors ///////////////////////////////////////////////////////////

    public ClientException(Throwable cause) {
        super(cause.getMessage());
        this.cause = cause;
    }

    public ClientException(String text) {
        this.text = text;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public Throwable getCause() {
        return cause;
    }
}
