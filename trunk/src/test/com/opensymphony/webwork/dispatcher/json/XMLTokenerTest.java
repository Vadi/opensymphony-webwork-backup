package com.opensymphony.webwork.dispatcher.json;

import junit.framework.TestCase;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class XMLTokenerTest extends TestCase {

    public void testNextCDATA() throws Exception {
        XMLTokener tokener = new XMLTokener("testing testing]]> apple pie");

        assertEquals(tokener.nextCDATA(), "testing testing");

        try {
           tokener.nextCDATA();
        }
        catch(JSONException e) {
            assertTrue(true);
        }
    }

    public void testNextContent() throws Exception {
        XMLTokener tokener = new XMLTokener("<tag>content</tag>");

        assertEquals(tokener.nextContent(), XML.LT);
        assertEquals(tokener.nextContent(), "tag>content");
        assertEquals(tokener.nextContent(), XML.LT);
        assertEquals(tokener.nextContent(), "/tag>");
    }


    public void testNextEntity() throws Exception {
        XMLTokener tokener = new XMLTokener("amp;apos;gt;lt;quot;#0002;");

        assertEquals(tokener.nextEntity('&'), XML.AMP);
        assertEquals(tokener.nextEntity('&'), XML.APOS);
        assertEquals(tokener.nextEntity('&'), XML.GT);
        assertEquals(tokener.nextEntity('&'), XML.LT);
        assertEquals(tokener.nextEntity('&'), XML.QUOT);
        assertEquals(tokener.nextEntity('&'), "&#0002;");
    }

    public void testNextMeta() throws Exception {
        XMLTokener tokener = new XMLTokener("<?xml version='1.0' encoding='UTF-8' ?>");

        assertEquals(tokener.nextMeta(), XML.LT);
        assertEquals(tokener.nextMeta(), XML.QUEST);
        assertEquals(tokener.nextMeta(), Boolean.TRUE);
        assertEquals(tokener.nextMeta(), Boolean.TRUE);
        assertEquals(tokener.nextMeta(), XML.EQ);
        assertEquals(tokener.nextMeta(), Boolean.TRUE);
        assertEquals(tokener.nextMeta(), Boolean.TRUE);
        assertEquals(tokener.nextMeta(), XML.EQ);
        assertEquals(tokener.nextMeta(), Boolean.TRUE);
        assertEquals(tokener.nextMeta(), XML.QUEST);
        assertEquals(tokener.nextMeta(), XML.GT);

        try {
            tokener.nextMeta();
            fail("Expecting JSONException");
        }
        catch(JSONException e) {
            assertTrue(true);
        }
    }


    public void testNextToken() throws Exception {
        XMLTokener tokener = new XMLTokener("tag name='toby' age='22'>");

        assertEquals(tokener.nextToken(), "tag");
        assertEquals(tokener.nextToken(), "name");
        assertEquals(tokener.nextToken(), XML.EQ);
        assertEquals(tokener.nextToken(), "toby");
        assertEquals(tokener.nextToken(), "age");
        assertEquals(tokener.nextToken(), XML.EQ);
        assertEquals(tokener.nextToken(), "22");
        assertEquals(tokener.nextToken(), XML.GT);
        
    }

}
