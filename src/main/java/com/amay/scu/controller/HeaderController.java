package com.amay.scu.controller;

import com.amay.scu.contservice.HeaderListener;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HeaderController {

    private Logger logger = LoggerFactory.getLogger(HeaderController.class);
    @FXML
    private Text menuNavigator;

    @FXML
    private Text monitorNavigator;

    @FXML
    private Text reportNavigator;

    private HeaderListener scuHeaderListener= null;

    @FXML
    void initialize() {
        // initialize logic

    }

    public void setListener(HeaderListener scuHeaderListener){
        this.scuHeaderListener = scuHeaderListener;
    }

    @FXML
    void onMenuClick(MouseEvent event) {

        scuHeaderListener.onMenuClick();
    }

    @FXML
    void onMonitorClick(MouseEvent event) {
        scuHeaderListener.onMonitorClick();

    }

    @FXML
    void onReportClick(MouseEvent event) {
        scuHeaderListener.onReportClick();

    }

    // For testing without a MouseEvent
    public void onMenuClick() {
        System.out.println("Menu clicked (no event)");
    }
}
