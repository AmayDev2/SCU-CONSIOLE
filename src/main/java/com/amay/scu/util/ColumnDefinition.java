package com.amay.scu.util;

import java.util.function.Function;

public class ColumnDefinition<T, R> {
    private final String header;
    private final Function<T, R> getter;

    public ColumnDefinition(String header, Function<T, R> getter) {
        this.header = header;
        this.getter = getter;
    }

    public String header() {
        return header;
    }

    public Function<T, R> getter() {
        return getter;
    }
}
