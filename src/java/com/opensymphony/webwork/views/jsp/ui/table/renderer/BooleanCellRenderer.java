package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;

public class BooleanCellRenderer extends AbstractCellRenderer {
    /**
     * value used if the boolean object is true
     */
    protected String _trueValue = "true";

    /**
     * value used if the boolean object is false
     */
    protected String _falseValue = "false";

    public BooleanCellRenderer() {
        super();
    }

    public String getCellValue(WebTable table, Object data, int row, int col) {
        if (data == null) {
            return "";
        }

        if (data instanceof Boolean) {
            return ((Boolean) data).booleanValue() ? _trueValue : _falseValue;
        }

        return data.toString(); //if here then not a boolean
    }

    public void setTrueValue(String trueValue) {
        _trueValue = trueValue;
    }

    public void setFalseValue(String falseValue) {
        _falseValue = falseValue;
    }
}