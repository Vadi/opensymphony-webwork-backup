package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.validators.IntRangeFieldValidator;

import java.util.Map;

public class JavaScriptIntRangeFieldValidator extends IntRangeFieldValidator implements ScriptValidationAware {
    public String validationScript(Map parameters) {
        String field = (String) parameters.get("name");
        StringBuffer js = new StringBuffer();

        js.append("value = form.elements['" + field + "'].value;\n");
        js.append("if (value < " + getMin() + " || value > " + getMax() + ") {\n");
        js.append("\talert('" + getMessage(null) + "');\n");
        js.append("\treturn '" + field + "';\n");
        js.append("}\n");
        js.append("\n");

        return js.toString();
    }
}
