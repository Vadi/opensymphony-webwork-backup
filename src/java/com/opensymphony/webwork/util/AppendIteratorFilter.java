/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.xwork.Action;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * A bean that takes several iterators and outputs them in sequence
 *
 * @author Rickard �berg (rickard@middleware-company.com)
 * @version $Revision$
 * @see <related>
 */
public class AppendIteratorFilter extends IteratorFilterSupport implements Iterator, Action {

    List iterators = new ArrayList();

    // Attributes ----------------------------------------------------
    List sources = new ArrayList();


    // Public --------------------------------------------------------
    public void setSource(Object anIterator) {
        sources.add(anIterator);
    }

    // Action implementation -----------------------------------------
    public String execute() {
        // Make source transformations
        for (int i = 0; i < sources.size(); i++) {
            Object source = sources.get(i);
            iterators.add(getIterator(source));
        }

        return SUCCESS;
    }

    // Iterator implementation ---------------------------------------
    public boolean hasNext() {
        if (iterators.size() > 0) {
            return (((Iterator) iterators.get(0)).hasNext());
        } else {
            return false;
        }
    }

    public Object next() {
        try {
            return ((Iterator) iterators.get(0)).next();
        } finally {
            if (iterators.size() > 0) {
                if (!((Iterator) iterators.get(0)).hasNext()) {
                    iterators.remove(0);
                }
            }
        }
    }

    public void remove() {
        throw new UnsupportedOperationException();
    }
}
