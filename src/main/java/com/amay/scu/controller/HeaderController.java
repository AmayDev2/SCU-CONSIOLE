package com.amay.scu.controller;

import com.amay.scu.ViewFactory;
import com.amay.scu.auth.AuthService;
import com.amay.scu.auth.functional.Authentication;
import com.amay.scu.command.CommandTest;
import com.amay.scu.contservice.HeaderListener;
import com.amay.scu.enums.StationSpecialMode;
import com.amay.scu.popup.PopupWindow;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;


public class HeaderController {

    public static BooleanProperty isCCUConnected;
    @FXML
    private  Button ccuConnection;


    @FXML
    private Button emergencyButton;
    @FXML
    private Label userName;
    @FXML
    private Button userButton;

    private Logger logger = LoggerFactory.getLogger(HeaderController.class);
//    @FXML
//    private Button menuNavigator;

    @FXML
    private Button monitorNavigator;

    @FXML
    private Button reportNavigator;

    private HeaderListener scuHeaderListener= null;

    private AuthService authService;



    @FXML private  Text localTime;
    @FXML private Text localDate;

        private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("hh:mm:ss a");
        private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd MMMM, yyyy");

        private void updateDateTime() {
            Platform.runLater(() -> {
                Timeline clock = new Timeline(
                        new KeyFrame(Duration.ZERO, e -> {
                            LocalDateTime now = LocalDateTime.now();
                            localTime.setText(now.format(TIME_FORMATTER).toUpperCase(Locale.ROOT));
                            localDate.setText(now.format(DATE_FORMATTER));
                        }),
                        new KeyFrame(Duration.seconds(1))
                );
                clock.setCycleCount(Timeline.INDEFINITE);
                clock.play();
            });
        }




    @FXML
    void initialize() {
        // initialize logic'
        authService = new AuthService(this);
//        menuNavigator.setVisible(false);
        reportNavigator.setVisible(false);
        monitorNavigator.setVisible(false);
        emergencyButton.setDisable(true);
        updateDateTime();



        StationSpecialMode.StationSpecialModeListener listener = newMode -> {
            logger.debug("New special mode: {}  {}", newMode, emergencyModeActive);

            if (newMode.equals(StationSpecialMode.EMERGENCY) && !emergencyModeActive) {
                // Entering Emergency Mode
                logger.debug("Entering emergency mode");
                emergencyModeActive = true;
                Platform.runLater(() -> {
                    emergencyButton.getStyleClass().add("emergencyButtonActive");
                });
            } else if (!newMode.equals(StationSpecialMode.EMERGENCY) && emergencyModeActive) {
                // Exiting Emergency Mode
                logger.debug("Exiting emergency mode");
                emergencyModeActive = false;
                Platform.runLater(() -> {
                    emergencyButton.getStyleClass().remove("emergencyButtonActive");
                });
            }else if (newMode.equals(StationSpecialMode.CCU_DISCONNECT)){
                Platform.runLater(() -> {
                        ccuConnection.setStyle("-fx-background-color: red;");
                        System.out.println("❌ CCU Disconnected");
                });

            }else if (newMode.equals(StationSpecialMode.CCU_CONNECT)){
                // Listener to change background
                Platform.runLater(() -> {
                    ccuConnection.setStyle("-fx-background-color: green;");
                    System.out.println(" CCU Connected");
                });
            }
        };

        StationSpecialMode.addStationSpecialModeListener(listener);



    }

    public void setListener(HeaderListener scuHeaderListener){
        this.scuHeaderListener = scuHeaderListener;
    }

    @FXML
    void onMenuClick(ActionEvent event) {
        scuHeaderListener.onMenuClick();
        event.consume();
    }

    @FXML
    void onMonitorClick(ActionEvent event) {
        scuHeaderListener.onMonitorClick();
        event.consume();

    }

    @FXML
    void onReportClick(ActionEvent event) {
        scuHeaderListener.onReportClick();
        event.consume();

    }


    @FXML
    private void onAuthClick(ActionEvent actionEvent) {
        PopupWindow popupWindow = new PopupWindow();
        FXMLLoader fxmlLoader=null;
        if(!authService.isAuthenticated().get()){
         fxmlLoader = ViewFactory.getLogin();
            fxmlLoader.setControllerFactory(c -> new LoginController((String userId, String password)->{if(authService.login(userId,password))popupWindow.Close();}, popupWindow));
        }
        else {
            fxmlLoader = ViewFactory.getLogout();
            fxmlLoader.setControllerFactory(c -> new LogoutController((String userId, String password)->{if(authService.logout())popupWindow.Close();}, popupWindow));
        }

        popupWindow.show(fxmlLoader);
        actionEvent.consume();

    }


    public void authenticated(){
//        menuNavigator.setVisible(true);
        reportNavigator.setVisible(true);
        monitorNavigator.setVisible(true);
        userName.setText(authService.getUsername());
        emergencyButton.setDisable(false);

    }

    public void logout(){
        onMonitorClick(null);
//        menuNavigator.setVisible(false);
        reportNavigator.setVisible(false);
        monitorNavigator.setVisible(false);
        userName.setText("user");
        emergencyButton.setDisable(true);

    }

    boolean emergencyModeActive=false;
    public void onEmergency(ActionEvent actionEvent) {

        if(emergencyModeActive){
            CommandTest.INSTANCE.sendStationCommand(StationSpecialMode.STATION_NORMAL);
            emergencyButton.getStyleClass().remove("emergencyButtonActive");
            emergencyModeActive=false;
            return;
        }

        PopupWindow popupWindow = new PopupWindow();
        FXMLLoader fxmlLoader=null;
        fxmlLoader = ViewFactory.getPermission();
        fxmlLoader.setControllerFactory(c -> new PermissionController(()->{
            CommandTest.INSTANCE.sendStationCommand(StationSpecialMode.EMERGENCY);popupWindow.Close();
                StationSpecialMode.setStationSpecialMode(StationSpecialMode.EMERGENCY);},
                popupWindow,"Do you really want to set Emergency Mode??"));
        popupWindow.show(fxmlLoader);
        actionEvent.consume();

    }


    public AuthService getAuthService() {
        if(authService==null){
            throw new IllegalStateException("AuthService is not initialized");
        }
        return authService;
    }
}
