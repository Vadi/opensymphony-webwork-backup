package org.hibernate.auction.persistence.components;

import org.hibernate.SessionFactory;

/**
 * User: plightbo
 * Date: Oct 17, 2004
 * Time: 11:34:40 PM
 */
public interface HibernateSessionFactory {
    SessionFactory getSessionFactory();
}
