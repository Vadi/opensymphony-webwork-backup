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
 */
public class ActionMapping {

    private String name;
    private String namespace;
    private String method;
    private Map params;
    private Result result;

    public ActionMapping() {}

    public ActionMapping(Result result) {
        this.result = result;
    }

    public ActionMapping(String name, String namespace, String method, Map params) {
        this.name = name;
        this.namespace = namespace;
        this.method = method;
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public String getNamespace() {
        return namespace;
    }

    public Map getParams() {
        return params;
    }

    public String getMethod() {
        if (null != method && "".equals(method)) {
            return null;
        } else {
            return method;
        }
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public void setParams(Map params) {
        this.params = params;
    }
}
