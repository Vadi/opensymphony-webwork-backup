/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;


/**
 * @author $author$
 * @version $Revision$
 */
public class DefaultCellRenderer extends AbstractCellRenderer {
    //~ Constructors ///////////////////////////////////////////////////////////

    public DefaultCellRenderer() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getCellValue(WebTable table, Object data, int row, int col) {
        if (data != null) {
            return data.toString();
        }

        return "null";
    }
}
