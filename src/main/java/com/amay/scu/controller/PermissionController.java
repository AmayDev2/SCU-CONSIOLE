package com.amay.scu.controller;

import com.amay.scu.auth.functional.Authentication;
import com.amay.scu.auth.functional.Operation;
import com.amay.scu.popup.PopupWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class PermissionController {
    @FXML
    private Label warningMessage;
    @FXML
    private Button logoutButton;
    @FXML
    private  Button backButton;

    private Operation operation;
    private PopupWindow popupWindow;
    private String warningMessageData;


    public PermissionController(Operation authentication, PopupWindow popupWindow,String warningMessage) {
        this.operation = authentication;
        this.popupWindow = popupWindow;
        this.warningMessageData = warningMessage;

    }

    @FXML
    void  initialize() {
        this.warningMessage.setText(warningMessageData);
        logoutButton.setOnAction(actionEvent -> {
            this.operation.execute();
        });
        backButton.setOnAction(actionEvent -> {
            popupWindow.Close();
        });
    }


}
