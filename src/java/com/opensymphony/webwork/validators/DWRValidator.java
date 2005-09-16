/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.validators;

import com.opensymphony.webwork.WebWorkStatics;
import com.opensymphony.xwork.*;
import com.opensymphony.xwork.config.entities.ActionConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ltd.getahead.dwr.ExecutionContext;

import javax.servlet.ServletConfig;
import java.util.HashMap;
import java.util.Map;

/**
 * User: plightbo
 * Date: Dec 11, 2004
 * Time: 6:17:58 PM
 * <p/>
 * <dwr>
 * <allow>
 * <create creator="new" javascript="validator" class="com.opensymphony.webwork.validators.DWRValidator"/>
 * <convert converter="bean" match="com.opensymphony.xwork.ValidationAwareSupport"/>
 * </allow>
 * </dwr>
 */
public class DWRValidator {
    private static final Log LOG = LogFactory.getLog(DWRValidator.class);

    public ValidationAwareSupport doPost(String namespace, String action, Map params) throws Exception {
        ServletConfig sc = ExecutionContext.get().getServletConfig();
        HashMap ctx = new HashMap();

        ctx.put(ActionContext.PARAMETERS, params);
        ctx.put(WebWorkStatics.SERVLET_CONTEXT, sc.getServletContext());

        try {
            ValidatorActionProxy proxy = new ValidatorActionProxy(namespace, action, ctx);
            proxy.execute();
            Object a = proxy.getAction();

            if (a instanceof ValidationAware) {
                ValidationAware aware = (ValidationAware) a;
                ValidationAwareSupport vas = new ValidationAwareSupport();
                vas.setActionErrors(aware.getActionErrors());
                vas.setActionMessages(aware.getActionMessages());
                vas.setFieldErrors(aware.getFieldErrors());

                return vas;
            } else {
                return null;
            }
        } catch (Exception e) {
            LOG.error("Error while trying to validate", e);
            return null;
        }
    }

    public static class ValidatorActionInvocation extends DefaultActionInvocation {
        protected ValidatorActionInvocation(ActionProxy proxy, Map extraContext) throws Exception {
            super(proxy, extraContext, true);
        }

        protected String invokeAction(Object action, ActionConfig actionConfig) throws Exception {
            return Action.NONE; // don't actually execute the action
        }
    }

    public static class ValidatorActionProxy extends DefaultActionProxy {
        protected ValidatorActionProxy(String namespace, String actionName, Map extraContext) throws Exception {
            super(namespace, actionName, extraContext, false);
        }

        protected void prepare() throws Exception {
            invocation = new ValidatorActionInvocation(this, extraContext);
        }
    }
}
