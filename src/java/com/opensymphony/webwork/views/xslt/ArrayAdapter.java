package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 14.10.2003
 * Time: 18:59:07
 */
public class ArrayAdapter extends DefaultElementAdapter {

    private Log log = LogFactory.getLog(this.getClass());

    public ArrayAdapter(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
    }


    protected List buildChildrenAdapters() {
        List children = new ArrayList();
        Object[] values = (Object[]) getValue();
        for (int i = 0; i < values.length; i++) {
            AdapterNode childAdapter = getRootAdapter().adapt(getRootAdapter(), this, "item", values[i]);
            children.add(childAdapter);
            log.debug(this + " adding adapter: " + childAdapter);
        }
        return children;
    }
}
