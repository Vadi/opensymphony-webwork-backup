package com.acme;

import java.util.ArrayList;
import java.util.List;

/**
 * User: plightbo
 * Date: Sep 20, 2005
 * Time: 6:27:07 PM
 */
public class PersonManager {
    private List people = new ArrayList();

    public void createPerson(Person person) {
        people.add(person);
    }

    public List getPeople() {
        return people;
    }
}
