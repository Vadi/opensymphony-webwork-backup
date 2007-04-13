/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;


/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a> Date: 14.10.2003 Time: 18:59:07
 */
public class ArrayAdapter extends AbstractAdapterElement {
    //~ Instance fields ////////////////////////////////////////////////////////

    private Log log = LogFactory.getLog(this.getClass());

    //~ Constructors ///////////////////////////////////////////////////////////

    public ArrayAdapter() {
    }

    public ArrayAdapter(
            AdapterFactory adapterFactory, AdapterNode parent, String propertyName, Object value) {
        setContext(adapterFactory, parent, propertyName, value);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    protected List buildChildAdapters() {
        List children = new ArrayList();
        Object[] values = (Object[]) getPropertyValue();

        for (int i = 0; i < values.length; i++) {
            Node childAdapter = getAdapterFactory().adaptNode(this, "item", values[i]);
            if( childAdapter != null)
                children.add(childAdapter);

            if (log.isDebugEnabled()) {
                log.debug(this + " adding adapter: " + childAdapter);
            }
        }

        return children;
    }
}
