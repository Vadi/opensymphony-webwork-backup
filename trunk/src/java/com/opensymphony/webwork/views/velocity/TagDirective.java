/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;


/**
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 * @deprecated Automatic JSP tag support doesn't work well and is likely to break. Please use the native Velocity
 * tags introduced in WebWork 2.2
 */
public class TagDirective extends AbstractTagDirective {
    public String getName() {
        return "tag";
    }

    public int getType() {
        return LINE;
    }
}
