/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor.debugging;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.Interceptor;
import com.opensymphony.xwork.interceptor.PreResultListener;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.views.freemarker.FreemarkerResult;

import javax.servlet.http.HttpServletResponse;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.*;

/**
 * <!-- START SNIPPET: description -->
 * 
 * Provides several different debugging screens to provide insight into the
 * data behind the page. The value of the 'debug' request parameter determines
 * the screen.
 * <p/>
 * This interceptor only is activated when devMode is enabled in
 * webwork.properties. The 'debug' parameter is removed from the parameter list
 * before the action is executed. All operations occur before the natural
 * Result has a chance to execute. </p>
 * 
 * <!-- END SNIPPET: description -->
 * 
 * 
 *  <!-- START SNIPPET: parameters -->
 * The value of the 'debug' request parameter determines
 * the screen.
 * <ul>
 * <li> <code>xml</code> - Dumps the parameters, context, session, and value
 * stack as an XML document.</li>
 * <li> <code>console</code> - Shows a popup 'OGNL Console' that allows the
 * user to test OGNL expressions against the value stack. The XML data from
 * the 'xml' mode is inserted at the top of the page.</li>
 * <li> <code>command</code> - Tests an OGNL expression and returns the
 * string result. Only used by the OGNL console.</li>
 * </ul>
 *  <!-- END SNIPPET: parameters -->
 * 
 * <!-- START SNIPPET: extending -->
 * There's no intended extension points
 * <!-- END SNIPPET: extending -->
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 * &lt;action ...&gt;
 *    &lt;interceptor-ref name="debugging" /&gt;
 *    ....
 * &lt;/action&gt;
 * <!-- END SNIPPET: example -->
 * </pre>
 */
public class DebuggingInterceptor implements Interceptor {

    private static final long serialVersionUID = -3097324155953078783L;

    private final static Log log = LogFactory.getLog(DebuggingInterceptor.class);

    private String[] ignorePrefixes = new String[]{"com.opensymphony.webwork.",
            "com.opensymphony.xwork.", "xwork."};
    private String[] _ignoreKeys = new String[]{"application", "session",
            "parameters", "request"};
    private HashSet ignoreKeys = new HashSet(Arrays.asList(_ignoreKeys));

    private final static String XML_MODE = "xml";
    private final static String CONSOLE_MODE = "console";
    private final static String COMMAND_MODE = "command";

    private final static String SESSION_KEY = "com.opensymphony.webwork.interceptor.debugging.VALUE_STACK";

    private final static String DEBUG_PARAM = "debug";
    private final static String EXPRESSION_PARAM = "expression";


    /**
     * Unused.
     */
    public void init() {
    }


    /**
     * Unused.
     */
    public void destroy() {
    }


