package org.hibernate.auction.web.actions.users;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import org.hibernate.auction.dao.UserDAO;
import org.hibernate.auction.dao.UserDAOAware;
import org.hibernate.auction.model.User;
import org.hibernate.auction.web.interceptors.AuthenticationInterceptor;

import java.util.Map;

/**
 * User: plightbo
 * Date: Oct 6, 2004
 * Time: 10:27:06 PM
 */
public class Login extends ActionSupport implements SessionAware, UserDAOAware {
    Map session;
    User user;
    private UserDAO userDAO;

    public void setSession(Map session) {
        this.session = session;
    }

    public String execute() throws Exception {
        user = userDAO.findByCredentials(user.getUsername(), user.getPassword());
        if (user ==  null) {
            return INPUT;
        } else {
            session.put(AuthenticationInterceptor.USER, user);
            return SUCCESS;
        }
    }

    public String doDefault() throws Exception {
        return INPUT;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setUserDAO(UserDAO dao) {
        this.userDAO = dao;
    }
}
