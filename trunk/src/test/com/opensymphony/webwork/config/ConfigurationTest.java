/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.config;

import com.opensymphony.webwork.WebWorkTestCase;
import com.opensymphony.webwork.WebWorkConstants;
import com.opensymphony.xwork.util.LocalizedTextUtil;

import java.util.Iterator;
import java.util.Locale;


/**
 * ConfigurationTest
 *
 * @author Jason Carreira
 *         Created Apr 9, 2003 10:42:30 PM
 */
public class ConfigurationTest extends WebWorkTestCase {

    public void testConfiguration() {
        assertEquals("12345", Configuration.getString(WebWorkConstants.WEBWORK_MULTIPART_MAXSIZE));
        assertEquals("\temp", Configuration.getString(WebWorkConstants.WEBWORK_MULTIPART_SAVEDIR));

        assertEquals("test,com/opensymphony/webwork/othertest", Configuration.getString( WebWorkConstants.WEBWORK_CUSTOM_PROPERTIES));
        assertEquals("testvalue", Configuration.getString("testkey"));
        assertEquals("othertestvalue", Configuration.getString("othertestkey"));

        Locale locale = Configuration.getLocale();
        assertEquals("de", locale.getLanguage());

        int count = getKeyCount();
        assertEquals(23, count);
    }

    public void testDefaultResourceBundlesLoaded() {
        assertEquals("testmessages,testmessages2", Configuration.getString(WebWorkConstants.WEBWORK_CUSTOM_I18N_RESOURCES));
        assertEquals("This is a test message", LocalizedTextUtil.findDefaultText("default.testmessage", Locale.getDefault()));
        assertEquals("This is another test message", LocalizedTextUtil.findDefaultText("default.testmessage2", Locale.getDefault()));
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
