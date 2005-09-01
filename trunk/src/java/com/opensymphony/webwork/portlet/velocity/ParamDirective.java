/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.velocity;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import java.io.IOException;
import java.io.Writer;

/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public final class ParamDirective extends Directive {

    private static Log log;

    static {
        log = LogFactory.getLog(com.opensymphony.webwork.portlet.velocity.ParamDirective.class);
    }

    public ParamDirective() {
    }

    public String getName() {
        return "decoratorParam";
    }

    public int getType() {
        return 2;
    }

    public void init(RuntimeServices services, InternalContextAdapter adapter, Node node) throws Exception {
        super.init(services, adapter, node);
        int numArgs = node.jjtGetNumChildren();
        if (numArgs != 2)
            services.error("#decoratorParam error: You must specify a param name and value.");
    }

    public boolean render(InternalContextAdapter adapter, Writer writer, Node node) throws IOException, ResourceNotFoundException,
            ParseErrorException, MethodInvocationException {
        ApplyDecoratorDirective.DirectiveStack stack = (ApplyDecoratorDirective.DirectiveStack) adapter
                .get(ApplyDecoratorDirective.STACK_KEY);
        if (stack == null)
            throw new ParseErrorException("#decoratorParam error: You must nest this directive within a #applyDecorator directive");
        ApplyDecoratorDirective parent = stack.peek();
        if (parent == null) {
            log.error("#decoratorParam error: You must nest this directive within a #applyDecorator directive");
            return false;
        } else {
            String paramName = (String) node.jjtGetChild(0).value(adapter);
            Object paramValue = node.jjtGetChild(1).value(adapter);
            parent.addParameter(paramName, paramValue);
            return true;
        }
    }

}