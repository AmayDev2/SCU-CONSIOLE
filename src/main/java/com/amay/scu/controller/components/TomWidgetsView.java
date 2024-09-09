package com.amay.scu.controller.components;

import com.amay.scu.model.Alerts;
import com.amay.scu.popup.PopupContent;
import com.amay.scu.popup.SleCommandInfo;
import com.amay.scu.service.ScuGrpcService;
import com.amay.scu.util.TimeUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.time.Instant;
import java.time.LocalDate;

public class TomWidgetsView {

    /**********************************+++++++++++++++++^^^^^^^^^^^^^^^^^^^^^^^^^^^^++++++++++++++++++++**********************************/
    @FXML
    private Label configVersion;
    @FXML
    private Label userVersion;
    @FXML
    private Label parameterVersion;
    @FXML
    private Label productVersion;
    @FXML
    private Label blacklistVersion;
    @FXML
    private Label softwareVersion;
    @FXML
    private Label fareVersion;
    @FXML
    private Label ipAddress;
/**********************************+++++++++++++++++^^^^^^^^^^^^^^^^^^^^^^^^^^^^++++++++++++++++++++**********************************/

    @FXML
    private  Label headerTomId;
    @FXML
    private Label lastTransaction;
    @FXML
    private DatePicker datePicker;
    @FXML
    private ListView alarmsListView;
    @FXML
    private  ListView commandListView;
    @FXML
    private  Label qrSale;
    @FXML
    private  Label cscSale;
    @FXML
    private  Label totalSale;
    @FXML
    private  Label qrPaper;
    @FXML
    private  Label csc;
    @FXML
    private  Label remainedQR;
    @FXML
    private  Label remainedCSC;
    @FXML
    private  Button cancelButton;

    private final PopupContent popupContent;

    private String equipmentId;
    private final SleCommandInfo sleCommandInfo;

    public TomWidgetsView(PopupContent popupContent, SleCommandInfo sleCommandInfo) {
        this.equipmentId = sleCommandInfo.getEquipId();
        this.popupContent = popupContent;
        this.equipmentId = sleCommandInfo.getEquipId();
        this.sleCommandInfo = sleCommandInfo;
    }
    @FXML
    void initialize() {
        headerTomId.setText(headerTomId.getText().split("-")[0]+" - "+equipmentId);
        // TODO
        cancelButton.setOnAction(event -> {
            popupContent.Close();
        });

        datePicker.setOnAction(event -> {
            updateRevenue(TimeUtil.datePickerToEpoch(datePicker.getValue()));
            System.out.println("Date Picker Clicked");
        }
        );

       datePicker.setValue(java.time.LocalDate.now());
       updateRevenue(TimeUtil.datePickerToEpoch(datePicker.getValue()));
       updateStock();
       commandListView.getItems().clear();
       commandListView.getItems().addAll(this.sleCommandInfo.getAllCommands().split("\n"));
       alarmsListView.getItems().clear();
       alarmsListView.getItems().addAll(Alerts.getAlertsByDeviceId(equipmentId));

        updateVersion();

    }

    private void updateStock() {
        Platform.runLater(() -> {
            String qrStock=ScuGrpcService.INSTANCE.getStockReport(equipmentId, "12345");
            qrPaper.setText(qrStock.split("-")[0]);
            remainedQR.setText(String.valueOf(Integer.parseInt(qrStock.split("-")[0])-Integer.parseInt(qrStock.split("-")[1])));
            csc.setText(qrStock.split("-")[2]);
            remainedCSC.setText(String.valueOf(Integer.parseInt(qrStock.split("-")[2])-Integer.parseInt(qrStock.split("-")[3])));
        });
    }
    private void updateRevenue(String value) {
        System.out.println("Updating Revenue Tom :"+value+" : "+"01");
//        Platform.runLater(() -> {
            String qrRevenue=ScuGrpcService.INSTANCE.getTotalRevenue(equipmentId, value);
            qrSale.setText(qrRevenue.split("-")[0]);
            totalSale.setText(Integer.parseInt(qrRevenue.split("-")[0])+Integer.parseInt(qrRevenue.split("-")[1])+"");
            cscSale.setText(qrRevenue.split("-")[1]);
            String lastTransactionTime=TimeUtil.epochMilliToFormattedSystemTime(qrRevenue.split("-")[3], "dd MMM yyyy HH:mm");
            lastTransaction.setText(lastTransaction.getText().split(":")[0]+" : "+lastTransactionTime);

//        });
    }

    private void updateVersion(){
        Platform.runLater(() -> {
            ipAddress.setText(ipAddress.getText().split(":")[0]+" : "+this.sleCommandInfo.getEquipIp());
        });
    }
}
