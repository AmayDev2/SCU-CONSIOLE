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

public class AGCommandController {
    @FXML
    private RadioButton CardFareOpen;
    @FXML
    private RadioButton CardFareClose;
    @FXML
    private RadioButton QRFareOpen;
    @FXML
    private RadioButton QRFareClose;
    @FXML
    private  RadioButton DoorNormalOpen;
    @FXML
    private  RadioButton DoorNormalClose;
    @FXML
    private  RadioButton entryMode;
    @FXML
    private  RadioButton biDirectional;
    @FXML
    private  RadioButton exitMode;
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

    private org.network.monitorandcontrol.CommandType command;

    private SleCommandInfo sleCommandInfo;


    public AGCommandController(PopupContent popupContent, SleCommandInfo sleCommandInfo) {
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
        CardFareOpen.setToggleGroup(cardProcessingModeGroup);
        CardFareClose.setToggleGroup(cardProcessingModeGroup);

        // ToggleGroup for QR Sale Mode
        ToggleGroup qrSaleModeGroup = new ToggleGroup();
        QRFareOpen.setToggleGroup(qrSaleModeGroup);
        QRFareClose.setToggleGroup(qrSaleModeGroup);

        // ToggleGroup for Test Mode
        ToggleGroup testModeGroup = new ToggleGroup();
        TestOpen.setToggleGroup(testModeGroup);
        TestClose.setToggleGroup(testModeGroup);

        ToggleGroup doorDirectionGroup = new ToggleGroup();
        entryMode.setToggleGroup(doorDirectionGroup);
        exitMode.setToggleGroup(doorDirectionGroup);
        biDirectional.setToggleGroup(doorDirectionGroup);


        ToggleGroup doorNormalGroup = new ToggleGroup();
        DoorNormalOpen.setToggleGroup(doorNormalGroup);
        DoorNormalClose.setToggleGroup(doorNormalGroup);

        this.setCommands();
    }

    private void setCommands() {
        ParameterVersionOn.setOnAction(event -> {
            this.command= CommandType.GET_PERIPHERAL_STATUS;
        });

        SoftwareVersionOn.setOnAction(event -> {
            this.command= CommandType.GET_DIVICE_VERSIONS;
        });

        DeviceStatusOn.setOnAction(event -> {
            this.command= CommandType.GET_DEVICE_INFO;
        });

        ShiftEndOn.setOnAction(event -> {
            this.command= CommandType.MODE_CONTROL;
        });


    }

    private void handleMenuAction(String command) {
        this.command= org.network.monitorandcontrol.CommandType.valueOf(command);
    }

    @FXML
    void cancelCommand(ActionEvent event) {
        this.popupContent.Close();
        logger.debug("Popup closed");

    }

    public void applyCommand(ActionEvent actionEvent) {
        logger.debug("given command : {} {}",id,command);

        popupContent.sendCommand(id,command,null);

    }
}
