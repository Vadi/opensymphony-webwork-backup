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

    VelocityContext[] chainedContexts;
    OgnlValueStack stack;

    //~ Constructors ///////////////////////////////////////////////////////////

    public WebWorkVelocityContext(OgnlValueStack stack) {
        this(null, stack);
    }

    public WebWorkVelocityContext(VelocityContext[] chainedContexts, OgnlValueStack stack) {
        this.chainedContexts = chainedContexts;
        this.stack = stack;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean internalContainsKey(Object key) {
        boolean contains = super.internalContainsKey(key);

        // first let's check to see if we contain the requested key
        if (contains) {
            return true;
        }

        // if not, let's search for the key in the ognl value stack
        if (stack != null) {
            Object o = stack.findValue(key.toString());

            if (o != null) {
                return true;
            }
        }

        // if we still haven't found it, le's search through our chained contexts
        if (chainedContexts != null) {
            for (int index = 0; index < chainedContexts.length; index++) {
                if (chainedContexts[index].containsKey(key)) {
                    return true;
                }
            }
        }

        // nope, i guess it's really not here
        return false;
    }

    public Object internalGet(String key) {
        // first, let's check to see if have the requested value
        if (super.internalContainsKey(key)) {
            return super.internalGet(key);
        }

        // still no luck?  let's look against the value stack
        if (stack != null) {
            Object object = stack.findValue(key);
            if( object != null ) {
                return object;
            }
        }

        // finally, if we're chained to other contexts, let's look in them
        if( chainedContexts != null ) {
            for (int index = 0; index < chainedContexts.length; index++) {
                if (chainedContexts[index].containsKey(key)) {
                    return chainedContexts[index].internalGet(key);
                }
            }
        }

        // nope, i guess it's really not here
        return null;
    }
}
