package com.amay.scu.controller;

import com.amay.scu.ViewFactory;
import com.amay.scu.auth.AuthService;
import com.amay.scu.auth.functional.Authentication;
import com.amay.scu.command.CommandTest;
import com.amay.scu.contservice.HeaderListener;
import com.amay.scu.enums.StationSpecialMode;
import com.amay.scu.popup.PopupWindow;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class HeaderController {

    @FXML
    private Button emergencyButton;
    @FXML
    private Label userName;
    @FXML
    private Button userButton;

    private Logger logger = LoggerFactory.getLogger(HeaderController.class);
    @FXML
    private Text menuNavigator;

    @FXML
    private Text monitorNavigator;

    @FXML
    private Text reportNavigator;

    private HeaderListener scuHeaderListener= null;

    private AuthService authService;


    @FXML
    void initialize() {
        // initialize logic'
        authService = new AuthService(this);
        menuNavigator.setVisible(false);
        reportNavigator.setVisible(false);
        monitorNavigator.setVisible(false);
        emergencyButton.setDisable(true);

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
            }
        };

        StationSpecialMode.addStationSpecialModeListener(listener);



    }

    public void setListener(HeaderListener scuHeaderListener){
        this.scuHeaderListener = scuHeaderListener;
    }

    @FXML
    void onMenuClick(MouseEvent event) {

        scuHeaderListener.onMenuClick();
    }

    @FXML
    void onMonitorClick(MouseEvent event) {
        scuHeaderListener.onMonitorClick();

    }

    @FXML
    void onReportClick(MouseEvent event) {
        scuHeaderListener.onReportClick();

    }

    // For testing without a MouseEvent
    public void onMenuClick() {
        System.out.println("Menu clicked (no event)");
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

    }


    public void authenticated(){
        menuNavigator.setVisible(true);
        reportNavigator.setVisible(true);
        monitorNavigator.setVisible(true);
        userName.setText(authService.getUsername());
        emergencyButton.setDisable(false);

    }

    public void logout(){
        onMonitorClick(null);
        menuNavigator.setVisible(false);
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

    }


    public AuthService getAuthService() {
        if(authService==null){
            throw new IllegalStateException("AuthService is not initialized");
        }
        return authService;
    }
}
