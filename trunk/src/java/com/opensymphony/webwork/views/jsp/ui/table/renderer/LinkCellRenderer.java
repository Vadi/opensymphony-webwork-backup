/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.views.jsp.ui.table.renderer;

import com.opensymphony.webwork.views.jsp.ui.table.WebTable;


/**
 * DOCUMENT ME!
 *
 * @author $author$
 * @version $Revision$
 */
public class LinkCellRenderer extends AbstractCellRenderer {
    //~ Instance fields ////////////////////////////////////////////////////////

    /**
     * this is the actual renderer tha will be used to display the text
     */
    protected CellRenderer _delegateRenderer = new DefaultCellRenderer();

    /**
     * this is the link we are setting (required)
     */
    protected String _link = null;

    /**
     * if set there will be a parameter attached to link.  (optional)
     * This should be extended to allow multiple parameters
     */
    protected String _param = null;

    /**
     * directly set the value for the param.  Will overide paramColumn if set.
     * optional.  Either this or paramColumn must be set if param is used.
     * Will be ignored if param not used
     */
    protected String _paramValue = null;

    /**
     * the target frame to open in. Optional
     */
    protected String _target = null;

    /**
     * if used the param value will be taken from another column in the table.  Useful if each row
     * needs a different paramter.  The paramter can be taken from a hidden cell.
     * if paramValue is also set it will overrid this.  (option either this or paramValue must be set
     * if param is used. Will be ignored if param not used
     */
    protected int _paramColumn = -1;

    //~ Constructors ///////////////////////////////////////////////////////////

    public LinkCellRenderer() {
    }

    //~ Methods ////////////////////////////////////////////////////////////////

    /**
     * should the link data be encodeed?
     */
    public String getCellValue(WebTable table, Object data, int row, int col) {
        String value = _delegateRenderer.renderCell(table, data, row, col);

        StringBuffer cell = new StringBuffer(256);
        cell.append("<a href='").append(_link);

        if (_param != null) {
            cell.append("?").append(_param).append("=");

            if (_paramValue != null) {
                cell.append(_paramValue);
            } else if (_paramColumn >= 0) {
                cell.append(table.getModel().getValueAt(row, _paramColumn).toString());
            }
        }

        cell.append("'");

        if ((_target != null) && (!"".equals(_target))) {
            cell.append(" target='").append(_target).append("'");
        }

        cell.append(">").append(value).append("</a>");

        return cell.toString();
    }

    public void setLink(String link) {
        _link = link;
    }

    public void setParam(String param) {
        _param = param;
    }

    public void setParamColumn(int paramColumn) {
        _paramColumn = paramColumn;
    }

    public void setParamValue(String paramValue) {
        _paramValue = paramValue;
    }

    /**
     * used to set the renderer to delgate to.
     * if the render is an AbstractCellRenderer then it will take the alignment from
     * the delegate renderer and set it that way.
     */
    public void setRenderer(CellRenderer delegateRenderer) {
        _delegateRenderer = delegateRenderer;

        if (_delegateRenderer instanceof AbstractCellRenderer) {
            setAlignment(((AbstractCellRenderer) _delegateRenderer).getAlignment());
        }
    }

    public void setTarget(String target) {
        _target = target;
    }
}
