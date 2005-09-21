package com.acme;

import com.opensymphony.xwork.ActionSupport;

/**
 * User: plightbo
 * Date: Aug 9, 2005
 * Time: 9:24:03 PM
 */
public class CreatePerson extends ActionSupport {
    PersonManager pm;
    Person person;

    public void setPm(PersonManager pm) {
        this.pm = pm;
    }

    public String execute() {
        pm.createPerson(person);

        return SUCCESS;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
