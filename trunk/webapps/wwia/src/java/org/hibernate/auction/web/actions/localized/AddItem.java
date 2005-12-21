/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.web.actions.localized;

import org.hibernate.auction.dao.LocalizedTextDAOAware;
import org.hibernate.auction.dao.LocalizedTextDAO;

/**
 * AddItem
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public class AddItem extends org.hibernate.auction.web.actions.items.AddItem  implements LocalizedTextDAOAware {
    private LocalizedTextDAO dao;

    public void setLocalizedTextDAO(LocalizedTextDAO dao) {
        this.dao = dao;
    }
}
