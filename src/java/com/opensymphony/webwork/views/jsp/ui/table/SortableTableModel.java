package com.opensymphony.webwork.views.jsp.ui.table;

import javax.swing.table.TableModel;

public interface SortableTableModel extends TableModel {
    final static public String NONE = "NONE";
    final static public String ASC = "ASC";
    final static public String DESC = "DESC";

    public int getSortedColumnNumber();

    public void sort(int columnNumber, String direction);

    public String getSortedDirection(int columnNumber);
}