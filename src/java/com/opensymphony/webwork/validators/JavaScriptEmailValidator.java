package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.validators.EmailValidator;

import java.util.Map;

public class JavaScriptEmailValidator extends EmailValidator implements ScriptValidationAware {
    public String validationScript(Map parameters) {
        String field = (String) parameters.get("name");
        StringBuffer js = new StringBuffer();

        js.append("value = form.elements['" + field + "'].value;\n");
        //
        js.append("if (!value.match(/\\b(^(\\S+@).+((\\.com)|(\\.net)|(\\.org)|(\\.info)|(\\.edu)|(\\.mil)|(\\.gov)|(\\.biz)|(\\.ws)|(\\.us)|(\\.tv)|(\\.cc)|(\\..{2,2}))$)\\b/gi)) {\n");
        js.append("\talert('" + getMessage(null) + "');\n");
        js.append("\treturn '" + field + "';\n");
        js.append("}\n");
        js.append("\n");

        return js.toString();
    }
}
