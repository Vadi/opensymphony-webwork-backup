/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import com.opensymphony.xwork.Action;
import org.apache.commons.logging.LogFactory;

import java.util.*;


/**
 * A bean that takes an iterator and outputs a subset of it.
 *
 * @author Rickard �berg (rickard@middleware-company.com)
 * @version $Revision$
 * @see <related>
 */
public class SortIteratorFilter extends IteratorFilterSupport implements Iterator, Action {

    Comparator comparator;
    Iterator iterator;
    List list;

    // Attributes ----------------------------------------------------
    Object source;


    public void setComparator(Comparator aComparator) {
        this.comparator = aComparator;
    }

    public List getList() {
        return list;
    }

    // Public --------------------------------------------------------
    public void setSource(Object anIterator) {
        source = anIterator;
    }

    // Action implementation -----------------------------------------
    public String execute() {
        if (source == null) {
            return ERROR;
        } else {
            try {
                if (!MakeIterator.isIterable(source)) {
                    LogFactory.getLog(SortIteratorFilter.class.getName()).warn("Cannot create SortIterator for source " + source);

                    return ERROR;
                }

                list = new ArrayList();

                Iterator i = MakeIterator.convert(source);

                while (i.hasNext()) {
                    list.add(i.next());
                }

                // Sort it
                Collections.sort(list, comparator);
                iterator = list.iterator();

                return SUCCESS;
            } catch (Exception e) {
                LogFactory.getLog(SortIteratorFilter.class.getName()).warn("Error creating sort iterator.", e);

                return ERROR;
            }
        }
    }

    // Iterator implementation ---------------------------------------
    public boolean hasNext() {
        return (source == null) ? false : iterator.hasNext();
    }

    public Object next() {
        return iterator.next();
    }

    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported in SortIteratorFilter.");
    }
}
