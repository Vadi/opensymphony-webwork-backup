/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher;

import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import com.opensymphony.xwork.ActionInvocation;

/**
 * <!-- START SNIPPET: description -->
 * 
 * A result that send the content out as plain text. Usefull typically when needed
 * to display the raw content of a JSP or Html file for example.
 * 
 * <!-- END SNIPPET: description -->
 * 
 * 
 * <!-- START SNIPPET: params -->
 * 
 * <ul>
 * 	<li>location = location of the file (jsp/html) to be displayed as plain text.</li>
 * </ul>
 * 
 * <!-- END SNIPPET: params -->
 * 
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 * &lt;action name="displayJspRawContent" &gt;
 *   &lt;result type="plaintext"&gt;/myJspFile.jsp&lt;result&gt;
 * &lt;/action&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * @author tm_jee
 * @version $Date$ $Id$
 */
public class PlainTextResult extends WebWorkResultSupport {

	private static final long serialVersionUID = 3633371605905583950L;
	
	public static final int BUFFER_SIZE = 1000;
	
	protected void doExecute(String finalLocation, ActionInvocation invocation) throws Exception {
		
		HttpServletResponse response = (HttpServletResponse) invocation.getInvocationContext().get(HTTP_RESPONSE);
		ServletContext servletContext = (ServletContext) invocation.getInvocationContext().get(SERVLET_CONTEXT);

		InputStreamReader reader = new InputStreamReader(servletContext.getResourceAsStream(location));
		response.setContentType("text/plain");
		response.setHeader("Content-Disposition", "inline");
		
		PrintWriter writer = response.getWriter();
		try {
			char[] buffer = new char[BUFFER_SIZE];
			int charRead = 0;
			while((charRead = reader.read(buffer)) != -1) {
				writer.write(buffer, 0, charRead);
			}
		}
		finally {
			reader.close();
			writer.flush();
			writer.close();
		}
	}

}
