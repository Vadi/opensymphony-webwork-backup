package com.opensymphony.webwork.hibernate;

/**
 * User: plightbo
 * Date: Sep 14, 2005
 * Time: 1:51:56 AM
 */
public interface HibernateTransactionAware {
    void setHibernateTransaction(HibernateTransaction hibernateTransaction);
}
