package com.amay.scu.contservice;

import com.amay.scu.ViewFactory;
import com.amay.scu.controller.SCUController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class SCUControllerService {
    private Logger logger = LoggerFactory.getLogger(SCUControllerService.class);

    private InnerListener scuControllerListener;

    Node monitorView;

    public SCUControllerService( InnerListener scuControllerListener) {
        this.scuControllerListener = scuControllerListener;
    }

    public void login() {
        // login logic
    }

    public void logout() {
        // logout logic
    }

    public void onMenuClick() {
        // onMenuClick logic
        logger.debug("Menu Clicked Service");
    }

    public void onMonitorClick(BorderPane borderPane) {
        // onMonitorClick logic
        logger.debug("Monitor Clicked Service {}",monitorView);
        if(monitorView==null)
            return;
        borderPane.setCenter(monitorView);
    }

    public void onReportClick() {
        // onReportClick logic
        logger.debug("Report Clicked Service");
    }

    public void onReportClick(BorderPane borderPane, FXMLLoader report) {
        // onReportClick logic
        logger.debug("Report Clicked Service {}",report);
        monitorView=borderPane.getCenter();
        try {
            borderPane.setCenter(report.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
