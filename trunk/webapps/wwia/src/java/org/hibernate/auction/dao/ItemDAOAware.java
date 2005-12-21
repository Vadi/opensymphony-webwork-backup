/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.dao;

/**
 * ItemDAOAware
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public interface ItemDAOAware {
    void setItemDAO(ItemDAO dao);
}
