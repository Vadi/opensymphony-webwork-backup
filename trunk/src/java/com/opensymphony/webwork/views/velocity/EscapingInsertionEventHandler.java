/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.webwork.views.util.TextUtil;

import org.apache.velocity.app.event.ReferenceInsertionEventHandler;


/**
 *
 * @version $Id$
 * @author Matt Ho <a href="mailto:matt@enginegreen.com">&lt;matt@enginegreen.com&gt;</a>
 */
public class EscapingInsertionEventHandler implements ReferenceInsertionEventHandler {
    //~ Methods ////////////////////////////////////////////////////////////////

    public Object referenceInsert(String reference, Object value) {
        if ((value != null) && value instanceof String) {
            value = TextUtil.escapeHTML(value.toString());
        }

        return value;
    }
}
