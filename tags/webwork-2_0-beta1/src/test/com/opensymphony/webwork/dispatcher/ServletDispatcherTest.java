/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import junit.framework.TestCase;


/**
 * ServletDispatcherTest
 * @author Jason Carreira
 * Created Mar 25, 2003 11:55:37 PM
 */
public class ServletDispatcherTest extends TestCase {
    //~ Methods ////////////////////////////////////////////////////////////////

    public void testGetActionName() {
        ServletDispatcher dispatcher = new ServletDispatcher();
        String actionName = dispatcher.getActionName("/foo/bar/Baz.action");
        assertEquals("Baz", actionName);

        // todo do we care if foo.bar.Baz.action returns foo.bar.Baz?
        // We use aliases for everything now, so they had to set this up
        actionName = dispatcher.getActionName("/foo/bar/foo.bar.Baz.action");
        assertEquals("foo.bar.Baz", actionName);
    }
}
