package com.opensymphony.webwork.hibernate;

import org.hibernate.HibernateException;

/**
 * User: plightbo
 * Date: Sep 14, 2005
 * Time: 1:52:36 AM
 */
public interface HibernateTransaction {
    void commit() throws HibernateException;

    void rollback() throws HibernateException;

    boolean wasRolledBack() throws HibernateException;

    boolean wasCommitted() throws HibernateException;

    boolean isActive() throws HibernateException;

}
