package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;

public class DefaultCellRenderer extends AbstractCellRenderer {
    public DefaultCellRenderer() {
        super();
    }

    public String getCellValue(WebTable table, Object data, int row, int col) {
        if (data != null) {
            return data.toString();
        }

        return "null";
    }
}