package com.opensymphony.webwork.dispatcher;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.util.*;

/**
 *
 *
 * @author $Author$
 * @version $Revision$
 */
public class RequestMap extends AbstractMap implements Serializable {
    HashSet entrySet = new HashSet();

    public RequestMap(final HttpServletRequest request) {
        for (Enumeration attrNames = request.getAttributeNames(); attrNames.hasMoreElements();) {
            final String attrName = (String) attrNames.nextElement();
            entrySet.add(new Map.Entry() {
                public Object getKey() {
                    return attrName;
                }

                public Object getValue() {
                    return request.getAttribute(attrName);
                }

                public Object setValue(Object value) {
                    request.setAttribute(attrName, value);
                    return value;
                }

                public int hashCode() {
                    return getValue().hashCode() + getKey().hashCode();
                }

                public boolean equals(Object obj) {
                    if (obj != null && obj instanceof Entry) {
                        Entry me = (Entry) obj;
                        return me.getKey().equals(getKey()) &&
                                me.getValue().equals(getValue());
                    }

                    return false;
                }
            });
        }
    }

    public Set entrySet() {
        return null;
    }
}
