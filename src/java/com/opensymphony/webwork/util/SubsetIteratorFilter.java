package com.opensymphony.webwork.util;

import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *	A bean that takes an iterator and outputs a subset of it.
 *
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public class SubsetIteratorFilter
        extends IteratorFilterSupport
        implements java.util.Iterator, webwork.action.Action {
    // Attributes ----------------------------------------------------
    int start = 0;
    int count = -1;
    int currentCount = 0;
    Object source;
    Iterator iterator;

    // Public --------------------------------------------------------
    public void setSource(Object anIterator) {
        source = anIterator;
    }

    public void setStart(int aStart) {
        this.start = aStart;
    }

    public void setCount(int aCount) {
        this.count = aCount;
    }

    // Action implementation -----------------------------------------
    public String execute() {
        if (source == null) {
            LogFactory.getLog(SubsetIteratorFilter.class.getName()).warn("Source is null returning empty set.");
            return ERROR;
        }

        // Make source transformations
        source = getIterator(source);

        // Calculate iterator filter
        if (source instanceof Iterator) {
            iterator = (Iterator) source;

            // Read away <start> items
            for (int i = 0; i < start && iterator.hasNext(); i++)
                iterator.next();
        } else if (source.getClass().isArray()) {
            ArrayList list = new ArrayList(((Object[]) source).length);
            Object[] objects = (Object[]) source;
            int len = objects.length;
            if (count != -1)
                len -= count;
            for (int j = start; j < len; j++)
                list.add(objects[j]);
            count = -1; // Don't have to check this in the iterator code
            iterator = list.iterator();
        }

        if (iterator == null)
            throw new IllegalArgumentException("Source is not an iterator:" + source);

        return SUCCESS;
    }

    // Iterator implementation ---------------------------------------
    public boolean hasNext() {
        return (iterator == null) ? false : iterator.hasNext() && (count == -1 || currentCount < count);
    }

    public Object next() {
        currentCount++;
        return iterator.next();
    }

    public void remove() {
        iterator.remove();
    }
}
