package com.opensymphony.webwork.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *	A bean that takes several iterators and outputs them in sequence
 *
 *	@see <related>
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public class AppendIteratorFilter
        extends IteratorFilterSupport
        implements java.util.Iterator, webwork.action.Action {
    // Attributes ----------------------------------------------------
    List sources = new ArrayList();
    List iterators = new ArrayList();

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
        } else
            return false;
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
