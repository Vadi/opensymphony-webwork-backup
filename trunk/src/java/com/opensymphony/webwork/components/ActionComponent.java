package com.opensymphony.webwork.components;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.webwork.WebWorkException;
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
 * <!-- START SNIPPET: javadoc -->
 * <p>This tag enables developers to call actions directly from a JSP page by specifying the action name and an optional
 * namespace.  The body content of the tag is used to render the results from the Action.  Any result processor defined
 * for this action in xwork.xml will be ignored, <i>unless</i> the executeResult parameter is specified.</p>
 * <!-- END SNIPPET: javadoc -->
 *
 * <!-- START SNIPPET: params -->
 * <ul>
 *      <li>id (String) - the id (if specified) to put the action under stack's context.
 * 		<li>name* (String) - name of the action to be executed (without the extension suffix eg. .action)</li>
 * 		<li>namespace (String) - default to the namespace where this action tag is invoked</li>
 *      <li>executeResult (Boolean) -  default is false. Decides wheather the result of this action is to be executed or not</li>
 *      <li>ignoreContextParams (Boolean) - default to false. Decides wheather the request parameters are to be included when the action is invoked</li>
 * </ul>
 * <!-- END SNIPPET: params -->
 *
 * <pre>
 * <!-- START SNIPPET: javacode -->
 * public class ActionTagAction extends ActionSupport {
 *
 *	public String execute() throws Exception {
 *		return "done";
 *	}
 *
 *	public String doDefault() throws Exception {
 *		ServletActionContext.getRequest().setAttribute("stringByAction", "This is a String put in by the action's doDefault()");
 *		return "done";
 *	}
 * }
 * <!-- END SNIPPET: javacode -->
 * </pre>
 *
 * <pre>
 * <!-- START SNIPPET: webworkxml -->
 *   <xwork>
 *      ....
 *     <action name="actionTagAction1" class="tmjee.testing.ActionTagAction">
 *         <result name="done">success.jsp</result>
 *     </action>
 *      <action name="actionTagAction2" class="tmjee.testing.ActionTagAction" method="default">
 *         <result name="done">success.jsp</result>
 *     </action>
 *      ....
 *   </xwork>
 * <!-- END SNIPPET: webworkxml -->
 * </pre>
 *
 * <pre>
 * <!-- START SNIPPET: example -->
 *  <div>The following action tag will execute result and include it in this page</div>
 *	<br />
 *	<ww:action name="actionTagAction" executeResult="true" />
 *  <br />
 *  <div>The following action tag will do the same as above, but invokes method specialMethod in action</div>
 *	<br />
 *	<ww:action name="actionTagAction!specialMethod" executeResult="true" />
 *  <br />
 *  <div>The following action tag will not execute result, but put a String in request scope
 *       under an id "stringByAction" which will be retrieved using property tag</div>
 *  <ww:action name="actionTagAction!default" executeResult="false" />
 *  <ww:property value="#attr.stringByAction" />
 * <!-- END SNIPPET: example -->
 * </pre>
 *
 * @author <a href="mailto:plightbo@gmail.com">Pat Lightbody</a>
 * @author tm_jee ( tm_jee (at) yahoo.co.uk )
 * @author Rene Gielen
 * @version $Revision$
 * @since 2.2
 *
 * @ww.tag name="action" tld-body-content="JSP" tld-tag-class="com.opensymphony.webwork.views.jsp.ActionTag"
 * description="Execute an action from within a view"
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

    public boolean end(Writer writer, String body) {
        executeAction();

        if ((getId() != null) && (proxy != null)) {
            getStack().setValue("#attr['" + getId() + "']", proxy.getAction());
        }

        return super.end(writer, "");
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
            throw new WebWorkException(message);
        }

        // handle "name!method" convention.
        final String actionName;
        final String methodName;

        int exclamation = actualName.lastIndexOf("!");
        if (exclamation != -1) {
            actionName = actualName.substring(0, exclamation);
            methodName = actualName.substring(exclamation + 1);
        } else {
            actionName = actualName;
            methodName = null;
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
            proxy = ActionProxyFactory.getFactory().createActionProxy(namespace, actionName, createExtraContext(), executeResult, true);
            if (null != methodName) {
                proxy.setMethod(methodName);
            }
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

    /**
     * the id (if speficied) to put the action under stack's context.
     * @ww.tagattribute required="false" type="String"
     */
    public void setId(String id) {
        super.setId(id);
    }

    /**
     * name of the action to be executed (without the extension suffix eg. .action)
     * @ww.tagattribute required="true" type="String"
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * namespace for action to call
     * @ww.tagattribute required="false" type="String" default="namespace from where tag is used"
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * whether the result of this action (probably a view) should be executed/rendered
     * @ww.tagattribute required="false" type="Boolean" default="false"
     */
    public void setExecuteResult(boolean executeResult) {
        this.executeResult = executeResult;
    }

    /**
     * whether the request parameters are to be included when the action is invoked
     * @ww.tagattribute required="false" type="Boolean" default="false"
     */
    public void setIgnoreContextParams(boolean ignoreContextParams) {
        this.ignoreContextParams = ignoreContextParams;
    }
}
