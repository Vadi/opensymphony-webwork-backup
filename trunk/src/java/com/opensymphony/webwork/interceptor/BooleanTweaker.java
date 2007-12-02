/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.interceptor;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.opensymphony.xwork.ActionInvocation;
import com.opensymphony.xwork.interceptor.AroundInterceptor;

/**
 * <!-- START SNIPPET: description -->
 *
 * Interceptor tweaking boolean parameters. parameters with names
 * starting by PREFIX will be stripped of it, and if no value is available under
 * stripped name explicitely set to false.
 *
 * This helps to overcome problem with boolean checkboxes ( unchecked value is not sent
 * by browser )
 *
 * To be used before ParametersInterceptor. 
 *
 * <!-- END SNIPPET: description -->
 *
 *
 * <!-- START SNIPPET: extending -->
 *
 * No known extension points.
 *
 * <!-- END SNIPPET: extending -->
 *
 *
 * <!-- START SNIPPET: parameters -->
 * 
 * <ul>
 *      <li>prefix - The prefix that identify the parameter whose corresponding property will be set to false (by default).
 *                   Default to '_FALSE_'</li>
 *      <li>booleanValue - The boolean value to be set (default to 'false')</li>
 * </ul>
 *
 * <!-- END SNIPPET: parameters -->
 *
 * 
 * <pre>
 * <!-- START SNIPPET: example -->
 *
 * <package ...>
 *      <interceptors>
 *           <interceptor-stack name="myStack">
 *              ....
 *              <interceptor-ref name="booleanTweaker" />
 *              <intercetor-ref name="params" />
 *              ....
 *           </interceptor-stack>
 *       </interceptors>
 *
 *      <action ....>
 *          <interceptor-ref name="myStack" />
 *          ....
 *      </action>
 *      ....
 * </package>
 *
 * <!-- END SNIPPET: example -->
 * </pre>
 * 
 * @author k.pribluda
 * @version $Date$ $Id$
 */
public class BooleanTweaker extends AroundInterceptor {

    public final static String PREFIX = "_FALSE_.";
    public final static String FALSE = "false";
    private static final Log LOG = LogFactory.getLog(BooleanTweaker.class);

    private String prefix = PREFIX;
    private String booleanValue = FALSE;


    /**
     * Set the boolean value.
     * 
     * @param booleanValue
     */
    public void setBooleanValue(String booleanValue) {
        this.booleanValue = booleanValue;
    }

    /**
     * Get the boolean value to set.
     *
     * @return String
     */
    public String getBooleanValue() {
        return booleanValue;
    }

    /**
     * Set the prefix.
     * @param prefix
     */
    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Get the prefix.
     * @return String
     */
    public String getPrefix() {
        return prefix;
    }


    /**
     * Do nothing.
     * @param arg0
     * @param arg1
     * @throws Exception
     */
    protected void after(ActionInvocation arg0, String arg1) throws Exception {
	}

    /**
     * Scan through the paramaters and find those with starts with {@link #getPrefix()}  and set
     * their corresponding parameter with value from {@link #getBooleanValue()}.
     *
     * Eg, parameter <PREFIX>myBoolean  will have myBoolean property set to false.
     * 
     * @param ai
     * @throws Exception
     */
    protected void before(ActionInvocation ai) throws Exception {
		final Map parameters = ai.getInvocationContext().getParameters();

        String key;
		Map toSet = new HashMap();
		for(Iterator iter = parameters.keySet().iterator(); iter.hasNext();) {
			key = iter.next().toString();
			if(key.startsWith(prefix)) {
				key = key.substring(prefix.length());
				if(!parameters.containsKey(key)) {
					toSet.put(key,booleanValue);
				}
			}
		}
		parameters.putAll(toSet);
	}

}
