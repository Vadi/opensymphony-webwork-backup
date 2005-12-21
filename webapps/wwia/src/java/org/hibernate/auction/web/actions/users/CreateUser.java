package org.hibernate.auction.web.actions.users;

import com.opensymphony.xwork.ActionSupport;
import org.hibernate.auction.dao.UserDAO;
import org.hibernate.auction.dao.UserDAOAware;
import org.hibernate.auction.model.User;

/**
 * User: plightbo
 * Date: Oct 6, 2004
 * Time: 10:41:52 PM
 */
public class CreateUser extends ActionSupport implements UserDAOAware {
    User user;
    UserDAO userDAO;

    public void validate() {
        // see if the name already exists
        if (user != null) {
            User existing = userDAO.findByUsername(user.getUsername());
            if (existing != null) {
                addFieldError("user.username", getText("user.exists"));
            }
        }
    }

    public String execute() throws Exception {
        userDAO.makePersistent(user);
        return SUCCESS;
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
