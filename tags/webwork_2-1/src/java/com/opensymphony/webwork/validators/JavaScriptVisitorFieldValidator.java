/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.validators.VisitorFieldValidator;

import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class JavaScriptVisitorFieldValidator extends VisitorFieldValidator implements ScriptValidationAware {
    //~ Methods ////////////////////////////////////////////////////////////////

    public String validationScript(Map parameters) {
        return "";
    }
}
