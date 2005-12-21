package org.hibernate.auction.web.actions.users;

import com.opensymphony.webwork.interceptor.SessionAware;
import com.opensymphony.xwork.ActionSupport;
import com.opensymphony.xwork.Preparable;
import com.opensymphony.xwork.ModelDriven;
import org.hibernate.auction.dao.UserDAO;
import org.hibernate.auction.dao.UserDAOAware;
import org.hibernate.auction.model.User;
import org.hibernate.auction.web.interceptors.AuthenticationInterceptor;

import java.util.Map;

/**
 * User: plightbo
 * Date: Nov 20, 2004
 * Time: 8:55:34 PM
 */
public class UpdateUser extends ActionSupport implements UserDAOAware, Preparable, SessionAware, ModelDriven {
    UserDAO userDAO;
    Map session;

    User user;

    public void setSession(Map session) {
        this.session = session;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void prepare() throws Exception {
        Long id = ((User) session.get(AuthenticationInterceptor.USER)).getId();
        user = userDAO.getUserById(id, false);
    }

    public String execute() throws Exception {
        userDAO.makePersistent(user);
        return SUCCESS;
    }

    public String doDefault() throws Exception {
        return INPUT;
    }

    public Object getModel() {
        return user;
    }
}
