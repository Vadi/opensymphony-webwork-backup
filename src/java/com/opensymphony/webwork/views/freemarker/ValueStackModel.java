/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 27/08/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;

import com.opensymphony.xwork.util.OgnlValueStack;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleHash;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;


/**
 * @author CameronBraid
 */
public class ValueStackModel extends SimpleHash {

    //~ Instance fields ////////////////////////////////////////////////////////

    private TemplateHashModel wrappedModel= null;
    
    protected OgnlValueStack stack = null;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *
     */
    public ValueStackModel(OgnlValueStack stack, TemplateHashModel wrappedModel, ObjectWrapper wrapper) {
        super();
        this.stack = stack;
        this.wrappedModel = wrappedModel;
        this.setObjectWrapper(wrapper);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
    * @see freemarker.template.TemplateHashModel#isEmpty()
    */
    public boolean isEmpty() {
        // not quite sure what to return here.
        // should we check if anything exists in the stack ? - though won't something always exist ?
        return false;
    }

    /* (non-Javadoc)
    * @see freemarker.template.TemplateHashModel#get(java.lang.String)
    */
    public TemplateModel get(String key) throws TemplateModelException {
        Object result = null;

        if (stack != null) {
            result = stack.findValue(key);

            if (result != null) {
                return wrap(result);
            }
        }

        result = super.get(key);

        if (result != null) {
            if (result instanceof TemplateModel) {
                return (TemplateModel) result;
            } else {
                return wrap(result);
            }
        }

        if (wrappedModel ==  null) {
            return wrap(null);
        } else {
            return wrappedModel.get(key);
        }
    }
}
