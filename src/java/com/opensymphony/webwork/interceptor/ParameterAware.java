/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package com.opensymphony.webwork.interceptor;

import java.util.Map;

/**
 *	This interface gives actions an alternative way of receiving
 * input parameters. The map will contain all input parameters as
 * name/value entries. Actions that need this should simply implement it.
 *
 * One common use for this is to have the action propagate parameters
 * to internally instantiated data objects.
 *
 * Note that all parameter values for a given name will be returned,
 * so the type of the objects in the map is java.lang.String[]
 *
 *	@author Rickard Öberg (rickard@middleware-company.com)
 *	@version $Revision$
 */
public interface ParameterAware {
    public void setParameters(Map parameters);
}
