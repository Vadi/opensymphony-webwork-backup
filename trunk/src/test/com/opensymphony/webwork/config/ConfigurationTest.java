/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.config;

import junit.framework.TestCase;

import java.util.Iterator;
import java.util.Locale;


/**
 * ConfigurationTest
 * @author Jason Carreira
 * Created Apr 9, 2003 10:42:30 PM
 */
public class ConfigurationTest extends TestCase {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testConfiguration() {
        assertEquals("12345", Configuration.getString("webwork.multipart.maxSize"));
        assertEquals("\temp", Configuration.getString("webwork.multipart.saveDir"));

        assertEquals("test,com/opensymphony/webwork/othertest", Configuration.getString("webwork.custom.properties"));
        assertEquals("testvalue", Configuration.getString("testkey"));
        assertEquals("othertestvalue", Configuration.getString("othertestkey"));

        Locale locale = Configuration.getLocale();
        assertEquals("de", locale.getLanguage());

        int count = getKeyCount();
        assertEquals(12, count);
    }

    public void testSetConfiguration() {
        Configuration.setConfiguration(new TestConfiguration());

        String keyName = "a.long.property.key.name";
        assertEquals(keyName, Configuration.getString(keyName));
        assertEquals(2, getKeyCount());
    }

    private int getKeyCount() {
        int count = 0;
        Iterator keyNames = Configuration.list();

        while (keyNames.hasNext()) {
            String key = (String) keyNames.next();
            count++;
        }

        return count;
    }
}
