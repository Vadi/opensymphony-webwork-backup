/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.runtime.parser.node.Node;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by IntelliJ IDEA.
 * User: matt
 * Date: May 28, 2003
 * Time: 12:54:46 PM
 * To change this template use Options | File Templates.
 */
public class BodyTagDirective extends AbstractTagDirective {
    //~ Methods ////////////////////////////////////////////////////////////////

    public String getName() {
        return "bodytag";
    }

    public int getType() {
        return BLOCK;
    }

    /**
     * for BLOCK directives, the last element in the Node is the body, so we want to make sure not to include this in
     * the propertyMap that we're generating.
     *
     * @param node the node passed in to the render method
     * @return a map of the user specified properties
     * @throws org.apache.velocity.exception.ParseErrorException
     *          if a property was improperly formatted
     * @see #render
     * @see com.opensymphony.webwork.views.velocity.AbstractTagDirective#createPropertyMap
     */
    protected Map createPropertyMap(InternalContextAdapter contextAdapter, Node node) throws ParseErrorException, MethodInvocationException {
        Map propertyMap = new HashMap();

        for (int index = 1, length = node.jjtGetNumChildren() - 1;
             index < length; index++) {
            this.putProperty(propertyMap, contextAdapter, node.jjtGetChild(index));
        }

        return propertyMap;
    }
}
