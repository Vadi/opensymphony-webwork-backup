/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.exception;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * UpdateException.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tmjee
 */

public class UpdateException extends StorageException {

    private static final Log log = LogFactory.getLog(UpdateException.class);

    public UpdateException(String message) {
        super(message);
    }

    public UpdateException(Throwable cause) {
        super(cause);
    }

    public UpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
