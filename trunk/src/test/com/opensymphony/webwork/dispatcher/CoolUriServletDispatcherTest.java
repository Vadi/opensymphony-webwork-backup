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
public class CoolUriServletDispatcherTest extends AbstractServletDispatcherTestCase {
    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *
     */
    public CoolUriServletDispatcherTest(String name) {
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
        return new CoolUriServletDispatcher();
    }

    /* (non-Javadoc)
     * @see com.opensymphony.webwork.dispatcher.AbstractServletDispatcherTest#getServletPath()
     */
    public String getServletPath() {
        return "/Test/foo/bar";
    }
}
