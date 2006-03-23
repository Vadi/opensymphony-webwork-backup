/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.validators;

import com.opensymphony.webwork.dispatcher.ApplicationMap;
import com.opensymphony.webwork.dispatcher.DispatcherUtils;
import com.opensymphony.webwork.dispatcher.RequestMap;
import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.xwork.*;
import com.opensymphony.xwork.config.entities.ActionConfig;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import uk.ltd.getahead.dwr.ExecutionContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        HttpServletRequest req = ExecutionContext.get().getHttpServletRequest();
        ServletContext servletContext = ExecutionContext.get().getServletContext();
        HttpServletResponse res = ExecutionContext.get().getHttpServletResponse();

        Map requestParams = new HashMap(req.getParameterMap());
        if (params != null) {
            requestParams.putAll(params);
        } else {
            params = requestParams;
        }
        Map requestMap = new RequestMap(req);
        Map session = new SessionMap(req);
        Map application = new ApplicationMap(servletContext);
        DispatcherUtils du = DispatcherUtils.getInstance();
        HashMap ctx = du.createContextMap(requestMap,
                params,
                session,
                application,
                req,
                res,
                servletContext);

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
            super(namespace, actionName, extraContext, false, true);
        }

        protected void prepare() throws Exception {
            invocation = new ValidatorActionInvocation(this, extraContext);
        }
    }
}
