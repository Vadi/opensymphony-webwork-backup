/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components.table.renderer;

import com.opensymphony.webwork.components.table.WebTable;


/**
 * @author $author$
 * @version $Revision$
 */
public class BooleanCellRenderer extends AbstractCellRenderer {
    //~ Instance fields ////////////////////////////////////////////////////////

    /**
     * value used if the boolean object is false
     */
    protected String _falseValue = "false";

    /**
     * value used if the boolean object is true
     */
    protected String _trueValue = "true";

    //~ Constructors ///////////////////////////////////////////////////////////

    public BooleanCellRenderer() {
        super();
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    public String getCellValue(WebTable table, Object data, int row, int col) {
        if (data == null) {
            return "";
        }

        if (data instanceof Boolean) {
            return ((Boolean) data).booleanValue() ? _trueValue : _falseValue;
        }

        return data.toString(); //if here then not a boolean
    }

    public void setFalseValue(String falseValue) {
        _falseValue = falseValue;
    }

    public void setTrueValue(String trueValue) {
        _trueValue = trueValue;
    }
}
