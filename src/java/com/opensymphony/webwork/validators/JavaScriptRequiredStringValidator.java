package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.validators.RequiredStringValidator;

import java.util.Map;

public class JavaScriptRequiredStringValidator extends RequiredStringValidator implements ScriptValidationAware {
    public String validationScript(Map parameters) {
        String field = (String) parameters.get("name");
        StringBuffer js = new StringBuffer();

        js.append("value = form.elements['" + field + "'].value;\n");
        js.append("if (value == \"\") {\n");
        js.append("\talert('" + getMessage(null) + "');\n");
        js.append("\treturn false;\n");
        js.append("}\n");
        js.append("\n");

        return js.toString();
    }
}
