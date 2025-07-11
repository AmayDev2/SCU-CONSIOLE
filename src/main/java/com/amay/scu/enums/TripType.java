package com.amay.scu.enums;

public enum TripType {


    SJT("SJT","01"),
    RJT("RJT","02"),
    GROUP("GROUP","03"),
    PAID("PAID","05"),
    TEST("TEST","06"),
    FREE("FREE","04");

    private final String name;
    private final String code;
    TripType(String name, String code) {
        this.name = name;
        this.code = code;
    }
    public String getName() {
        return name;
    }
    public String getCode() {
        return code;
    }

    public String getType(String stringValue) {
        for (TripType tripType : TripType.values()) {
            if (tripType.getName().equalsIgnoreCase(stringValue) || tripType.getCode().equalsIgnoreCase(stringValue)) {
                return tripType.getName();
            }
        }
        return null;
    }
}
