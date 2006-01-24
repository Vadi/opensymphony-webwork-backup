/*
 * Created on Nov 23, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.opensymphony.webwork.portlet.example.spring;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Nils-Helge Garli
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ThingManager {
    private List things = new ArrayList();
    
    public void addThing(String thing) {
        things.add(thing);
    }
    
    public List getThings() {
        return things;
    }
}
