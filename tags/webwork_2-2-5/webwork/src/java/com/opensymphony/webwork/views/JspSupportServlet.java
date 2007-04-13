package com.opensymphony.webwork.views;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * A servlet that means to expose itself through a public static member 
 * variable such that its {@link ServletContext} could be accessible.
 * 
 * An example of configuration in web.xml would be :-
 * <pre>
 *  &lt;servlet&gt;
 *      &lt;servlet-name&gt;jspSupportServlet\&lt;/servlet-name&gt;
 *      &lt;servlet-class&gt;com.opensymphony.webwork.views.JspSupportServlet&lt;/servlet-class&gt;
 *      &lt;load-on-startup&gt;2&lt;/load-on-startup&gt;
 *  &lt;/servlet&gt;
 * </pre>
 * 
 * @author plightbo
 * @version $Date$ $Id$
 */
public class JspSupportServlet extends HttpServlet {
	
	private static final long serialVersionUID = -8268185076433481017L;
	
	public static JspSupportServlet jspSupportServlet;

	/**
	 * This method merely expose this servlet as a public static member variable
	 * called 'jspSupportServlet'.
	 */
    public void init(ServletConfig servletConfig) throws ServletException {
        super.init(servletConfig);

        jspSupportServlet = this;
    }
}
