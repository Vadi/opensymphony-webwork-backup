package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;

import java.text.SimpleDateFormat;

public class DateCellRenderer extends AbstractCellRenderer {
    /**
     * this is the string that  SimpleDateFormat needs to display the date
     * @see SimpleDateFormat
     */
    String _formatString = null;
    SimpleDateFormat _formater = new SimpleDateFormat();

    public DateCellRenderer() {
        super();
    }

    public String getCellValue(WebTable table, Object data, int row, int col) {
        java.util.Date cellValue = null;

        if (data == null) {
            return "";
        }

        if (data instanceof java.util.Date) {
            return _formater.format((java.util.Date) data);
        }
        return data.toString();
    }

    public void setFormatString(String format) {
        _formatString = format;
        _formater.applyPattern(_formatString);
    }
}