/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 2/10/2003
 *
 */
package com.opensymphony.webwork.dispatcher;

import java.util.HashMap;
import java.util.Map;


/**
 * @author CameronBraid
 */
public class ServletDispatcherTest extends AbstractServletDispatcherTestCase {
    //~ Methods ////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see com.opensymphony.webwork.dispatcher.AbstractServletDispatcherTest#getConfigFilename()
     */
    public String getConfigFilename() {
        return "com/opensymphony/webwork/dispatcher/ServletDispatcherTest-xwork.xml";
    }

    /* (non-Javadoc)
     * @see com.opensymphony.webwork.dispatcher.AbstractServletDispatcherTest#getServletDispatcher()
     */
    public ServletDispatcher getServletDispatcher() {
        return new ServletDispatcher();
    }

    /* (non-Javadoc)
     * @see com.opensymphony.webwork.dispatcher.AbstractServletDispatcherTest#getServletPath()
     */
    public String getServletPath() {
        return "/Test.action";
    }

//    public void testEncodingAndLocaleSetFromWebWorkProperties() throws IOException, ServletException {
//        testServletDispatcher();
//        assertEquals("ISO-8859-1",ServletDispatcher.getEncoding());
//        assertEquals(Locale.GERMANY,ServletDispatcher.getLocale());
//    }

    /* (non-Javadoc)
     * @see com.opensymphony.webwork.dispatcher.AbstractServletDispatcherTestCase#getParameterMap()
     */
    protected Map getParameterMap() {
        Map map = new HashMap();
        map.put("foo", "bar");

        return map;
    }
}
