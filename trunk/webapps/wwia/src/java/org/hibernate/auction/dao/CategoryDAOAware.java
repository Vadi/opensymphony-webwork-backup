/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.dao;

/**
 * Enabler interface for CategoryDAO
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public interface CategoryDAOAware {
    void setCategoryDAO(CategoryDAO dao);
}
