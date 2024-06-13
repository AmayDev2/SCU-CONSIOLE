package com.amay.scu.enums;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TOMOperationModeTest {


    @Test
    public void test(){
        TOMOperationMode mode = TOMOperationMode.IN_SERVICE;
//
//        System.out.println("IN_SERVICE QRSaleMode: " + mode.isQRSaleModeEnabled());
//        System.out.println("IN_SERVICE CardProcessingMode: " + mode.isCardProcessingModeEnabled());
//        System.out.println("IN_SERVICE Color: " + mode.getColor());

        try {
            mode = TOMOperationMode.OUT_OF_SERVICE;
            System.out.println("OUT_OF_SERVICE QRSaleMode: " + mode.isQRSaleModeEnabled());
            mode.updateCardProcessingMode(true);

        } catch (UnsupportedOperationException e) {
            System.out.println(e.getMessage());
        }
//
//        try {
//            System.out.println("OUT_OF_SERVICE CardProcessingMode: " + mode.isCardProcessingModeEnabled());
//        } catch (UnsupportedOperationException e) {
//            System.out.println(e.getMessage());
//        }

        System.out.println("OUT_OF_SERVICE Color: " + mode.getColor());
    }

}