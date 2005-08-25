/*
 * Copyright (c) 2005 Opensymphony. All Rights Reserved.
 */
package com.opensymphony.webwork.portlet.velocity;

import com.opensymphony.module.sitemesh.Decorator;
import com.opensymphony.module.sitemesh.Factory;
import com.opensymphony.module.sitemesh.HTMLPage;
import com.opensymphony.module.sitemesh.parser.FastPageParser;
import com.opensymphony.module.sitemesh.util.OutputConverter;
import com.opensymphony.webwork.portlet.context.PortletContext;
import com.opensymphony.webwork.portlet.sitemesh.VelocityUtils;
import com.opensymphony.webwork.views.velocity.VelocityManager;
import com.opensymphony.xwork.ActionContext;
import org.apache.log4j.Category;
import org.apache.velocity.context.Context;
import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.RuntimeServices;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @author <a href="mailto:hu_pengfei@yahoo.com.cn">Henry Hu </a>
 * @since 2005-7-18
 */
public final class ApplyDecoratorDirective extends Directive {
    private static final Category log;

    public static final String STACK_KEY;

    private DirectiveStack stack;

    private Map params;

    static {
        log = Category.getInstance(com.opensymphony.webwork.portlet.velocity.ApplyDecoratorDirective.class);
        STACK_KEY = (com.opensymphony.webwork.portlet.velocity.ApplyDecoratorDirective.DirectiveStack.class).getName();
    }

    public class DirectiveStack {

        public ApplyDecoratorDirective pop() {
            try {
                return (ApplyDecoratorDirective) stack.pop();
            } catch (EmptyStackException e) {
                ApplyDecoratorDirective.log.info("Someone's been popping out of order! " + e.getMessage(), e);
                return null;
            }
        }

        public void push(ApplyDecoratorDirective directive) {
            stack.push(directive);
        }

        public ApplyDecoratorDirective peek() {
            if (stack.size() > 0)
                return (ApplyDecoratorDirective) stack.peek();
            else
                return null;
        }

        Stack stack;

        public DirectiveStack() {
            stack = new Stack();
        }
    }

    public ApplyDecoratorDirective() {
        params = new HashMap();
    }

    public String getName() {
        return "applyDecorator";
    }

    public int getType() {
        return 1;
    }

    public void init(RuntimeServices services, InternalContextAdapter adapter, Node node) throws Exception {
        super.init(services, adapter, node);
        int numArgs = node.jjtGetNumChildren();
        if (numArgs < 2)
            services.error("#applyDecorator error: You need a decorator name in order to use this tag");
        else if (numArgs > 3)
            services.error("#applyDecorator error: Too many parameters");
    }

    public boolean render(InternalContextAdapter adapter, Writer writer, Node node) throws IOException, ResourceNotFoundException,
            ParseErrorException, MethodInvocationException {

        stack = (DirectiveStack) adapter.get(STACK_KEY);
        if (stack == null) {
            stack = new DirectiveStack();
            adapter.put(STACK_KEY, stack);
        }
        stack.push(this);

        boolean flag;
        HttpServletRequest request = (HttpServletRequest) adapter.get("req");
        if (request == null)
            throw new IOException("No request object in context.");

        HttpServletResponse response = (HttpServletResponse) adapter.get("res");
        if (response == null)
            throw new IOException("No response object in context.");

        try {
            String decoratorName = (String) node.jjtGetChild(0).value(adapter);
            StringWriter bodyContent = new StringWriter(1024);

            int bodyNode = 1;
            if (node.jjtGetNumChildren() == 3)
                bodyNode = 2;
            node.jjtGetChild(bodyNode).render(adapter, bodyContent);

            Factory factory = PortletContext.getContext().getSiteMeshFactory();
            Decorator decorator = factory.getDecoratorMapper().getNamedDecorator(request, decoratorName);
            if (decorator != null) {
                com.opensymphony.module.sitemesh.PageParser parser = factory.getPageParser("text/html");
                HTMLPage page = (HTMLPage) ((FastPageParser) parser).parse(new StringReader(bodyContent.toString()));
                Context context = VelocityManager.getInstance()
                        .createContext(ActionContext.getContext().getValueStack(), request, response);
                context.put("page", page);
                if (node.jjtGetNumChildren() == 3)
                    context.put("title", (String) node.jjtGetChild(1).value(adapter));
                else
                    context.put("title", page.getTitle());
                StringWriter buffer = new StringWriter();
                page.writeBody(OutputConverter.getWriter(buffer));
                context.put("body", buffer.toString());
                buffer = new StringWriter();
                page.writeHead(OutputConverter.getWriter(buffer));
                context.put("head", buffer.toString());
                context.put("params", params);
                writer.write(VelocityUtils.getRenderedTemplate(decorator.getPage(), context));
            } else {
                throw new IOException("could not find decorator with name: " + decoratorName);
            }
            flag = true;
            stack.pop();

            return flag;
        } catch (Exception exception) {

            stack.pop();
            throw new IOException(exception.toString());
        }
    }

    public void addParameter(String paramName, Object paramValue) {
        params.put(paramName, paramValue);
    }

}