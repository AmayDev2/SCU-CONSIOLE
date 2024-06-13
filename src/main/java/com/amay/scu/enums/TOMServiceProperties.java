package com.amay.scu.enums;

public interface TOMServiceProperties {
    boolean isQRSaleModeEnabled();
    boolean isCardProcessingModeEnabled();
    void updateQRSaleMode(boolean status);
    void updateCardProcessingMode(boolean status);

}

