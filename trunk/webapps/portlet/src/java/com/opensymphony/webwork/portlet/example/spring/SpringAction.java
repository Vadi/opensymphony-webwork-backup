/*
 * Created on Nov 23, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.opensymphony.webwork.portlet.example.spring;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork.ActionSupport;

/**
 * @author Nils-Helge Garli
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SpringAction extends ActionSupport {
    
    private ThingManager thingManager = null;
    private String thing = null;
    
    public void setThingManager(ThingManager thingManager) {
        this.thingManager = thingManager;
    }
    
    public List getThings() {
        return thingManager.getThings();
    }
    
    public String getThing() {
        return thing;
    }
    
    public void setThing(String thing) {
        this.thing = thing;
    }
    
    public String execute() {
        if(StringUtils.isNotEmpty(thing)) {
            thingManager.addThing(thing);
        }
        return SUCCESS;
    }
}
