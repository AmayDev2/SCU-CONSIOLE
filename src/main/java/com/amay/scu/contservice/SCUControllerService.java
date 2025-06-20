package com.amay.scu.contservice;

import com.amay.scu.ViewFactory;
import com.amay.scu.controller.ReportController;
import com.amay.scu.controller.SCUController;
import com.amay.scu.report.controller.ReportLeftView;
import com.amay.scu.report.controller.enums.ReportsListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;


public class SCUControllerService {
    private Logger logger = LoggerFactory.getLogger(SCUControllerService.class);

    private InnerListener scuControllerListener;

    Node monitorView;
    Node rightView;

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
        if(rightView==null)
            return;
        borderPane.setLeft(null);
        borderPane.setRight(rightView);

    }

    public void onReportClick() {
        // onReportClick logic
        logger.debug("Report Clicked Service");
    }

    public void onReportClick(BorderPane borderPane, FXMLLoader report,FXMLLoader leftView) {
        // onReportClick logic
        logger.debug("Report Clicked Service {}",report);
        monitorView=borderPane.getCenter();
        rightView=borderPane.getRight();
        try {
            borderPane.setRight(null);
            StackPane stackPane =report.load();
            ReportsListener controller = report.getController();
            leftView.setControllerFactory(x->new ReportLeftView(controller));
            VBox vBox=leftView.load();
             borderPane.setLeft(vBox);
             borderPane.setCenter(stackPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
