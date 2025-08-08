package com.amay.scu.controller.components;

import com.amay.scu.model.Alerts;
import com.amay.scu.popup.PopupContent;
import com.amay.scu.popup.SleCommandInfo;
import com.amay.scu.service.ScuGrpcService;
import com.amay.scu.util.TimeUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.OperationMode;
import org.network.monitorandcontrol.SpecialMode;
import org.network.monitorandcontrol.ag.AGModeControl;
import org.network.monitorandcontrol.ag.AisleMode;

public class AGWidgetsViewV2 {


    @FXML private RadioButton radioButtonExitOnly;
    @FXML private RadioButton radioButtonEntryOnly;
    @FXML private RadioButton radioButtonBiDirectional;
    @FXML private RadioButton radioButtonNormalClose;
    @FXML private RadioButton radioButtonNormalOpen;
    @FXML private RadioButton radioButtonInService;
    @FXML private RadioButton radioButtonOutOfService;
    @FXML private Button commandApplyButton;
    @FXML private RadioButton radioButtonStart;
    @FXML private RadioButton radioButtonShutdown;




    @FXML private Label configversion;
    @FXML private Label userversion;
    @FXML private Label paraversion;
    @FXML private Label productversion;
    @FXML private Label swversion;
    @FXML private Label ipAddress;
    @FXML private Label devicename;
    @FXML private Label deviceip;
    @FXML private DatePicker datePicker;

    @FXML private Label headerEquipmentId;
    @FXML private Label lastTransaction;
    @FXML private ListView<String> alarmsListView;
    @FXML private ListView<String> commandListView;

    @FXML private Button cancelButton;

    private final PopupContent popupContent;
    private final SleCommandInfo sleCommandInfo;
    private final String equipmentId;
    private final ToggleGroup globeGroup;
    private CommandType command;
    private AGModeControl.Builder agModeControlBuilder;

    public AGWidgetsViewV2(PopupContent popupContent,
                            SleCommandInfo sleCommandInfo) {
        this.popupContent = popupContent;
        this.sleCommandInfo = sleCommandInfo;
        this.equipmentId = sleCommandInfo.getEquipId();
        this.globeGroup = new ToggleGroup();
    }

    private void applyCommand(CommandType command, AGModeControl.Builder agModeControlBuilder) {

        AGModeControl agModeControl=agModeControlBuilder.build();
        this.saveIntoQueue(command,agModeControl); // to show in widget
        popupContent.sendCommand(this.equipmentId,command,agModeControl);

    }

    private void saveIntoQueue(CommandType command, AGModeControl agModeControl) {
        switch (command){
            case MODE_CONTROL:
                this.sleCommandInfo.addCommand(agModeControl.getOperationMode().name());
                break;
            case GET_DEVICE_INFO:
                this.sleCommandInfo.addCommand(command.name());
                break;
        }
    }

    @FXML
    void initialize() {
        headerEquipmentId.setText(equipmentId);
        cancelButton.setOnAction(event -> popupContent.Close());
        commandApplyButton.setOnAction(event -> {
            if (this.command == null) {
                return;
            }
            applyCommand(this.command,this.agModeControlBuilder);
            event.consume();
        });


        radioButtonNormalOpen.setToggleGroup(globeGroup);
        radioButtonNormalClose.setToggleGroup(globeGroup);

        radioButtonBiDirectional.setToggleGroup(globeGroup);
        radioButtonEntryOnly.setToggleGroup(globeGroup);
        radioButtonExitOnly.setToggleGroup(globeGroup);

        radioButtonShutdown.setToggleGroup(globeGroup);
        radioButtonStart.setToggleGroup(globeGroup);
        radioButtonInService.setToggleGroup(globeGroup);
        radioButtonOutOfService.setToggleGroup(globeGroup);

        radioButtonInService.setOnAction(event -> {
            this.command= CommandType.MODE_CONTROL;
            agModeControlBuilder= AGModeControl.newBuilder()
                    .setSpecialMode(SpecialMode.STATION_NORMAL)
                    .setOperationMode(OperationMode.IN_SERVICE);
        });

        radioButtonOutOfService.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            agModeControlBuilder=AGModeControl.newBuilder()
                    .setSpecialMode(SpecialMode.STATION_NORMAL)
                    .setOperationMode(OperationMode.OUT_OF_SERVICE);
        });
        radioButtonBiDirectional.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            this.agModeControlBuilder=AGModeControl.newBuilder()
                    .setSpecialMode(SpecialMode.STATION_NORMAL)
                    .setAisleMode(AisleMode.BI_DIRECTIONAL);
        });

        radioButtonEntryOnly.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            this.agModeControlBuilder=AGModeControl.newBuilder()
                    .setSpecialMode(SpecialMode.STATION_NORMAL)
                    .setAisleMode(AisleMode.ENTRY);

        });

        radioButtonExitOnly.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            this.agModeControlBuilder=AGModeControl.newBuilder()
                    .setSpecialMode(SpecialMode.STATION_NORMAL)
                    .setAisleMode(AisleMode.EXIT);
        });

        radioButtonNormalOpen.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
        });
        radioButtonNormalOpen.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
        });

        radioButtonStart.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            this.agModeControlBuilder=AGModeControl.newBuilder().setSpecialMode(SpecialMode.RESTART);
        });

        radioButtonShutdown.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            this.agModeControlBuilder=AGModeControl.newBuilder().setSpecialMode(SpecialMode.SHUT_DOWN);
        });


        commandListView.getItems().clear();
//        commandListView.getItems().addAll(sleCommandInfo.getAllCommands().split("\n"));

        alarmsListView.getItems().clear();
        alarmsListView.getItems().addAll(Alerts.getAlertsByDeviceId(equipmentId));

    }



    private void updateStock() {
//        Platform.runLater(() -> {
//            String qrStock = ScuGrpcService.INSTANCE.getStockReport(equipmentId, "12345");
//            String[] parts = qrStock.split("-");
//            qrPaper.setText(parts[0]);
//            remainedQR.setText(String.valueOf(Integer.parseInt(parts[0]) - Integer.parseInt(parts[1])));
//            csc.setText(parts[2]);
//            remainedCSC.setText(String.valueOf(Integer.parseInt(parts[2]) - Integer.parseInt(parts[3])));
//        });
    }

    private void updateRevenue(String value) {
        String qrRevenue = ScuGrpcService.INSTANCE.getTotalRevenue(equipmentId, value);
        String[] parts = qrRevenue.split("-");
//        qrSale.setText(parts[0]);
//        cscSale.setText(parts[1]);
//        totalSale.setText(String.valueOf(Integer.parseInt(parts[0]) + Integer.parseInt(parts[1])));
        String lastTransactionTime = TimeUtil.epochMilliToFormattedSystemTime(parts[3], "dd MMM yyyy HH:mm");
        lastTransaction.setText("Last Transaction : " + lastTransactionTime);
    }

    private void updateVersion() {
        Platform.runLater(() -> ipAddress.setText("IP Address : " + sleCommandInfo.getEquipIp()));
    }

}
