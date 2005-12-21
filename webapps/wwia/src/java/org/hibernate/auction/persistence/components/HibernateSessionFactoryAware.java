package org.hibernate.auction.persistence.components;

/**
 * User: plightbo
 * Date: Oct 17, 2004
 * Time: 11:33:09 PM
 */
public interface HibernateSessionFactoryAware {
    void setSessionFactory(HibernateSessionFactory sessionFactory);
}
