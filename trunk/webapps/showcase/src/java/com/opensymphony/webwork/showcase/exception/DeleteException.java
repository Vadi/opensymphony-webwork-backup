/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.showcase.exception;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * DeleteException.
 *
 * @author <a href="mailto:gielen@it-neering.net">Rene Gielen</a>
 * @author tmjee
 */

public class DeleteException extends StorageException {

    private static final Log log = LogFactory.getLog(DeleteException.class);

    public DeleteException(String message) {
        super(message);
    }

    public DeleteException(Throwable cause) {
        super(cause);
    }

    public DeleteException(String message, Throwable cause) {
        super(message, cause);
    }

}
