package com.amay.scu.report.controller;

import com.amay.scu.enums.filters.FilterEnums;
import javafx.beans.property.*;

import java.util.List;

public class FilterItem {
    private final BooleanProperty selected = new SimpleBooleanProperty(false);
    private final StringProperty title = new SimpleStringProperty("");
    private final StringProperty input = new SimpleStringProperty("");
    private final StringProperty titleValue = new SimpleStringProperty("");
    private final IntegerProperty index = new SimpleIntegerProperty(-1);
    private final List<String> choices; // add this to the class


    public FilterItem(String title, String titleValue,int index,List<String> choices) {
        this.title.set(title);
        this.titleValue.set(titleValue);
        this.index.set(index);
        this.choices = choices; // initialize choices
    }

    public BooleanProperty selectedProperty() { return selected; }
    public StringProperty titleProperty() { return title; }
    public StringProperty inputProperty() { return input; }
    public StringProperty titleValueProperty() { return titleValue; }
    public IntegerProperty indexProperty() { return index; }
    public List<String> getChoices() {
        return choices;
    }
}
