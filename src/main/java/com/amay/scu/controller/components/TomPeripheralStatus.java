package com.amay.scu.controller.components;

import javafx.fxml.FXML;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TomPeripheralStatus {

    @FXML
    private Rectangle iconIndicatorSCU;
    @FXML
    private Rectangle iconIndicatorCCU;
    @FXML
    private Rectangle iconIndicatorReader;
    @FXML
    private Rectangle iconIndicatorScanner;
    @FXML
    private Rectangle iconIndicatorPrinter;
    @FXML
    private Rectangle iconIndicatorPDU;
    @FXML
    private Rectangle iconIndicatorCashDrawer;
    @FXML
    private Rectangle iconIndicatorUPS;

    public void initialize() {
        // Sample connection statuses
        boolean scu_connected = true;
        boolean ccu_connected = false;
        boolean reader_connected = true;
        boolean scanner_connected = false;
        boolean printer_connected = true;
        boolean pdu_connected = true;
        boolean cash_drawer_connected = false;
        boolean ups_connected = true;

        // Set the status indicators based on connection statuses
        setIndicatorColor(iconIndicatorSCU, scu_connected);
        setIndicatorColor(iconIndicatorCCU, ccu_connected);
        setIndicatorColor(iconIndicatorReader, reader_connected);
        setIndicatorColor(iconIndicatorScanner, scanner_connected);
        setIndicatorColor(iconIndicatorPrinter, printer_connected);
        setIndicatorColor(iconIndicatorPDU, pdu_connected);
        setIndicatorColor(iconIndicatorCashDrawer, cash_drawer_connected);
        setIndicatorColor(iconIndicatorUPS, ups_connected);
    }

    private void setIndicatorColor(Rectangle indicator, boolean isConnected) {
        if (isConnected) {
            indicator.setFill(Color.GREEN);
        } else {
            indicator.setFill(Color.RED);
        }
    }
}

