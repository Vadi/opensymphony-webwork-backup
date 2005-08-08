package com.opensymphony.webwork.sandbox;

/**
 * User: plightbo
 * Date: Aug 7, 2005
 * Time: 5:53:28 PM
 */
public class GetPerson {
    long personId;
    Person person;

    public String execute() {
        if (personId == 1) {
            person = new Person(1, "Patrick");
        } else if (personId == 2) {
            person = new Person(2, "Jason");
        }

        return "success";
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    public Person getPerson() {
        return person;
    }
}
