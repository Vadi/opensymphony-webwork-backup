package com.opensymphony.webwork.components;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.dispatcher.DispatcherUtils;
import com.opensymphony.webwork.dispatcher.RequestMap;
import com.opensymphony.webwork.views.jsp.TagUtils;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionProxy;
import com.opensymphony.xwork.ActionProxyFactory;
import com.opensymphony.xwork.util.OgnlValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * ActionTag enables developers to call Actions directly from a JSP page by specifying the Action name and an optional
 * namespace.  The body content of the tag is used to render the results from the Action.  Any Result processor defined
 * for this Action in xwork.xml will be ignored.
 *
 * @author <a href="mailto:plightbo@gmail.com">Pat Lightbody</a>
 */
public class ActionComponent extends Component {
    private static final Log LOG = LogFactory.getLog(ActionComponent.class);

    protected HttpServletResponse res;
    protected HttpServletRequest req;

    protected ActionProxy proxy;
    protected String name;
    protected String namespace;
    protected boolean executeResult;
    protected boolean ignoreContextParams;

    public ActionComponent(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        super(stack);
        this.req = req;
        this.res = res;
    }

    public void end(Writer writer, String body) {
        executeAction();

        if ((getId() != null) && (proxy != null)) {
            getStack().setValue("#attr['" + getId() + "']", proxy.getAction());
        }
    }

    private Map createExtraContext() {
        Map parentParams = null;

        if (!ignoreContextParams) {
            parentParams = new ActionContext(getStack().getContext()).getParameters();
        }

        Map newParams = (parentParams != null) ? new HashMap(parentParams) : new HashMap();

        if (parameters != null) {
            newParams.putAll(parameters);
        }

        ActionContext ctx = new ActionContext(stack.getContext());
        ServletContext servletContext = (ServletContext) ctx.get(ServletActionContext.SERVLET_CONTEXT);
        PageContext pageContext = (PageContext) ctx.get(ServletActionContext.PAGE_CONTEXT);
        Map session = ctx.getSession();
        Map application = ctx.getApplication();

        DispatcherUtils.initialize(servletContext);
        DispatcherUtils du = DispatcherUtils.getInstance();
        Map extraContext = du.createContextMap(new RequestMap(req),
                newParams,
                session,
                application,
                req,
                res,
                servletContext);

        OgnlValueStack newStack = new OgnlValueStack(stack);
        extraContext.put(ActionContext.VALUE_STACK, newStack);

        // add page context, such that ServletDispatcherResult will do an include
        extraContext.put(ServletActionContext.PAGE_CONTEXT, pageContext);

        return extraContext;
    }

    public ActionProxy getProxy() {
        return proxy;
    }

    /**
     * Execute the requested action.  If no namespace is provided, we'll
     * attempt to derive a namespace using buildNamespace().  The ActionProxy
     * and the namespace will be saved into the instance variables proxy and
     * namespace respectively.
     *
     * @see com.opensymphony.webwork.views.jsp.TagUtils#buildNamespace
     */
    private void executeAction() {
        String actualName = findString(name, "name", "Action name is required. Example: updatePerson");

        if (actualName == null) {
            String message = "Unable to find value for name " + name;
            LOG.error(message);
            throw new RuntimeException(message);
        }

        String namespace;

        if (this.namespace == null) {
            namespace = TagUtils.buildNamespace(getStack(), req);
        } else {
            namespace = findString(this.namespace);
        }

        // get the old value stack from the request
        OgnlValueStack stack = getStack();
        // execute at this point, after params have been set
        try {
            proxy = ActionProxyFactory.getFactory().createActionProxy(namespace, actualName, createExtraContext(), executeResult);
            // set the new stack into the request for the taglib to use
            req.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, proxy.getInvocation().getStack());
            proxy.execute();

        } catch (Exception e) {
            String message = "Could not execute action: " + namespace + "/" + actualName;
            LOG.error(message, e);
        } finally {
            // set the old stack back on the request
            req.setAttribute(ServletActionContext.WEBWORK_VALUESTACK_KEY, stack);
        }

        if ((getId() != null) && (proxy != null)) {
            final Map context = stack.getContext();
            context.put(getId(), proxy.getAction());
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setExecuteResult(boolean executeResult) {
        this.executeResult = executeResult;
    }

    public void setIgnoreContextParams(boolean ignoreContextParams) {
        this.ignoreContextParams = ignoreContextParams;
    }
}
