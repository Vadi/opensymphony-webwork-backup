/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.FieldValidator;

import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public interface ScriptValidationAware extends FieldValidator {
    //~ Methods ////////////////////////////////////////////////////////////////

    public String validationScript(Map parameters);
}
