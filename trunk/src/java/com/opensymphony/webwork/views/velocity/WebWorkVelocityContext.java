/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.velocity.VelocityContext;


/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class WebWorkVelocityContext extends VelocityContext {
    //~ Instance fields ////////////////////////////////////////////////////////

    OgnlValueStack stack;

    //~ Constructors ///////////////////////////////////////////////////////////

    public WebWorkVelocityContext(OgnlValueStack stack) {
        this.stack = stack;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean internalContainsKey(Object key) {
        boolean contains = super.internalContainsKey(key);

        if (contains) {
            return true;
        } else {
            if (stack == null) {
                return false;
            }

            Object o = stack.findValue(key.toString());

            if (o != null) {
                return true;
            } else {
                return false;
            }
        }
    }

    public Object internalGet(String key) {
        if (super.internalContainsKey(key)) {
            return super.internalGet(key);
        } else {
            if (stack != null) {
                return stack.findValue(key);
            } else {
                return null;
            }
        }
    }
}
