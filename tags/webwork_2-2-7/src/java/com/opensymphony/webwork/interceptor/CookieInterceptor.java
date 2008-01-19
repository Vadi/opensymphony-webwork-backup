/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.webwork.ServletActionContext;
import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.util.TextParseUtil;

/**
 * <!-- START SNIPPET: description -->
 * 
 * The aim of this interceptor is to set values in the stack/action based on cookie name/value 
 * of interest. <p/>
 * 
 * If an asterik is present in cookiesName parameter, it will be assume that 
 * all cookies name are to be injected into webwork's action, even though 
 * cookiesName is comma-separated by other values, eg (cookie1,*,cookie2). <p/>
 * 
 * If cookiesName is left empty it will assume that no cookie will be injected 
 * into WebWork's action. <p/>
 * 
 * If an asterik is present in cookiesValue parameter, it will assume that all 
 * cookies name irrespective of its value will be injected into WebWork's action so
 * long as the cookie name matches those specified in cookiesName parameter.<p/>
 * 
 * If cookiesValue is left empty it will assume that all cookie that match the cookieName
 * parameter will be injected into WebWork's action.<p/>
 * 
 * The action could implements {@link CookiesAware} in order to have a {@link Map}
 * of filtered cookies set into it. <p/>
 * 
 * <!-- END SNIPPET: description -->
 * 
 * 
 * <!-- START SNIPPET: parameters -->
 * 
 * <ul>
 * 	<li>cookiesName (mandatory) - Name of cookies to be injected into the action. If more
 * 								  than one cookie name is desired it could be comma-separated.
 * 								  If all cookies name is desired, it could simply be *, an asterik.
 * 								  When many cookies name are comma-separated either of the cookie
 * 						          that match the name in the comma-separated list will be qualified.</li>
 *  <li>cookiesValue - Value of cookies that if its name matches cookieName attribute
 *                     and its value matched this, will be injected into WebWork's
 *                     action. If more than one cookie name is desired it could be
 *                     comma-separated. If left empty, as * (asterik) or not specified,
 *                     it will assume any value would
 *                     be ok. If more than one value is specified (comma-separated)
 *                     it will assume a match if either value is matched.
 * </ul>
 * 
 * <!-- END SNIPPET: parameters -->
 * 
 * 
 * <!-- START SNIPPET: extending -->
 * 
 * <ul>
 * 	populateCookieValueIntoStack - this method will decide if this cookie value is qualified to be 
 * 													   populated into the value stack (hence into the action itself)
 * 	injectIntoCookiesAwareAction - this method will inject selected cookies (as a java.util.Map) into 
 * 													  action that implements {@link CookiesAware}.
 * </ul>
 * 
 * <!-- END SNIPPET: extending -->
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 * 
 * &lt;!--
 *   This example will inject cookies named either 'cookie1' or 'cookie2' whose
 *   value could be either 'cookie1value' or 'cookie2value' into WebWork's action.
 * --&gt;
 * &lt;action ... &gt;
 *    &lt;interceptor-ref name="cookie"&gt;
 *        &lt;param name="cookiesName"&gt;cookie1, cookie2&lt;/param&gt;
 *        &lt;param name="cookiesValue"&gt;cookie1value, cookie2value&lt;/param&gt;
 *    &lt;/interceptor-ref&gt;
 *    ....
 * &lt;/action&gt;
 * 
 * 
 * &lt;!--
 * 	This example will inject cookies named either 'cookie1' or 'cookie2' 
 *     regardless of their value into WebWork's action.
 * --&gt;
 * &lt;action ... &gt;
 *   &lt;interceptor-ref name="cookie"&gt;
 *      &lt;param name="cookiesName"&gt;cookie1, cookie2&lt;/param&gt;
 *      &lt;param name="cookiesValue"&gt;*&lt;/param&gt;
 *   &lt;interceptor-ref&gt;
 *   ...
 * &lt;/action&gt;
 * 
 * 
 * &lt;!--
 * 	This example will inject cookies named either 'cookie1' with value 
 *      'cookie1value' or 'cookie2' with value 'cookie2value' into WebWork's
 *      action.
 * --&gt;
 * &lt;action ... &gt;
 *   &lt;interceptor-ref name="cookie"&gt;
 *      &lt;param name="cookiesName"&gt;cookie1&lt;/param&gt;
 *      &lt;param name="cookiesValue"&gt;cookie1value&lt;/param&gt;
 *   &lt;/interceptor-ref&gt;
 *   &lt;interceptor-ref name="cookie"&gt;
 *   	&lt;param name="cookiesName"&lt;cookie2&lt;/param&gt;
 *     &lt;param name="cookiesValue"&gt;cookie2value&lt;/param&gt;
 *   &lt;/interceptor-ref&gt;
 *   ....
 * &lt;/action&gt;
 * 
 * &lt;!--
 *    This example will inject any cookies regardless of its value into 
 *    WebWork's action.
 *  --&gt;
 * &lt;action ... &gt;
 *   &lt;interceptor-ref name="cookie"&gt;
 *      &lt;param name="cookiesName"&gt;*&lt;/param&gt;
 *      &lt;param name="cookiesValue"&gt;*&lt;/param&gt;
 *   &lt;/interceptor-ref&gt;
 *    ...
 * &lt;/action&gt;
 * 
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * @see CookiesAware
 *
 * @author Matthew Payne
 * @author tmjee
 * @version $Date$ $Id$
 */ 
