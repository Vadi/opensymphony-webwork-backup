/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action;

/**
 * If Actions throw this exception from execute(), the dispatcher will show
 * the view corresponding to the chosen result.
 * <p/>
 * This is primarily useful for certain types of checking. For example, if an
 * action can only be executed if the client is logged in, he can have a method
 * "checkLogin", which checks the login status and throws ResultException if
 * not logged in. The view would then be the login screen, and "checkLogin" should
 * be called first in the action's execute() method.
 *
 * @author Rickard Öberg (rickard@middleware-company.com)
 * @author Matt Baldree (matt@smallleap.com)
 * @version $Revision$
 * @see Action
 */
public class ResultException
        extends Exception {
    String result;
    String message;

    // Public --------------------------------------------------------
    public ResultException(String aResult) {
        this.result = aResult;
    }

    /**
     * @param aResult the result view; i.e. - ERROR, INPUT, etc
     * @param message the error message
     */
    public ResultException(String aResult, String message) {
        this.result = aResult;
        this.message = message;
    }


    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }
}
