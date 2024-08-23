package com.amay.scu.controller.components;

import com.amay.scu.popup.PopupContent;
import com.amay.scu.popup.SleCommandInfo;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class AGWidgetsView {

    private final PopupContent popupContent;
    @FXML
    private  Button cancelButton;

    public AGWidgetsView(PopupContent popupContent, SleCommandInfo sleCommandInfo) {

        this.popupContent = popupContent;
    }
    @FXML
    void initialize() {
        // TODO
        cancelButton.setOnAction(event -> {
            popupContent.Close();
        });
    }


}
