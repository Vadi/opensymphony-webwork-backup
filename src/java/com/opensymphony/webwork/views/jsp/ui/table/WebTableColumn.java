package com.opensymphony.webwork.views.jsp.ui.table;

import com.opensymphony.webwork.views.jsp.ui.table.renderer.CellRenderer;
import com.opensymphony.webwork.views.jsp.ui.table.renderer.DefaultCellRenderer;

public class WebTableColumn {
    String _name = null;
    String _displayName = null;
    int _offset = -1;
    boolean _hidden = false;

    CellRenderer _renderer = null;

    static final private CellRenderer DEFAULT_RENDERER = new DefaultCellRenderer();

    public WebTableColumn(String name, int offset) {
        _name = name;
        _offset = offset;
        _displayName = name;
        _renderer = DEFAULT_RENDERER;
    }

    public String getName() {
        return (_name);
    }

    public String getDisplayName() {
        return (_displayName);
    }

    public void setDisplayName(String displayName) {
        _displayName = displayName;
    }

    public int getOffset() {
        return (_offset);
    }

    public CellRenderer getRenderer() {
        return (_renderer);
    }

    public void setRenderer(CellRenderer renderer) {
        _renderer = renderer;
    }

    public boolean isHidden() {
        return _hidden;
    }

    public void setHidden(boolean hidden) {
        _hidden = hidden;
    }

    public boolean isVisible() {
        return !isHidden();
    }
}