public class CookieInterceptor extends AroundInterceptor {

	private static final long serialVersionUID = 4153142432948747305L;

	private static final Log LOG = LogFactory.getLog(CookieInterceptor.class);

	private Set cookiesNameSet = Collections.EMPTY_SET;
	private Set cookiesValueSet = Collections.EMPTY_SET;
	
	/**
	 * Set the <code>cookiesName</code> which if matche will allow the cookie 
	 * to be injected into action, could be comma-separated string.
	 * 
	 * @param cookiesName
	 */
	public void setCookiesName(String cookiesName) {
		if (cookiesName != null)
			this.cookiesNameSet = TextParseUtil.commaDelimitedStringToSet(cookiesName);
	}
	
	/**
	 * Set the <code>cookiesValue</code> which if matched (together with matching 
	 * cookiesName) will caused the cookie to be injected into action, could be 
	 * comma-separated string.
	 * 
	 * @param cookiesValue
	 */
	public void setCookiesValue(String cookiesValue) {
		if (cookiesValue != null)
			this.cookiesValueSet = TextParseUtil.commaDelimitedStringToSet(cookiesValue);
	}
	
	/**
	 * No operation.
	 */
	public void destroy() {
	}

	/**
	 * No operation.
	 */
	public void init() {
	}

	/**
	 * Inject cookies into action if they match the <code>cookiesName</code>
	 * and <code>cookiesValue</code> configured.
	 * 
	 * @param invocation
	 */
	protected void before(ActionInvocation invocation) throws Exception {
		
		if (LOG.isDebugEnabled())
			LOG.debug("start interception");

		final OgnlValueStack stack = ActionContext.getContext().getValueStack();
		HttpServletRequest request = ServletActionContext.getRequest();

		// contains selected cookies
		Map cookiesMap = new LinkedHashMap();
		
		Cookie cookies[] = request.getCookies();
		for (int a=0; a< cookies.length; a++) {
			String name = cookies[a].getName();
			String value = cookies[a].getValue();
			
			if (cookiesNameSet.contains("*")) {
				if (LOG.isDebugEnabled())
					LOG.debug("contains cookie name [*] in configured cookies name set, cookie with name ["+name+"] with value ["+value+"] will be injected");
				populateCookieValueIntoStack(name, value, cookiesMap, stack);
			}
			else if (cookiesNameSet.contains(cookies[a].getName())) {
				populateCookieValueIntoStack(name, value, cookiesMap, stack);
			}
		}
		
		injectIntoCookiesAwareAction(invocation.getAction(), cookiesMap);
	}
	
	/**
	 * No operation.
	 */
	protected void after(ActionInvocation invocation, String result)
			throws Exception {
		if (LOG.isDebugEnabled())
			LOG.debug("after interception");
	}

	/**
	 * Hook that populate cookie value into ognl value stack (hence the action) 
	 * if the criteria is satisfied (if the cookie value matches with those configured).
	 * 
	 * @param cookieName
	 * @param cookieValue
	 * @param cookiesMap
	 * @param stack
	 */
	protected void populateCookieValueIntoStack(String cookieName, String cookieValue, Map cookiesMap, OgnlValueStack stack) {
		if (cookiesValueSet.isEmpty() || cookiesValueSet.contains("*")) {
			// If the interceptor is configured to accept any cookie value
			// OR
			// no cookiesValue is defined, so as long as the cookie name match
			// we'll inject it into WebWork's action
			if (LOG.isDebugEnabled()) {
				if (cookiesValueSet.isEmpty()) 
					LOG.debug("no cookie value is configured, cookie with name ["+cookieName+"] with value ["+cookieValue+"] will be injected");
				else if (cookiesValueSet.contains("*"))
					LOG.debug("interceptor is configured to accept any value, cookie with name ["+cookieName+"] with value ["+cookieValue+"] will be injected");
			}
			cookiesMap.put(cookieName, cookieValue);
			stack.setValue(cookieName, cookieValue);
		}
		else {
			// if cookiesValues is specified, the cookie's value must match before we
			// inject them into WebWork's action
			if (cookiesValueSet.contains(cookieValue)) {
				if (LOG.isDebugEnabled())
					LOG.debug("both configured cookie name and value matched, cookie ["+cookieName+"] with value ["+cookieValue+"] will be injected");
				cookiesMap.put(cookieName, cookieValue);
				stack.setValue(cookieName, cookieValue);
			}
		}
	}
	
	/**
	 * Hook that set the <code>cookiesMap</code> into action that implements
	 * {@link CookiesAware}.
	 * 
	 * @param action
	 * @param cookiesMap
	 */
	protected void injectIntoCookiesAwareAction(Object action, Map cookiesMap) {
		if (action instanceof CookiesAware) {
			if (LOG.isDebugEnabled())
				LOG.debug("action ["+action+"] implements CookiesAware, injecting cookies map ["+cookiesMap+"]");
			((CookiesAware)action).setCookiesMap(cookiesMap);
		}
	}
	
}
