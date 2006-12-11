/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.dispatcher.SessionMap;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

/**
 * <!-- START SNIPPET: description -->
 * This interceptor invalidates http session based on the type of operation it is in. 
 * There's three type of operations:-
 * <ul>
 * 	<li>NextRequest - This causes the interceptor to invalidate the session in 
 * 							      the next comming request where this interceptor is present in the
 * 								  interceptor stack. This interceptor mark this in the http session 
 * 								  using a key determined by the key attribute of this interceptor</li>
 * 	<li>Now - This causes this interceptor to invalidate the session at the end of 
 * 					 this interceptor's interception</li>
 * 	<li>NoOperation - This causes this interceptor to basically do nothing. 
 * 								   It is here such that users could have this interceptor in their
 * 							      default stack and still allows it to do nothing</li>
 * </ul>
 * <!-- END SNIPPET: description -->
 * 
 * <!-- START SNIPPET: extending -->
 * No intended extension points.
 * <!-- END SNIPPET: extending -->
 * 
 * <!-- START SNIPPET: parameters -->
 * <ul>
 * 	<li>type - indicate the operation of this interceptor, valid values are 'NextRequest', 'Now' and 'NoOperation'
 *                      See description above for more information.</li>
 *  	<li>key - this is the http session key used by the interceptor to mark the situation
 *  				   whereby the next comming request with this interceptor present in the 
 *  				   interception stack, it will invalidate the http session.</li>
 * </ul>
 * <!-- END SNIPPET: parameters -->
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 * &lt;action name="logout" ... &gt;
 * 	&lt;intereptor-ref name="sessionInvalidate"&gt;
 * 	    &lt;param name="type"&gt;Now&lt;/param&gt;
 *     &lt;/interceptor-ref&gt;
 *     ....
 * &lt;/action&gt;
 *   
 *  or 
 *  
 *  &lt;action name="sayByeByeNextRequestWillHaveSessionLost" ... &gt;
 *      &lt;interceptor-ref name="sessionInvalidate"&gt;
 *      	&lt;param name="type"&lt;NextRequest&lt;/param&gt;
 *      &lt;/interceptor-ref&gt;
 *      ....
 *  &lt;/action&gt;
 *  
 *  &lt;!-- This is the next request, "sessionInvalidate"  will find the marker inserted
 *          by the action above and invalidate the session --&gt;
 *  &lt!-- The type="NoOperation" is just there so that the type is a valid one, and 
 *         we don't get a warning log meessage --&gt;        
 *  &lt;action name="nextRequest" ... &gt;
 *  	&lt;interceptor-ref name="sessionInvalidate"&gt;
 *  		&lt;param name="type"&gt;NoOperation&lt;/param&gt;
 *     &lt;/interceptor-ref&gt;
 *      ...
 *  &lt;/action&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * @author tmjee
 * @version $Date$ $Id$
 */
public class SessionInvalidationInterceptor extends AroundInterceptor {

	private static final Log LOG = LogFactory.getLog(SessionInvalidationInterceptor.class);
	
	private static final long serialVersionUID = 1L;

	public static String NEXT_REQUEST = "NextRequest";
	public static String NOW = "Now";
	public static String NO_OPERATION = "NoOperation";
	
	protected String key="___invalidateSession";
	protected String type = NO_OPERATION;
	
	/**
	 * Set the session key, of which this interceptor will use to mark if the next request 
	 * with this interceptor in the stack should have the session invalidated.
	 * @param key
	 */
	public void setKey(String key) { this.key = key; }
	/** 
	 * Get the session key, of which this interceptor will use to mark if the next request 
	 * with this interceptor in the stack should have the session invalidated.
	 * @return String
	 */
	public String getKey() { return this.key; }
	
	/**
	 * Set the operation type, either 'NextRequest', 'Now', or 'NoOperation' (default).
	 * @param key
	 */
	public void setType(String type) { this.type = type; }
	/** 
	 * Returns the operation type. 
	 * @return String
	 */
	public String getType() { return this.type; }
	
	
	/**
	 * Decides if this interceptor should invalidate the session or mark the session
	 * to be invalidated upon the next request that contains this interceptor in the stack.
	 * 
	 * @see com.opensymphony.xwork.interceptor.AroundInterceptor#after(com.opensymphony.xwork.ActionInvocation, java.lang.String)
	 */
	protected void after(ActionInvocation invocation, String result) throws Exception {
		SessionMap sessionMap = (SessionMap) invocation.getInvocationContext().get(ActionContext.SESSION);
		if (NOW.equalsIgnoreCase(type)) {
			if (LOG.isDebugEnabled())
				LOG.debug("type=now, invalidating session now");
			sessionMap.invalidate();
			LOG.info("session invalidated");
		}
		else if (NEXT_REQUEST.equalsIgnoreCase(type)) {
			if (LOG.isDebugEnabled())
				LOG.debug("type=NextRequest, mark key in session, such that next request that have this interceptor will invalidate the session");
			sessionMap.put(key, "true");
		}
		else if (NO_OPERATION.equalsIgnoreCase(type)) {
			if (LOG.isDebugEnabled())
				LOG.debug("no operation");
		}
		else {
			LOG.warn("unrecognized type, type should be either "+NOW+", "+NEXT_REQUEST+" or "+NO_OPERATION);
		}
	}

	/**
	 * Invalidate this session if it is marked to be invalidated from previous request.
	 * 
	 * @see com.opensymphony.xwork.interceptor.AroundInterceptor#before(com.opensymphony.xwork.ActionInvocation)
	 */
	protected void before(ActionInvocation invocation) throws Exception {
		SessionMap sessionMap = (SessionMap) invocation.getInvocationContext().get(ActionContext.SESSION);
			if (sessionMap.containsKey(key) && sessionMap.get(key).equals("true")) {
				if (LOG.isDebugEnabled())
					LOG.debug("found marker in session indicating this is the 'next request', session should be invalidated");
				sessionMap.invalidate();
				LOG.info("session invalidated");
			}
	}
}
