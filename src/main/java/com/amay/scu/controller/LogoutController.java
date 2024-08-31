package com.amay.scu.controller;

import com.amay.scu.auth.functional.Authentication;
import com.amay.scu.popup.PopupWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class LogoutController {
    public Label warningMessage;
    @FXML
    private Button logoutButton;
    @FXML
    private  Button backButton;

    private Authentication authentication;
    private PopupWindow popupWindow;


    public LogoutController(Authentication authentication, PopupWindow popupWindow) {
        this.authentication = authentication;
        this.popupWindow = popupWindow;
    }

    @FXML
    void  initialize() {
        logoutButton.setOnAction(actionEvent -> {
            this.authentication.execute("","");
        });
        backButton.setOnAction(actionEvent -> {
            popupWindow.Close();
        });
    }


}
