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
 *
 */
public class ServletDispatcherTest extends AbstractServletDispatcherTestCase {
    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *
     */
    public ServletDispatcherTest(String name) {
        super(name);
    }

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

    public void testGetActionName() {
        ServletDispatcher dispatcher = new ServletDispatcher();
        String actionName = null;

        actionName = dispatcher.getActionName("/foo/bar/Baz.action");
        assertEquals("Baz", actionName);

        actionName = dispatcher.getActionName("/foo/bar/Baz");
        assertEquals("Baz", actionName);

        // todo do we care if foo.bar.Baz.action returns foo.bar.Baz?
        // We use aliases for everything now, so they had to set this up
        actionName = dispatcher.getActionName("/foo/bar/foo.bar.Baz.action");
        assertEquals("foo.bar.Baz", actionName);
    }

    /* (non-Javadoc)
     * @see com.opensymphony.webwork.dispatcher.AbstractServletDispatcherTestCase#getParameterMap()
     */
    protected Map getParameterMap() {
        Map map = new HashMap();
        map.put("foo", "bar");

        return map;
    }
}
