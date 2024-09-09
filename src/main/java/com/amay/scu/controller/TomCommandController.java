package com.amay.scu.controller;

import com.amay.scu.popup.PopupContent;
import com.amay.scu.popup.SleCommandInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.OperationMode;
import org.network.monitorandcontrol.SpecialMode;
import org.network.monitorandcontrol.tom.TOMModeControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TomCommandController {

    @FXML
    private  Button inService;
    @FXML
    private  Button outOfService;
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

    private TOMModeControl.Builder tomModeControlBuilder;
    ToggleGroup globleGroup =null;


    public TomCommandController(PopupContent popupContent, SleCommandInfo sleCommandInfo) {
        this.popupContent=popupContent;
        this.sleCommandInfo=sleCommandInfo;
        globleGroup = new ToggleGroup();
        this.id=sleCommandInfo.getEquipId();
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
        CardProcessingClose.setToggleGroup(globleGroup);
        CardProcessingOpen.setToggleGroup(globleGroup);

        // ToggleGroup for QR Sale Mode
        ToggleGroup qrSaleModeGroup = new ToggleGroup();
        QRSaleOpen.setToggleGroup(qrSaleModeGroup);
        QRSaleClose.setToggleGroup(qrSaleModeGroup);
        QRSaleClose.setToggleGroup(globleGroup);
        QRSaleOpen.setToggleGroup(globleGroup);

        // ToggleGroup for Test Mode
        ToggleGroup testModeGroup = new ToggleGroup();
        TestOpen.setToggleGroup(testModeGroup);
        TestClose.setToggleGroup(testModeGroup);
        TestClose.setToggleGroup(globleGroup);
        TestOpen.setToggleGroup(globleGroup);



        this.setCommand();
    }

    private void setCommand() {
        ParameterVersionOn.setToggleGroup(globleGroup);
        DeviceStatusOn.setToggleGroup(globleGroup);
        RegisterDataOn.setToggleGroup(globleGroup);
        ShiftEndOn.setToggleGroup(globleGroup);
        ReStartOn.setToggleGroup(globleGroup);
        ShutdownOn.setToggleGroup(globleGroup);
        SoftwareVersionOn.setToggleGroup(globleGroup);

        inService.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            tomModeControlBuilder=TOMModeControl.newBuilder().setOperationMode(OperationMode.IN_SERVICE);
            logger.info("In Service");
        });

        outOfService.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            tomModeControlBuilder=TOMModeControl.newBuilder().setOperationMode(OperationMode.OUT_OF_SERVICE);
            logger.info("Out Of Service");
        });


        ParameterVersionOn.setOnAction(event -> {
            System.out.println("ParameterVersionOn");
            this.command=CommandType.GET_PERIPHERAL_STATUS;
            tomModeControlBuilder=TOMModeControl.newBuilder();
        });
        SoftwareVersionOn.setOnAction(event -> {
            this.command=CommandType.GET_DIVICE_VERSIONS;
            tomModeControlBuilder=TOMModeControl.newBuilder();
        });

        DeviceStatusOn.setOnAction(event -> {
            this.command=CommandType.GET_DEVICE_INFO;
            tomModeControlBuilder=TOMModeControl.newBuilder();
        });

        RegisterDataOn.setOnAction(event -> {
            this.command=CommandType.GET_DEVICE_INFO;
            tomModeControlBuilder=TOMModeControl.newBuilder();
        });

        ShiftEndOn.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            tomModeControlBuilder=TOMModeControl.newBuilder().setSpecialMode(SpecialMode.SHIFT_END);
        });

        ReStartOn.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            tomModeControlBuilder=TOMModeControl.newBuilder().setSpecialMode(SpecialMode.RESTART);
        });

        ShutdownOn.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            tomModeControlBuilder=TOMModeControl.newBuilder().setSpecialMode(SpecialMode.SHUT_DOWN);
        });

        CardProcessingOpen.setOnAction(event -> {
            if(CardProcessingOpen.isSelected()){
            this.command=CommandType.MODE_CONTROL;
            tomModeControlBuilder=TOMModeControl.newBuilder().setCardProcessMode(true);}
        });

        CardProcessingClose.setOnAction(event -> {
            if(CardProcessingClose.isSelected()){
                this.command=CommandType.MODE_CONTROL;
                tomModeControlBuilder=TOMModeControl.newBuilder().setCardProcessMode(false);}
        });


        QRSaleOpen.setOnAction(event -> {
            if(QRSaleOpen.isSelected()){
                this.command=CommandType.MODE_CONTROL;
                tomModeControlBuilder=TOMModeControl.newBuilder().setQrSaleMode(true);}
        });

        QRSaleClose.setOnAction(event -> {
            if(QRSaleClose.isSelected()){
                this.command=CommandType.MODE_CONTROL;
                tomModeControlBuilder=TOMModeControl.newBuilder().setQrSaleMode(false);}
        });

        TestOpen.setOnAction(event -> {
            if(TestOpen.isSelected()){
                this.command=CommandType.MODE_CONTROL;
                tomModeControlBuilder=TOMModeControl.newBuilder().setOperationMode(OperationMode.TEST);}
        });

        TestClose.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
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
        TOMModeControl tomModeControl=tomModeControlBuilder.build();
        saveIntoQueue(command,tomModeControl);
        popupContent.sendCommand(id,command,tomModeControl);

    }

    private void saveIntoQueue(CommandType command, TOMModeControl tomModeControl) {
        switch (command){
            case MODE_CONTROL:
                logger.info("Command : {} {}",command,tomModeControl.getOperationMode());
                this.sleCommandInfo.addCommand(tomModeControl.getOperationMode().name());
                break;
            case GET_DEVICE_INFO:
                logger.info("Command : {} {}",command);
                this.sleCommandInfo.addCommand(command.name());
                break;
            case GET_DIVICE_VERSIONS:
                logger.info("Command : {} {}",command);
                break;
            case GET_PERIPHERAL_STATUS:
                logger.info("Command : {} {}",command);
                break;
        }
    }
}
