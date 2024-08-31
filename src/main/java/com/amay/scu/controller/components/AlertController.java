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




    public AlertController(String name, String alertDescription, String alertTime, int priority) {
        this.name = name;
        this.alertDescription = alertDescription;
        this.timeOfAlert = alertTime;
        this.priority = priority>=500?"status-indicator-red":priority>=400?"status-indicator":"status-indicator-green";
    }


    @FXML
    private void initialize() {
        device.setText(name);
        status.setText(alertDescription);
        time.setText(timeOfAlert);
        iconIndicator.getStyleClass().add(this.priority);
    }
}
