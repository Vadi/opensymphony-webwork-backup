/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components.table;

import com.opensymphony.webwork.components.table.AbstractFilterModel;

import javax.swing.table.TableModel;


/**
 * @author $author$
 * @version $Revision$
 */
public class RenderFilterModel extends AbstractFilterModel {
    //~ Instance fields ////////////////////////////////////////////////////////

    private boolean rendered;

    //~ Constructors ///////////////////////////////////////////////////////////

    public RenderFilterModel(TableModel tm) {
        super(tm);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    public boolean isRendered() {
        return rendered;
    }
}
