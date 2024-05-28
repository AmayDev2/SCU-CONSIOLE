package com.amay.scu.controller;

import com.amay.scu.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;


public class SCUController {
    @FXML
    private BorderPane borderPane;
    @FXML
    private Label welcomeText;

    @FXML
    public void onSCUButtonClick(ActionEvent actionEvent) {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @FXML
    void initialize() {

    }

    @FXML
    private  void onConnect(ActionEvent actionEvent) {
    }

    @FXML
    private void onDisconnect(ActionEvent actionEvent) {
    }

    @FXML
    private void onSend(ActionEvent actionEvent) {
    }
}