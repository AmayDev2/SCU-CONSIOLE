package com.amay.scu.enums;

public enum EquipmentType {

    TOM("TOM","01"),
    EFO("EFO","02"),
    TVM("TVM","03");



    private final String name;
    private final String code;
    EquipmentType(String name, String code) {
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
        for (EquipmentType type : EquipmentType.values()) {
            if (type.getName().equalsIgnoreCase(stringValue) || type.getCode().equalsIgnoreCase(stringValue)) {
                return type.getName();
            }
        }
        return "UNKNOWN"; // or throw an exception if not found
    }
}
