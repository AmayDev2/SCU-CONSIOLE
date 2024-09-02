package com.amay.scu.controller;

import com.amay.scu.ViewFactory;
import com.amay.scu.auth.AuthService;
import com.amay.scu.auth.functional.Authentication;
import com.amay.scu.contservice.HeaderListener;
import com.amay.scu.contservice.InnerListener;
import com.amay.scu.contservice.OuterListener;
import com.amay.scu.contservice.SCUControllerService;
import com.amay.scu.enums.StationSpecialMode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
/*login
*logout
*
*
*/

public class SCUController implements Initializable{

    private Logger logger = LoggerFactory.getLogger(SCUController.class);
    @FXML
    private BorderPane borderPane;   // BorderPane is a layout pane that splits the scene into five areas: top, right, bottom, left, and center.

    private SCUControllerService scuControllerService=null;
    private  SCUControllerListener scuControllerListener=null;

//    @FXML
//    void initialize() {
//
//
//
//    }

    private void setRight() {
            try{
                FXMLLoader loader= ViewFactory.getMonitorRightView();
                loader.setControllerFactory(c -> new MonitorRightView(authService));
                borderPane.setRight(loader.load());

            }catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    private HeaderController topHeaderIncludeController;

    private AuthService authService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println("SCUController initialize : "+location+" : "+resources+" : "+topHeaderIncludeController);
        System.out.println("SCUController initialize 1");
        scuControllerListener = new SCUControllerListener();
        scuControllerService = new SCUControllerService( scuControllerListener);

        // Initialization logic
        if (topHeaderIncludeController != null) {
            topHeaderIncludeController.onMenuClick(); // Example usage
            topHeaderIncludeController.setListener(scuControllerListener);
            authService= topHeaderIncludeController.getAuthService();


        } else {
            logger.error("topHeaderIncludeController is not initialized");
        }
//        initialize();
        this.setRight();
    }

    //Listener for SCUController
    private
    class SCUControllerListener implements InnerListener, OuterListener, HeaderListener {
        int i=1;

        @Override
        public void onMenuClick() {
            System.out.println("Menu Clicked");
            if(i==0){
                return;
            }
            i=0;
            scuControllerService.onMenuClick();
        }

        @Override
        public void onMonitorClick() {
            System.out.println("Monitor Clicked");
            if(i==1){
                return;
            }
            i=1;
            scuControllerService.onMonitorClick(borderPane);
        }

        @Override
        public void onReportClick() {
            System.out.println("Report Clicked");
            if(i==2){
                return;
            }
            i=2;
            scuControllerService.onReportClick(borderPane, ViewFactory.getReport());

        }


    }
}