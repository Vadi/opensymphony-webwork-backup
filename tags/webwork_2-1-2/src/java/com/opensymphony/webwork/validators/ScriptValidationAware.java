/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.validators;

import java.util.Map;


/**
 * This interface indicates that its implementor can produce JavaScript to perform client-side
 * validation.
 */
public interface ScriptValidationAware {
    //~ Methods ////////////////////////////////////////////////////////////////

    public String validationScript(Map parameters);
}
