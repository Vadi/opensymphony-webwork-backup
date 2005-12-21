package org.hibernate.auction.persistence.components;

import org.hibernate.Session;

/**
 * User: plightbo
 * Date: Oct 17, 2004
 * Time: 11:41:06 PM
 */
public interface PersistenceManager {
    Session getSession();

    void begin();

    void commit();

    void rollback();
    
}
