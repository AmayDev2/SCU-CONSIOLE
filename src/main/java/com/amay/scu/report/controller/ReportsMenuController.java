package com.amay.scu.report.controller;

import com.amay.scu.ViewFactory;
import com.amay.scu.report.controller.enums.ReportsListener;
import com.amay.scu.util.ColumnDefinition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;


public class ReportsMenuController implements ReportsListener {


//    @FXML
//    private Button btnRevenueReport;
//
//    @FXML
//    private Button btnRidershipReport;
//
//    @FXML
//    private Button btnShiftReport;



    @FXML
    private TabPane reportsTabPane;

//    private AuthService authService;
//
//    public ReportsMenuController(AuthService authService) {
//        this.authService=authService;
//    }



    @FXML
    private void initialize()  {

//        reportsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
//        this.onClickRevenueReport();

    }


    @Override
    public void onClickRevenueReport() {
        List<ColumnDefinition<RevenueReport, ?>> columns = List.of(
            new ColumnDefinition<>("Ticket Id", RevenueReport::getTicketId),
            new ColumnDefinition<>("Equipment Id", RevenueReport::getEquipmentId),
            new ColumnDefinition<>("Station", RevenueReport::getStation),
            new ColumnDefinition<>("Equipment Type", RevenueReport::getEquipmentType),
            new ColumnDefinition<>("Trip Type", RevenueReport::getTripType),
            new ColumnDefinition<>("Fare Media", RevenueReport::getFareMedia),
            new ColumnDefinition<>("Payment Mode", RevenueReport::getPaymentMode),
            new ColumnDefinition<>("Amount", RevenueReport::getAmount),
            new ColumnDefinition<>("Ticket Time", RevenueReport::getTicketTime),
            new ColumnDefinition<>("Transaction Type", RevenueReport::getTransactionType)
            );

        try {
            FXMLLoader loader=ViewFactory.getRevenueReport();
            loader.setControllerFactory(x-> new RevenueReportsController(columns,"Revenue Report") );
            Node node=loader.load();
            addTabToPane("Revenue Report",node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClickRidershipReport() {
        List<ColumnDefinition< RidershipReport, ?>> columns = List.of(
                new ColumnDefinition<>("Time", RidershipReport::getTime),
                new ColumnDefinition<>("Station Id", RidershipReport::getStationId),
                new ColumnDefinition<>("Equipment Type",RidershipReport::getEquipmentType),
                new ColumnDefinition<>("Equipment Id", RidershipReport::getEquipmentId),
                new ColumnDefinition<>("Status", RidershipReport::getStatus)
                );

        try {
            FXMLLoader loader=ViewFactory.getRevenueReport();
            loader.setControllerFactory(x-> new RevenueReportsController(columns, "Ridership Report") );
            Node node=loader.load();
            addTabToPane("Ridership Report",node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClickShiftReport() {
        List<ColumnDefinition<RevenueReport, ?>> columns = List.of(
                new ColumnDefinition<>("Ticket Id", RevenueReport::getTicketId),
                new ColumnDefinition<>("Equipment Id", RevenueReport::getEquipmentId),
                new ColumnDefinition<>("Station", RevenueReport::getStation),
                new ColumnDefinition<>("Equipment Type", RevenueReport::getEquipmentType),
                new ColumnDefinition<>("Trip Type", RevenueReport::getTripType),
                new ColumnDefinition<>("Fare Media", RevenueReport::getFareMedia),
                new ColumnDefinition<>("Payment Mode", RevenueReport::getPaymentMode),
                new ColumnDefinition<>("Amount", RevenueReport::getAmount),
                new ColumnDefinition<>("Ticket Time", RevenueReport::getTicketTime),
                new ColumnDefinition<>("Transaction Type", RevenueReport::getTransactionType)
        );

        try {
            FXMLLoader loader=ViewFactory.getRevenueReport();
            loader.setControllerFactory(x-> new RevenueReportsController(columns, "Shift Report") );
            Node node=loader.load();
            addTabToPane("ShiftReport Report",node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    @FXML
//    void onMainMenuBtnClicked(ActionEvent event) {
//
//        Button clickedBtn = (Button) event.getSource();

//        if (clickedBtn == btnRevenueReport){
////            addTabToPane("Revenue Report", initializer.loadContent(FileConstants.Reports.REVENUE_REPORT));
//        }
//        if (clickedBtn == btnRidershipReport){
////            addTabToPane("Ridership Report", initializer.loadContent(FileConstants.Reports.REVENUE_REPORT));
//        }
//        if (clickedBtn == btnShiftReport){
////            addTabToPane("Shift Report", initializer.loadContent(FileConstants.Reports.REVENUE_REPORT));
//        }
//    }

    private void addTabToPane(String tabName, Node content) {
        for (Tab tab : reportsTabPane.getTabs()) {
            if (tab.getText().equals(tabName)) {
                reportsTabPane.getSelectionModel().select(tab);
                return;
            }
        }

        Tab newTab = new Tab(tabName);
        newTab.setClosable(true);

        if (content == null) {
            AnchorPane placeholder = new AnchorPane();
            placeholder.setPrefSize(1536, 828);
            placeholder.setStyle("-fx-background-color: linear-gradient(to bottom right, #f0f4f8, #d9e2ec);");

            Label comingSoonLabel = new Label("Coming Soon");
            comingSoonLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: #333; -fx-font-weight: bold;");
            AnchorPane.setTopAnchor(comingSoonLabel, placeholder.getPrefHeight() / 2 - 15);
            AnchorPane.setLeftAnchor(comingSoonLabel, placeholder.getPrefWidth() / 2 - 75);

            placeholder.getChildren().add(comingSoonLabel);
            newTab.setContent(placeholder);
        } else {
            newTab.setContent(content);
        }

        reportsTabPane.getTabs().add(newTab);
        reportsTabPane.getSelectionModel().select(newTab);
    }


}
