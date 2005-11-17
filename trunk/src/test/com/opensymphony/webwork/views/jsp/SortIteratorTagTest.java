/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.opensymphony.webwork.views.jsp.iterator.SortIteratorTag;
import com.opensymphony.xwork.Action;
import com.opensymphony.xwork.ActionSupport;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Test case to test SortIteratorTag.
 *
 * @author tm_jee (tm_jee(at)yahoo.co.uk)
 * @version $Date$ $Id$
 */
public class SortIteratorTagTest extends AbstractTagTest {

    public void testSortWithoutId() throws Exception {
        SortIteratorTag tag = new SortIteratorTag();

        tag.setComparator("comparator");
        tag.setSource("source");

        tag.setPageContext(pageContext);
        tag.doStartTag();

        // if not a List, just let the ClassCastException be thrown as error instead of failure
        List sortedList = (List) stack.findValue("top");

        assertNotNull(sortedList);
        assertEquals(sortedList.size(), 5);
        assertEquals(sortedList.get(0), new Integer(1));
        assertEquals(sortedList.get(1), new Integer(2));
        assertEquals(sortedList.get(2), new Integer(3));
        assertEquals(sortedList.get(3), new Integer(4));
        assertEquals(sortedList.get(4), new Integer(5));

        tag.doEndTag();
    }

    public void testSortWithId() throws Exception {
        SortIteratorTag tag = new SortIteratorTag();

        tag.setId("myId");
        tag.setComparator("comparator");
        tag.setSource("source");

        tag.setPageContext(pageContext);
        tag.doStartTag();

        {
            List sortedList = (List) stack.findValue("top");

            assertNotNull(sortedList);
            assertEquals(sortedList.size(), 5);
            assertEquals(sortedList.get(0), new Integer(1));
            assertEquals(sortedList.get(1), new Integer(2));
            assertEquals(sortedList.get(2), new Integer(3));
            assertEquals(sortedList.get(3), new Integer(4));
            assertEquals(sortedList.get(4), new Integer(5));
        }

        {
            List sortedList = (List) pageContext.getAttribute("myId");

            assertNotNull(sortedList);
            assertEquals(sortedList.size(), 5);
            assertEquals(sortedList.get(0), new Integer(1));
            assertEquals(sortedList.get(1), new Integer(2));
            assertEquals(sortedList.get(2), new Integer(3));
            assertEquals(sortedList.get(3), new Integer(4));
            assertEquals(sortedList.get(4), new Integer(5));
        }

        tag.doEndTag();
    }

    public void testSortWithIllegalSource() throws Exception {
        SortIteratorTag tag = new SortIteratorTag();

        tag.setComparator("comparator");
        tag.setSource("badSource");

        try {
            tag.setPageContext(pageContext);
            tag.doStartTag();
            tag.doEndTag();
            fail("ClassCastException expected");
        }
        catch (ClassCastException e) {
            // ok
            assertTrue(true);
        }
    }

    public void testSortWithIllegalComparator() throws Exception {
        SortIteratorTag tag = new SortIteratorTag();

        tag.setComparator("badComparator");
        tag.setSource("source");

        try {
            tag.setPageContext(pageContext);
            tag.doStartTag();
            tag.doEndTag();
            fail("ClassCastException expected");
        }
        catch (ClassCastException e) {
            // good
            assertTrue(true);
        }

    }

    public Action getAction() {
        return new ActionSupport() {
            public Comparator getComparator() {
                return new Comparator() {
                    public int compare(Object o1, Object o2) {
                        Integer i1 = (Integer) o1;
                        Integer i2 = (Integer) o2;

                        return (i1.intValue() - i2.intValue());
                    }
                };
            }

            public List getSource() {
                List l = new ArrayList();
                l.add(new Integer(3));
                l.add(new Integer(1));
                l.add(new Integer(2));
                l.add(new Integer(5));
                l.add(new Integer(4));
                return l;
            }

            public Object getBadComparator() {
                return new Object();
            }

            public Object getBadSource() {
                return new Object();
            }
        };
    }
}
