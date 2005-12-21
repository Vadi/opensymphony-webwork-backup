package org.hibernate.auction.web.actions;

import java.util.Collection;

/**
 * User: plightbo
 * Date: Oct 6, 2004
 * Time: 8:31:43 PM
 */
public class Dashboard extends AbstractCategoryAwareAction {
    Collection categories;
    public String execute() throws Exception {
        categories = categoryDAO.findAll(true);

        return SUCCESS;
    }

    public Collection getCategories() {
        return categories;
    }
}
