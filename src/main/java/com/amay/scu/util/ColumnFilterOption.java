package com.amay.scu.util;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ColumnFilterOption {
    private final String columnName;
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final StringProperty filterText = new SimpleStringProperty("");

    public ColumnFilterOption(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnName() {
        return columnName;
    }

    public BooleanProperty selectedProperty() {
        return selected;
    }

    public StringProperty filterTextProperty() {
        return filterText;
    }

    public boolean isSelected() {
        return selected.get();
    }

    public String getFilterText() {
        return filterText.get();
    }

    @Override
    public String toString() {
        return columnName;
    }
}
