/*
 * Copyright (c) 2002-2003 by OpenSymphony
 * All rights reserved.
 */
package com.opensymphony.webwork.components.table;

import javax.swing.table.TableModel;


/**
 * @author $author$
 * @version $Revision$
 */
public interface SortableTableModel extends TableModel {
    //~ Static fields/initializers /////////////////////////////////////////////

    final static public String NONE = "NONE";
    final static public String ASC = "ASC";
    final static public String DESC = "DESC";

    //~ Methods ////////////////////////////////////////////////////////////////

    public int getSortedColumnNumber();

    public String getSortedDirection(int columnNumber);

    public void sort(int columnNumber, String direction);
}
