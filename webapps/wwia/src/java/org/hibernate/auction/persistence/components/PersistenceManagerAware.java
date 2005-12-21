package org.hibernate.auction.persistence.components;

/**
 * User: plightbo
 * Date: Oct 17, 2004
 * Time: 11:40:41 PM
 */
public interface PersistenceManagerAware {
    void setPersistenceManager(PersistenceManager persistenceManager);
}
