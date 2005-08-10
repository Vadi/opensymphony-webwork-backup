package com.opensymphony.webwork.hibernate;

import java.util.List;

/**
 * User: plightbo
 * Date: Aug 10, 2005
 * Time: 12:05:48 AM
 */
public interface HibernateSession {
    public List getAll(Class type);

    public void create(Object o);
}
