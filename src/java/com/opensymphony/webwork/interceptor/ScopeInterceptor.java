package com.opensymphony.webwork.interceptor;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.interceptor.AroundInterceptor;
import com.opensymphony.xwork.interceptor.PreResultListener;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * @author mimo
 */
public class ScopeInterceptor extends AroundInterceptor implements PreResultListener {
    private static final Log LOG = LogFactory.getLog(ScopeInterceptor.class);

    private static Map locks = new IdentityHashMap();

    String[] application = null;
    String[] session = null;
    String key;
    String type = null;

    //list of application scoped properties
    public void setApplication(String s) {
        if (s != null) {
            application = s.split(" *, *");
        }
    }

    //list of session scoped properties
    public void setSession(String s) {
        if (s != null) {
            session = s.split(" *, *");
        }
    }

    private String getKey(ActionInvocation invocation) {
        ActionProxy proxy = invocation.getProxy();
        if (key == null || "CLASS".equals(key)) {
            //key = "webwork.ScopeInterceptor:" + proxy.getNamespace() + ":" + proxy.getActionName();
            key = "webwork.ScopeInterceptor:" + proxy.getNamespace() + ":" + proxy.getAction().getClass();
        } else if ("ACTION".equals(key)) {
            key = "webwork.ScopeInterceptor:" + proxy.getNamespace() + ":" + proxy.getActionName();
        }
        return key;
    }

    public ScopeInterceptor() {
        super();
    }


    private static final Object NULL = new Object() {
        public String toString() {
            return "NULL";
        }
    };

    private static Object nullConvert(Object o) {
        if (o == null) {
            return NULL;
        }

        if (o == NULL) {
            return null;
        }

        return o;
    }

    static void lock(Object o, ActionInvocation invocation) throws Exception {
        synchronized (o) {
            int count = 3;
            Object previous;
            while ((previous = locks.get(o)) != null) {
                if (previous == invocation) {
                    return;
                }

                if (count-- <= 0) {
                    throw new RuntimeException("Deadlock in session lock");
                }
                o.wait(60000);
            }
            locks.put(o, invocation);
        }
    }

    static void unlock(Object o) {
        synchronized (o) {
            locks.remove(o);
            o.notify();
        }
    }

    protected void after(ActionInvocation dispatcher, String result) throws Exception {
    }

    protected void before(ActionInvocation invocation) throws Exception {
        invocation.addPreResultListener(this);

        ActionContext ctx = invocation.getInvocationContext();
        HttpServletRequest req = (HttpServletRequest) ctx.get(ServletActionContext.HTTP_REQUEST);
        ServletContext app = (ServletContext) ctx.get(ServletActionContext.SERVLET_CONTEXT);
        OgnlValueStack stack = invocation.getStack();
        HttpSession ses = req.getSession(true);

        // lock on the session & get the key associated with this wizard
        lock(ses, invocation);
        String key = getKey(invocation);

        if (application != null) {
            for (int i = 0; i < application.length; i++) {
                String string = application[i];
                Object attribute = app.getAttribute(key + string);
                if (attribute != null) {
                    if (LOG.isDebugEnabled()) {
                        log.debug("application scoped variable set " + string + " = " + String.valueOf(attribute));
                    }

                    stack.setValue(string, nullConvert(attribute));
                }
            }
        }

        if (session != null && (!"start".equals(type))) {
            for (int i = 0; i < session.length; i++) {
                String string = session[i];
                Object attribute = ses.getAttribute(key + string);
                if (attribute != null) {
                    if (LOG.isDebugEnabled()) {
                        log.debug("application scoped variable set " + string + " = " + String.valueOf(attribute));
                    }

                    stack.setValue(string, nullConvert(attribute));
                }
            }
        }
    }

    /**
     * @param key The key to set.
     */
    public void setKey(String key) {
        this.key = key;
    }

    public void beforeResult(ActionInvocation invocation, String resultCode) {
        HttpSession ses = ServletActionContext.getRequest().getSession();
        try {
            String key = getKey(invocation);
            ServletContext app = ServletActionContext.getServletContext();
            final OgnlValueStack stack = ActionContext.getContext().getValueStack();

            if (application != null)
                for (int i = 0; i < application.length; i++) {
                    String string = application[i];
                    Object value = stack.findValue(string);
                    if (LOG.isDebugEnabled()) {
                        log.debug("application scoped variable saved " + string + " = " + String.valueOf(value));
                    }

                    //if( value != null)
                    app.setAttribute(key + string, nullConvert(value));
                }

            boolean ends = "end".equals(type);

            if (session != null)
                for (int i = 0; i < session.length; i++) {
                    String string = session[i];
                    if (ends) {
                        ses.removeAttribute(key + string);
                    } else {
                        Object value = stack.findValue(string);

                        if (LOG.isDebugEnabled()) {
                            log.debug("session scoped variable saved " + string + " = " + String.valueOf(value));
                        }

                        // Null value should be scoped too
                        //if( value != null)
                        ses.setAttribute(key + string, nullConvert(value));
                    }
                }


        } finally {
            unlock(ses);
        }
    }

    /**
     * @return Returns the type.
     */
    public String getType() {
        return type;
    }

    /**
     * @param type The type to set.
     */
    public void setType(String type) {
        type = type.toLowerCase();
        if ("start".equals(type) || "end".equals(type)) {
            this.type = type;
        } else {
            throw new IllegalArgumentException("Only start or end are allowed arguments for type");
        }
    }
}