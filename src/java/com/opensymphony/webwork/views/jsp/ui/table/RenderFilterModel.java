package com.opensymphony.webwork.views.jsp.ui.table;

import javax.swing.table.TableModel;

public class RenderFilterModel extends AbstractFilterModel {
    public RenderFilterModel(TableModel tm) {
        super(tm);
    }

    public boolean isRendered() {
        return rendered;
    }

    public void setRendered(boolean rendered) {
        this.rendered = rendered;
    }

    private boolean rendered;
}
