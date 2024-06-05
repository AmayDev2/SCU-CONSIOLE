package com.amay.scu.enums;
public enum SLEStatus {
    ONLINE("-fx-background-color: #00FF00"),
    OFFLINE("-fx-background-color: #FF0000"),
    PERIPHERAL_OFFLINE("-fx-background-color: #FFA500"),
    UNKNOWN("-fx-background-color: #FBA500");

    private final String status;

    SLEStatus(String status) {
        this.status = status;
    }

    public String getStatus() {

        return status;
    }
}
