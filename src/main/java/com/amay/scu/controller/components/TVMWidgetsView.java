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
import org.network.monitorandcontrol.tvm.TVMModeControl;

public class TVMWidgetsView {

    @FXML
    private RadioButton radioButtonInService;
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

    @FXML private Label headerEquipmentId;;
    @FXML private Label lastTransaction;
    @FXML private ListView<String> alarmsListView;
    @FXML private ListView<String> commandListView;

    @FXML private Button cancelButton;

    private final PopupContent popupContent;
    private final SleCommandInfo sleCommandInfo;
    private final String equipmentId;
    private final ToggleGroup globeGroup;
    private CommandType command;
    private TVMModeControl.Builder tomModeControlBuilder;

    public TVMWidgetsView(PopupContent popupContent,
                            SleCommandInfo sleCommandInfo) {
        this.popupContent = popupContent;
        this.sleCommandInfo = sleCommandInfo;
        this.equipmentId = sleCommandInfo.getEquipId();
        this.globeGroup = new ToggleGroup();
    }

    private void applyCommand(CommandType command, TVMModeControl.Builder tomModeControlBuilder) {
        TVMModeControl tomModeControl=tomModeControlBuilder.build();
        this.saveIntoQueue(command,tomModeControl); // to show in widget
        popupContent.sendCommand(this.equipmentId,command,tomModeControl);

    }

    private void saveIntoQueue(CommandType command,TVMModeControl tomModeControl) {
        switch (command){
            case MODE_CONTROL:
                this.sleCommandInfo.addCommand(tomModeControl.getOperationMode().name());
                break;
            case GET_DEVICE_INFO:
                this.sleCommandInfo.addCommand(command.name());
                break;
        }
    }

    @FXML
    void initialize() {
        headerEquipmentId.setText(headerEquipmentId.getText().split("-")[0] + " - " + equipmentId);
        cancelButton.setOnAction(event -> popupContent.Close());
        commandApplyButton.setOnAction(event -> {
            applyCommand(this.command,this.tomModeControlBuilder);
            event.consume();
        });


        radioButtonShutdown.setToggleGroup(globeGroup);
        radioButtonStart.setToggleGroup(globeGroup);
        radioButtonInService.setToggleGroup(globeGroup);
        radioButtonOutOfService.setToggleGroup(globeGroup);

        radioButtonInService.setOnAction(event -> {
            this.command= CommandType.MODE_CONTROL;
            tomModeControlBuilder= TVMModeControl.newBuilder()
                    .setQrSaleMode(true)
                    .setCardProcessMode(true)
                    .setOperationMode(OperationMode.IN_SERVICE);
        });

        radioButtonOutOfService.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            tomModeControlBuilder=TVMModeControl.newBuilder()
                    .setOperationMode(OperationMode.OUT_OF_SERVICE);
        });


        radioButtonStart.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            this.tomModeControlBuilder=TVMModeControl.newBuilder().setSpecialMode(SpecialMode.RESTART);
        });

        radioButtonShutdown.setOnAction(event -> {
            this.command=CommandType.MODE_CONTROL;
            this.tomModeControlBuilder=TVMModeControl.newBuilder().setSpecialMode(SpecialMode.SHUT_DOWN);
        });


        commandListView.getItems().clear();
        commandListView.getItems().addAll(sleCommandInfo.getAllCommands().split("\n"));

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
