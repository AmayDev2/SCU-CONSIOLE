package com.amay.scu.enums;

public enum AGOperationMode implements OperationMode, AGInServiceProperties {
    IN_SERVICE(true, true, true, true, "-fx-background-color: #00FF00"),  // Green
    OUT_OF_SERVICE("-fx-background-color: #FF0000"),                      // Red
    DEFICIENT("-fx-background-color: #FFA500"),                           // Orange
    MAINTENANCE("-fx-background-color: #FFFF00"),                         // Yellow
    TEST("-fx-background-color: #0000FF"),                                // Blue
    PAUSE("-fx-background-color: #808080"),                               // Grey
    DISCONNECTED("-fx-background-color: #800080"),                        // Purple
    OTHER("-fx-background-color: #FBA500");                               // Another Orange

    private final boolean cardFareModeEnabled;
    private final boolean directionModeEnabled;
    private final boolean doorModeEnabled;
    private final boolean qrFareModeEnabled;
    private final String color;

    // Constructor for modes other than IN_SERVICE
    AGOperationMode(String color) {
        this.cardFareModeEnabled = false;
        this.directionModeEnabled = false;
        this.doorModeEnabled = false;
        this.qrFareModeEnabled = false;
        this.color = color;
    }

    // Constructor for IN_SERVICE mode
    AGOperationMode(boolean cardFareModeEnabled, boolean directionModeEnabled, boolean doorModeEnabled, boolean qrFareModeEnabled, String color) {
        this.cardFareModeEnabled = cardFareModeEnabled;
        this.directionModeEnabled = directionModeEnabled;
        this.doorModeEnabled = doorModeEnabled;
        this.qrFareModeEnabled = qrFareModeEnabled;
        this.color = color;
    }

    // Implementation of interface methods for IN_SERVICE specific properties
    @Override
    public boolean isCardFareModeEnabled() {
        if (this == IN_SERVICE) {
            return cardFareModeEnabled;
        }
        throw new UnsupportedOperationException("CardFareMode is not applicable for " + this.name());
    }

    @Override
    public boolean isDirectionModeEnabled() {
        if (this == IN_SERVICE) {
            return directionModeEnabled;
        }
        throw new UnsupportedOperationException("DirectionMode is not applicable for " + this.name());
    }

    @Override
    public boolean isDoorModeEnabled() {
        if (this == IN_SERVICE) {
            return doorModeEnabled;
        }
        throw new UnsupportedOperationException("DoorMode is not applicable for " + this.name());
    }

    @Override
    public boolean isQRFareModeEnabled() {
        if (this == IN_SERVICE) {
            return qrFareModeEnabled;
        }
        throw new UnsupportedOperationException("QRFareMode is not applicable for " + this.name());
    }

    // Implementation of interface method for color
    @Override
    public String getColor() {
        return color;
    }

//    public static void main(String[] args) {
//        // Example usage
//        AGOperationMode mode = AGOperationMode.IN_SERVICE;
//        System.out.println("IN_SERVICE CardFareMode: " + mode.isCardFareModeEnabled());
//        System.out.println("IN_SERVICE DirectionMode: " + mode.isDirectionModeEnabled());
//        System.out.println("IN_SERVICE DoorMode: " + mode.isDoorModeEnabled());
//        System.out.println("IN_SERVICE QRFareMode: " + mode.isQRFareModeEnabled());
//        System.out.println("IN_SERVICE Color: " + mode.getColor());
//
//        try {
//            mode = AGOperationMode.OUT_OF_SERVICE;
//            System.out.println("OUT_OF_SERVICE CardFareMode: " + mode.isCardFareModeEnabled());
//        } catch (UnsupportedOperationException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            System.out.println("OUT_OF_SERVICE DirectionMode: " + mode.isDirectionModeEnabled());
//        } catch (UnsupportedOperationException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            System.out.println("OUT_OF_SERVICE DoorMode: " + mode.isDoorModeEnabled());
//        } catch (UnsupportedOperationException e) {
//            System.out.println(e.getMessage());
//        }
//
//        try {
//            System.out.println("OUT_OF_SERVICE QRFareMode: " + mode.isQRFareModeEnabled());
//        } catch (UnsupportedOperationException e) {
//            System.out.println(e.getMessage());
//        }
//
//        System.out.println("OUT_OF_SERVICE Color: " + mode.getColor());
//    }
}
