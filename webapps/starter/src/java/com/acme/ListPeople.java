package com.acme;

import com.opensymphony.xwork.ActionSupport;

import java.util.List;

/**
 * User: plightbo
 * Date: Aug 10, 2005
 * Time: 12:03:15 AM
 */
public class ListPeople extends ActionSupport {
    String foo;
    PersonManager personManager;
    List people;

    public void setPersonManager(PersonManager personManager) {
        this.personManager = personManager;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    public String execute() {
        people = personManager.getPeople();
        System.out.println(foo);
        System.out.println(foo);
        System.out.println(foo);
        System.out.println(foo);
        System.out.println(foo);

        return SUCCESS;
    }

    public List getPeople() {
        return people;
    }
}
