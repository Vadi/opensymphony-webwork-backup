/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;


/**
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 */
public class TagDirective extends AbstractTagDirective {
    //~ Methods ////////////////////////////////////////////////////////////////

    public String getName() {
        return "tag";
    }

    public int getType() {
        return LINE;
    }
}
