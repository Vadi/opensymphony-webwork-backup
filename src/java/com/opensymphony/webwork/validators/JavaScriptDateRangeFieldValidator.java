package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.validators.DateRangeFieldValidator;

import java.util.Map;

public class JavaScriptDateRangeFieldValidator extends DateRangeFieldValidator implements ScriptValidationAware {
    public String validationScript(Map parameters) {
        return "";
    }
}
