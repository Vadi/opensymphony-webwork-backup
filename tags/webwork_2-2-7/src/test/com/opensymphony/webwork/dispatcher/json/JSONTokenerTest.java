/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.json;

import junit.framework.TestCase;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class JSONTokenerTest extends TestCase {

    public void testMoreAndNext() throws Exception {
        JSONTokener tokener = new JSONTokener("123\n4");

        assertTrue(tokener.more());
        assertEquals(tokener.next(), '1');
        assertTrue(tokener.more());
        assertEquals(tokener.next(), '2');
        assertTrue(tokener.more());
        assertEquals(tokener.next(), '3');
        assertTrue(tokener.more());
        assertEquals(tokener.next(), '\n');
        assertTrue(tokener.more());
        assertEquals(tokener.next(), '4');
    }

    public void testNextCharacter() throws Exception {
        JSONTokener tokener = new JSONTokener("123\b456");

        assertEquals(tokener.next('1'), '1');
        assertEquals(tokener.next('2'), '2');
        try {
            tokener.next('5');
            fail("Expecting JSONException");
        }
        catch(JSONException e) {
            assertTrue(true);
        }
    }


    public void testNextCharacters() throws Exception {
        JSONTokener tokener = new JSONTokener("123\n456");

        String tmp = tokener.next(3);
        assertEquals(tmp, "123");

        tmp = tokener.next(3);
        assertEquals(tmp, "\n45");
    }


    public void testNextClean() throws Exception {
        {
            JSONTokener tokener = new JSONTokener("#comment\n123\n456");
            assertEquals(tokener.nextClean(), '1');
        }

        {
            JSONTokener tokener = new JSONTokener("//comment\n123");
            assertEquals(tokener.nextClean(), '1');
        }

        {
            JSONTokener tokener = new JSONTokener("/*comment*/123");
            assertEquals(tokener.nextClean(), '1');
        }

        {
            JSONTokener tokener = new JSONTokener("1");
            assertEquals(tokener.nextClean(), '1');
            assertEquals(tokener.nextClean(), 0);   // or '\u0000'
        }
    }


    public void testNextString() throws Exception {
        JSONTokener tokener = new JSONTokener("one'two'xxxx'three'four\"five");

        {
            String tmp = tokener.nextString('\'');
            assertEquals(tmp, "one");
        }

        {
            String tmp = tokener.nextString('\'');
            assertEquals(tmp, "two");
        }

        {
            String tmp = tokener.nextString('\'');
            assertEquals(tmp, "xxxx");
        }

        {
            String tmp = tokener.nextString('\'');
            assertEquals(tmp, "three");
        }
    }


    public void testNextToChar() throws Exception {
        JSONTokener tokener = new JSONTokener("12345678");

        assertEquals(tokener.nextTo('3'), "12");
        assertEquals(tokener.nextTo('6'), "345");
    }


    public void testNextToDelimeter() throws Exception {
        JSONTokener tokener = new JSONTokener("123a456b789c111");

        assertEquals(tokener.nextTo("abc"), "123");
        assertEquals(tokener.nextTo("bc"), "a456");
        assertEquals(tokener.nextTo("c"), "b789");
        assertEquals(tokener.nextTo("z"), "c111");
    }


    public void testSkipTo() throws Exception {
        JSONTokener tokener = new JSONTokener("123456789");

        assertEquals(tokener.skipTo('4'), '4');
        assertEquals(tokener.skipTo('2'), 0);
        assertEquals(tokener.skipTo('8'), '8');
        assertEquals(tokener.skipTo('a'), 0);
    }


    public void testSkipPass() throws Exception {
        JSONTokener tokener = new JSONTokener("123456789abcdefg");

        assertTrue(tokener.skipPast("34"));
        assertTrue(tokener.skipPast("56"));
        assertTrue(tokener.skipPast("abc"));
        assertTrue(tokener.skipPast("fg"));
        assertFalse(tokener.skipPast("zz"));
    }


    public void testNextValue() throws Exception {
        {   // Number
            JSONTokener tokener = new JSONTokener("123");
            Object val = tokener.nextValue();
            assertTrue(val instanceof Number);
            assertEquals(val, new Integer(123));
        }

        {   // integer
            JSONTokener tokener = new JSONTokener("123");
            Object val = tokener.nextValue();
            assertTrue(val instanceof Integer);
            assertEquals(val, new Integer(123));
        }

        {   // double
            JSONTokener tokener = new JSONTokener("123.334");
            Object val = tokener.nextValue();
            assertTrue(val instanceof Double);
            assertEquals(val, new Double(123.334));
        }

        {   // hex
            JSONTokener tokener = new JSONTokener("0x4");
            Object val = tokener.nextValue();
            assertTrue(val instanceof Integer);
            assertEquals(val, new Integer(4));
        }

        {   // octal
            JSONTokener tokener = new JSONTokener("03");
            Object val = tokener.nextValue();
            assertTrue(val instanceof Integer);
            assertEquals(val, new Integer(3));
        }

        {   // boolean
            JSONTokener tokener = new JSONTokener("true");
            Object val = tokener.nextValue();
            assertTrue(val instanceof Boolean);
            assertTrue(((Boolean)val).booleanValue());
        }

        {   // String
            JSONTokener tokener = new JSONTokener("\"this is a string\"");
            Object val = tokener.nextValue();
            assertTrue(val instanceof String);
            assertEquals(val, "this is a string");
        }

        {   // object
            JSONTokener tokener = new JSONTokener("{name:\"tmjee\", age=25}");
            Object val = tokener.nextValue();
            assertTrue(val instanceof JSONObject);
            assertEquals(((JSONObject)val).get("name"), "tmjee");
            assertEquals(((JSONObject)val).get("age"), new Integer(25));
        }

        {   // Array
            JSONTokener tokener = new JSONTokener("[1,2,3]");
            Object val = tokener.nextValue();
            assertTrue(val instanceof JSONArray);
        }
    }
}
