package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.validators.VisitorFieldValidator;

import java.util.Map;

public class JavaScriptVisitorFieldValidator extends VisitorFieldValidator implements ScriptValidationAware {
    public String validationScript(Map parameters) {
        return "";
    }
}
