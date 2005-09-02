/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp;

import com.mockobjects.servlet.MockBodyContent;
import com.mockobjects.servlet.MockJspWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @author $Author$
 * @version $Revision$
 */
public class IteratorTagTest extends AbstractUITagTest {
    //~ Instance fields ////////////////////////////////////////////////////////

    IteratorTag tag;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void testArrayIterator() {
        Foo foo = new Foo();
        foo.setArray(new String[]{"test1", "test2", "test3"});

        stack.push(foo);

        tag.setValue("array");

        iterateThreeStrings();
    }

    public void testCollectionIterator() {
        Foo foo = new Foo();
        ArrayList list = new ArrayList();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        foo.setList(list);

        stack.push(foo);

        tag.setValue("list");

        iterateThreeStrings();
    }

    public void testIteratorWithDefaultValue() {
        stack.push(new String[]{"test1", "test2", "test3"});
        iterateThreeStrings();
    }

    public void testMapIterator() {
        Foo foo = new Foo();
        HashMap map = new HashMap();
        map.put("test1", "123");
        map.put("test2", "456");
        map.put("test3", "789");
        foo.setMap(map);

        stack.push(foo);

        tag.setValue("map");

        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.EVAL_BODY_AGAIN, result);
        assertEquals(4, stack.size());
        assertTrue(stack.getRoot().peek() instanceof Map.Entry);

        try {
            result = tag.doAfterBody();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.EVAL_BODY_AGAIN, result);
        assertEquals(4, stack.size());
        assertTrue(stack.getRoot().peek() instanceof Map.Entry);

        try {
            result = tag.doAfterBody();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.EVAL_BODY_AGAIN, result);
        assertEquals(4, stack.size());
        assertTrue(stack.getRoot().peek() instanceof Map.Entry);

        try {
            result = tag.doAfterBody();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(TagSupport.SKIP_BODY, result);
        assertEquals(3, stack.size());
    }

    public void testStatus() {
        Foo foo = new Foo();
        foo.setArray(new String[]{"test1", "test2", "test3"});

        stack.push(foo);

        tag.setValue("array");
        tag.setStatus("fooStatus");

        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(result, TagSupport.EVAL_BODY_AGAIN);
        assertEquals("test1", stack.getRoot().peek());
        assertEquals(4, stack.size());

        IteratorStatus status = (IteratorStatus) context.get("fooStatus");
        assertNotNull(status);
        assertFalse(status.isLast());
        assertTrue(status.isFirst());
        assertEquals(0, status.getIndex());
        assertEquals(1, status.getCount());
        assertTrue(status.isOdd());
        assertFalse(status.isEven());

        try {
            result = tag.doAfterBody();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(result, TagSupport.EVAL_BODY_AGAIN);
        assertEquals("test2", stack.getRoot().peek());
        assertEquals(4, stack.size());

        status = (IteratorStatus) context.get("fooStatus");
        assertNotNull(status);
        assertFalse(status.isLast());
        assertFalse(status.isFirst());
        assertEquals(1, status.getIndex());
        assertEquals(2, status.getCount());
        assertFalse(status.isOdd());
        assertTrue(status.isEven());

        try {
            result = tag.doAfterBody();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(result, TagSupport.EVAL_BODY_AGAIN);
        assertEquals("test3", stack.getRoot().peek());
        assertEquals(4, stack.size());

        status = (IteratorStatus) context.get("fooStatus");
        assertNotNull(status);
        assertTrue(status.isLast());
        assertFalse(status.isFirst());
        assertEquals(2, status.getIndex());
        assertEquals(3, status.getCount());
        assertTrue(status.isOdd());
        assertFalse(status.isEven());
    }

    protected void setUp() throws Exception {
        super.setUp();

        // create the needed objects
        tag = new IteratorTag();

        MockBodyContent mockBodyContent = new MockBodyContent();
        mockBodyContent.setupGetEnclosingWriter(new MockJspWriter());
        tag.setBodyContent(mockBodyContent);

        // associate the tag with the mock page request
        tag.setPageContext(pageContext);
    }

    private void iterateThreeStrings() {
        int result = 0;

        try {
            result = tag.doStartTag();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(result, TagSupport.EVAL_BODY_AGAIN);
        assertEquals("test1", stack.getRoot().peek());
        assertEquals(4, stack.size());

        try {
            result = tag.doAfterBody();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(result, TagSupport.EVAL_BODY_AGAIN);
        assertEquals("test2", stack.getRoot().peek());
        assertEquals(4, stack.size());

        try {
            result = tag.doAfterBody();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(result, TagSupport.EVAL_BODY_AGAIN);
        assertEquals("test3", stack.getRoot().peek());
        assertEquals(4, stack.size());

        try {
            result = tag.doAfterBody();
        } catch (JspException e) {
            e.printStackTrace();
            fail();
        }

        assertEquals(result, TagSupport.SKIP_BODY);
        assertEquals(3, stack.size());
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    class Foo {
        private Collection list;
        private Map map;
        private String[] array;

        public void setArray(String[] array) {
            this.array = array;
        }

        public String[] getArray() {
            return array;
        }

        public void setList(Collection list) {
            this.list = list;
        }

        public Collection getList() {
            return list;
        }

        public void setMap(Map map) {
            this.map = map;
        }

        public Map getMap() {
            return map;
        }
    }
}
