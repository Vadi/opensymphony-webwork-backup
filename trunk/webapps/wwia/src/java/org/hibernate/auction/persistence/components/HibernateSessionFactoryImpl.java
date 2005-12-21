package org.hibernate.auction.persistence.components;

import com.opensymphony.xwork.interceptor.component.Initializable;
import com.opensymphony.xwork.interceptor.component.Disposable;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;
import org.hibernate.HibernateException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * User: plightbo
 * Date: Oct 17, 2004
 * Time: 11:34:42 PM
 */
public class HibernateSessionFactoryImpl implements HibernateSessionFactory, Initializable, Disposable {
    private static final Log LOG = LogFactory.getLog(HibernateSessionFactoryImpl.class);

    SessionFactory sessionFactory;

    public void init() {
        try {
            Configuration configuration = new Configuration();
            sessionFactory = configuration.configure().buildSessionFactory();
            // We could also let Hibernate bind it to JNDI:
            // configuration.configure().buildSessionFactory()
        } catch (Throwable e) {
            // We have to catch Throwable, otherwise we will miss
            // NoClassDefFoundError and other subclasses of Error
            LOG.error("Building SessionFactory failed.", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void dispose() {
        try {
            sessionFactory.close();
        } catch (HibernateException e) {
            LOG.error("Closing SessionFactory failed.", e);
        }
    }
}
