package com.opensymphony.webwork.components;

import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.dispatcher.DispatcherUtils;
import com.opensymphony.webwork.portlet.context.PortletActionContext;
import com.opensymphony.webwork.portlet.util.PortletUrlHelper;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.xwork.config.ConfigurationManager;
import com.opensymphony.xwork.config.RuntimeConfiguration;
import com.opensymphony.xwork.config.entities.ActionConfig;
import com.opensymphony.xwork.config.entities.InterceptorMapping;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ObjectFactory;
import com.opensymphony.xwork.interceptor.MethodFilterInterceptorUtil;
import com.opensymphony.xwork.validator.Validator;
import com.opensymphony.xwork.validator.FieldValidator;
import com.opensymphony.xwork.validator.ActionValidatorManagerFactory;
import com.opensymphony.xwork.validator.ValidationInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

/**
 * <!-- START SNIPPET: javadoc -->
 *
 * Renders HTML an input form.<p/>
 *
 * The remote form allows the form to be submitted without the page being refreshed. The results from the form
 * can be inserted into any HTML element on the page.<p/>
 *
 * NOTE:<p/>
 * The order / logic in determining the posting url of the generated HTML form is as follows:-
 * <ol>
 * 	 <li>
 * 	 If the action attribute is not specified, then the current request will be used to
 *   determine the posting url
 *   </li>
 *   <li>
 *   If the action is given, SAF will try to obtain an ActionConfig. This will be
 *   successfull if the action attribute is a valid action alias defined xwork.xml.
 *   </li>
 *   <li>
 *   If the action is given and is not an action alias defined in xwork.xmlm SAF
 *   will used the action attribute as if it is the posting url, separting the namespace
 *   from it and using UrlHelper to generate the final url.
 *   </li>
 * </ol>
 *
 * <!-- END SNIPPET: javadoc -->
 *
 * <p/> <b>Examples</b>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 *
 * &lt;ww:form ... /&gt;
 *
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author Patrick Lightbody
 * @author Ian Roughley
 * @author Rene Gielen
 * @author Rainer Hermanns
 * @version $Date$ $Id$
 * @since 2.2
 *
 * @ww.tag name="form" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ui.FormTag"
 * description="Renders an input form"
  */
public class Form extends ClosingUIBean {
    public static final String OPEN_TEMPLATE = "form";
    public static final String TEMPLATE = "form-close";

    protected String onsubmit;
    protected String action;
    protected String target;
    protected String enctype;
    protected String method;
    protected String namespace;
    protected String validate;
    protected String portletMode;
    protected String windowState;
    protected String acceptcharset;

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

        boolean isAjax = "ajax".equalsIgnoreCase(this.theme);

        if (validate != null) {
            addParameter("validate", findValue(validate, Boolean.class));
        }

        // calculate the action and namespace
        String action = null;
        if (this.action != null) {
            // if it isn't specified, we'll make somethig up
            action = findString(this.action);
        }

        if (DispatcherUtils.isPortletSupportActive() && PortletActionContext.isPortletRequest()) {
            evaluateExtraParamsPortletRequest(namespace, action);
        } else {
            String namespace = determineNamespace(this.namespace, getStack(),
                    request);
            evaluateExtraParamsServletRequest(action, namespace, isAjax);
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

        if (acceptcharset != null) {
            addParameter("acceptcharset", findString(acceptcharset));
        }

        // keep a collection of the tag names for anything special the templates might want to do (such as pure client
        // side validation)
        if (!parameters.containsKey("tagNames")) {
            // we have this if check so we don't do this twice (on open and close of the template)
            addParameter("tagNames", new ArrayList());
        }
    }

