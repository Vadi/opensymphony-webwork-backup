/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.exception;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;


/**
 * DuplicateKeyException.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tmjee
 */

public class DuplicateKeyException extends CreateException {

    private static final Log log = LogFactory.getLog(DuplicateKeyException.class);

    public DuplicateKeyException(String message) {
        super(message);
    }

    public DuplicateKeyException(Throwable cause) {
        super(cause);
    }

    public DuplicateKeyException(String message, Throwable cause) {
        super(message, cause);
    }

}
