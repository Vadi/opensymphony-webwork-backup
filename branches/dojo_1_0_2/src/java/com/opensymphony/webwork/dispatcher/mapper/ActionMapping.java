/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.dispatcher.mapper;

import com.opensymphony.xwork.Result;

import java.util.Map;

/**
 * Simple class that holds the action mapping information used to invoke a
 * WebWork action. The name and namespace are required, but the params map
 * is optional, and as such may be null. If a params map is supplied,
 * it <b>must</b> be a mutable map, such as a HashMap.
 *
 * @author Patrick Lightbody
 * @author tmjee
 *
 * @version $Date$ $Id$
 */
public class ActionMapping {

    private String name;
    private String namespace;
    private String method;
    private Map params;
    private Result result;

    /**
     * Create a default ActionMapping with the followings properties as null :-
     * <ul>
     *  <li>name</li>
     *  <li>namespace</li>
     *  <li>method</li>
     *  <li>params</li>
     *  <li>result</li> 
     * </ul>
     */
    public ActionMapping() {}

    /**
     * Create an ActionMapping with <code>result</code> supplied as arguments with
     * the remaining properties as null.
     * @param result
     */
    public ActionMapping(Result result) {
        this.result = result;
    }

    /**
     * Create an ActionMapping with the <code>name</code>, <code>namespace</code>,
     * <code>method</code> and <code>params</code> supplied.
     * @param name
     * @param namespace
     * @param method
     * @param params
     */
    public ActionMapping(String name, String namespace, String method, Map params) {
        this.name = name;
        this.namespace = namespace;
        this.method = method;
        this.params = params;
    }


    /**
     * Return the action's name.
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Return the action's namespace.
     * @return String
     */
    public String getNamespace() {
        return namespace;
    }

    /**
     * Return the action parameters.
     * @return Map
     */
    public Map getParams() {
        return params;
    }

    /**
     * Return the action's execution method's name.
     * @return String
     */
    public String getMethod() {
        if (null != method && "".equals(method)) {
            return null;
        } else {
            return method;
        }
    }

    /**
     * Return the action's {@link Result}
     * @return Result
     */
    public Result getResult() {
        return result;
    }

    /**
     * Set the action's {@link Result}.
     * @param result
     */
    public void setResult(Result result) {
        this.result = result;
    }

    /**
     * Set the action's name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the action's namespace.
     * @param namespace
     */
    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    /**
     * Set the action's execution method name.
     * @param method
     */
    public void setMethod(String method) {
        this.method = method;
    }

    /**
     * Set the action's parameters.
     * @param params
     */
    public void setParams(Map params) {
        this.params = params;
    }
}
