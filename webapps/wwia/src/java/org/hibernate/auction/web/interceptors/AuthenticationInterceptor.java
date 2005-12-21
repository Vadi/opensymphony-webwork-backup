package org.hibernate.auction.web.interceptors;

import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;
import org.hibernate.auction.model.User;
import org.hibernate.auction.web.actions.UserAware;

import java.util.Map;

/**
 * User: plightbo
 * Date: Oct 6, 2004
 * Time: 10:01:44 PM
 */
public class AuthenticationInterceptor implements Interceptor {
    public static final String USER = "user";

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation actionInvocation) throws Exception {
        Map session = actionInvocation.getInvocationContext().getSession();
        User user = (User) session.get(USER);
        if (user == null) {
            return Action.LOGIN;
        } else {
            Object action = actionInvocation.getAction();
            if (action instanceof UserAware) {
                ((UserAware)action).setUser(user);
            }
            return actionInvocation.invoke();
        }
    }
}
