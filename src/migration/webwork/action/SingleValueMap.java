/*
 * WebWork, Web Application Framework
 *
 * Distributable under Apache license.
 * See terms of license at opensource.org
 */
package webwork.action;

import java.io.Serializable;
import java.util.*;

/**
 * A Map that can be used to wrap a map whose values are object arrays.
 * This wrapper will then allow one to access only the first object of those arrays.
 *
 * A common usage is to use this wrap the Map received through the ParameterAware interface.
 *
 * @see ParameterAware
 * @author Rickard Öberg (rickard@middleware-company.com)
 */
public class SingleValueMap implements Map, Serializable {
    private Map m;	        // Backing Map

    public SingleValueMap(Map m) {
        if (m == null)
            throw new NullPointerException();
        this.m = m;
    }

    public int size() {
        return m.size();
    }

    public boolean isEmpty() {
        return m.isEmpty();
    }

    public boolean containsKey(Object key) {
        return m.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return m.containsValue(value);
    }

    public Object get(Object key) {
        Object[] value = (Object[]) m.get(key);
        return value == null ? null : ((Object[]) value)[0];
    }

    public Object put(Object key, Object value) {
        Object[] val = (Object[]) m.put(key, new Object[]{value});
        return val == null ? null : ((Object[]) val)[0];
    }

    public Object remove(Object key) {
        Object[] val = (Object[]) m.remove(key);
        return val == null ? null : ((Object[]) val)[0];
    }

    public void putAll(Map map) {
        throw new UnsupportedOperationException();
    }

    public void clear() {
        m.clear();
    }

    private transient Set keySet = null;
    private transient Set entrySet = null;
    private transient Collection values = null;

    public Set keySet() {
        return m.keySet();
    }

    public Set entrySet() {
        //TODO: NYI
        return m.entrySet();
    }

    public Collection values() {
        Collection vals = m.values();
        Collection realVals = new ArrayList(vals.size());
        for (Iterator iterator = vals.iterator(); iterator.hasNext();) {
            Object o = (Object) iterator.next();
            if (o != null) {
                realVals.add(((Object[]) o)[0]);
            }
        }

        return realVals;
    }
}
