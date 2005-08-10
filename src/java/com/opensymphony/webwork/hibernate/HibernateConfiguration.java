package com.opensymphony.webwork.hibernate;

import org.hibernate.SessionFactory;

/**
 * User: plightbo
 * Date: Aug 10, 2005
 * Time: 12:04:38 AM
 */
public interface HibernateConfiguration {
    SessionFactory getSessionFactory();
}
