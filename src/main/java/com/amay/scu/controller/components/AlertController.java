package com.amay.scu.controller.components;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlertController {
    @FXML
    private Rectangle iconIndicator;
    @FXML
    private Label device;
    @FXML
    private Label time;
    @FXML
    private Label status;

    private  String name;
    private String alertDescription;
    private String timeOfAlert;
    private String priority;




    public AlertController(String name, String alertDescription, String alertType,String priority) {
        this.name = name;
        this.alertDescription = alertDescription;
        this.timeOfAlert = alertType;
        this.priority = priority;
    }


    @FXML
    private void initialize() {
        device.setText(name);
        status.setText(alertDescription);
        time.setText(timeOfAlert);
        iconIndicator.getStyleClass().add(this.priority);
    }
}
