/*
 * Copyright (c) 2005 Cameron Braid. All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Cameron Braid.
 * ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with Cameron Braid.
 *
 * CAMERON BRAID MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. CAMERON BRAID SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 */

package com.opensymphony.webwork.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author CameronBraid
 *
 * @todo TODO another idea may be to provide a extension based matching algorithm.
 * i.e. permit *.js to be loaded
 * this way people can request /webwork/com/opensymphony/webwork/js/client.js as a resource 
 * This will allow the static resources to be in the same package as the server side resources
 * 
 * 
 * 
 *	This class streams a static resource from within a classpath package to the response output stream
 *
 *	It requries the specification of package search prefixes
 *
 *	For Example 
 *	
 *	 <init-param>
 *	 	<param-name>packages</param-name>
 *	 	<param-value>
 *	 		com.opensymphony.webwork.js
 *	 		com.opensymphony.webwork.validation.static
 *	 	</param-value>
 *	 </init-param>
 *
 *	If this servlet is mapped to /webwork
 *	A request for /webwork/validation.js will search in the following order 
 *	until a resource is found
 *   
 *  1. /com/opensymphony/webwork/js/validation.js 
 *  2. /com/opensymphony/webwork/validation/static/validation.js
 *  
 *  if you are concerned about name colisions, map multiple instances of this servlet
 *  to different servlet paths
 *  
 *  if no packages init param is specified, this servlet will return Http 500 errors for any get request
 *  the packages parameter can contain whitespace and/or commas as delimiters
 *  
 *  only GET requests are supported by this servlet
 *  
 */
public class StaticResourceServlet extends HttpServlet {
	
	private static final long serialVersionUID = 3546644325803765813L;
	
	protected Log log = LogFactory.getLog(StaticResourceServlet.class);
	protected String[] pathPrefixes;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		if (pathPrefixes == null) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "no packages specified - servlet disabled");
			return;
		}
		
		for (int i = 0; i < pathPrefixes.length; i++) {
			InputStream is = findInputStream(pathPrefixes[i], request);
			if (is != null) {
				try {
					copy(is, response.getOutputStream());
				}
				finally {
					is.close();
				}
				return;
			}
		}

		response.sendError(HttpServletResponse.SC_NOT_FOUND);
		
	}
	
	protected void copy(InputStream input, OutputStream output) throws IOException {
        final byte[] buffer = new byte[4096];
        int count = 0;
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
            count += n;
        }
	}
	
	protected InputStream findInputStream(String packagePrefix, HttpServletRequest request) throws IOException {

		// handle the case with mapping=/webwork, request=/webwork
		if (request.getRequestURI().equals(request.getServletPath())) {
			return null;
		}

		String relativePath = request.getPathInfo().substring(1);

		String resourcePath = packagePrefix + relativePath;
		
		String rawResourcePath = resourcePath; 
		
		// TODO allow the enc type to be configured
		resourcePath = URLDecoder.decode(resourcePath);
		
		// TODO when the resourcePath is a folder - it seems to return a stream containing the contents of the folder - probably bad !
		URL url = null;
		
		url = Thread.currentThread().getContextClassLoader().getResource(resourcePath);
		if (url != null && checkUrl(url, rawResourcePath)) {
			return url.openStream();
		}
		
		url = StaticResourceServlet.class.getResource(resourcePath);
		if (url != null && checkUrl(url, rawResourcePath)) {
			return url.openStream();
		}
		return null;
		
	}
	
	/**
	 * handle .. chars here and other URL hacks 
	 * 
	 */
	protected boolean checkUrl(URL url, String rawResourcePath) {
		
		// ignore folder resources - they provide streams too ! dunno why :)
		if (url.getPath().endsWith("/")) {
			return false;
		}
		
		// check for parent path access
		// NOTE : most servlet containers shoudl resolve .. chars in the request url anyway
		if (url.toExternalForm().indexOf(rawResourcePath) == -1) {
			return false;
		}
		
		return true;
	}
	
	public void init(ServletConfig servletConfig) throws ServletException {
		super.init(servletConfig);
		this.pathPrefixes = parse(servletConfig.getInitParameter("packages"));
		if (this.pathPrefixes == null) {
			log.warn("no packages specified - servlet disabled");
		}
	}
	
	protected String[] parse(String packages) {
		if (packages == null) {
			return null;
		}
		List pathPrefixes = new ArrayList();
		
		StringTokenizer st = new StringTokenizer(packages, ", \n\t");
		while (st.hasMoreTokens()) {
			String pathPrefix = st.nextToken().replace('.', '/');
			if (!pathPrefix.endsWith("/")) {
				pathPrefix += "/";
			}
			pathPrefixes.add(pathPrefix);
		}
		
		return (String[]) pathPrefixes.toArray(new String[pathPrefixes.size()]);
	}
}
