/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.views.jsp.TagUtils;
import com.opensymphony.webwork.views.util.JavaScriptValidationHolder;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


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
    String actionName;
    JavaScriptValidationHolder javaScriptValidationHolder;

    String actionAttr;
    String targetAttr;
    String enctypeAttr;
    String methodAttr;
    String namespaceAttr;
    String validateAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAction(String action) {
        this.actionAttr = action;
    }

    public void setTarget(String target) {
        this.targetAttr = target;
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
             * -> because we want to be able to use the tags if we went directly to the page
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

            final String action = (String) findString(actionAttr);
            String namespace;

            if (namespaceAttr == null) {
                namespace = TagUtils.buildNamespace(getStack(), (HttpServletRequest) pageContext.getRequest());
            } else {
                namespace = findString(namespaceAttr);
            }

            if (namespace == null) {
                namespace = "";
            }

            final ActionConfig actionConfig = ConfigurationManager.getConfiguration().getRuntimeConfiguration().getActionConfig(namespace, action);

            if (actionConfig != null) {
                try {
                    actionClass = ObjectFactory.getObjectFactory().getClassInstance(actionConfig.getClassName());
                } catch (ClassNotFoundException e) {
                    // this is ok
                }

                actionName = action;

                String result = UrlHelper.buildUrl(namespace + "/" + action + "." + Configuration.get("webwork.action.extension"), request, response, null);
                addParameter("action", result);
                addParameter("namespace", namespace);

                // if the name isn't specified, use the action name
                if (nameAttr == null) {
                    nameAttr = action;
                    addParameter("name", action);
                }

                // if the id isn't specified, use the action name
                if (id == null) {
                    id = action;
                    addParameter("id", action);
                }
            } else if (action != null) {
                String result = UrlHelper.buildUrl(action, request, response, null);
                addParameter("action", result);

                // cut out anything between / and . should be the id and name
                int slash = result.lastIndexOf('/');
                int dot = result.indexOf('.', slash);
                if (dot != -1) {
                    id = result.substring(slash + 1, dot);
                } else {
                    id = result.substring(slash + 1);
                }
                addParameter("id", id);
            }

            // only create the javaScriptValidationHolder if the actionName,and class is known
            // and the javaScriptValidationHolder hasn't been created already
            // i.e. don'r re-create it on the second call to evaluateExtraParams
            if (actionName != null && actionClass != null && javaScriptValidationHolder == null) {
                javaScriptValidationHolder = new JavaScriptValidationHolder(actionName, actionClass, getStack());
            }
        }

        if (targetAttr != null) {
            addParameter("target", findString(targetAttr));
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

        if (javaScriptValidationHolder != null && javaScriptValidationHolder.hasValidators()) {
            addParameter("javascriptValidation", javaScriptValidationHolder.toJavaScript());
        } else {
            addParameter("javascriptValidation", "// cannot find any applicable validators");
        }
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

        javaScriptValidationHolder = null;
        if (getActionName() != null && getActionClass() != null) {
            javaScriptValidationHolder = new JavaScriptValidationHolder(getActionName(), getActionClass(), getStack());
        }
    }

    /**
     * Provide access to the JavaScriptValidationHolder so that the AbstractUITag
     * can trigger the registration of all validators.
     *
     * @return
     */
    JavaScriptValidationHolder getJavaScriptValidationHolder() {
        return javaScriptValidationHolder;
    }
}
