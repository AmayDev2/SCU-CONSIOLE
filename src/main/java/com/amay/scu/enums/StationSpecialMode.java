package com.amay.scu.enums;

public enum StationSpecialMode {
    EMERGENCY("-fx-background-color: red;"),
    NORMAL("-fx-background-color: green;"),
    TIME_OVERRIDE("-fx-background-color: yellow;"),
    ENTRY_EXIT_OVERRIDE("-fx-background-color: yellow;"),
    EXCESS_FARE_OVERRIDE("-fx-background-color: yellow;"),
    FARE_BYPASS_MODE_1("-fx-background-color: yellow;"),
    FARE_BYPASS_MODE_2("-fx-background-color: yellow;"),
    STATION_CLOSED("-fx-background-color: yellow;"),
    HIGH_SECURITY_MODE("-fx-background-color: yellow;");

    private final String color;

    StationSpecialMode(String color) {
        this.color = color;
    }


    public String getColor() {
        return color;
    }
}
