package org.hibernate.auction.web.actions.users;

import com.opensymphony.xwork.ActionSupport;
import org.hibernate.auction.dao.UserDAOAware;
import org.hibernate.auction.dao.UserDAO;
import org.hibernate.auction.model.User;

import java.util.List;
import java.util.Iterator;

/**
 * Created by IntelliJ IDEA.
 * User: plightbo
 * Date: Apr 11, 2005
 */
public class CreateUsers extends ActionSupport implements UserDAOAware {
    List users;
    UserDAO userDAO;

    public void validate() {
        // see if the name already exists
        if (users != null) {
            int i = 0;
            for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                User user = (User) iterator.next();
                User existing = userDAO.findByUsername(user.getUsername());
                if (existing != null) {
                    addFieldError("users[" + i + "].username", getText("user.exists"));
                }
                i++;
            }
        }
    }

    public String execute() throws Exception {
        if (users != null) {
            for (Iterator iterator = users.iterator(); iterator.hasNext();) {
                User user = (User) iterator.next();
                userDAO.makePersistent(user);
            }
        }

        return SUCCESS;
    }

    public String doDefault() throws Exception {
        return INPUT;
    }

    public List getUsers() {
        return users;
    }

    public void setUsers(List users) {
        this.users = users;
    }

    public void setUserDAO(UserDAO dao) {
        this.userDAO = dao;
    }
}
