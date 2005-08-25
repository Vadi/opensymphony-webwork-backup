/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.sitemesh;

/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public class InfrastructureException extends RuntimeException {

    public InfrastructureException(Throwable cause) {
        super(cause);
    }

    public InfrastructureException(String msg) {
        super(msg);
    }

    public InfrastructureException(String msg, Throwable cause) {
        super(msg, cause);
    }
}