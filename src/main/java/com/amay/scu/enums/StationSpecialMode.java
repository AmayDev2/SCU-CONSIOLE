package com.amay.scu.enums;

import org.network.monitorandcontrol.SpecialMode;

public enum StationSpecialMode {
    EMERGENCY("-fx-background-color: red;", SpecialMode.EMERGENCY),
//    NORMAL("-fx-background-color: green;", SpecialMode.N),
    TIME_OVERRIDE("-fx-background-color: yellow;", SpecialMode.TIME_OVERRIDE),
    ENTRY_EXIT_OVERRIDE("-fx-background-color: yellow;", SpecialMode.ENTRY_EXIT_OVERRIDE),
    EXCESS_FARE_OVERRIDE("-fx-background-color: yellow;", SpecialMode.EXCESS_FARE_OVERRIDE),
    FARE_BYPASS_MODE_1("-fx-background-color: yellow;", SpecialMode.FARE_BYPASS_MODE_1),
    FARE_BYPASS_MODE_2("-fx-background-color: yellow;", SpecialMode.FARE_BYPASS_MODE_2),
    STATION_CLOSED("-fx-background-color: yellow;", SpecialMode.STATION_CLOSED_MODE),
//    HIGH_SECURITY_MODE("-fx-background-color: yellow;", SpecialMode.H)
        ;

    private final String color;
    private final SpecialMode specialMode;

    StationSpecialMode(String color,SpecialMode specialMode) {
        this.color = color;
        this.specialMode = specialMode;
    }

    public SpecialMode getGrpcMode() {
        return specialMode;
    }

    public String getColor() {
        return color;
    }
}
