/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.util;

import java.util.Enumeration;
import java.util.Iterator;


/**
 *        A base class for iterator filters
 *
 *        @author Rickard Öberg (rickard@middleware-company.com)
 *        @version $Revision$
 */
public abstract class IteratorFilterSupport {
    //~ Methods ////////////////////////////////////////////////////////////////

    // Protected implementation --------------------------------------
    protected Object getIterator(Object source) {
        return MakeIterator.convert(source);
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    // Wrapper for enumerations
    public class EnumerationIterator implements Iterator {
        Enumeration enum;

        public EnumerationIterator(Enumeration aEnum) {
            enum = aEnum;
        }

        public boolean hasNext() {
            return enum.hasMoreElements();
        }

        public Object next() {
            return enum.nextElement();
        }

        public void remove() {
            throw new UnsupportedOperationException("Remove is not supported in IteratorFilterSupport.");
        }
    }
}
