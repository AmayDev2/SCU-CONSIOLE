package com.amay.scu.controller;

import com.amay.scu.ViewFactory;
import com.amay.scu.contservice.HeaderListener;
import com.amay.scu.contservice.InnerListener;
import com.amay.scu.contservice.OuterListener;
import com.amay.scu.contservice.SCUControllerService;
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

    @FXML
    void initialize() {
        System.out.println("SCUController initialize 1");
        scuControllerListener = new SCUControllerListener();
        scuControllerService = new SCUControllerService( scuControllerListener);
        this.setRight();

    }

    private void setRight() {
            try{
                FXMLLoader loader= ViewFactory.getMonitorRightView();
                borderPane.setRight(loader.load());
            }catch (IOException e) {
                e.printStackTrace();
            }
    }

    @FXML
    private HeaderController topHeaderIncludeController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initialize();
        System.out.println("SCUController initialize : "+location+" : "+resources+" : "+topHeaderIncludeController);
        // Initialization logic
        if (topHeaderIncludeController != null) {
            topHeaderIncludeController.onMenuClick(); // Example usage
            topHeaderIncludeController.setListener(scuControllerListener);
        } else {
            logger.error("topHeaderIncludeController is not initialized");
        }
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