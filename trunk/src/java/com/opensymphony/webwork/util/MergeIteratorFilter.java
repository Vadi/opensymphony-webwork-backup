/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 *        A bean that takes several iterators and outputs the merge of them.
 *
 *        @author Rickard �berg (rickard@middleware-company.com)
 *        @version $Revision$
 */
public class MergeIteratorFilter extends IteratorFilterSupport implements java.util.Iterator, webwork.action.Action {
    //~ Instance fields ////////////////////////////////////////////////////////

    List iterators = new ArrayList();

    // Attributes ----------------------------------------------------
    List sources = new ArrayList();
    int idx = 0;

    //~ Methods ////////////////////////////////////////////////////////////////

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
        while (iterators.size() > 0) {
            if (((Iterator) iterators.get(idx)).hasNext()) {
                return true;
            } else {
                iterators.remove(idx);

                if (iterators.size() > 0) {
                    idx = idx % iterators.size();
                }
            }
        }

        return false;
    }

    public Object next() {
        try {
            return ((Iterator) iterators.get(idx)).next();
        } finally {
            idx = (idx + 1) % iterators.size();
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported in MergeIteratorFilter.");
    }
}
