package com.amay.scu.controller;

import com.amay.scu.popup.PopupContent;
import com.amay.scu.popup.SleCommandInfo;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveSLE;
import com.amay.scu.sleobj.LiveTOM;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import org.network.monitorandcontrol.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommandController {

    @FXML
    private MenuButton commandOption;
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

    private PopupContent popupContent =null;
    private String id;
//    private LiveAG liveAG;

    CommandType command;

    public void setInitialData(PopupContent popupContent, SleCommandInfo SleCommandInfo) {
        this.popupContent=popupContent;

        // Check if liveSLE is an instance of LiveAG before casting
            name.setText(SleCommandInfo.getEquipId());
            ip.setText(SleCommandInfo.getEquipIp());
            id=SleCommandInfo.getEquipId();

    }

    @FXML
    void initialize() {
        String[] commands = {CommandType.GET_DEVICE_INFO.name(),CommandType.MODE_CONTROL.name(),CommandType.GET_DIVICE_VERSIONS.name(),CommandType.GET_PERIPHERAL_STATUS.name()};

        // Add items to MenuButton
        for (String command : commands) {
            MenuItem menuItem = new MenuItem(command);
            menuItem.setOnAction(event -> handleMenuAction(command));
            commandOption.getItems().add(menuItem);
        }

    }

    private void handleMenuAction(String command) {
        this.command= CommandType.valueOf(command);
    }

    @FXML
    void cancelCommand(ActionEvent event) {
        this.popupContent.Close();
        logger.debug("Popup closed");

    }

    public void applyCommand(ActionEvent actionEvent) {
        logger.debug("given command : {} {}",id,command);
        popupContent.sendCommand(id,command);

    }
}
