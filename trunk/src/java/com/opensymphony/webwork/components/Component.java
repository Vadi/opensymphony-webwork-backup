package com.opensymphony.webwork.components;

import com.opensymphony.webwork.config.Configuration;
import com.opensymphony.webwork.util.FastByteArrayOutputStream;
import com.opensymphony.webwork.views.jsp.TagUtils;
import com.opensymphony.webwork.views.util.UrlHelper;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapping;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapper;
import com.opensymphony.webwork.dispatcher.mapper.ActionMapperFactory;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

/**
 * User: plightbo
 * Date: Jul 2, 2005
 * Time: 5:52:26 PM
 */
public class Component {
    private static final Log LOG = LogFactory.getLog(Component.class);

    public static final boolean ALT_SYNTAX = "true".equals(Configuration.getString("webwork.tag.altSyntax"));
    public static final String COMPONENT_STACK = "__component_stack";

    protected OgnlValueStack stack;
    protected Map parameters;
    protected String id;

    public Component(OgnlValueStack stack) {
        this.stack = stack;
        this.parameters = new HashMap();
        getComponentStack().push(this);
    }

    private String getComponentName() {
        Class c = getClass();
        String name = c.getName();
        int dot = name.lastIndexOf('.');

        return name.substring(dot + 1).toLowerCase();
    }

    public OgnlValueStack getStack() {
        return stack;
    }

    protected Stack getComponentStack() {
        Stack componentStack = (Stack) stack.getContext().get(COMPONENT_STACK);
        if (componentStack == null) {
            componentStack = new Stack();
            stack.getContext().put(COMPONENT_STACK, componentStack);
        }
        return componentStack;
    }

    public void start(Writer writer) {
    }

    public void end(Writer writer, String body) {
        assert(body != null);

        try {
            writer.write(body);
        } catch (IOException e) {
            throw new RuntimeException("IOError: " + e.getMessage(), e);
        }
        getComponentStack().pop();
    }

    public Component findAncestor(Class clazz) {
        Stack componentStack = getComponentStack();
        for (int i = componentStack.size() - 1; i >= 0; i--) {
            Component component = (Component) componentStack.get(i);
            if (clazz.isAssignableFrom(component.getClass()) && component != this) {
                return component;
            }
        }

        return null;
    }

    protected String findString(String expr) {
        return (String) findValue(expr, String.class);
    }

    protected String findString(String expr, String field, String errorMsg) {
        if (expr == null) {
            throw fieldError(field, errorMsg, null);
        } else {
            return findString(expr);
        }
    }

    protected RuntimeException fieldError(String field, String errorMsg, Exception e) {
        String msg = "tag " + getComponentName() + ", field " + field + ": " + errorMsg;
        if (e == null) {
            LOG.error(msg);
            return new RuntimeException(msg);
        } else {
            LOG.error(msg, e);
            return new RuntimeException(msg, e);
        }
    }

    protected Object findValue(String expr) {
        if (expr == null) {
            return null;
        }

        if (altSyntax()) {
            // does the expression start with %{ and end with }? if so, just cut it off!
            if (expr.startsWith("%{") && expr.endsWith("}")) {
                expr = expr.substring(2, expr.length() - 1);
            }
        }

        return getStack().findValue(expr);
    }

    public boolean altSyntax() {
        return ALT_SYNTAX || stack.getContext().containsKey("useAltSyntax");
    }

    protected Object findValue(String expr, String field, String errorMsg) {
        if (expr == null) {
            throw fieldError(field, errorMsg, null);
        } else {
            Object value = null;
            Exception problem = null;
            try {
                value = findValue(expr);
            } catch (Exception e) {
                problem = e;
            }

            if (value == null) {
                throw fieldError(field, errorMsg, problem);
            }

            return value;
        }
    }

    protected Object findValue(String expr, Class toType) {
        if (altSyntax() && toType == String.class) {
            return TextParseUtil.translateVariables('%', expr, stack);
        } else {
            if (altSyntax()) {
                // does the expression start with %{ and end with }? if so, just cut it off!
                if (expr.startsWith("%{") && expr.endsWith("}")) {
                    expr = expr.substring(2, expr.length() - 1);
                }
            }

            return getStack().findValue(expr, toType);
        }
    }

    /**
     * Renders an action URL by consulting the {@link com.opensymphony.webwork.dispatcher.mapper.ActionMapper}.
     */
    protected String determineActionURL(String action, String namespace, String method,
                                      HttpServletRequest req, HttpServletResponse res, Map parameters) {
        String finalAction = findString(action);
        String finalNamespace = determineNamespace(namespace, getStack(), req);
        ActionMapping mapping = new ActionMapping(finalAction, finalNamespace, method, parameters);
        ActionMapper mapper = ActionMapperFactory.getMapper();
        String uri = mapper.getUriFromActionMapping(mapping);
        return UrlHelper.buildUrl(uri, req, res, parameters);
    }

    /**
     * Determines the namespace of the current page being renderdd. Useful for Form, URL, and Href generations.
     */
    protected String determineNamespace(String namespace, OgnlValueStack stack, HttpServletRequest req) {
        String result;

        if (namespace == null) {
            result = TagUtils.buildNamespace(stack, req);
        } else {
            result = findString(namespace);
        }

        if (result == null) {
            result = "";
        }

        return result;
    }

    /**
     * Pushes this component's parameter Map as well as the component itself on to the stack
     * and then copies the supplied parameters over. Because the component's parameter Map is
     * pushed before the component itself, any key-value pair that can't be assigned to componet
     * will be set in the parameters Map.
     *
     * @param params
     */
    public void copyParams(Map params) {
        stack.push(parameters);
        stack.push(this);
        try {
            for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
                Map.Entry entry = (Map.Entry) iterator.next();
                String key = (String) entry.getKey();
                stack.setValue(key, entry.getValue());
            }
        } finally {
            stack.pop();
            stack.pop();
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

    /**
     * @ww.tagattribute required="false"
     * description="HTML id attribute"
     */
    public void setId(String id) {
        this.id = id;
    }

    public boolean usesBody() {
        return false;
    }
}
