/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui.table;

import com.opensymphony.webwork.views.jsp.ui.ComponentTag;
import com.opensymphony.webwork.views.jsp.ui.table.renderer.CellRenderer;

import com.opensymphony.xwork.util.OgnlUtil;
import com.opensymphony.xwork.util.OgnlValueStack;
import com.opensymphony.xwork.ActionContext;

import org.apache.commons.logging.LogFactory;

import java.util.*;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;

import javax.swing.table.TableModel;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class WebTable extends ComponentTag {
    //~ Static fields/initializers /////////////////////////////////////////////

    /**
     * The name of the default template for the CheckboxTag
     */
    final public static String TEMPLATE = "table.vm";
    //~ Instance fields ////////////////////////////////////////////////////////

    protected String modelNameAttr = null;
    protected String _sortOrder = SortableTableModel.NONE;
    protected TableModel model = null;
    protected WebTableColumn[] _columns = null;
    protected boolean sortableAttr = false;
    protected int _sortColumn = -1;
    int _curRow = 0;

    //~ Constructors ///////////////////////////////////////////////////////////

    public WebTable() {
        super();
    }

    public WebTable(TableModel model) {
        setModel(model);
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    protected String getDefaultTemplate() {
        return TEMPLATE;
    }


    public WebTableColumn getColumn(int index) {
        try {
            return (_columns[index]);
        } catch (Exception E) {
            //blank
        }

        return null;
    }

    public int getColumnCount() {
        return (_columns.length);
    }

    public void setColumnDisplayName(int column, String displayName) {
        _columns[column].setDisplayName(displayName);
    }

    public void getColumnDisplayName(int column) {
        _columns[column].getDisplayName();
    }

    public void setColumnHidden(int column, boolean hide) {
        _columns[column].setHidden(hide);
    }

    public boolean isColumnHidden(int column) {
        return _columns[column].isHidden();
    }

    public void setColumnRenderer(int column, CellRenderer renderer) {
        _columns[column].setRenderer(renderer);
    }

    public CellRenderer getColumnRenderer(int column) {
        return _columns[column].getRenderer();
    }

    public WebTableColumn[] getColumns() {
        return _columns;
    }

    public String[] getFormattedRow(int row) {
        ArrayList data = new ArrayList(getNumberOfVisibleColumns());

        for (int i = 0; i < getColumnCount(); ++i) {
            if (_columns[i].isVisible()) {
                data.add(_columns[i].getRenderer().renderCell(this, model.getValueAt(row, i), row, i));
            }
        }

        return (String[]) data.toArray(new String[0]);
    }

    public void setModel(TableModel model) {
        this.model = model;
        _columns = new WebTableColumn[this.model.getColumnCount()];

        for (int i = 0; i < _columns.length; ++i) {
            _columns[i] = new WebTableColumn(this.model.getColumnName(i), i);
        }

        if ((sortableAttr == true) && !(this.model instanceof SortableTableModel)) {
            this.model = new SortFilterModel(this.model);
        }
    }

    public TableModel getModel() {
        return (model);
    }

    public void setModelName(String modelName) {
        this.modelNameAttr = modelName;
    }

    public String getModelName() {
        return modelNameAttr;
    }

    public Object getRawData(int row, int column) {
        return model.getValueAt(row, column);
    }

    public Iterator getRawDataRowIterator() {
        return new WebTableRowIterator(this, WebTableRowIterator.RAW_DATA);
    }

    public Object[] getRow(int row) {
        ArrayList data = new ArrayList(getNumberOfVisibleColumns());

        for (int i = 0; i < getColumnCount(); ++i) {
            if (_columns[i].isVisible()) {
                data.add(model.getValueAt(row, i));
            }
        }

        return (Object[]) data.toArray(new Object[0]);
    }

    public int getRowCount() {
        return model.getRowCount();
    }

    public Iterator getRowIterator() {
        return new WebTableRowIterator(this);
    }

    public void setSortColumn(int sortColumn) {
        _sortColumn = sortColumn;
    }

    public int getSortColumn() {
        if (model instanceof SortableTableModel) {
            return ((SortableTableModel) model).getSortedColumnNumber();
        }

        return -1;
    }

    public String getSortColumnLinkName() {
        return "WEBTABLE_" + modelNameAttr + "_SORT_COLUMN";
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
        if ((model instanceof SortableTableModel) && (getSortColumn() >= 0)) {
            return ((SortableTableModel) model).getSortedDirection(getSortColumn());
        }

        return SortableTableModel.NONE;
    }

    public String getSortOrderLinkName() {
        return "WEBTABLE_" + modelNameAttr + "_SORT_ORDER";
    }

    public void setSortable(boolean sortable) {
        sortableAttr = sortable;

        if ((sortableAttr == true) && (model != null) && !(model instanceof SortableTableModel)) {
            model = new SortFilterModel(model);
        }
    }

    public boolean isSortable() {
        return sortableAttr;
    }

    public void addParameter(String name, Object value) {
        OgnlUtil.setProperty(name, value, this, getStack().getContext());
        super.addParam(name, value);
    }


    protected void evaluateExtraParams(OgnlValueStack stack) {
        if(modelNameAttr != null) {
            modelNameAttr = (String) stack.findValue(modelNameAttr);

            Object obj = stack.findValue(this.modelNameAttr);

            if (obj instanceof TableModel) {
                setModel((TableModel) obj);
            }
        }

        // evaluate all the parameters for the webtable
        Map params = getParams();
        Set set = params.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            Object value = params.get(key);
            OgnlUtil.setProperty(key, value, this, ActionContext.getContext().getContextMap());
        }

        super.evaluateExtraParams(stack);    //To change body of overriden methods use Options | File Templates.
    }





    public int doEndTag() throws JspException {
        if (sortableAttr && model instanceof SortableTableModel) {
            LogFactory.getLog(this.getClass()).debug("we are looking for " + getSortColumnLinkName());

            String sortColumn = pageContext.getRequest().getParameter(getSortColumnLinkName());
            String sortOrder = pageContext.getRequest().getParameter(getSortOrderLinkName());

            try {
                if ((sortColumn != null) || (sortOrder != null)) {
                    if (sortColumn != null) {
                        try {
                            _sortColumn = Integer.parseInt(sortColumn);
                        } catch (Exception ex) {
                            if (LogFactory.getLog(this.getClass()).isDebugEnabled()) {
                                LogFactory.getLog(this.getClass()).debug("coudn't convert column, take default");
                            }
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
                        ((SortableTableModel) model).sort(_sortColumn, _sortOrder);
                    } catch (Exception ex) {
                        if (LogFactory.getLog(this.getClass()).isDebugEnabled()) {
                            LogFactory.getLog(this.getClass()).debug("couldn't sort the data");
                        }
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

    protected int getNumberOfVisibleColumns() {
        int count = 0;

        for (int i = 0; i < _columns.length; ++i) {
            if (!_columns[i].isHidden()) {
                ++count;
            }
        }

        return count;
    }

    //~ Inner Classes //////////////////////////////////////////////////////////

    /**
 * inner class to iteratoe over a row of the table.
 * It can return formatted data, using the columnRenderer
 * for the column or it can return the raw data.
 */
    public class WebTableRowIterator implements Iterator {
        public static final int FORMATTED_DATA = 0;
        public static final int RAW_DATA = 1;
        protected WebTable _table;
        protected int _curRow = 0;
        protected int _mode = 0;

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
