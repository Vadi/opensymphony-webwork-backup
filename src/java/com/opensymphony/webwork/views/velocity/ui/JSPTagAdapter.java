/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.velocity.ui;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.Writer;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.jsp.tagext.Tag;


/**
 * @author Matt Ho <matt@indigoegg.com>
 */
public class JSPTagAdapter implements Map {
    //~ Static fields/initializers /////////////////////////////////////////////

    private static Log log = LogFactory.getLog(JSPTagAdapter.class);

    //~ Instance fields ////////////////////////////////////////////////////////

    private Map tagclassMap;
    private ServletConfig config;
    private ServletRequest request;
    private ServletResponse response;
    private Writer out;

    //~ Constructors ///////////////////////////////////////////////////////////

    public JSPTagAdapter(Map tagMap, ServletConfig config, ServletRequest request, ServletResponse response) {
        this.tagclassMap = tagMap;
        this.config = config;
        this.request = request;
        this.response = response;
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public boolean isEmpty() {
        throw new UnsupportedOperationException();
    }

    public void setWriter(Writer out) {
        this.out = out;
    }

    public void clear() {
        throw new UnsupportedOperationException();
    }

    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    public boolean containsValue(Object value) {
        throw new UnsupportedOperationException();
    }

    public Set entrySet() {
        throw new UnsupportedOperationException();
    }

    public Object get(Object tagname) {
        Class c = (Class) tagclassMap.get(tagname);

        if (c == null) {
            try {
                c = Class.forName("com.opensymphony.webwork.views.jsp.ui." + tagname + "Tag");
            } catch (ClassNotFoundException e) {
            }

            try {
                c = Class.forName("com.opensymphony.webwork.views.jsp.ui." + tagname);
            } catch (ClassNotFoundException e) {
            }

            if (c != null) {
                synchronized (tagclassMap) {
                    tagclassMap.put(tagname, c);
                }
            } else {
                try {
                    out.write("[No tag, '" + tagname + "', found in path");
                } catch (IOException e) {
                }
            }
        }

        try {
            /**
            * if a writer has not already been specified, then we're going to use current response writer.
            */
            if (this.out == null) {
                this.out = response.getWriter();
            }

            Tag tag = (Tag) c.newInstance();
            tag.setPageContext(new WebWorkPageContext(config, request, response, out));

            //
            //            // All WebWork UI Tags derive ultimately from the AbstractUITag which doesn't require a pageContext
            //            if (tag instanceof AbstractUITag) {
            //                ((AbstractUITag) tag).setOut(this.out);
            //
            //                // every other tag requires pageContext, so let's provide our _VERY DRAFT_ simulator.
            //            } else {
            //                tag.setPageContext(new WebWorkPageContext(request, response));
            //            }
            return tag;
        } catch (Exception e) {
            log.error("Unable to render JSP Tag!", e);

            return null;
        }
    }

    public Set keySet() {
        throw new UnsupportedOperationException();
    }

    public Object put(Object key, Object value) {
        throw new UnsupportedOperationException();
    }

    public void putAll(Map t) {
        throw new UnsupportedOperationException();
    }

    public Object remove(Object key) {
        throw new UnsupportedOperationException();
    }

    public int size() {
        throw new UnsupportedOperationException();
    }

    public Collection values() {
        throw new UnsupportedOperationException();
    }
}
