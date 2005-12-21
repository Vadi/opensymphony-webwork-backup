/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions.localized;

import org.hibernate.auction.dao.LocalizedTextDAO;
import org.hibernate.auction.dao.LocalizedTextDAOAware;
import org.hibernate.auction.model.Category;
import org.hibernate.auction.model.LocalizedText;
import org.hibernate.auction.web.actions.AbstractCategoryAwareAction;

import java.util.Iterator;
import java.util.Map;

/**
 * CreateLocalizedCategory
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class CreateLocalizedCategory extends AbstractCategoryAwareAction  implements LocalizedTextDAOAware {
    private Category category;
    Map categoryTexts = null;
    private LocalizedTextDAO localizedTextDao;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public LocalizedTextDAO getLocalizedTextDao() {
        return localizedTextDao;
    }

    public Map getCategoryTexts() {
        return categoryTexts;
    }

    public void setCategoryTexts(Map categoryTexts) {
        this.categoryTexts = categoryTexts;
    }

    public void setLocalizedTextDAO(LocalizedTextDAO dao) {
        this.localizedTextDao = dao;
    }

    public void setLocalizedTextDao(LocalizedTextDAO localizedTextDao) {
        this.localizedTextDao = localizedTextDao;
    }

    public String saveCategory() {
       if (category == null) {
            return INPUT;
        }
        categoryDAO.makePersistent(category);
        for (Iterator iterator = categoryTexts.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            LocalizedText text = new LocalizedText();
            text.setKey(category.getName());
            text.setLocaleStr((String) entry.getKey());
            text.setText((String) entry.getValue());
            localizedTextDao.makePersistent(text);
        }
        return SUCCESS;
    }
}
