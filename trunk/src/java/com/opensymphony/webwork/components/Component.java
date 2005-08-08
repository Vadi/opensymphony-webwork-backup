package com.opensymphony.webwork.components;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.FastByteArrayOutputStream;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.*;

/**
 * User: plightbo
 * Date: Jul 2, 2005
 * Time: 5:52:26 PM
 */
public class Component {
    public static final boolean ALT_SYNTAX = "true".equals(Configuration.getString("webwork.tag.altSyntax"));
    public static final String COMPONENT_STACK = "__component_stack";

    protected OgnlValueStack stack;
    protected Map parameters;
    protected String id;

    public Component(OgnlValueStack stack) {
        this.stack = stack;
        this.parameters = new HashMap();
        stack.push(this);
        getComponentStack().push(this);
    }

    public OgnlValueStack getStack() {
        return stack;
    }

    private Stack getComponentStack() {
        Stack componentStack = (Stack) stack.getContext().get(COMPONENT_STACK);
        if (componentStack == null) {
            componentStack = new Stack();
            stack.getContext().put(COMPONENT_STACK, componentStack);
        }
        return componentStack;
    }

    public void start(Writer writer) {
    }

    public void end(Writer writer) {
        getComponentStack().pop();
        stack.pop();
    }

    public Component findAncestor(Class clazz) {
        Stack componentStack = getComponentStack();
        List parents = componentStack.subList(1, componentStack.size());
        for (Iterator iterator = parents.iterator(); iterator.hasNext();) {
            Component component = (Component) iterator.next();
            if (component.getClass().isAssignableFrom(clazz)) {
                return component;
            }
        }

        return null;
    }

    protected String findString(String expr) {
        return (String) findValue(expr, String.class);
    }

    protected Object findValue(String expr) {
        if (ALT_SYNTAX) {
            // does the expression start with %{ and end with }? if so, just cut it off!
            if (expr.startsWith("%{") && expr.endsWith("}")) {
                expr = expr.substring(2, expr.length() - 1);
            }
        }

        return getStack().findValue(expr);
    }

    protected Object findValue(String expr, Class toType) {
        if (ALT_SYNTAX && toType == String.class) {
            return translateVariables(expr, getStack());
        } else {
            if (ALT_SYNTAX) {
                // does the expression start with %{ and end with }? if so, just cut it off!
                if (expr.startsWith("%{") && expr.endsWith("}")) {
                    expr = expr.substring(2, expr.length() - 1);
                }
            }

            return getStack().findValue(expr, toType);
        }
    }

    public static String translateVariables(String expression, OgnlValueStack stack) {
        return TextParseUtil.translateVariables('%', expression, stack);
    }

    public void copyParams(Map params) {
        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            String key = (String) entry.getKey();
            stack.setValue(key, entry.getValue());
        }
    }

    protected String toString(Throwable t) {
        FastByteArrayOutputStream bout = new FastByteArrayOutputStream();
        PrintWriter wrt = new PrintWriter(bout);
        t.printStackTrace(wrt);
        wrt.close();

        return bout.toString();
    }

    public Map getParameters() {
        return parameters;
    }

    public void addAllParameters(Map params) {
        parameters.putAll(params);
    }

    public void addParameter(String key, Object value) {
        if (key != null) {
            Map params = getParameters();

            if (value == null) {
                params.remove(key);
            } else {
                params.put(key, value);
            }
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
