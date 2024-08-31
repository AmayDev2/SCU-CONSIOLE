package com.amay.scu.controller;

import com.amay.scu.auth.functional.Authentication;
import com.amay.scu.popup.PopupWindow;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginController {
    @FXML
    private Text warning;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private Button loginButton;
    @FXML
    private Button cancelButton;

    /*************************************************************/
    private Authentication authentication;
    private PopupWindow popupWindow;


    public LoginController(Authentication authentication, PopupWindow popupWindow) {
        System.out.println("LoginController: constructor");
        this.authentication = authentication;
        this.popupWindow = popupWindow;
    }

    private void login(String username, String password) {
         this.authentication.execute(username, password);
            warning.setVisible(true);
    }

    @FXML
    void initialize() {
        System.out.println("LoginController: initialize");
        warning.setVisible(false);
        loginButton.setOnAction(e -> login(usernameField.getText().trim(), passwordField.getText().trim()));
        cancelButton.setOnAction(e -> popupWindow.Close());
    }

}
