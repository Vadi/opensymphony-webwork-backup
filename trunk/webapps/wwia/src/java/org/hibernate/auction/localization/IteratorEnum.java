/*
 * Copyright (c) 2004 Manning Publications. All Rights Reserved.
 */
package org.hibernate.auction.localization;

import java.util.Enumeration;
import java.util.Iterator;

/**
 * IteratorEnum
 *
 * @author Jason Carreira <jason@zenfrog.com>
 */
class IteratorEnum implements Enumeration {
    Iterator iterator;
    public IteratorEnum(Iterator iterator) {
        this.iterator = iterator;
    }

    public boolean hasMoreElements() {
        return iterator.hasNext();
    }

    public Object nextElement() {
        return iterator.next();
    }
}

