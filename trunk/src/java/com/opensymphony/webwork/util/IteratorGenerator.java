/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


/**
 *        A bean that generates an iterator filled with a given object
 *
 *        @author Rickard Öberg (rickard@middleware-company.com)
 *        @version $Revision$
 */
public class IteratorGenerator implements java.util.Iterator, webwork.action.Action {
    //~ Instance fields ////////////////////////////////////////////////////////

    List values;
    Object value;
    String separator;

    // Attributes ----------------------------------------------------
    int count = 0;
    int currentCount = 0;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setCount(int aCount) {
        this.count = aCount;
    }

    public boolean getHasNext() {
        return hasNext();
    }

    public Object getNext() {
        return next();
    }

    public void setSeparator(String aChar) {
        separator = aChar;
    }

    // Public --------------------------------------------------------
    public void setValues(Object aValue) {
        value = aValue;
    }

    // Action implementation -----------------------------------------
    public String execute() {
        if (value == null) {
            return ERROR;
        } else {
            values = new ArrayList();

            if (separator != null) {
                StringTokenizer tokens = new StringTokenizer(value.toString(), separator);

                while (tokens.hasMoreTokens()) {
                    values.add(tokens.nextToken());
                }
            } else {
                values.add(value.toString());
            }

            // Count default is the size of the list of values
            if (count == 0) {
                count = values.size();
            }

            return SUCCESS;
        }
    }

    // Iterator implementation ---------------------------------------
    public boolean hasNext() {
        return (value == null) ? false : ((currentCount < count) || (count == -1));
    }

    public Object next() {
        try {
            return values.get(currentCount % values.size());
        } finally {
            currentCount++;
        }
    }

    public void remove() {
        throw new UnsupportedOperationException("Remove is not supported in IteratorGenerator.");
    }
}
