/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 2/10/2003
 *
 */
package com.opensymphony.webwork.dispatcher;

import java.net.URLDecoder;

import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Servlet Dispatcher that maps servlet paths to actions.
 * The format is the following: <tt>http://HOST/ACTION_NAME/PARAM_NAME1/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt>.
 * You can have as many parameters you'd like to use. Alternatively the URL can be shorten to the following:
 * <tt>http://HOST/ACTION_NAME/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt> which will be the same as
 * <tt>http://HOST/ACTION_NAME/ACTION_NAME/PARAM_VALUE1/PARAM_NAME2/PARAM_VALUE2</tt>.<br />
 * Suppose for example we would like to display some articles by id at using the following URL sheme:
 * <tt>http://HOST/article/ID</tt>. All we would have to do is to map the <tt>/article/*</tt> to this servlet and
 * declare in WebWork an action named <tt>article</tt>. This action would its <tt>article</tt> parameter set to
 * <tt>ID</tt>.
 *
 * @author CameronBraid (cameron@datacodex.net)
 * @author Jerome Bernard (jerome.bernard@xtremejava.com)
 *
 */
public class CoolUriServletDispatcher extends ServletDispatcher {
    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *
     */
    public CoolUriServletDispatcher() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#service(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String actionName = request.getServletPath().substring(1, request.getServletPath().indexOf('/', 1));
        Map parameters = new HashMap();

        try {
            StringTokenizer st = new StringTokenizer(request.getServletPath().substring(request.getServletPath().indexOf('/', 1)), "/");
            boolean isNameTok = true;
            String paramName = null;
            String paramValue = null;

            // check if we have the first parameter name
            if ((st.countTokens() % 2) != 0) {
                isNameTok = false;
                paramName = actionName;
            }

            while (st.hasMoreTokens()) {
                if (isNameTok) {
                    paramName = URLDecoder.decode(st.nextToken());
                    isNameTok = false;
                } else {
                    paramValue = URLDecoder.decode(st.nextToken());

                    if ((paramName != null) && (paramName.length() > 0)) {
                        parameters.put(paramName, paramValue);
                    }

                    isNameTok = true;
                }
            }
        } catch (Exception e) {
            log.warn(e);
        }

        try {
            request = wrapRequest(request);
            serviceAction(request, response, "", actionName, parameters, getSessionMap(request), getApplicationMap());
        } catch (IOException e) {
            String message = "Could not wrap servlet request with MultipartRequestWrapper!";
            log.error(message, e);
            sendError(request, response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, new ServletException(message, e));
        }
    }
}
