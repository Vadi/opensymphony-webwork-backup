package com.opensymphony.webwork.views.jsp.ui.ajax;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;

/**
 * @author		Ian Roughley
 * @version		$Id$
 */
public interface JavascriptEmitter {

    /**
     * Emitts the component specific Javascript used for all instances of this class.
     *
     * @param page the page context to emitt the javascript to
     */
    void emittJavascript( PageContext page ) throws JspException;

    /**
     * @return the unique name of the component, to make it easy this should be the class name
     */
    String getComponentName();

    /**
     * Emitts the Javascript required to configure an instance of this class.
     *
     * @param page the page context to emitt the javascript to
     */
    void emittInstanceConfigurationJavascript( PageContext page ) throws JspException;

}
