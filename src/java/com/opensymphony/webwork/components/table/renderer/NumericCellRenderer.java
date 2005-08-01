/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components.table.renderer;

import com.opensymphony.webwork.components.table.WebTable;

import java.text.DecimalFormat;


/**
 * @author $author$
 * @version $Revision$
 */
public class NumericCellRenderer extends AbstractCellRenderer {
    //~ Instance fields ////////////////////////////////////////////////////////

    DecimalFormat _formater = new DecimalFormat();

    /**
     * this is the format string that DecimalFormat would use.
     *
     * @see DecimalFormat
     */
    String _formatString = null;

    /**
     * if set the is the color to use if Number is negative.
     */
    String _negativeColor = null;

    /**
     * if set this is the color to render if number is positive
     */
    String _positiveColor = null;

    //~ Constructors ///////////////////////////////////////////////////////////

    public NumericCellRenderer() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getCellValue(WebTable table, Object data, int row, int col) {
        StringBuffer retVal = new StringBuffer(128);

        if (data == null) {
            return "";
        }

        if (data instanceof Number) {
            double cellValue = ((Number) data).doubleValue();

            if (cellValue >= 0) {
                processNumber(retVal, _positiveColor, cellValue);
            } else {
                processNumber(retVal, _negativeColor, cellValue);
            }

            return retVal.toString();
        }

        return data.toString();
    }

    public void setFormatString(String format) {
        _formatString = format;
        _formater.applyPattern(_formatString);
    }

    public void setNegativeColor(String color) {
        _negativeColor = color;
    }

    public void setPositiveColor(String color) {
        _positiveColor = color;
    }

    protected void processNumber(StringBuffer buf, String color, double cellValue) {
        if (color != null) {
            buf.append(" <font color='").append(color).append("'>");
        }

        buf.append(_formater.format(cellValue));

        if (color != null) {
            buf.append("</font>");
        }
    }
}
