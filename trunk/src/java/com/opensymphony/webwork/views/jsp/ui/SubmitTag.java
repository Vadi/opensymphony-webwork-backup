/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui;

import com.opensymphony.xwork.util.OgnlValueStack;

import javax.servlet.jsp.JspException;


/**
 * @author Matt Ho <a href="mailto:matt@indigoegg.com">&lt;matt@indigoegg.com&gt;</a>
 * @version $Id$
 */
public class SubmitTag extends AbstractUITag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the LabelTag
     */
    final public static String TEMPLATE = "submit";

    //~ Instance fields ////////////////////////////////////////////////////////

    protected String alignAttr;

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setAlign(String align) {
        this.alignAttr = align;
    }

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }

    protected void evaluateParams(OgnlValueStack stack) {
        if (alignAttr == null) {
            alignAttr = "'right'";
        }

        if (valueAttr == null) {
            valueAttr = "'Submit'";
        }

        super.evaluateParams(stack);

        addParameter("align", findString(alignAttr));
    }

    /**
     * @return the 'id' of the enclosing form
     *
     * @throws JspException if there is no enclosing form or the id is null
     */
    public String getFormId() throws JspException {
        FormTag form = (FormTag)findAncestorWithClass( this, FormTag.class );
        if( null!=form && null!=((FormTag)form).getId() )
            return ((FormTag)form).getId();
        else
            throw new JspException( "When using the ajax theme an enclosing form with an 'id' attribute is required" );
    }

    /**
     * @return the 'id' of the html element to place the html from submitting the form
     */
    public String getFormResultId() {
        FormTag form = (FormTag)findAncestorWithClass( this, FormTag.class );
        if( null!=form )
            return ((FormTag)form).getResultDivId();
        else
            return null;
    }

    /**
     * @return the JS to evaluate after submitting the form
     */
    public String getOnLoadJS() {
        FormTag form = (FormTag)findAncestorWithClass( this, FormTag.class );
        if( null!=form )
            return ((FormTag)form).getOnLoadJS();
        else
            return null;
    }

    /**
     * @return the topics to listen to and submit the form when a message is recieved
     */
    public String getListenTopics() {
        FormTag form = (FormTag)findAncestorWithClass( this, FormTag.class );
        if( null!=form )
            return ((FormTag)form).getListenTopics();
        else
            return null;
    }

    /**
     * @return the topics to publish to when the form has been submitted
     */
    public String getNotifyTopics() {
        FormTag form = (FormTag)findAncestorWithClass( this, FormTag.class );
        if( null!=form )
            return ((FormTag)form).getNotifyTopics();
        else
            return null;
    }
}
