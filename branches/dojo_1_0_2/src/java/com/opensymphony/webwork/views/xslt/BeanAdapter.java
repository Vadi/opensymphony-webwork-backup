/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.opensymphony.webwork.WebWorkException;


/**
 * This class is the most general type of adapter, utilizing reflective introspection to present a DOM view of all of
 * the public properties of its value.  For example, a property returning a JavaBean such as:
 *
 * <pre>
 * public Person getMyPerson() { ... }
 * ...
 * class Person {
 * 		public String getFirstName();
 * 		public String getLastName();
 * }
 * </pre>
 *
 * would be rendered as: <myPerson> <firstName>...</firstName> <lastName>...</lastName> </myPerson>
 *
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * @author Pat Niemeyer (pat@pat.net)
 */
public class BeanAdapter extends AbstractAdapterElement {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static final Object[] NULLPARAMS = new Object[0];

    /**
     * Cache can savely be static because the cached information is the same for all instances of this class.
     */
    private static Map propertyDescriptorCache;

    //~ Instance fields ////////////////////////////////////////////////////////

    private Log log = LogFactory.getLog(this.getClass());

    //~ Constructors ///////////////////////////////////////////////////////////

    public BeanAdapter() {
    }

    public BeanAdapter(
            AdapterFactory adapterFactory, AdapterNode parent, String propertyName, Object value) {
        setContext(adapterFactory, parent, propertyName, value);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getTagName() {
        return getPropertyName();
    }

    public NodeList getChildNodes() {
        NodeList nl = super.getChildNodes();
        // Log child nodes for debug:
        if (log.isDebugEnabled() && nl != null) {
            log.debug("BeanAdapter getChildNodes for: " + getTagName());
            log.debug(nl.toString());
        }
        return nl;
    }

    protected List buildChildAdapters() {
        log.debug("BeanAdapter building children.  PropName = " + getPropertyName());
        List newAdapters = new ArrayList();
        Class type = getPropertyValue().getClass();
        PropertyDescriptor[] props = getPropertyDescriptors(getPropertyValue());

        if (props.length > 0) {
            for (int i = 0; i < props.length; i++) {
                Method m = props[i].getReadMethod();

                if (m == null) {
                    //FIXME: write only property or indexed access
                    continue;
                }
                if (log.isDebugEnabled())
                	log.debug("Bean reading property method: " + m.getName());

                String propertyName = props[i].getName();
                Object propertyValue;

                /*
                        Unwrap any invocation target exceptions and log them.
                        We really need a way to control which properties are accessed.
                        Perhaps with annotations in Java5?
                    */
                try {
                    propertyValue = m.invoke(getPropertyValue(), NULLPARAMS);
                } catch (Exception e) {
                    if (e instanceof InvocationTargetException)
                        e = (Exception) ((InvocationTargetException) e).getTargetException();
                    log.error(e);
                    continue;
                }

                Node childAdapter;

                if (propertyValue == null) {
                    childAdapter = getAdapterFactory().adaptNullValue(this, propertyName);
                } else {
                    childAdapter = getAdapterFactory().adaptNode(this, propertyName, propertyValue);
                }

                if( childAdapter != null)
                    newAdapters.add(childAdapter);

                if (log.isDebugEnabled()) {
                    log.debug(this + " adding adapter: " + childAdapter);
                }
            }
        } else {
            // No properties found
            log.info(
                    "Class " + type.getName() + " has no readable properties, " + " trying to adapt " + getPropertyName() + " with StringAdapter...");

            //newAdapters.add(new StringAdapter(getRootAdapter(), this, getPropertyName(), getValue()));
        }

        return newAdapters;
    }

    /**
     * Caching facade method to Introspector.getBeanInfo(Class, Class).getPropertyDescriptors();
     */
    private synchronized PropertyDescriptor[] getPropertyDescriptors(Object bean) {
        try {
            if (propertyDescriptorCache == null) {
                propertyDescriptorCache = new HashMap();
            }

            PropertyDescriptor[] props = (PropertyDescriptor[]) propertyDescriptorCache.get(bean.getClass());

            if (props == null) {
                log.debug("Caching property descriptor for " + bean.getClass().getName());
                props = Introspector.getBeanInfo(bean.getClass(), Object.class).getPropertyDescriptors();
                propertyDescriptorCache.put(bean.getClass(), props);
            }

            return props;
        } catch (IntrospectionException e) {
            e.printStackTrace();
            throw new WebWorkException("Error getting property descriptors for " + bean + " : " + e.getMessage());
        }
    }
}
