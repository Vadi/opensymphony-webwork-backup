/*
 * Copyright (c) 2002-2006 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.portlet.dispatcher;

import com.opensymphony.xwork.ActionSupport;

/**
 * DirectRenderFromEventAction. Insert description.
 * 
 * @author Nils-Helge Garli
 * @version $Revision$ $Date$
 */
public class DirectRenderFromEventAction extends ActionSupport {
    private String location = null;

    /**
     * @return Returns the location.
     */
    public String getLocation() {
        return location;
    }

    /**
     * @param location The location to set.
     */
    public void setLocation(String location) {
        this.location = location;
    }
}