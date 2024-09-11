package com.amay.scu.controller;

import com.amay.scu.ViewFactory;
import com.amay.scu.auth.AuthService;
import com.amay.scu.command.CommandTest;
import com.amay.scu.controller.components.AlertController;
import com.amay.scu.enums.StationSpecialMode;
import com.amay.scu.listenner.impl.MonitoringRightViewListener;
import com.amay.scu.model.Alerts;
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
import org.network.monitorandcontrol.RequestType;
import org.network.monitorandcontrol.SpecialMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

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

    private final Logger logger = LoggerFactory.getLogger(MonitorRightView.class);


    private MonitoringRightViewListener monitoringRightViewListener;

    private final AuthService authService;

    public  MonitorRightView(AuthService authService){
        this.authService=authService;

    }


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
        monitoringRightViewListener=MonitoringRightViewListener.initialize(this);
        pickTime();
        updateEntryExitCount();
        alerts = alertsListView.getItems();

//        updateRevenueContinue();
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
//        highSecurityMode.setToggleGroup(commandGroup);
//        highSecurityMode.setText(StationSpecialMode.HIGH_SECURITY_MODE.name());

        // Add event handlers to each button
        emergencyButton.setOnAction(event -> handleSelection(emergencyButton));
        stationCloseButton.setOnAction(event -> handleSelection(stationCloseButton));
        excessFareOverrideButton.setOnAction(event -> handleSelection(excessFareOverrideButton));
        fareBypassMode1Button.setOnAction(event -> handleSelection(fareBypassMode1Button));
        fareBypassMode2Button.setOnAction(event -> handleSelection(fareBypassMode2Button));
        highSecurityMode.setOnAction(event -> handleSelection(highSecurityMode));

        StationSpecialMode.StationSpecialModeListener listener = newMode -> {
            if( newMode.equals(StationSpecialMode.EMERGENCY ) || newMode.equals(StationSpecialMode.STATION_CLOSED)){
                logger.debug("Entering emergency mode");
                stationCloseButton.setDisable(true);
                excessFareOverrideButton.setDisable(true);
                fareBypassMode1Button.setDisable(true);
                fareBypassMode2Button.setDisable(true);
                highSecurityMode.setDisable(true);
                emergencyButton.setDisable(true);
            }else{
                stationCloseButton.setDisable(false);
                excessFareOverrideButton.setDisable(false);
                fareBypassMode1Button.setDisable(false);
                fareBypassMode2Button.setDisable(false);
                highSecurityMode.setDisable(false);
                emergencyButton.setDisable(false);
            }
            switch (newMode) {
                case EMERGENCY:
                    emergencyButton.setSelected(true);
                    emergencyButton.setDisable(false);
                    break;
                case STATION_CLOSED:
                    stationCloseButton.setSelected(true);
                    stationCloseButton.setDisable(false);
                    break;
                case EXCESS_FARE_OVERRIDE:
                    excessFareOverrideButton.setSelected(true);
                    break;
                case FARE_BYPASS_MODE_1:
                    fareBypassMode1Button.setSelected(true);
                    break;
                case FARE_BYPASS_MODE_2:
                    fareBypassMode2Button.setSelected(true);
                    break;
                case HIGH_SECURITY_MODE:
                    highSecurityMode.setSelected(true);
                    break;
                default:
                    emergencyButton.setSelected(false);
                    break;
            }

        };
        StationSpecialMode.addStationSpecialModeListener(listener);

        authService.isAuthenticated().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                // User is authenticated
                logger.info("User is authenticated");
                // Enable the emergency button
                applyCommandButton.setDisable(false);
            } else {
                // User is not authenticated
                logger.info("User is not authenticated");
                // Disable the emergency button
                applyCommandButton.setDisable(true);
            }
        });


        applyCommandButton.setOnAction(event ->{
            if (selectedCommand != null) {
                System.out.println("Selected command: " + selectedCommand.getText());
                CommandTest.INSTANCE.sendStationCommand(StationSpecialMode.valueOf(selectedCommand.getText()));
            } else {
                System.out.println("No command selected");
                CommandTest.INSTANCE.sendStationCommand(StationSpecialMode.STATION_NORMAL);
            }
        });
//        qrSale.onMouseClickedProperty().addListener((observable, oldValue, newValue) -> {
////            logger.info("qrSale clicked");
//            updateRevenue();
//        });
        updateRevenueContinue();
     }

    private void updateRevenueContinue() {
//
        // Create a timeline that updates the revenue every 5 seconds
        javafx.animation.Timeline timeline = new javafx.animation.Timeline(new javafx.animation.KeyFrame(javafx.util.Duration.minutes(5), event -> {
            updateRevenue();
        }));
        timeline.setCycleCount(javafx.animation.Animation.INDEFINITE); // Run indefinitely
        timeline.play(); // Start the timeline

    }

    private void updateRevenue() {
        Platform.runLater(() -> {
            System.out.println("Updating revenue"+this.hashCode());
            String qrRevenue= ScuGrpcService.INSTANCE.getTotalRevenue(TimeUtil.getCurrentDateInEpoch());
            qrSale.setText("₹"+qrRevenue.split("-")[0]+"/-");
            totalSale.setText("₹"+qrRevenue.split("-")[0]+"/-");

        });
    }

    private void updateEntryExitCount() {
        Platform.runLater(() -> {
            entryCountTotal.setText("100");
            exitCountTotal.setText("100");
            entryQRCount.setText("50");
            exitQRCount.setText("50");
            entryCSCCount.setText("50");
            exitCSCCount.setText("50");
        });
    }

    public void sendAlarm(Map<Integer,String> alarms, String equipId, String requestType) {
        for(Map.Entry<Integer, String> entry : alarms.entrySet()) {
            Alerts.addAlert(new Alerts.Alert(equipId,String.valueOf(entry.getKey()),entry.getValue(), TimeUtil.getAlertDate()));
            Platform.runLater(() -> {
                System.out.println("Updating alerts"+this.hashCode());
                try {
                    FXMLLoader loader = ViewFactory.getAlert();
                    loader.setControllerFactory(c -> new AlertController(requestType+"-"+equipId,"("+entry.getKey().toString()+") "+ entry.getValue(), TimeUtil.getAlertDate(),entry.getKey()));
                    Parent alert = loader.load();
                    alerts.add(alert);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        }

    }

//    public void setStationMode(StationSpecialMode mode) {
//
//        switch (mode) {
//            case EMERGENCY:
//                emergencyButton.setSelected(true);
//                break;
//            case STATION_CLOSED:
//                stationCloseButton.setSelected(true);
//                break;
//            case EXCESS_FARE_OVERRIDE:
//                excessFareOverrideButton.setSelected(true);
//                break;
//            case FARE_BYPASS_MODE_1:
//                fareBypassMode1Button.setSelected(true);
//                break;
//            case FARE_BYPASS_MODE_2:
//                fareBypassMode2Button.setSelected(true);
//                break;
//            case HIGH_SECURITY_MODE:
//                highSecurityMode.setSelected(true);
//                break;
//            default:
//                break;
//        }
//    }
}
