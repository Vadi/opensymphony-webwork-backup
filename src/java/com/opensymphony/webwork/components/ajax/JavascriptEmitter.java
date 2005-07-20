package com.opensymphony.webwork.components.ajax;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.JspException;
import java.io.Writer;

/**
 * @author		Ian Roughley
 * @version		$Id$
 */
public interface JavascriptEmitter {

    /**
     * Emitts the component specific Javascript used for all instances of this class.
     */
    void emittJavascript( Writer writer );

    /**
     * @return the unique name of the component, to make it easy this should be the class name
     */
    String getComponentName();

    /**
     * Emitts the Javascript required to configure an instance of this class.
     */
    void emittInstanceConfigurationJavascript( Writer writer );

}
