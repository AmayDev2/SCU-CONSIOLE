package com.amay.scu.util;

import java.util.List;
import java.util.function.Function;

public class ColumnDefinition<T, R> {
    private final String header;
    private final Function<T, R> getter;
    // can add column width
    private List<ColumnDefinition<T, R>> subColumns;

    public ColumnDefinition(String header, Function<T, R> getter, List<ColumnDefinition<T, R>> subColumns) {
        this.header = header;
        this.getter = getter;
        this.subColumns = subColumns;
    }

    public String header() {
        return header;
    }

    public Function<T, R> getter() {
        return getter;
    }

    public List<ColumnDefinition<T, R>> subColumns() {
        return subColumns;
    }
}
