/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.dao;

/**
 * UserDAOAware
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
public interface UserDAOAware {
    void setUserDAO(UserDAO dao);
}
