/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;


/**
 * usefull if a column has an embeded ID number needed for a link but you want it to
 * say something else.
 */
public class FixedTextCellRenderer extends AbstractCellRenderer {
    //~ Instance fields ////////////////////////////////////////////////////////

    /**
     * this is the text that will be shown in the column
     */
    protected String _text = "";

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getCellValue(WebTable table, Object data, int row, int col) {
        return _text;
    }

    public void setText(String text) {
        _text = text;
    }

    public String getText() {
        return _text;
    }
}
