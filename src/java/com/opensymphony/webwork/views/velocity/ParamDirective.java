/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity;

import com.opensymphony.webwork.views.jsp.ParameterizedTag;

import com.opensymphony.xwork.util.OgnlValueStack;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;


/**
 * Created by IntelliJ IDEA.
 * User: matt
 * Date: May 28, 2003
 * Time: 1:03:38 PM
 * To change this template use Options | File Templates.
 */
public class ParamDirective extends Directive {
    //~ Methods ////////////////////////////////////////////////////////////////

    public String getName() {
        return "param";
    }

    public int getType() {
        return LINE;
    }

    /**
    *
    * @param contextAdapter
    * @param writer
    * @param node
    * @return
    * @throws java.io.IOException
    * @throws org.apache.velocity.exception.ResourceNotFoundException
    * @throws org.apache.velocity.exception.ParseErrorException
    * @throws org.apache.velocity.exception.MethodInvocationException
    */
    public boolean render(InternalContextAdapter contextAdapter, Writer writer, Node node) throws IOException, ResourceNotFoundException, ParseErrorException, MethodInvocationException {
        Object object = contextAdapter.get(VelocityManager.TAG);

        if ((object != null) && (object instanceof ParameterizedTag)) {
            if (node.jjtGetNumChildren() != 2) {
                throw new ParseErrorException("#param directive requires two parameters, a key and a value");
            }

            Object key = node.jjtGetChild(0).value(contextAdapter);
            Object value = node.jjtGetChild(1).value(contextAdapter);

            ParameterizedTag parameterizedTag = (ParameterizedTag) object;

            if (key != null) {
                parameterizedTag.addParam(key.toString(), value);
            }

            return true;
        } else {
            return false;
        }
    }
}
