package com.opensymphony.webwork;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.xwork.XWorkTestCase;

/**
 * User: plightbo
 * Date: Jun 25, 2005
 * Time: 9:20:27 AM
 */
public abstract class WebWorkTestCase extends XWorkTestCase {
    protected void setUp() throws Exception {
        super.setUp();

        Configuration.reset();
    }

    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
