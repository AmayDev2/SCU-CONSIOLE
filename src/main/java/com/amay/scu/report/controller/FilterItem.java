package com.amay.scu.report.controller;

import javafx.beans.property.*;

public class FilterItem {
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty input = new SimpleStringProperty("");
    private final StringProperty titleValue = new SimpleStringProperty("");
    private final IntegerProperty index = new SimpleIntegerProperty(-1);


    public FilterItem(String title, String titleValue,int index) {
        this.title.set(title);
        this.titleValue.set(titleValue);
        this.index.set(index);
    }

    public BooleanProperty selectedProperty() { return selected; }
    public StringProperty titleProperty() { return title; }
    public StringProperty inputProperty() { return input; }
    public StringProperty titleValueProperty() { return titleValue; }
    public IntegerProperty indexProperty() { return index; }
}
