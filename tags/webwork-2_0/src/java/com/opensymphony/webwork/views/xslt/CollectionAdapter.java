package com.opensymphony.webwork.views.xslt;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 14.10.2003
 * Time: 18:59:07
 */
public class CollectionAdapter extends DefaultElementAdapter {

    private Log log = LogFactory.getLog(this.getClass());

    public CollectionAdapter(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
    }


    protected List buildChildrenAdapters() {
        Collection values = (Collection) getValue();
        List children = new ArrayList(values.size());
        for (Iterator i = values.iterator(); i.hasNext();) {
            AdapterNode childAdapter = getRootAdapter().adapt(getRootAdapter(), this, "item", i.next());
            children.add(childAdapter);
            log.debug(this + " adding adapter: " + childAdapter);

        }
        return children;
    }
}
