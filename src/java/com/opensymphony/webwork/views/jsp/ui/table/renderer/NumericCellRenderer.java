package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;

import java.text.DecimalFormat;

public class NumericCellRenderer extends AbstractCellRenderer {
    /**
     * if set this is the color to render if number is positive
     */
    String _positiveColor = null;

    /**
     * if set the is the color to use if Number is negative.
     */
    String _negativeColor = null;

    /**
     * this is the format string that DecimalFormat would use.
     * @see DecimalFormat
     */
    String _formatString = null;
    DecimalFormat _formater = new DecimalFormat();

    public NumericCellRenderer() {
        super();
    }

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

    protected void processNumber(StringBuffer buf, String color, double cellValue) {
        if (color != null) {
            buf.append(" <font color='").append(color).append("'>");
        }
        buf.append(_formater.format(cellValue));
        if (color != null) {
            buf.append("</font>");
        }
    }

    public void setPositiveColor(String color) {
        _positiveColor = color;
    }

    public void setNegativeColor(String color) {
        _negativeColor = color;
    }

    public void setFormatString(String format) {
        _formatString = format;
        _formater.applyPattern(_formatString);
    }
}