    /**
     * @param isAjax
     * @param namespace
     * @param action
     */
    private void evaluateExtraParamsServletRequest(String action, String namespace, boolean isAjax) {
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

        String actionMethod = "";
        if (action.indexOf("!") != -1) {
            int endIdx = action.lastIndexOf("!");
            actionMethod = action.substring(endIdx + 1, action.length());
            action = action.substring(0, endIdx);
        }

        final ActionConfig actionConfig = ConfigurationManager.getConfiguration().getRuntimeConfiguration().getActionConfig(namespace, action);
        String actionName = action;
        if (actionConfig != null) {

            ActionMapping mapping = new ActionMapping(action, namespace, actionMethod, parameters);
            String result = UrlHelper.buildUrl(ActionMapperFactory.getMapper().getUriFromActionMapping(mapping), request, response, null);
            addParameter("action", result);

            // let's try to get the actual action class and name
            // this can be used for getting the list of validators
            addParameter("actionName", actionName);
            try {
                Class clazz = ObjectFactory.getObjectFactory().getClassInstance(actionConfig.getClassName());
                addParameter("actionClass", clazz);
            } catch (ClassNotFoundException e) {
                // this is OK, we'll just move on
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
            addParameter("action", result);

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

        // WW-1284
        // evaluate if client-side js is to be enabled. (if validation interceptor
        // does allow validation eg. method is not filtered out)
        evaluateClientSideJsEnablement(actionName, namespace, actionMethod);
    }

    private void evaluateClientSideJsEnablement(String actionName, String namespace, String actionMethod) {

    	// Only evaluate if Client-Side js is to be enable when validate=true
    	Boolean validate = (Boolean) getParameters().get("validate");
    	if (validate != null && validate.booleanValue()) {

    		addParameter("performValidation", Boolean.FALSE);

    		RuntimeConfiguration runtimeConfiguration = ConfigurationManager.getConfiguration().getRuntimeConfiguration();
    		ActionConfig actionConfig = runtimeConfiguration.getActionConfig(namespace, actionName);

    		if (actionConfig != null) {
    			List interceptors = actionConfig.getInterceptors();
    			for (Iterator i = interceptors.iterator(); i.hasNext(); ) {
    				InterceptorMapping interceptorMapping = (InterceptorMapping) i.next();
    				if (ValidationInterceptor.class.isInstance(interceptorMapping.getInterceptor())) {
    					ValidationInterceptor validationInterceptor = (ValidationInterceptor) interceptorMapping.getInterceptor();

    					Set excludeMethods = validationInterceptor.getExcludeMethodsSet();
    					Set includeMethods = validationInterceptor.getIncludeMethodsSet();

    					if (MethodFilterInterceptorUtil.applyMethod(excludeMethods, includeMethods, actionMethod)) {
    						addParameter("performValidation", Boolean.TRUE);
    					}
    					return;
    				}
    			}
    		}
    	}
    }

    /**
     * Constructs the action url adapted to a portal environment.
     * @param action The action to create the URL for.
     */
    private void evaluateExtraParamsPortletRequest(String namespace, String action) {

        if (this.action != null) {
            // if it isn't specified, we'll make somethig up
            action = findString(this.action);
        }

        String type = "action";
        if (StringUtils.isNotEmpty(method)) {
            if ("GET".equalsIgnoreCase(method.trim())) {
                type = "render";
            }
        }
        if (action != null) {
            String result = PortletUrlHelper.buildUrl(action, namespace,
                    getParameters(), type, portletMode, windowState);
            addParameter("action", result);

            // namespace: cut out anything between the start and the last /
            int slash = result.lastIndexOf('/');
            if (slash != -1) {
                addParameter("namespace", result.substring(0, slash));
            } else {
                addParameter("namespace", "");
            }

            // name/id: cut out anything between / and . should be the id and
            // name
            if (id == null) {
                slash = action.lastIndexOf('/');
                int dot = action.indexOf('.', slash);
                if (dot != -1) {
                    id = action.substring(slash + 1, dot);
                } else {
                    id = action.substring(slash + 1);
                }
                addParameter("id", escape(id));
            }
        }

    }

    public List getValidators(String name) {
        Class actionClass = (Class) getParameters().get("actionClass");
        if (actionClass == null) {
            return Collections.EMPTY_LIST;
        }

        List all = ActionValidatorManagerFactory.getInstance().getValidators(actionClass, (String) getParameters().get("actionName"));
        List validators = new ArrayList();
        for (Iterator iterator = all.iterator(); iterator.hasNext();) {
            Validator validator = (Validator) iterator.next();
            if (validator instanceof FieldValidator) {
                FieldValidator fieldValidator = (FieldValidator) validator;
                if (fieldValidator.getFieldName().equals(name)) {
                    validators.add(fieldValidator);
                }
            }
        }

        return validators;
    }


    /**
     * HTML onsubmit attribute
     * @ww.tagattribute required="false"
     */
    public void setOnsubmit(String onsubmit) {
        this.onsubmit = onsubmit;
    }

    /**
     * Set action nane to submit to, without .action suffix
     * @ww.tagattribute required="false" default="current action"
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * HTML form target attribute
     * @ww.tagattribute required="false"
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * HTML form enctype attribute
     * @ww.tagattribute required="false"
     */
    public void setEnctype(String enctype) {
        this.enctype = enctype;
    }

    /**
     * HTML form method attribute
     * @ww.tagattribute required="false"
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * namespace for action to submit to
     * @ww.tagattribute required="false" default="current namespace"
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Whether client side/remote validation should be performed. Only useful with theme xhtml/ajax
     * @ww.tagattribute required="false" type="Boolean" default="false"
     */
    public void setValidate(String validate) {
        this.validate = validate;
    }

    /**
     * The portlet mode to display after the form submit
     * @ww.tagattribute required="false"
     */
    public void setPortletMode(String portletMode) {
        this.portletMode = portletMode;
    }

    /**
     * The window state to display after the form submit
     * @ww.tagattribute required="false"
     */
    public void setWindowState(String windowState) {
        this.windowState = windowState;
    }

    /**
     * The accepted charsets for this form. The values may be comma or blank delimited.
     * @ww.tagattribute required="false"
     */
    public void setAcceptcharset(String acceptcharset) {
        this.acceptcharset = acceptcharset;
    }
}
