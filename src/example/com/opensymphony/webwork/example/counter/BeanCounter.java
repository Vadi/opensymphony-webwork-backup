package com.opensymphony.webwork.example.counter;

/**
 * Created by IntelliJ IDEA.
 * User: matt
 * Date: Jul 2, 2003
 * Time: 7:10:33 AM
 * To change this template use Options | File Templates.
 */
public class BeanCounter extends SimpleCounter {
    public BeanCounter() {
        super(new Counter());
    }
}
