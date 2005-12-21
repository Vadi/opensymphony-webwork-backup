package org.hibernate.auction.web.actions.categories;

import org.hibernate.auction.web.actions.AbstractCategoryAwareAction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * User: plightbo
 * Date: Oct 8, 2004
 * Time: 4:50:36 PM
 */
public class CategoryPicker extends AbstractCategoryAwareAction {
    List categories;

    public String execute() throws Exception {
        Collection savedCategories = categoryDAO.findAll(false);

        this.categories = new ArrayList();
        this.categories.add(null);
        this.categories.addAll(savedCategories);

        return SUCCESS;
    }

    public List getCategories() {
        return categories;
    }

}
