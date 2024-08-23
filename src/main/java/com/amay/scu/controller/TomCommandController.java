package com.amay.scu.controller;

import com.amay.scu.popup.PopupContent;
import com.amay.scu.popup.SleCommandInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.network.monitorandcontrol.CommandType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TomCommandController {

    @FXML
    private RadioButton ParameterVersionOn;
    @FXML
    private RadioButton SoftwareVersionOn;
    @FXML
    private RadioButton DeviceStatusOn;
    @FXML
    private RadioButton RegisterDataOn;
    @FXML
    private RadioButton ShiftEndOn;
    @FXML
    private RadioButton ReStartOn;
    @FXML
    private RadioButton ShutdownOn;





    @FXML
    private RadioButton CardProcessingOpen;

    @FXML
    private RadioButton CardProcessingClose;

    @FXML
    private RadioButton QRSaleOpen;

    @FXML
    private RadioButton QRSaleClose;

    @FXML
    private RadioButton TestOpen;

    @FXML
    private RadioButton TestClose;

    @FXML
    private MenuButton commandOption;
    Logger logger = LoggerFactory.getLogger(TomCommandController.class);

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

    private CommandType command;

    private SleCommandInfo sleCommandInfo;


    public TomCommandController(PopupContent popupContent, SleCommandInfo sleCommandInfo) {
        this.popupContent=popupContent;
        this.sleCommandInfo=sleCommandInfo;
    }



    @FXML
    void initialize() {
        // Check if liveSLE is an instance of LiveAG before casting
        name.setText(this.sleCommandInfo.getEquipId());
        ip.setText(this.sleCommandInfo.getEquipIp());
        id=this.sleCommandInfo.getEquipId();


        //setting toggle
        // ToggleGroup for Card Processing Mode
        ToggleGroup cardProcessingModeGroup = new ToggleGroup();
        CardProcessingOpen.setToggleGroup(cardProcessingModeGroup);
        CardProcessingClose.setToggleGroup(cardProcessingModeGroup);

        // ToggleGroup for QR Sale Mode
        ToggleGroup qrSaleModeGroup = new ToggleGroup();
        QRSaleOpen.setToggleGroup(qrSaleModeGroup);
        QRSaleClose.setToggleGroup(qrSaleModeGroup);

        // ToggleGroup for Test Mode
        ToggleGroup testModeGroup = new ToggleGroup();
        TestOpen.setToggleGroup(testModeGroup);
        TestClose.setToggleGroup(testModeGroup);

        this.setCommand();


    }

    private void setCommand() {
        ParameterVersionOn.setOnAction(event -> {
            System.out.println("ParameterVersionOn");
            this.command=CommandType.GET_DIVICE_VERSIONS;
        });

        DeviceStatusOn.setOnAction(event -> {
            this.command=CommandType.GET_PERIPHERAL_STATUS;
        });


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
