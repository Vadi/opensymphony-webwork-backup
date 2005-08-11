package com.opensymphony.webwork.components;

/**
 * @author		Ian Roughley
 * @version		$Id$
 */
public interface ContentPane {

    /**
     * The unique identifier for this content pane.
     *
     * @return unique identifier for the pane
     */
    String getId();

    /**
     * The name to diplay in the tabbed header.
     *
     * @return the name to diplay in the tabbed header
     */
    String getTabName();

}
