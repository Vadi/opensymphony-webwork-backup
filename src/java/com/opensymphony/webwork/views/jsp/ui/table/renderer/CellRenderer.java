package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;

public interface CellRenderer {
    public String renderCell(WebTable table, Object data, int row, int col);
}