package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;

/**
 * usefull if a column has an embeded ID number needed for a link but you want it to
 * say something else.
 */
public class FixedTextCellRenderer extends AbstractCellRenderer {
    /**
     * this is the text that will be shown in the column
     */
    protected String _text = "";

    public String getCellValue(WebTable table, Object data, int row, int col) {
        return _text;
    }

    public String getText() {
        return _text;
    }

    public void setText(String text) {
        _text = text;
    }
}