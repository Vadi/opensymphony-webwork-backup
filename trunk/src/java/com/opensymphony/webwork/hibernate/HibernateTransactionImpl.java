package com.opensymphony.webwork.hibernate;

import com.opensymphony.xwork.interceptor.component.Initializable;
import com.opensymphony.xwork.interceptor.component.Disposable;
import org.hibernate.Transaction;
import org.hibernate.HibernateException;

/**
 * User: plightbo
 * Date: Sep 14, 2005
 * Time: 1:52:44 AM
 */
public class HibernateTransactionImpl implements HibernateTransaction, HibernateSessionAware, Initializable, Disposable {
    private HibernateSession session;
    private Transaction transaction;

    public void setHibernateSession(HibernateSession session) {
        this.session = session;
    }

    public void init() {
        transaction = session.beginTransaction();
    }

    public void dispose() {
        if (!wasRolledBack() && !wasCommitted()) {
            commit();
        }
    }

    public void commit() throws HibernateException {
        transaction.commit();
    }

    public void rollback() throws HibernateException {
        transaction.rollback();
    }

    public boolean wasRolledBack() throws HibernateException {
        return transaction.wasRolledBack();
    }

    public boolean wasCommitted() throws HibernateException {
        return transaction.wasCommitted();
    }

    public boolean isActive() throws HibernateException {
        return transaction.isActive();
    }
}
