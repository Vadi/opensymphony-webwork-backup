/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.spring;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.spring.SpringProxyableObjectFactory;
import com.opensymphony.webwork.spring.lifecycle.ApplicationContextSessionListener;
import org.springframework.context.ApplicationContext;

import java.util.Map;

/**
 * SessionContextSpringProxyableObjectFactory
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class SessionContextSpringProxyableObjectFactory extends SpringProxyableObjectFactory {

    protected ApplicationContext getApplicationContext() {
        Map session = ActionContext.getContext().getSession();
        ApplicationContext sessionContext = (ApplicationContext) session.get(ApplicationContextSessionListener.APP_CONTEXT_SESSION_KEY);
        if (sessionContext == null) {
            throw new IllegalStateException("There is no application context in the user's session");
        }
        return sessionContext;
    }
}
