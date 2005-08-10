package com.acme;

import com.opensymphony.webwork.hibernate.HibernateSession;
import com.opensymphony.webwork.hibernate.HibernateSessionAware;

import java.util.List;

/**
 * User: plightbo
 * Date: Aug 10, 2005
 * Time: 12:03:15 AM
 */
public class ListPeople implements HibernateSessionAware {
    List people;
    HibernateSession session;

    public void setHibernateSession(HibernateSession session) {
        this.session = session;
    }

    public String execute() {
        people = session.getAll(Person.class);
        return "success";
    }

    public List getPeople() {
        return people;
    }
}
