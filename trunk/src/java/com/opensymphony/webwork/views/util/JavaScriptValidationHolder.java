/*
 * Copyright (c) 2004 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.views.util;

import com.opensymphony.webwork.validators.JavaScriptVisitorFieldValidator;
import com.opensymphony.webwork.validators.ScriptValidationAware;
import com.opensymphony.xwork.ModelDriven;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.validator.*;
import ognl.OgnlRuntime;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.beans.PropertyDescriptor;
import java.util.*;

/**
 * @author CameronBraid
 */
public class JavaScriptValidationHolder {

    public JavaScriptValidationHolder(String actionName, Class actionClass, OgnlValueStack stack) {
        this.actionName = actionName;
        this.actionClass = actionClass;
        this.valueStack = stack;
    }

    private static final Log LOG = LogFactory.getLog(JavaScriptValidationHolder.class);

    OgnlValueStack valueStack;
    Class actionClass;
    String actionName;
    List fieldValidators = new ArrayList();
    List fieldParameters = new ArrayList();
    
    public boolean hasValidators() {
        return (fieldValidators.size() > 0);
    }
    
    public String toJavaScript() {
        StringBuffer js = new StringBuffer();

        // loop backwards so that the first elements are validated first
        for (int i = 0; i < fieldValidators.size(); i++) {
            ScriptValidationAware sva = (ScriptValidationAware) fieldValidators.get(i);
            Map params = (Map) fieldParameters.get(i);
            js.append(sva.validationScript(params));
            js.append('\n');
        }
        return js.toString();
    }
    
    public void registerValidator(ScriptValidationAware scriptValidator, Map params) {
        fieldValidators.add(scriptValidator);
        fieldParameters.add(params);
    }
    
    public void registerValidateField(String fieldName, Map parameters) {
        registerScriptingValidators(fieldName, parameters, actionClass, null);
    }

    /**
     * Finds all ScriptValidationAware validators that apply to the field covered by this tag.
     *
     * @param fieldName    the name of the field to validate (used for error message key)
     * @param parameters   any parameters that can be used in generating the validation message
     * @param fieldClass   the Class of the object the field is for
     * @param propertyName the actual property name to get validator for; if null, fieldName is used
     */
    protected void registerScriptingValidators(String fieldName, Map parameters, Class fieldClass, String propertyName) {
        List validators = ActionValidatorManager.getValidators(fieldClass, actionName);
        String name = fieldName;

        if (propertyName != null) {
            name = propertyName;
        }

        for (Iterator iterator = validators.iterator(); iterator.hasNext();) {
            Validator validator = (Validator) iterator.next();

            if (!(validator instanceof ScriptValidationAware)) {
                continue;
            }

            ValidatorContext validatorContext = new DelegatingValidatorContext(fieldClass);

            if (validator instanceof FieldValidator) {
                FieldValidator fieldValidator = (FieldValidator) validator;

                // JavaScriptVisitorFieldValidators must validate model, not action
                if (validator instanceof JavaScriptVisitorFieldValidator) {
                    JavaScriptVisitorFieldValidator visitorValidator = (JavaScriptVisitorFieldValidator) validator;
                    String propName = null;
                    boolean visit;

                    if (visitorValidator.getFieldName().equals("model") && ModelDriven.class.isAssignableFrom(fieldClass)) {
                        visit = true;
                    } else {
                        String baseName = name;
                        int idx = name.indexOf(".");

                        if (idx != -1) {
                            baseName = name.substring(0, idx);
                            propName = name.substring(idx + 1);
                        }

                        visit = baseName.equals(visitorValidator.getFieldName());
                    }

                    if (visit) {
                        Class realFieldClass = visitorValidator.getValidatedClass();

                        if (realFieldClass == null) {
                            for (Iterator iterator1 = valueStack.getRoot().iterator(); iterator1.hasNext();) {
                                Object o = iterator1.next();
                                try {
                                    PropertyDescriptor pd =
                                            OgnlRuntime.getPropertyDescriptor(o.getClass(), visitorValidator.getFieldName());
                                    realFieldClass = pd.getPropertyType();
                                    break;
                                } catch (Throwable t) {
                                    // just keep trying
                                }
                            }
                        }

                        if (realFieldClass != null) {
                            if (visitorValidator.isAppendPrefix()) {
                                registerScriptingValidators(visitorValidator.getFieldName() + "." + name, parameters, realFieldClass, propName);
                            } else {
                                registerScriptingValidators(name, parameters, realFieldClass, propName);
                            }
                        } else {
                            LOG.warn("Cannot figure out class of visited object");
                        }
                    }
                } else if (fieldValidator.getFieldName().equals(name)) {
                    validator.setValidatorContext(validatorContext);
                    registerValidator((ScriptValidationAware) fieldValidator, new HashMap(parameters));
                }
            } else {
                validator.setValidatorContext(validatorContext);
                registerValidator((ScriptValidationAware) validator, new HashMap(parameters));
            }
        }
    }
}
