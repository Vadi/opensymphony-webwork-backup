package com.opensymphony.webwork.views.jsp;

import java.util.Enumeration;
import java.util.Iterator;

/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class EnumeratorIterator implements Iterator {
    Enumeration e;

    public EnumeratorIterator(Enumeration e) {
        this.e = e;
    }

    public boolean hasNext() {
        return e.hasMoreElements();
    }

    public Object next() {
        return e.nextElement();
    }

    public void remove() {
        throw UnsupportedOperationException("Cannot remove elements from an Enumerator");
    }
}
