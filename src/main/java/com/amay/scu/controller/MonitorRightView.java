package com.amay.scu.controller;

import com.amay.scu.ViewFactory;
import com.amay.scu.command.CommandTest;
import com.amay.scu.controller.components.AlertController;
import com.amay.scu.enums.StationSpecialMode;
import com.amay.scu.service.ScuGrpcService;
import com.amay.scu.util.TimeUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.SpecialMode;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadPoolExecutor;

public class MonitorRightView {
    @FXML
    private Label qrSale;

    //*************************************************************

    @FXML
    private Label entryCountTotal;
    @FXML
    private Label exitCountTotal;
    @FXML
    private Label totalSale;
    @FXML
    private Label exitQRCount;
    @FXML
    private Label entryQRCount;
    @FXML
    private Label exitCSCCount;
    @FXML
    private Label entryCSCCount;

    //*************************************************************

    @FXML
    private ComboBox timeIntervalComboBox;
    @FXML
    private Button applyButton;
    @FXML
    private Button resetButton;
    @FXML
    private DatePicker customDatePicker;
    @FXML
    private VBox tomSection;
    @FXML
    private VBox agSection;
    @FXML
    private ToggleButton highSecurityMode;
    @FXML
    private ListView<Parent> alertsListView; // Parent type allows you to add any FXML component

    private ObservableList<Parent> alerts;

    @FXML
    private ToggleButton emergencyButton;

    @FXML
    private ToggleButton stationCloseButton;

    @FXML
    private ToggleButton excessFareOverrideButton;

    @FXML
    private ToggleButton fareBypassMode1Button;

    @FXML
    private ToggleButton fareBypassMode2Button;

    private  ToggleButton selectedCommand;

    @FXML
    private Button applyCommandButton;


    private void handleSelection(ToggleButton button) {
        // Handle the logic when a button is toggled
        if (button.isSelected()) {
            selectedCommand = button;
        } else {
            selectedCommand = null;
        }
    }

    void pickTime() {
        customDatePicker.setOnAction(event -> {
            timeIntervalComboBox.setValue(customDatePicker.getValue());
            System.out.println("Selected date: " + customDatePicker.getValue());
        });

    }


    @FXML
    private void initialize() {
        pickTime();
        alerts = alertsListView.getItems();

        updateRevenueContinue();
        updateRevenue();

        ToggleGroup commandGroup = new ToggleGroup();
        // Add each button to the ToggleGroup
        emergencyButton.setToggleGroup(commandGroup);
        emergencyButton.setText(StationSpecialMode.EMERGENCY.name());
        stationCloseButton.setToggleGroup(commandGroup);
        stationCloseButton.setText(StationSpecialMode.STATION_CLOSED.name());
        excessFareOverrideButton.setToggleGroup(commandGroup);
        excessFareOverrideButton.setText(StationSpecialMode.EXCESS_FARE_OVERRIDE.name());
        fareBypassMode1Button.setToggleGroup(commandGroup);
        fareBypassMode1Button.setText(StationSpecialMode.FARE_BYPASS_MODE_1.name());
        fareBypassMode2Button.setToggleGroup(commandGroup);
        fareBypassMode2Button.setText(StationSpecialMode.FARE_BYPASS_MODE_2.name());
        highSecurityMode.setToggleGroup(commandGroup);
        highSecurityMode.setText(StationSpecialMode.HIGH_SECURITY_MODE.name());

        // Add event handlers to each button
        emergencyButton.setOnAction(event -> handleSelection(emergencyButton));
        stationCloseButton.setOnAction(event -> handleSelection(stationCloseButton));
        excessFareOverrideButton.setOnAction(event -> handleSelection(excessFareOverrideButton));
        fareBypassMode1Button.setOnAction(event -> handleSelection(fareBypassMode1Button));
        fareBypassMode2Button.setOnAction(event -> handleSelection(fareBypassMode2Button));
        highSecurityMode.setOnAction(event -> handleSelection(highSecurityMode));
        //*************************

        // Dynamically create and add ToggleButtons to the TOM section
//        ToggleButton tomButton1 = new ToggleButton("TOM Command 1");
////        tomButton1.setMinWidth(200);
//        tomButton1.getStyleClass().add("select-button1");
////        tomButton1.getStyleClass().add("transparent-button");
//
//        ToggleButton tomButton2 = new ToggleButton("TOM Command 2");
//        tomButton2.setMinWidth(200);
//        tomButton2.getStyleClass().add("select-button");
//
//        tomSection.getChildren().addAll(tomButton1, tomButton2);
//
//        // Dynamically create and add ToggleButtons to the AG section
//        ToggleButton agButton1 = new ToggleButton("AG Command 1");
//        agButton1.setMinWidth(200);
//        agButton1.getStyleClass().add("select-button");
//
//        ToggleButton agButton2 = new ToggleButton("AG Command 2");
//        agButton2.setMinWidth(200);
//        agButton2.getStyleClass().add("select-button");
//
//        agSection.getChildren().addAll(agButton1, agButton2);


        //******************************





        applyCommandButton.setOnAction(event ->{

            if (selectedCommand != null) {
                System.out.println("Selected command: " + selectedCommand.getText());
                CommandTest.INSTANCE.sendStationCommand(CommandType.MODE_CONTROL, StationSpecialMode.valueOf(selectedCommand.getText()));
            } else {
                System.out.println("No command selected");
            }
        });

//            try {
//                FXMLLoader loader11 = ViewFactory.getAlert();
//                loader11.setControllerFactory(c -> new AlertController("RE-AG-033", "Alert description Alert description Alert description Alert description Alert description", "26 April 2024 18:28:39", "status-indicator"));
//                Parent alert111 = loader11.load();
//                alerts.add(alert111);
//
//            // Load the FXML for the alert item
//            FXMLLoader loader = ViewFactory.getAlert();
//            loader.setControllerFactory(c -> new AlertController("RE-AG-031", "Alert description Alert description Alert description Alert description Alert description","26 April 2024 18:28:39", "status-indicator-red"));
//            Parent alert1 = loader.load();
//            alerts.add(alert1);
//
//            FXMLLoader loader1 = ViewFactory.getAlert();
//            loader1.setControllerFactory(c -> new AlertController("RE-AG-032", "Alert description Alert description Alert description Alert description Alert description","26 April 2024 18:28:39", "status-indicator-green"));
//            Parent alert11 = loader1.load();
//            alerts.add(alert11);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    });
        }

    private void updateRevenueContinue() {
//
        // Create a timeline that updates the revenue every 5 seconds
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.minutes(1), event -> {
            updateRevenue();
        }));
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE); // Run indefinitely
        timeline.play(); // Start the timeline

    }

    private void updateRevenue() {
        Platform.runLater(() -> {
            System.out.println("Updating revenue"+this.hashCode());
            String qrRevenue= ScuGrpcService.INSTANCE.getTotalRevenue("01", TimeUtil.getCurrentDateInEpoch());
            qrSale.setText("₹"+qrRevenue.split("-")[0]+"/-");
            totalSale.setText("₹"+qrRevenue.split("-")[0]+"/-");

        });
    }
}
