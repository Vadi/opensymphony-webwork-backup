package com.acme;

import com.opensymphony.webwork.hibernate.HibernateSession;
import com.opensymphony.webwork.hibernate.HibernateSessionAware;

/**
 * User: plightbo
 * Date: Aug 9, 2005
 * Time: 9:24:03 PM
 */
public class CreatePerson implements HibernateSessionAware {
    Person person;
    HibernateSession session;

    public void setHibernateSession(HibernateSession session) {
        this.session = session;
    }

    public String execute() {
        session.create(person);
        return "success";
    }

    public String doDefault() {
        return "input";
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
