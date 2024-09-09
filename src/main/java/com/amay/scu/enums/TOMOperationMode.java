package com.amay.scu.enums;

public enum TOMOperationMode implements OperationMode, TOMServiceProperties {
    IN_SERVICE(true, true, "-fx-background-color: #00FF00"),  // Green 100,110,111
    OUT_OF_SERVICE("-fx-background-color: #FF0000"),         // Red 200
    DEFICIENT("-fx-background-color: #FFA500"),              // Orange 300
    MAINTENANCE("-fx-background-color: #FFFF00"),            // Yellow 400
    TEST("-fx-background-color: #0000FF"),                   // Blue 500
    PAUSE("-fx-background-color: #808080"),                  // Grey 600
    DISCONNECTED("-fx-background-color: #800080"),           // Purple 700
    OTHER("-fx-background-color: #FBA500");                  // Another Orange 800

    private final String color;
    private boolean qrSaleModeEnabled;
    private boolean cardProcessingModeEnabled;

    // Constructor for modes other than IN_SERVICE
    TOMOperationMode(String color) {
        this.qrSaleModeEnabled = false;
        this.cardProcessingModeEnabled = false;
        this.color = color;
    }

    // Constructor for IN_SERVICE mode
    TOMOperationMode(boolean qrSaleModeEnabled, boolean cardProcessingModeEnabled, String color) {
        this.qrSaleModeEnabled = qrSaleModeEnabled;
        this.cardProcessingModeEnabled = cardProcessingModeEnabled;
        this.color = color;
    }

    // Implementation of interface methods for IN_SERVICE specific properties
    @Override
    public boolean isQRSaleModeEnabled() {
        if (this == IN_SERVICE) {
            return qrSaleModeEnabled;
        }
        throw new UnsupportedOperationException("QRSaleMode is not applicable for " + this.name());
    }

    @Override
    public boolean isCardProcessingModeEnabled() {
        if (this == IN_SERVICE) {
            return cardProcessingModeEnabled;
        }
        throw new UnsupportedOperationException("CardProcessingMode is not applicable for " + this.name());
    }

    @Override
    public void updateQRSaleMode(boolean status) {
        if (this != IN_SERVICE)
            throw new UnsupportedOperationException("QRSaleMode is not applicable for " + this.name());
        qrSaleModeEnabled = status;

    }

    @Override
    public void updateCardProcessingMode(boolean status) {
        if (this != IN_SERVICE)
            throw new UnsupportedOperationException("CardProcessingMode is not applicable for " + this.name());
        cardProcessingModeEnabled = status;

    }

    // Implementation of interface method for color
    @Override
    public String getColor() {
        return color;
    }

}
