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
public interface CellRenderer {
    //~ Methods ////////////////////////////////////////////////////////////////

    public String renderCell(WebTable table, Object data, int row, int col);
}
