package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;

/**
 * this is the base class that most renderers will be derived from.
 * It allows setting the alignment.  Subclasses should set there actuall
 * content by implementing getCellValue
 */
abstract public class AbstractCellRenderer implements CellRenderer {
    /**
     * used for horizontal cell alignmnet
     */
    protected String _alignment = null;

    protected boolean isAligned() {
        return _alignment != null;
    }

    public String getAlignment() {
        return _alignment;
    }

    public void setAlignment(String alignment) {
        _alignment = alignment;
    }

    /**
     * implememnts CellRenderer renderCell.  It sets the alignment.  gets the actual
     * data from getCellValue
     */
    public String renderCell(WebTable table, Object data, int row, int col) {
        if (isAligned()) {
            StringBuffer buf = new StringBuffer(256);
            buf.append("<div align='").append(_alignment).append("'>");
            buf.append(getCellValue(table, data, row, col));
            buf.append("</div>");
            return buf.toString();
        }
        return getCellValue(table, data, row, col);
    }

    /**
     * this is the method that subclasses need to implement to set their value.
     * they should not override renderCell unless they want to change the alignmnent
     * renderering
     */
    abstract protected String getCellValue(WebTable table, Object data, int row, int col);
}