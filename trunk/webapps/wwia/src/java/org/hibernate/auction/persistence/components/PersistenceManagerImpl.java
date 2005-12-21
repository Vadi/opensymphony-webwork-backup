package org.hibernate.auction.persistence.components;

import com.opensymphony.xwork.interceptor.component.Initializable;
import com.opensymphony.xwork.interceptor.component.Disposable;
import org.hibernate.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: plightbo
 * Date: Oct 17, 2004
 * Time: 11:41:09 PM
 */
public class PersistenceManagerImpl implements PersistenceManager, HibernateSessionFactoryAware, Initializable, Disposable {
    private static final Log LOG = LogFactory.getLog(PersistenceManagerImpl.class);

    SessionFactory sessionFactory;
    Session session;
    Transaction transaction;
    boolean rollback;
    boolean commited;

    public void setSessionFactory(HibernateSessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory.getSessionFactory();
    }

    public void init() {
        try {
            session = sessionFactory.openSession();
            session.setFlushMode(FlushMode.NEVER);
        } catch (HibernateException e) {
            LOG.error("Could not open Hibernate session.", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public Session getSession() {
        return session;
    }

        public void begin() {
        try {
            transaction = session.beginTransaction();
        } catch (HibernateException e) {
            LOG.error("Could not begin transaction.", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public void commit() {
        if (transaction == null) {
            throw new RuntimeException("Transaction must be started before it can be commited!");
        }

        if (!commited) {
            try {
                session.flush();
                transaction.commit();
                commited = true;
            } catch (HibernateException e) {
                LOG.error("Could not commit transaction.", e);
            }
        }
    }

    public void rollback() {
        if (transaction == null) {
            throw new RuntimeException("Transaction must be started before it can be rolled back!");
        }

        if (!commited) {
            try {
                transaction.rollback();
                rollback = true;
            } catch (HibernateException e) {
                LOG.error("Could not roll transaction back.", e);
            }
        }
    }

    public void dispose() {
        try {
            if (transaction != null) {
                commit();
            }
            
            session.close();
        } catch (HibernateException e) {
            LOG.error("Could not close Hiberante session.");
        }
    }
}
