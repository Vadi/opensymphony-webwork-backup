/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.validators.RequiredStringValidator;

import java.util.Map;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class JavaScriptRequiredStringValidator extends RequiredStringValidator implements ScriptValidationAware {
    //~ Methods ////////////////////////////////////////////////////////////////

    public String validationScript(Map parameters) {
        String field = (String) parameters.get("name");
        StringBuffer js = new StringBuffer();

        js.append("value = form.elements['" + field + "'].value;\n");
        js.append("if (value == \"\") {\n");
        js.append("\talert('" + getMessage(null) + "');\n");
        js.append("\treturn '" + field + "';\n");
        js.append("}\n");
        js.append("\n");

        return js.toString();
    }
}
