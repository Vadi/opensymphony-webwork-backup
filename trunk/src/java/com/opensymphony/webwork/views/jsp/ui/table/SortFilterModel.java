package com.opensymphony.webwork.views.jsp.ui.table;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author Onyeje Bose
 * @version 1.1*/
public class SortFilterModel extends AbstractFilterModel implements TableModelListener, SortableTableModel {
    private int sortColumn = -1;
    private ArrayList rows = new ArrayList();
    private boolean dirty = true;


    public SortFilterModel(TableModel tm) {
        super(tm);
        setModel(tm);
    }

    protected void refresh() {
        rows.clear();
        for (int i = 0; i < model.getRowCount(); i++) {
            rows.add(new Row(i));
        }
        if (dirty && (sortColumn > -1)) {
            sort(sortColumn);
        }
    }

    protected void sort(int c) {
        boolean sorted = (sortColumn == c);
        sortColumn = c;
        if (dirty || !sorted) {
            Collections.sort(rows);
            dirty = false;
        } else
            Collections.reverse(rows);
        fireTableDataChanged();
    }

    public void addMouseListener(final JTable table) {
        table.getTableHeader().addMouseListener(
                new MouseAdapter() {
                    public void mouseClicked(MouseEvent event) {
                        // check for double click
                        if (event.getClickCount() < 2) return;
                        // find column of click and
                        int tableColumn = table.columnAtPoint(event.getPoint());
                        // translate to table model index and sort
                        int modelColumn = table.convertColumnIndexToModel(tableColumn);
                        sort(modelColumn);
                    }
                });
    }

    /* compute the moved row for the three methods that access
       model elements
    */

    public Object getValueAt(int r, int c) {
        if (rows.size() > 0 && r < rows.size()) {
            return model.getValueAt(((Row) rows.get(r)).index, c);
        }
        return null;
    }

    public boolean isCellEditable(int r, int c) {
        if (rows.size() > 0 && r < rows.size()) {
            return model.isCellEditable(((Row) rows.get(r)).index, c);
        }
        return false;
    }

    public void setValueAt(Object aValue, int r, int c) {
        if (rows.size() > 0 && r < rows.size()) {
            model.setValueAt(aValue, ((Row) rows.get(r)).index, c);
        }
    }

    /**
     * Implements the TableModelListener interface to receive
     notifications of * changes to the table model. SortFilterModel needs
     to receive events for adding and removing rows. */
    public void tableChanged(TableModelEvent e) {
        dirty = true;
        refresh();
        fireTableChanged(e);
    }

    public void removeRow(int rowNum) throws ArrayIndexOutOfBoundsException,
            IllegalStateException {
        int mappedRow = ((Row) rows.get(rowNum)).index;
        super.removeRow(mappedRow);
    }

    public void setModel(TableModel tm) {
        super.setModel(tm);
        rows.ensureCapacity(model.getRowCount());
        model.addTableModelListener(this);
        sortColumn = -1;
        dirty = true;
        refresh();
    }

    /* this inner class holds the index of the model row
     * Rows are compared by looking at the model row entries
     * in the sort column
     */

    private class Row implements Comparable {
        public Row(int index) {
            this.index = index;
        }

        public int index;

        public int compareTo(Object other) {
            Row otherRow = (Row) other;
            Object a = model.getValueAt(index, sortColumn);
            Object b = model.getValueAt(otherRow.index, sortColumn);

            boolean areTheyCompareable = (a instanceof Comparable &&
                    b instanceof Comparable &&
                    a.getClass() == b.getClass());

            if (areTheyCompareable)
                return ((Comparable) a).compareTo((Comparable) b);
            else
                return a.toString().compareTo(b.toString());
        }
    }


    /**
     * These are just here to implement the interface
     */
    private String _sortDirection = NONE;

    public int getSortedColumnNumber() {
        return sortColumn;
    }

    public void sort(int columnNumber, String direction) {
        _sortDirection = ASC;
        dirty = true;
        sort(columnNumber);
        if (DESC.equals(direction)) {
            sort(columnNumber);
            _sortDirection = DESC;
        }
    }

    public String getSortedDirection(int columnNumber) {
        if (getSortedColumnNumber() < 0) {
            return NONE;
        }
        return _sortDirection;
    }
}


