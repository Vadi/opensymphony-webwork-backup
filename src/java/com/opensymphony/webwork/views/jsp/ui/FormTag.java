/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.validators.ScriptValidationAware;
import com.opensymphony.webwork.views.jsp.TagUtils;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * FormTag
 *
 * @author Jason Carreira
 *         Created Apr 1, 2003 8:19:47 PM
 */
public class FormTag extends AbstractClosingUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    final public static String OPEN_TEMPLATE = "form";
    final public static String TEMPLATE = "form-close";

    //~ Instance fields ////////////////////////////////////////////////////////

    Class actionClass;
    List fieldParameters;
    List fieldValidators;
    String actionAttr;
    String actionName;
    String enctypeAttr;
    String methodAttr;
    String namespaceAttr;
    String validateAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAction(String action) {
        this.actionAttr = action;
    }

    public Class getActionClass() {
        return actionClass;
    }

    public String getActionName() {
        return actionName;
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    public void setEnctype(String enctype) {
        this.enctypeAttr = enctype;
    }

    public void setMethod(String method) {
        this.methodAttr = method;
    }

    public void setNamespace(String namespace) {
        this.namespaceAttr = namespace;
    }

    public void setValidate(String validate) {
        this.validateAttr = validate;
    }

    public void evaluateExtraParams(OgnlValueStack stack) {
        super.evaluateExtraParams(stack);

        if (actionAttr != null) {
            /**
             * If called from a JSP, pageContext will not be null.  otherwise, we'll get request and response from the
             * ServletActionContext.
             *
             * todo - determine if there's any reason we can't just always use ServletActionContext
             */
            HttpServletResponse response;
            HttpServletRequest request;

            if (pageContext != null) {
                response = (HttpServletResponse) pageContext.getResponse();
                request = (HttpServletRequest) pageContext.getRequest();
            } else {
                request = ServletActionContext.getRequest();
                response = ServletActionContext.getResponse();
            }

            String action = (String) findString(actionAttr);
            String namespace;

            if (namespaceAttr == null) {
                namespace = TagUtils.buildNamespace(getStack(), (HttpServletRequest) pageContext.getRequest());
            } else {
                namespace = findString(namespaceAttr);
            }

            if (namespace == null) {
                namespace = "";
            }

            ActionConfig actionConfig = ConfigurationManager.getConfiguration().getRuntimeConfiguration().getActionConfig(namespace, action);

            if (actionConfig != null) {
                try {
                    actionClass = ObjectFactory.getObjectFactory().getClassInstance(actionConfig.getClassName());
                } catch (ClassNotFoundException e) {
                    // this is ok
                }

                actionName = action;

                String result = UrlHelper.buildUrl(namespace + "/" + action + "." + Configuration.get("webwork.action.extension"), request, response, null);
                addParameter("action", result);
            } else if (action != null) {
                String result = UrlHelper.buildUrl(action, request, response, null);
                addParameter("action", result);
            }
        }

        if (enctypeAttr != null) {
            addParameter("enctype", findString(enctypeAttr));
        }

        if (methodAttr != null) {
            addParameter("method", findString(methodAttr));
        }

        if (validateAttr != null) {
            addParameter("validate", findValue(validateAttr, Boolean.class));
        }

        if (fieldValidators != null) {
            StringBuffer js = new StringBuffer();

            // loop backwards so that the first elements are validated first
            for (int i = 0; i < fieldValidators.size(); i++) {
                ScriptValidationAware sva = (ScriptValidationAware) fieldValidators.get(i);
                Map params = (Map) fieldParameters.get(i);
                js.append(sva.validationScript(params));
                js.append('\n');
            }

            addParameter("javascriptValidation", js.toString());
        } else {
            addParameter("javascriptValidation", "// cannot find any applicable validators");
        }
    }

    /**
     * Registers ScriptAware validators that should be called when the form is closed to output
     * necessary script.
     * <p />
     * Registration of validators is open until the first time the end of the tag is reached or
     * there will be duplicate validators if the tag is cached.
     */
    public void registerValidator(ScriptValidationAware sva, Map params) {
        if (fieldValidators == null) {
            fieldValidators = new ArrayList();
            fieldParameters = new ArrayList();
        }

        fieldValidators.add(sva);
        fieldParameters.add(params);
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected boolean evaluateNameValue() {
        return false;
    }

    /**
     * Resets the attributes of this tag so that the tag may be reused.  As a general rule, only
     * properties that are not specified as an attribute or properties that are derived need to be
     * reset.  Examples of this would include the parameters Map in ParameterizedTag and the
     * namespace in the ActionTag (which can be a derived value). <p /> This should be the last
     * thing called as part of the doEndTag
     */
    protected void reset() {
        super.reset();

        if (fieldValidators != null) {
            fieldValidators.clear();
            fieldParameters.clear();
        }
    }
}
