package com.opensymphony.webwork.views.jsp.ui.table;


import com.opensymphony.webwork.views.jsp.ui.ComponentTag;
import com.opensymphony.webwork.views.jsp.ui.table.renderer.CellRenderer;
import com.opensymphony.xwork.util.OgnlUtil;
import org.apache.commons.logging.LogFactory;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.swing.table.TableModel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class WebTable extends ComponentTag {
    protected String _modelName = null;
    protected WebTableColumn[] _columns = null;
    protected TableModel _model = null;
    int _curRow = 0;
    protected boolean _sortable = false;
    protected int _sortColumn = -1;
    protected String _sortOrder = SortableTableModel.NONE;

    public WebTable() {
        super();
        setTemplate("table.jsp");
    }

    public WebTable(TableModel model) {
        setModel(model);
    }

    public void setSortColumn(int sortColumn) {
        _sortColumn = sortColumn;
    }

    public int getSortColumn() {
        if (_model instanceof SortableTableModel) {
            return ((SortableTableModel) _model).getSortedColumnNumber();
        }

        return -1;
    }

    public void setSortOrder(String sortOrder) {
        if (sortOrder.equals(SortableTableModel.NONE)) {
            _sortOrder = SortableTableModel.NONE;
        } else if (sortOrder.equals(SortableTableModel.DESC)) {
            _sortOrder = SortableTableModel.DESC;
        } else if (sortOrder.equals(SortableTableModel.ASC)) {
            _sortOrder = SortableTableModel.ASC;
        } else {
            _sortOrder = SortableTableModel.NONE;
        }
    }

    public String getSortOrder() {
        if ((_model instanceof SortableTableModel) && (getSortColumn() >= 0)) {

            return ((SortableTableModel) _model).getSortedDirection(getSortColumn());
        }
        return SortableTableModel.NONE;
    }

    public boolean isSortable() {
        return _sortable;
    }

    public void setSortable(boolean sortable) {
        _sortable = sortable;
        if ((_sortable == true) && (_model != null) && !(_model instanceof SortableTableModel)) {
            _model = new SortFilterModel(_model);
        }
    }

    public String getModelName() {
        return _modelName;
    }

    public void addParameter(String name, Object value) {
        OgnlUtil.setProperty(name, value, this, getStack().getContext());
        super.addParam(name, value);
    }

    public void setModelName(String modelName) {
        _modelName = (String) findValue(modelName);
        Object obj = findValue(_modelName);
        if (obj instanceof TableModel) {
            setModel((TableModel) obj);
        }
    }

    public void setModel(TableModel model) {
        _model = model;
        _columns = new WebTableColumn[_model.getColumnCount()];
        for (int i = 0; i < _columns.length; ++i) {
            _columns[i] = new WebTableColumn(_model.getColumnName(i), i);
        }
        if ((_sortable == true) && !(_model instanceof SortableTableModel)) {
            _model = new SortFilterModel(_model);
        }
    }

    public TableModel getModel() {
        return (_model);
    }

    public int getColumnCount() {
        return (_columns.length);
    }

    public WebTableColumn getColumn(int index) {
        try {
            return (_columns[index]);
        } catch (Exception E) {
            //blank
        }
        return null;
    }

    public WebTableColumn[] getColumns() {
        return _columns;
    }

    public int getRowCount() {
        return _model.getRowCount();
    }

    public String[] getFormattedRow(int row) {
        ArrayList data = new ArrayList(getNumberOfVisibleColumns());
        for (int i = 0; i < getColumnCount(); ++i) {
            if (_columns[i].isVisible()) {
                data.add(_columns[i].getRenderer().renderCell(this, _model.getValueAt(row, i), row, i));
            }
        }
        return (String[]) data.toArray(new String[0]);
    }

    public Object[] getRow(int row) {
        ArrayList data = new ArrayList(getNumberOfVisibleColumns());
        for (int i = 0; i < getColumnCount(); ++i) {
            if (_columns[i].isVisible()) {
                data.add(_model.getValueAt(row, i));
            }
        }
        return (Object[]) data.toArray(new Object[0]);
    }

    public Object getRawData(int row, int column) {
        return _model.getValueAt(row, column);
    }

    public Iterator getRowIterator() {
        return new WebTableRowIterator(this);
    }

    public Iterator getRawDataRowIterator() {
        return new WebTableRowIterator(this, WebTableRowIterator.RAW_DATA);
    }

    public void setColumnHidden(int column, boolean hide) {
        _columns[column].setHidden(hide);
    }

    public boolean isColumnHidden(int column) {
        return _columns[column].isHidden();
    }

    public void setColumnDisplayName(int column, String displayName) {
        _columns[column].setDisplayName(displayName);
    }

    public void getColumnDisplayName(int column) {
        _columns[column].getDisplayName();
    }

    public void setColumnRenderer(int column, CellRenderer renderer) {
        _columns[column].setRenderer(renderer);
    }

    public CellRenderer getColumnRenderer(int column) {
        return _columns[column].getRenderer();
    }

    protected int getNumberOfVisibleColumns() {
        int count = 0;
        for (int i = 0; i < _columns.length; ++i) {
            if (!_columns[i].isHidden()) {
                ++count;
            }
        }
        return count;
    }

    public String getSortColumnLinkName() {
        return "WEBTABLE_" + _modelName + "_SORT_COLUMN";
    }

    public String getSortOrderLinkName() {
        return "WEBTABLE_" + _modelName + "_SORT_ORDER";
    }

    public int doEndTag() throws JspException {
        if (_sortable && _model instanceof SortableTableModel) {
            LogFactory.getLog(this.getClass()).debug("we are looking for " + getSortColumnLinkName());
            String sortColumn = pageContext.getRequest().getParameter(getSortColumnLinkName());
            String sortOrder = pageContext.getRequest().getParameter(getSortOrderLinkName());

            try {
                if ((sortColumn != null) || (sortOrder != null)) {
                    if (sortColumn != null) {
                        try {
                            _sortColumn = Integer.parseInt(sortColumn);
                        } catch (Exception ex) {
                            LogFactory.getLog(this.getClass()).debug("coudn't convert column, take default");
                        }
                    }

                    if (sortOrder != null) {
                        _sortOrder = sortOrder;
                    }
                } else {
                    LogFactory.getLog(this.getClass()).debug("no sorting info in the request");
                }

                if (_sortColumn >= 0) {
                    LogFactory.getLog(this.getClass()).debug("we have the sortColumn " + Integer.toString(_sortColumn));
                    LogFactory.getLog(this.getClass()).debug("we have the sortOrder " + _sortOrder);

                    try {
                        ((SortableTableModel) _model).sort(_sortColumn, _sortOrder);
                    } catch (Exception ex) {
                        LogFactory.getLog(this.getClass()).debug("couldn't sort the data");
                    }

                    LogFactory.getLog(this.getClass()).debug("we just sorted the data");
                }
            } catch (Exception e) {
                LogFactory.getLog(this.getClass()).error(e);
                throw new JspTagException("Error with WebTable: " + toString(e));
            }

        }
        return super.doEndTag();
    }

    /**
     * inner class to iteratoe over a row of the table.
     * It can return formatted data, using the columnRenderer
     * for the column or it can return the raw data.
     */
    public class WebTableRowIterator implements Iterator {
        protected WebTable _table;
        protected int _curRow = 0;
        protected int _mode = 0;

        public static final int FORMATTED_DATA = 0;
        public static final int RAW_DATA = 1;

        protected WebTableRowIterator(WebTable table) {
            this(table, FORMATTED_DATA);
        }

        protected WebTableRowIterator(WebTable table, int mode) {
            _table = table;
            _mode = mode;
        }

        public boolean hasNext() {
            if (_table == null) {
                return false;
            }

            return (_table.getRowCount() > _curRow);
        }

        public Object next() throws NoSuchElementException {
            if (_table == null) {
                throw new NoSuchElementException("WebTable is null");
            }

            if (!hasNext()) {
                throw new NoSuchElementException("Beyond end of WebTable");
            }

            if (_mode == RAW_DATA) {
                return _table.getRow(_curRow++);
            }

            return _table.getFormattedRow(_curRow++);
        }

        public void remove() throws UnsupportedOperationException, IllegalStateException {
            throw new UnsupportedOperationException("Remove not supported in WebTable");
        }
    }
}
