/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import org.hibernate.auction.model.User;
import org.hibernate.auction.web.interceptors.AuthenticationInterceptor;

import java.util.Map;

/**
 * AbstractUserAwareAction
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class AbstractUserAwareAction extends ActionSupport implements SessionAware {
    protected Map session;

    public void setSession(Map session) {
        this.session = session;
    }

    public User getUser() {
        return  (User) session.get(AuthenticationInterceptor.USER);
    }
}
