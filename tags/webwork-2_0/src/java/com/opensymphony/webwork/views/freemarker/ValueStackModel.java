/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
/*
 * Created on 27/08/2003
 *
 */
package com.opensymphony.webwork.views.freemarker;

import com.opensymphony.xwork.ActionContext;
import com.opensymphony.xwork.util.OgnlValueStack;

import freemarker.template.SimpleHash;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;


/**
 * @author CameronBraid
 */
public class ValueStackModel extends SimpleHash implements TemplateHashModel {
    //~ Instance fields ////////////////////////////////////////////////////////

    protected OgnlValueStack stack = null;
    protected TemplateHashModel wrappedModel = null;

    //~ Constructors ///////////////////////////////////////////////////////////

    /**
     *
     */
    public ValueStackModel(TemplateHashModel wrappedModel) {
        super();
        this.wrappedModel = wrappedModel;

        if (ActionContext.getContext() != null) {
            this.stack = ActionContext.getContext().getValueStack();
        }
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /* (non-Javadoc)
    * @see freemarker.template.TemplateHashModel#isEmpty()
    */
    public boolean isEmpty() throws TemplateModelException {
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

        return wrappedModel.get(key);
    }
}