    /*
     * (non-Javadoc)
     *
     * @see com.opensymphony.xwork2.interceptor.Interceptor#invoke(com.opensymphony.xwork2.ActionInvocation)
     */
    public String intercept(ActionInvocation inv) throws Exception {

        boolean devMode = ((Boolean) ActionContext.getContext().get(
                ActionContext.DEV_MODE)).booleanValue();
        boolean cont = true;
        if (devMode) {
            final ActionContext ctx = ActionContext.getContext();
            String type = getParameter(DEBUG_PARAM);
            ctx.getParameters().remove(DEBUG_PARAM);
            if (XML_MODE.equals(type)) {
                inv.addPreResultListener(
                        new PreResultListener() {
                            public void beforeResult(ActionInvocation inv, String result) {
                                printContext();
                            }
                        });
            } else if (CONSOLE_MODE.equals(type)) {
                inv.addPreResultListener(
                        new PreResultListener() {
                            public void beforeResult(ActionInvocation inv, String actionResult) {
                                StringWriter writer = new StringWriter();
                                printContext(new PrettyPrintWriter(writer));
                                String xml = writer.toString();
                                xml = xml.replaceAll("&", "&amp;");
                                xml = xml.replaceAll(">", "&gt;");
                                xml = xml.replaceAll("<", "&lt;");
                                ActionContext.getContext().put("debugXML", xml);

                                FreemarkerResult result = new FreemarkerResult();
                                result.setContentType("text/html");
                                result.setLocation("/com/opensymphony/webwork/interceptor/debugging/console.ftl");
                                result.setParse(false);
                                try {
                                    result.execute(inv);
                                } catch (Exception ex) {
                                    log.error("Unable to create debugging console", ex);
                                }

                            }
                        });
            } else if (COMMAND_MODE.equals(type)) {
                OgnlValueStack stack = (OgnlValueStack) ctx.getSession().get(SESSION_KEY);
                String cmd = getParameter(EXPRESSION_PARAM);

                HttpServletResponse res = ServletActionContext.getResponse();
                res.setContentType("text/plain");

                try {
                    PrintWriter writer =
                            ServletActionContext.getResponse().getWriter();
                    writer.print(stack.findValue(cmd));
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                cont = false;
            }
        }
        if (cont) {
            try {
                return inv.invoke();
            } finally {
                if (devMode) {
                    final ActionContext ctx = ActionContext.getContext();
                    ctx.getSession().put(SESSION_KEY, ctx.get(ActionContext.VALUE_STACK));
                }
            }
        } else {
            return null;
        }
    }


    /**
     * Gets a single string from the request parameters
     *
     * @param key The key
     * @return The parameter value
     */
    private String getParameter(String key) {
        String[] arr = (String[]) ActionContext.getContext().getParameters().get(key);
        if (arr != null && arr.length > 0) {
            return arr[0];
        }
        return null;
    }


    /**
     * Prints the current context to the response in XML format.
     */
    protected void printContext() {
        HttpServletResponse res = ServletActionContext.getResponse();
        res.setContentType("text/xml");

        try {
            PrettyPrintWriter writer = new PrettyPrintWriter(
                    ServletActionContext.getResponse().getWriter());
            printContext(writer);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    /**
     * Prints the current request to the existing writer.
     *
     * @param writer The XML writer
     */
    protected void printContext(PrettyPrintWriter writer) {
        ActionContext ctx = ActionContext.getContext();
        writer.startNode(DEBUG_PARAM);
        serializeIt(ctx.getParameters(), "parameters", writer,
                new ArrayList());
        writer.startNode("context");
        String key;
        Map ctxMap = ctx.getContextMap();

        Iterator iterator = ctxMap.keySet().iterator();
        while(iterator.hasNext()){
            key = ((Object)iterator.next()).toString();
            boolean print = !ignoreKeys.contains(key);
            for(int i = 0; i<ignorePrefixes.length;i++){
                if (key.startsWith(ignorePrefixes[i])) {
                    print = false;
                    break;
                }
            }
            if (print) {
                serializeIt(ctxMap.get(key), key, writer, new ArrayList());
            }
        }
        writer.endNode();
        serializeIt(ctx.getSession(), "request", writer, new ArrayList());
        serializeIt(ctx.getSession(), "session", writer, new ArrayList());

        OgnlValueStack stack = (OgnlValueStack) ctx.get(ActionContext.VALUE_STACK);
        serializeIt(stack.getRoot(), "valueStack", writer, new ArrayList());
        writer.endNode();
    }


    /**
     * Recursive function to serialize objects to XML. Currently it will
     * serialize Collections, maps, Arrays, and JavaBeans. It maintains a stack
     * of objects serialized already in the current functioncall. This is used
     * to avoid looping (stack overflow) of circular linked objects. Struts and
     * XWork objects are ignored.
     *
     * @param bean   The object you want serialized.
     * @param name   The name of the object, used for element &lt;name/&gt;
     * @param writer The XML writer
     * @param stack  List of objects we're serializing since the first calling
     *               of this function (to prevent looping on circular references).
     */
    protected void serializeIt(Object bean, String name,
                               PrettyPrintWriter writer, List stack) {
        writer.flush();
        // Check stack for this object
        if ((bean != null) && (stack.contains(bean))) {
            if (log.isInfoEnabled()) {
                log.info("Circular reference detected, not serializing object: "
                        + name);
            }
            return;
        } else if (bean != null) {
            // Push object onto stack.
            // Don't push null objects ( handled below)
            stack.add(bean);
        }
        if (bean == null) {
            return;
        }
        String clsName = bean.getClass().getName();

        writer.startNode(name);

        // It depends on the object and it's value what todo next:
        if (bean instanceof Collection) {
            Collection col = (Collection) bean;

            // Iterate through components, and call ourselves to process
            // elements
            Iterator iterator = col.iterator();
            while(iterator.hasNext()){
                serializeIt(iterator.next(), "value", writer, stack);
            }
        } else if (bean instanceof Map) {
            Map map = (Map) bean;
            // Loop through keys and call ourselves
            Iterator mapIterator = map.keySet().iterator();
            while(mapIterator.hasNext()){
                Object key = mapIterator.next();
                Object value = map.get(key);
                serializeIt(value, key.toString(), writer, stack);
            }

        } else if (bean.getClass().isArray()) {
            // It's an array, loop through it and keep calling ourselves
            for (int i = 0; i < Array.getLength(bean); i++) {
                serializeIt(Array.get(bean, i), "arrayitem", writer, stack);
            }
        } else {
            if (clsName != null && clsName.startsWith("org.opensymphony.webwork")) {
                // ignore
            } else if (clsName != null
                    && clsName.startsWith("com.opensymphony.xwork")) {
                // ignore
            } else if (clsName.startsWith("java.lang")) {
                writer.setValue(bean.toString());
            } else {
                // Not java.lang, so we can call ourselves with this object's
                // values
                try {
                    BeanInfo info = Introspector.getBeanInfo(bean.getClass());
                    PropertyDescriptor[] props = info.getPropertyDescriptors();

                    for (int i = 0; i<props.length; i++) {
                        String n = props[i].getName();
                        Method m = props[i].getReadMethod();

                        // Call ourselves with the result of the method
                        // invocation
                        if (m != null) {
                            serializeIt(m.invoke(bean, null), n, writer, stack);
                        }
                    }
                } catch (Exception e) {
                    log.error(e, e);
                }
            }
        }

        writer.endNode();

        // Remove object from stack
        stack.remove(bean);
    }

}


