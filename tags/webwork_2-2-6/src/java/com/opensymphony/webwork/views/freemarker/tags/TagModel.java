/*
 * Copyright (c) 2002-2007 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.freemarker.tags;

import com.opensymphony.webwork.components.Component;
import com.opensymphony.xwork.util.OgnlValueStack;

import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateTransformModel;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * An abstract class mean to be implemented by WebWork's Freemarker's
 * Tag. It abstracts away the logic of parameter conversion exposed by 
 * Freemarker's {@link TemplateTransformModel#getWriter(Writer, Map)} method
 * and populating them into WebWork's component exposed by subclass 
 * through {@link #getBean()} method.
 * 
 * @author plightbo
 * @author tmjee
 * @version $Date$ $Id$
 */
public abstract class TagModel implements TemplateTransformModel {
    private static final Log LOG = LogFactory.getLog(TagModel.class);

    protected OgnlValueStack stack;
    protected HttpServletRequest req;
    protected HttpServletResponse res;

    public TagModel(OgnlValueStack stack, HttpServletRequest req, HttpServletResponse res) {
        this.stack = stack;
        this.req = req;
        this.res = res;
    }

    /**
     * Returns a Writer that will render WebWork Freemarker tag.
     * 
     * @see TemplateTransformModel#getWriter(Writer, Map)
     */
    public Writer getWriter(Writer writer, Map params) throws TemplateModelException, IOException {
        Component bean = getBean();
        
        Map unwrappedParameters = unwrapParameters(params);
        bean.copyParams(unwrappedParameters);
        
        //Map basicParams = convertParams(params);
        //bean.copyParams(basicParams);
        //bean.addAllParameters(getComplexParams(params));
        
        return new CallbackWriter(bean, writer);
    }
    
    /**
     * Unwraped the parameters (Map) passed in by 
     * {TemplateTransformModel{@link #getWriter(Writer, Map)}. It makes use
     * of {DefaultObjectWrapper{@link #unwrapParameters(Map)} to do the 
     * unwrapping if the value of the entry in the <code>params</code> is 
     * of type {#TemplateModel} (which should ALWAYS be the case) else it
     * will just use the <code>toString()</code> representation of it.
     * 
     * @param params
     * @return Map
     */
    protected Map unwrapParameters(Map params) {
    	Map map = new HashMap(params.size());
    	DefaultObjectWrapper objectWrapper = new DefaultObjectWrapper();
    	for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext(); ) {
    		Map.Entry entry = (Map.Entry) iterator.next();
    		
    		Object value = entry.getValue();
    		
    		if (value != null) {
    				// the value should ALWAYS be a decendant of TemplateModel
    				if (value instanceof TemplateModel) {
    					try {
    						map.put(entry.getKey(), objectWrapper.unwrap((TemplateModel) value));
    					}catch(TemplateModelException e) {
    						LOG.error("failed to unwrap ["+value+"] it will be ignored", e);
    					}
    				}
    				// if it doesn't, we'll do it the old way by just returning the toString() representation
    				else {
    					map.put(entry.getKey(), value.toString());
    				}
    		}
    	}
    	return map;
    }


    /**
     * An abstract method subclass should implement, exposing the WebWork's
     * underlying {Component} this tag is supposed to delegate to.
     * 
     * @return Component
     */
    protected abstract Component getBean();

    /*private Map convertParams(Map params) {
        HashMap map = new HashMap(params.size());
        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object value = entry.getValue();
            if (value != null && !complexType(value)) {
                map.put(entry.getKey(), value.toString());
            }
        }
        return map;
    }*/

    /*private Map getComplexParams(Map params) {
        HashMap map = new HashMap(params.size());
        for (Iterator iterator = params.entrySet().iterator(); iterator.hasNext();) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Object value = entry.getValue();
            if (value != null && complexType(value)) {
                if (value instanceof freemarker.ext.beans.BeanModel) {
                    map.put(entry.getKey(), ((freemarker.ext.beans.BeanModel) value).getWrappedObject());
                } else if (value instanceof SimpleNumber) {
                    map.put(entry.getKey(), ((SimpleNumber) value).getAsNumber());
                } else if (value instanceof SimpleSequence) {
                    try {
                        map.put(entry.getKey(), ((SimpleSequence) value).toList());
                    } catch (TemplateModelException e) {
                        LOG.error("There was a problem converting a SimpleSequence to a list", e);
                    }
                }
            }
        }
        return map;
    }*/

    /*private boolean complexType(Object value) {
        return value instanceof freemarker.ext.beans.BeanModel
                || value instanceof SimpleNumber
                || value instanceof SimpleSequence;
    }*/
}
