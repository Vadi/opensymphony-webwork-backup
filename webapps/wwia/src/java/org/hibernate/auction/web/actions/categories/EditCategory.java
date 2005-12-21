/*
 * Copyright (c) 2005 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions.categories;

import com.opensymphony.xwork.Preparable;
import org.hibernate.auction.model.Category;
import org.hibernate.auction.web.actions.AbstractCategoryAwareAction;

/**
 * EditCategory
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class EditCategory extends AbstractCategoryAwareAction implements Preparable {
    private Long categoryId;
    protected Category category;

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void prepare() throws Exception {
        if (categoryId != null) {
            category = categoryDAO.getCategoryById(categoryId,false);
        }
    }

    public String saveCategory() {
        if (category == null) {
            return INPUT;
        }
        categoryDAO.makePersistent(category);
        return SUCCESS;
    }
}
