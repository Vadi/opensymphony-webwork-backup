package com.opensymphony.webwork.hibernate;

import com.opensymphony.xwork.interceptor.component.Disposable;
import com.opensymphony.xwork.interceptor.component.Initializable;
import org.hibernate.Session;

import java.util.List;

/**
 * User: plightbo
 * Date: Aug 10, 2005
 * Time: 12:06:19 AM
 */
public class HibernateSessionImpl implements HibernateSession, HibernateConfigurationAware, Initializable, Disposable {
    HibernateConfiguration config;
    Session session;

    public void setHibernateConfiguration(HibernateConfiguration config) {
        this.config = config;
    }

    public void init() {
        session = config.getSessionFactory().openSession();
    }

    public void dispose() {
        session.flush();
        session.close();
    }

    public List getAll(Class type) {
        return session.createCriteria(type).list();
    }

    public void create(Object o) {
        session.save(o);
    }
}
