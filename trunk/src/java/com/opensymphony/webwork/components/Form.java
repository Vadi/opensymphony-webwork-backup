package com.opensymphony.webwork.components;

import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Form extends ClosingUIBean {
    final public static String OPEN_TEMPLATE = "form";
    final public static String TEMPLATE = "form-close";

    protected String onsubmit;
    protected String action;
    protected String target;
    protected String enctype;
    protected String method;
    protected String namespace;
    protected String validate;

    public Form(OgnlValueStack stack, HttpServletRequest request, HttpServletResponse response) {
        super(stack, request, response);
    }

    protected boolean evaluateNameValue() {
        return false;
    }

    public String getDefaultOpenTemplate() {
        return OPEN_TEMPLATE;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    /*
    * Revised for Portlet actionURL as form action, and add wwAction as hidden
    * field. Refer to template.simple/form.vm
    */
    protected void evaluateExtraParams() {
        super.evaluateExtraParams();

        String actionURL = com.opensymphony.webwork.portlet.context.PortletContext.getContext().getActionURL();
        boolean isPortlet = (actionURL != null && !"".equals(actionURL));

        // calculate the action and namespace
        String namespace = determineNamespace(this.namespace, getStack(), request);
        String action = null;
        if (this.action != null) {
            // if it isn't specified, we'll make somethig up
            action = findString(this.action);
        }

        if (action == null) {
            // no action supplied? ok, then default to the current request (action or general URL)
            ActionInvocation ai = (ActionInvocation) getStack().getContext().get(ActionContext.ACTION_INVOCATION);
            if (ai != null) {
                action = ai.getProxy().getActionName();
                namespace = ai.getProxy().getNamespace();
            } else {
                // hmm, ok, we need to just assume the current URL cut down
                String uri = request.getRequestURI();
                action = uri.substring(uri.lastIndexOf('/'));
            }
        }

        final ActionConfig actionConfig = ConfigurationManager.getConfiguration().getRuntimeConfiguration().getActionConfig(namespace, action);
        if (actionConfig != null) {
            if (isPortlet) {
                addParameter("action", actionURL);
                addParameter("wwAction", action);
                addParameter("isPortlet", "Portlet");
            } else {
                String actionMethod = "";
                if (action.indexOf("!") != -1) {
                    int endIdx = action.lastIndexOf("!");
                    actionMethod = action.substring(endIdx + 1, action.length());
                    action = action.substring(0, endIdx);
                }

                ActionMapping mapping = new ActionMapping(action, namespace, actionMethod, parameters);
                String result = UrlHelper.buildUrl(ActionMapperFactory.getMapper().getUriFromActionMapping(mapping), request, response, null);
                addParameter("action", result);
            }

            addParameter("namespace", namespace);

            // if the name isn't specified, use the action name
            if (name == null) {
                addParameter("name", action);
            }

            // if the id isn't specified, use the action name
            if (id == null) {
                addParameter("id", action);
            }
        } else if (action != null) {
            String result = UrlHelper.buildUrl(action, request, response, null);

            //Add Portlet Support. -- Added by Henry Hu
            if (isPortlet) {
                addParameter("action", actionURL);
                addParameter("wwAction", action);
                addParameter("isPortlet", "Portlet");
                //////Fix End///////////
            } else {
                addParameter("action", result);
            }

            // namespace: cut out anything between the start and the last /
            int slash = result.lastIndexOf('/');
            if (slash != -1) {
                addParameter("namespace", result.substring(0, slash));
            } else {
                addParameter("namespace", "");
            }

            // name/id: cut out anything between / and . should be the id and name
            if (id == null) {
                slash = result.lastIndexOf('/');
                int dot = result.indexOf('.', slash);
                if (dot != -1) {
                    id = result.substring(slash + 1, dot);
                } else {
                    id = result.substring(slash + 1);
                }
                addParameter("id", escape(id));
            }
        }

        if (onsubmit != null) {
            addParameter("onsubmit", findString(onsubmit));
        }

        if (target != null) {
            addParameter("target", findString(target));
        }

        if (enctype != null) {
            addParameter("enctype", findString(enctype));
        }

        if (method != null) {
            addParameter("method", findString(method));
        }

        if (validate != null) {
            addParameter("validate", findValue(validate, Boolean.class));
        }
    }

    public void setOnsubmit(String onsubmit) {
        this.onsubmit = onsubmit;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setValidate(String validate) {
        this.validate = validate;
    }


}
