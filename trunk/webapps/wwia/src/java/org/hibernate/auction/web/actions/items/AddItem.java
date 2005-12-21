package org.hibernate.auction.web.actions.items;

import org.hibernate.auction.web.actions.AbstractCategoryAwareAction;
import org.hibernate.auction.web.interceptors.AuthenticationInterceptor;
import org.hibernate.auction.model.*;
import org.hibernate.auction.persistence.components.PersistenceManagerAware;
import org.hibernate.auction.persistence.components.PersistenceManager;
import com.opensymphony.webwork.interceptor.SessionAware;

import java.util.Map;
import java.util.List;
import java.util.Iterator;

/**
 * User: plightbo
 * Date: Oct 31, 2004
 * Time: 5:31:25 PM
 */
public class AddItem extends AbstractCategoryAwareAction
        implements SessionAware, PersistenceManagerAware {
    Long[] categoryIds;
    List items;
    Map session;
    PersistenceManager persistenceManager;
    int numItems = 1;
    int removeItem;

    public void setSession(Map session) {
        this.session = session;
    }

    public void setPersistenceManager(PersistenceManager persistenceManager) {
        this.persistenceManager = persistenceManager;
    }

    public String execute() throws Exception {
        // get the user object
        User user = (User) session.get(AuthenticationInterceptor.USER);

        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();

            // complete Item object
            item.setSeller(user);
            item.setState(ItemState.ACTIVE);
        }

        // start tx
        persistenceManager.begin();

        for (Iterator iterator = items.iterator(); iterator.hasNext();) {
            Item item = (Item) iterator.next();

            // add the item
            persistenceManager.getSession().saveOrUpdate(item);

            // loop through all categories given
            for (int i = 0; i < categoryIds.length; i++) {
                Long categoryId = categoryIds[i];
                Category cat = categoryDAO.getCategoryById(categoryId, false);
                CategorizedItem catItem = new CategorizedItem(user.getUsername(), cat, item);
                cat.addCategorizedItem(catItem);
            }
        }

        // commit tx
        persistenceManager.commit();

        return SUCCESS;
    }

    public String doDefault() throws Exception {
        return INPUT;
    }

    public String addItem() throws Exception {
        numItems++;
        return INPUT;
    }

    public String removeItem() throws Exception {
        numItems--;
        items.remove(removeItem);
        return INPUT;
    }

    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public int getNumItems() {
        return numItems;
    }

    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }

    public Long[] getCategoryIds() {
        return categoryIds;
    }

    public void setCategoryIds(Long[] categoryIds) {
        this.categoryIds = categoryIds;
    }

    public void setRemoveItem(int removeItem) {
        this.removeItem = removeItem;
    }
}
