/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import java.util.Enumeration;
import java.util.Iterator;


/**
 * @author $Author$
 * @version $Revision$
 */
public class EnumeratorIterator implements Iterator {
    //~ Instance fields ////////////////////////////////////////////////////////

    Enumeration e;

    //~ Constructors ///////////////////////////////////////////////////////////

    public EnumeratorIterator(Enumeration e) {
        this.e = e;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean hasNext() {
        return e.hasMoreElements();
    }

    public Object next() {
        return e.nextElement();
    }

    public void remove() {
        throw new UnsupportedOperationException("Cannot remove elements from an Enumerator");
    }
}
