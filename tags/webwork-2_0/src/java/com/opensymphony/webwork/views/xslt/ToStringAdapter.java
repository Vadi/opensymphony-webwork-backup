package com.opensymphony.webwork.views.xslt;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:meier@meisterbohne.de">Philipp Meier</a>
 * Date: 10.10.2003
 * Time: 19:45:12
 */
public class ToStringAdapter extends DefaultElementAdapter {
    public ToStringAdapter(DOMAdapter rootAdapter, AdapterNode parent, String propertyName, Object value) {
        super(rootAdapter, parent, propertyName, value);
    }

    protected List buildChildrenAdapters() {
        List children = new ArrayList();
        children.add(new SimpleTextNode(getRootAdapter(), this, "text", getValue()));
        return children;
    }
}

