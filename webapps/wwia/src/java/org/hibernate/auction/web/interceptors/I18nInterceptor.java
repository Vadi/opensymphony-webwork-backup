/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.interceptors;

import com.opensymphony.xwork.interceptor.Interceptor;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionSupport;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.auction.localization.LocaleUtil;

import java.util.Locale;

/**
 * I18nInterceptor
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class I18nInterceptor implements Interceptor {
    protected static final Log log = LogFactory.getLog(I18nInterceptor.class);

    public static final String DEFAULT_SESSION_ATTRIBUTE = "webwork_locale";
    public static final String DEFAULT_PARAMETER = "request_locale";

    protected String parameterName = DEFAULT_PARAMETER;
    protected String attributeName = DEFAULT_SESSION_ATTRIBUTE;

    /**
     */
    public I18nInterceptor() {
        if (log.isDebugEnabled()) log.debug("new I18nInterceptor()");
    }

    public void setParameterName(String parameterName) {
        this.parameterName = parameterName;
    }

    public void setAttributeName(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     */
    public void init() {
        if (log.isDebugEnabled()) log.debug("init()");
    }

    /**
     */
    public void destroy() {
        if (log.isDebugEnabled()) log.debug("destroy()");
    }

    /**
     */
    public String intercept(ActionInvocation invocation) throws Exception {
        if (log.isDebugEnabled()) log.debug("intercept '" + invocation.getProxy().getNamespace() + "/" + invocation.getProxy().getActionName() + "' { ");

        //get requested locale
        Object requested_locale = invocation.getInvocationContext().getParameters().get(parameterName);
        if (requested_locale != null && requested_locale.getClass().isArray() && ((Object[]) requested_locale).length == 1) {
            requested_locale = ((Object[]) requested_locale)[0];
        }
        if (log.isDebugEnabled()) log.debug("requested_locale=" + requested_locale);
        //save it in session
        if (requested_locale != null) {
            Locale locale = (requested_locale instanceof Locale) ? (Locale) requested_locale : nonNullLocaleFromString(requested_locale);
            if (log.isDebugEnabled()) log.debug("store locale=" + locale);
            if (locale != null) invocation.getInvocationContext().getSession().put(attributeName, locale);
        }
        //set locale for action
        Object locale = invocation.getInvocationContext().getSession().get(attributeName);
        if (locale != null && locale instanceof Locale) {
            if (log.isDebugEnabled()) log.debug("apply locale=" + locale);
            invocation.getInvocationContext().setLocale((Locale) locale);
        }

        if (log.isDebugEnabled()) log.debug("before Locale=" + ((ActionSupport) invocation.getAction()).getLocale());
        final String result = invocation.invoke();
        if (log.isDebugEnabled()) log.debug("after Locale=" + ((ActionSupport) invocation.getAction()).getLocale());

        if (log.isDebugEnabled()) log.debug("intercept } ");
        return result;
    }

    private Locale nonNullLocaleFromString(Object requested_locale) {
        Locale aLocale = LocaleUtil.localeFromString(requested_locale.toString());
        return (aLocale == null) ? Locale.getDefault() : aLocale;
    }
}
