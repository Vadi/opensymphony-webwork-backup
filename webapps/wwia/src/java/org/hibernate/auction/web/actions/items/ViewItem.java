/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions.items;

import org.hibernate.auction.dao.ItemDAOAware;
import org.hibernate.auction.dao.ItemDAO;
import org.hibernate.auction.model.Item;
import com.opensymphony.xwork.ActionSupport;

/**
 * ViewItem
 *
 * @author Jason Carreira <jcarreira@eplus.com>
 */
public class ViewItem extends ActionSupport implements ItemDAOAware {
    private ItemDAO itemDao;
    private Item item;
    private Long id;

    public void setItemDAO(ItemDAO dao) {
        this.itemDao = dao;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Item getItem() {
        return item;
    }

    public String execute() throws Exception {
        item = itemDao.getItemById(id,false);
        return SUCCESS;
    }
}
