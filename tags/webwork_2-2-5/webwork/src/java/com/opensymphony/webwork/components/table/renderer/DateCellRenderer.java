/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components.table.renderer;

import com.opensymphony.webwork.components.table.WebTable;

import java.text.SimpleDateFormat;


/**
 * @author $author$
 * @version $Revision$
 */
public class DateCellRenderer extends AbstractCellRenderer {

    SimpleDateFormat _formater = new SimpleDateFormat();

    /**
     * this is the string that  SimpleDateFormat needs to display the date
     *
     * @see SimpleDateFormat
     */
    String _formatString = null;


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
