package webwork.action;

import com.opensymphony.webwork.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Backwards compatible class for WebWork 1.x migration
 *
 * @author $Author$
 * @version $Revision$
 */
public class ActionSupport extends com.opensymphony.xwork.ActionSupport implements Serializable, Action {
    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String execute() throws Exception {
        try {
            // if we're in a web context - check to see if there already is a result exception
            // in the request. If so, add its message as an error message and throw it.
            HttpServletRequest request = ServletActionContext.getRequest();
            if (request != null) {
                ResultException re = (ResultException) request.getAttribute("webwork.action.ResultException");
                if (re != null) {
                    // Any error message in the exception is added in the catch later
                    // Remove it since it has been handled
                    request.removeAttribute("webwork.action.ResultException");
                    throw re;
                }
            }

            // Check whether command is being invoked
            if (command != null && command.length() > 0 && this instanceof CommandDriven) {
                return invokeCommand();
            } else {
                // Validate input data
                validate();

                // Do main processing
                return doExecute();
            }
        } catch (ResultException e) {
            // Add the error message from the exception, it there is one
            String msg = e.getMessage();
            if (msg != null)
                addActionError(msg);
            return e.getResult();
        }
    }

    /**
     * Invokes an alternate execution path for the action. The name of the action
     * is derived by prepending a "do" and capitalizing the first letter of the
     * action.
     */
    protected String invokeCommand() throws Exception {
        StringBuffer sb = new StringBuffer("do");
        sb.append(command);
        sb.setCharAt(2, Character.toUpperCase(sb.charAt(2)));
        String cmd = sb.toString();
        LOG.debug("Executing action with command=" + command + " (mapped to method: " + cmd + ")");
        Method method;

        try {
            method = getClass().getMethod(cmd, new Class[0]);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("No command '" + command + "' in action");
        }
        try {
            return (String) method.invoke(this, new Object[0]);
        } catch (InvocationTargetException e) {
            /**
             * We try to return the source exception.
             */
            Throwable t = e.getTargetException();
            if (t instanceof Exception)
                throw (Exception) t;
            throw e;
        }
    }

    protected String doExecute() throws Exception {
        return SUCCESS;
    }

    public String doDefault() {
        return SUCCESS;
    }

    public void validate() {
        doValidation();
    }

    protected void doValidation() {
    }
}
