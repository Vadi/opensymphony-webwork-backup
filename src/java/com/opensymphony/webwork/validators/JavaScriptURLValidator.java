package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.validators.URLValidator;

import java.util.Map;

public class JavaScriptURLValidator extends URLValidator implements ScriptValidationAware {
    public String validationScript(Map parameters) {
        return "";
    }
}
