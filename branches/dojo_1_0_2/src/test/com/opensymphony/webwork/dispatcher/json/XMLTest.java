/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.json;

import org.custommonkey.xmlunit.XMLTestCase;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class XMLTest extends XMLTestCase {

    public void testEscape() throws Exception {
        assertEquals(XML.escape("&"), "&amp;");
        assertEquals(XML.escape("<"), "&lt;");
        assertEquals(XML.escape(">"), "&gt;");
        assertEquals(XML.escape("\""), "&quot;");
        assertEquals(XML.escape("ddsd"), "ddsd");
    }

    public void testToJSONObject() throws Exception {
        String xml =
                "<!-- asd --><name>toby</name>" +
                        "<age>22</age>";

        JSONObject jsonObject = XML.toJSONObject(xml);
        assertEquals(jsonObject.get("name"), "toby");
        assertEquals(jsonObject.get("age"), "22");
    }

    public void testToString() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "tmjee");
        jsonObject.put("age", "22");

        assertXMLEqual("<root>"+XML.toString(jsonObject)+"</root>",
                "<root><name>tmjee</name><age>22</age></root>");
    }

    public void testToStringWithRoot() throws Exception {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "tmjee");
        jsonObject.put("age", "22");

        assertXMLEqual(XML.toString(jsonObject, "root"),
                "<root><name>tmjee</name><age>22</age></root>");
    }

    

}
