package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.validators.URLValidator;

import java.util.Map;

public class JavaScriptURLValidator extends URLValidator implements ScriptValidationAware {
    public String validationScript(Map parameters) {
        String field = (String) parameters.get("name");
        StringBuffer js = new StringBuffer();

        js.append("value = form.elements['" + field + "'].value;\n");
        js.append("if (!value.match(/^(file|http):\\/\\/\\S+\\.(com|net|org|info|edu|mil|gov|biz|ws|us|tv|cc)$/i)) {\n");
        js.append("\talert('" + getMessage(null) + "');\n");
        js.append("\treturn '" + field + "';\n");
        js.append("}\n");
        js.append("\n");

        return js.toString();
    }
}
