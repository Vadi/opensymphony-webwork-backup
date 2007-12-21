/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.json;

import junit.framework.TestCase;

/**
 * @author tmjee
 * @version $Date$ $Id$
 */
public class JSONObjectTest extends TestCase {

    public void test1() throws Exception {
        JSONObject j = new JSONObject();
        j.put("name", "tmjee");
        j.put("age", "22");

        assertEquals(j.toString(), "{\"name\":\"tmjee\",\"age\":\"22\"}");
    }
    
}
