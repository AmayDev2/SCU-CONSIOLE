package com.amay.scu.report.controller;

import com.amay.scu.controller.ReportController;
import com.amay.scu.report.controller.enums.ReportsListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;

public class ReportLeftView {

    @FXML
    private Button btnRevenueReport;

    @FXML
    private Button btnRidershipReport;

    @FXML
    private Button btnShiftReport;

    private final ReportsListener reportsListener;

    public ReportLeftView(ReportsListener controller) {
        this.reportsListener=controller;
    }
//
//    @FXML
//    private TabPane reportsTabPane;
//
//    @FXML
//    private void initialize(){
//        reportsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
//    }

    @FXML
    void onMainMenuBtnClicked(ActionEvent event) {

        Button clickedBtn = (Button) event.getSource();

        if (clickedBtn == btnRevenueReport){
            this.reportsListener.onClickRevenueReport();
//            addTabToPane("Revenue Report", initializer.loadContent(FileConstants.Reports.REVENUE_REPORT));
        }
        if (clickedBtn == btnRidershipReport){
            this.reportsListener.onClickRidershipReport();
//            addTabToPane("Ridership Report", initializer.loadContent(FileConstants.Reports.REVENUE_REPORT));
        }
        if (clickedBtn == btnShiftReport){
            this.reportsListener.onClickShiftReport();
//            addTabToPane("Shift Report", initializer.loadContent(FileConstants.Reports.REVENUE_REPORT));
        }
    }
}
