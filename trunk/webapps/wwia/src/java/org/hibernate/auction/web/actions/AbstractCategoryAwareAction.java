/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions;

import org.hibernate.auction.dao.CategoryDAO;
import org.hibernate.auction.dao.CategoryDAOAware;

/**
 * AbstractCategoryAwareAction
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public abstract class AbstractCategoryAwareAction extends AbstractUserAwareAction implements CategoryDAOAware {
    protected CategoryDAO categoryDAO;

    public void setCategoryDAO(CategoryDAO dao) {
        this.categoryDAO = dao;
    }

}
