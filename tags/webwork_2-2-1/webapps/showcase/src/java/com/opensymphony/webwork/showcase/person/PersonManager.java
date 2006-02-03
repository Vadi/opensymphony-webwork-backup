package com.opensymphony.webwork.showcase.person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * User: plightbo
 * Date: Sep 20, 2005
 * Time: 6:27:07 PM
 */
public class PersonManager {
    private Set people = new HashSet();
    private static long COUNT = 0;

    public void createPerson(Person person) {
        person.setId(new Long(++COUNT));
        people.add(person);
    }

    public void updatePerson(Person person) {
        people.add(person);
    }

    public Set getPeople() {
        return people;
    }
}
