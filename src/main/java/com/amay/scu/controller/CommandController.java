package com.amay.scu.controller;

import com.amay.scu.popup.PopupContent;
import com.amay.scu.sleobj.LiveSLE;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandController {

    Logger logger = LoggerFactory.getLogger(CommandController.class);

    @FXML
    private Button apply;

    @FXML
    private Button cancel;

    @FXML
    private TextArea enteredCommand;

    @FXML
    private Text ip;

    @FXML
    private Text name;

    PopupContent popupContent =null;

    public void setInitialData(PopupContent popupContent, LiveSLE liveSLE) {
        this.popupContent=popupContent;

    }

    @FXML
    void initialize() {


    }

    @FXML
    void cancelCommand(ActionEvent event) {
        this.popupContent.Close();
        logger.debug("{}",enteredCommand.getText());

    }

    public void applyCommand(ActionEvent actionEvent) {
        logger.debug("{}",enteredCommand.getText());
    }
}
