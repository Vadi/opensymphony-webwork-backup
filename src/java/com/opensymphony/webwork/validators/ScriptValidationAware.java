package com.opensymphony.webwork.validators;

import com.opensymphony.xwork.validator.FieldValidator;

import java.util.Map;

public interface ScriptValidationAware extends FieldValidator {
    public String validationScript(Map parameters);
}
