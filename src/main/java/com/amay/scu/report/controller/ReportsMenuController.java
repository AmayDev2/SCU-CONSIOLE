package com.amay.scu.report.controller;

import com.amay.scu.ViewFactory;
import com.amay.scu.enums.filters.FilterEnums;
import com.amay.scu.report.controller.enums.ReportsListener;
import com.amay.scu.service.ScuGrpcService;
import com.amay.scu.util.ColumnDefinition;
import com.amay.scu.util.EpochRangeUtil;
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;


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
        reportsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.SELECTED_TAB);
        this.onClickRevenueReport();
    }


    @Override
    public void onClickRevenueReport() {
        List<ColumnDefinition<RevenueReport, ?>> columns = List.of(
            new ColumnDefinition<>("Ticket Id", RevenueReport::getTicketId,List.of()),
//            new ColumnDefinition<>("Station", RevenueReport::getStation,List.of()),
            new ColumnDefinition<>("Equipment Id", RevenueReport::getEquipmentId,List.of()),
            new ColumnDefinition<>("Equipment Type", RevenueReport::getEquipmentType,List.of()),
            new ColumnDefinition<>("Trip Type", RevenueReport::getTripType,List.of()),
            new ColumnDefinition<>("Fare Media", RevenueReport::getFareMedia,List.of()),
            new ColumnDefinition<>("Payment Mode", RevenueReport::getPaymentMode,List.of()),
            new ColumnDefinition<>("Amount", RevenueReport::getAmount,List.of()),
            new ColumnDefinition<>("Ticket Time", RevenueReport::getTicketTime,List.of()),
            new ColumnDefinition<>("Transaction Type", RevenueReport::getTransactionType,List.of())
            );

        //start from 2
        List<FilterItem> filterItems=List.of(
            new FilterItem("Payment Mode","payment_mode",2,List.of(
                    FilterEnums.UPI.getTitle(),
                    FilterEnums.CASH.getTitle(),
                    FilterEnums.POS.getTitle()
            )),
            new FilterItem("Transaction Type","transaction_type",3,List.of(
                    FilterEnums.Issuance.getTitle(),
                    FilterEnums.Penalty.getTitle(),
                    FilterEnums.Refund.getTitle(),
                    FilterEnums.Cancel.getTitle()
            )),
            new FilterItem("Fare Media","fare_media",4,List.of(
                    FilterEnums.QR.getTitle(),
                    FilterEnums.NCMC.getTitle(),
                    FilterEnums.MQR.getTitle()
            )),
            new FilterItem("Trip Type","trip_type",7,List.of(
                    FilterEnums.SJT.getTitle(),
                    FilterEnums.RJT.getTitle(),
                    FilterEnums.GROUP.getTitle(),
                    FilterEnums.PAID.getTitle(),
                    FilterEnums.FREE.getTitle()
                )),
            new FilterItem("Equipment Id","equipment_id",5,List.of(
                )),
            new FilterItem("Equipment Type","equipment_type",6,List.of(
                FilterEnums.TOM.getTitle(),
                FilterEnums.EFO.getTitle(),
                FilterEnums.TVM.getTitle()
            ))

        );


        try {
            FXMLLoader loader=ViewFactory.getRevenueReport();

            Callable<List<RevenueReport>> task = () -> (List<RevenueReport>) ScuGrpcService.INSTANCE.getRevenueReport(ListValue.newBuilder()
                    .addValues(Value.newBuilder().setNumberValue(EpochRangeUtil.getLast30DaysRange()[0]).build())
                    .addValues(Value.newBuilder().setNumberValue(EpochRangeUtil.getLast30DaysRange()[1]).build())
                    .build());

            loader.setControllerFactory(x-> new RevenueReportsController<>(columns,"Revenue Report", task,builder -> (List<RevenueReport>) ScuGrpcService.INSTANCE.getRevenueReport(builder.build()),filterItems) );
            Node node=loader.load();
            addTabToPane("Revenue Report",node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClickRidershipReport() {
        List<ColumnDefinition< RidershipReport, ?>> columns = List.of(
                new ColumnDefinition<>("Time", RidershipReport::getTime,List.of()),
                new ColumnDefinition<>("Ticket Id", RidershipReport::getTicketId,List.of()),
                new ColumnDefinition<>("Equipment Type",RidershipReport::getEquipmentType,List.of()),
                new ColumnDefinition<>("Equipment Id", RidershipReport::getEquipmentId,List.of()),
                new ColumnDefinition<>("Status", RidershipReport::getStatus,List.of())
                );


        List<FilterItem> filterItems=List.of(
//                new FilterItem("Entry > QR","entry_qr",2,List.of(
//
//                )),
//                new FilterItem("Entry > NCMC","entry_ncmc",3,List.of(
//
//                )),
//                new FilterItem("Entry > MQR","entry_mqr",4,List.of()),
//                new FilterItem("Exit > QR","exit_qr",5,List.of()),
//                new FilterItem("Exit > NCMC","exit_ncmc",6,List.of()),
//                new FilterItem("Exit > MQR","exit_mqr",7,List.of())
        );

        try {
            FXMLLoader loader=ViewFactory.getRevenueReport();
            Callable<List<RidershipReport>> task = () -> (List<RidershipReport>) ScuGrpcService.INSTANCE.getRidershipReport(ListValue.newBuilder().build());
            loader.setControllerFactory(x-> new RevenueReportsController<>(columns, "Ridership Report",task, builder -> (List<RidershipReport>) ScuGrpcService.INSTANCE.getRidershipReport(builder.build()),filterItems) );
            Node node=loader.load();
            addTabToPane("Ridership Report",node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClickRidershipPerDayReport() {
        List<ColumnDefinition< RidershipReportPerDay, ?>> columns = List.of(

                new ColumnDefinition<>("Date", RidershipReportPerDay::getDate,
                        List.of()),

                new ColumnDefinition<>("Station", RidershipReportPerDay::getStation,List.of()),

                new ColumnDefinition<>("Entry", RidershipReportPerDay::getTotalEntry, List.of(
                        new ColumnDefinition<>("QR", RidershipReportPerDay::getEntryQR, List.of()),
                        new ColumnDefinition<>("NCMC", RidershipReportPerDay::getEntryNCMC, List.of()),
                        new ColumnDefinition<>("MQR",RidershipReportPerDay::getEntryMQR, List.of())
                )),

                new ColumnDefinition<>("Exit", RidershipReportPerDay::getTotalExit, List.of(
                        new ColumnDefinition<>("QR", RidershipReportPerDay::getExitQR, List.of()),
                        new ColumnDefinition<>("NCMC", RidershipReportPerDay::getExitNCMC, List.of()),
                        new ColumnDefinition<>("MQR",RidershipReportPerDay::getExitMQR, List.of())
                )),

                new ColumnDefinition<>("Total", RidershipReportPerDay::getTotalRidership, List.of(
                        new ColumnDefinition<>("Entry", RidershipReportPerDay::getTotalEntry, List.of()),
                        new ColumnDefinition<>("Exit", RidershipReportPerDay::getTotalExit, List.of())
                )));

        List<FilterItem> filterItems=List.of(
//                new FilterItem("Entry > QR","entry_qr",2,List.of()),
//                new FilterItem("Entry > NCMC","entry_ncmc",3,List.of()),
//                new FilterItem("Entry > MQR","entry_mqr",4,List.of()),
//                new FilterItem("Exit > QR","exit_qr",5,List.of()),
//                new FilterItem("Exit > NCMC","exit_ncmc",6,List.of()),
//                new FilterItem("Exit > MQR","exit_mqr",7,List.of())
        );

        try {
            FXMLLoader loader=ViewFactory.getRevenueReport();
            Callable<List<RidershipReportPerDay>> task = () -> (List<RidershipReportPerDay>) ScuGrpcService.INSTANCE.getRidershipParDayReport(ListValue.newBuilder().build());

            loader.setControllerFactory(x -> new RevenueReportsController<>(
                    columns,
                    "Ridership Daily Report",
                    task,
                    builder -> (List<RidershipReportPerDay>) ScuGrpcService.INSTANCE.getRidershipParDayReport(builder.build())
                    ,filterItems
            ));
            Node node=loader.load();
            addTabToPane("Ridership Daily Report",node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onClickRidershipPerHourReport() {
        List<ColumnDefinition< RidershipReportPerHour, ?>> columns = new java.util.ArrayList<>(List.of(
                new ColumnDefinition<>("Date", RidershipReportPerHour::getDate,
                        List.of()),
            new ColumnDefinition<>("Total", RidershipReportPerHour::getDate,
                List.of(
                        new ColumnDefinition<>("Entry", RidershipReportPerHour::getTotalEntry, List.of()),
                        new ColumnDefinition<>("Exit", RidershipReportPerHour::getTotalExit, List.of(
                ))))));

        // Generate 24 hourly columns
        for (int i = 0; i < 24; i++) {
            final int hourIndex = i;
            String hourLabel = String.format("%02d:00", i);

            columns.add(new ColumnDefinition<>(
                    hourLabel,
                    RidershipReportPerHour::getRidershipReportHours, // used only for nesting
                    List.of(
                            new ColumnDefinition<>("Entry", report -> report.getRidershipReportHours().get(hourIndex).getEntryCount(),List.of()),
                            new ColumnDefinition<>("Exit", report -> report.getRidershipReportHours().get(hourIndex).getExitCount(), List.of())
                    )
            ));
        }


        List<FilterItem> filterItems=List.of(

        );

        try {
            FXMLLoader loader=ViewFactory.getRevenueReport();
            Callable<List<RidershipReportPerHour>> task = () -> (List<RidershipReportPerHour>) ScuGrpcService.INSTANCE.getRidershipParHourReport(ListValue.newBuilder().build());
            loader.setControllerFactory(x-> new RevenueReportsController<>(columns, "Ridership Hourly Report",task,builder -> (List<RidershipReportPerHour>) ScuGrpcService.INSTANCE.getRidershipParHourReport(builder.build()),filterItems) );
            Node node=loader.load();
            addTabToPane("Ridership Hourly Report",node);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String date;
    private String operatorId;
    private String loginTime;
    private String logoutTime;
    private int QR;
    private int UPI,CASH,POS;
    private int NCMC;
    private int revenue;

    @Override
    public void onClickShiftReport() {
        List<ColumnDefinition<ShiftReport, ?>> columns = List.of(
                new ColumnDefinition<>("Date", ShiftReport::getDate,List.of()),
                new ColumnDefinition<>("Shift Id", ShiftReport::getShiftId,List.of()),
                new ColumnDefinition<>("Start Time", ShiftReport::getLoginTime,List.of()),
                new ColumnDefinition<>("End Time", ShiftReport::getLogoutTime,List.of()),
                new ColumnDefinition<>("Operator Id", ShiftReport::getOperatorId,List.of()),
                new ColumnDefinition<>("Equipment Id", ShiftReport::getEquipmentId,List.of()),
                new ColumnDefinition<>("Equipment Type", ShiftReport::getEquipmentType,List.of()),
                new ColumnDefinition<>("QR Tickets Transactions", ShiftReport::getQR,List.of(
                        new ColumnDefinition<>("UPI", ShiftReport::getUPI,List.of()),
                        new ColumnDefinition<>("POS", ShiftReport::getPOS,List.of()),
                        new ColumnDefinition<>("CASH", ShiftReport::getCASH,List.of())
                )),
                new ColumnDefinition<>("NCMC", ShiftReport::getNCMC,List.of()),
                new ColumnDefinition<>("Revenue", ShiftReport::getRevenue,List.of())
        );

        List<FilterItem> filterItems=List.of(
                new FilterItem("Operator Id","operator_id",2,List.of()),
                new FilterItem("Equipment Type","equipment_type",3,List.of(
                FilterEnums.TOM.getTitle(),
                FilterEnums.EFO.getTitle(),
                FilterEnums.TVM.getTitle()
        )),
                new FilterItem("Equipment Id","equipment_id",4,List.of()),
                new FilterItem("Shift Id","shift_id",5,List.of()),
                new FilterItem("Station Id","station_id",6,List.of())

        );

        try {
            FXMLLoader loader=ViewFactory.getRevenueReport();
            Callable<List<ShiftReport>> task = () -> (List<ShiftReport>) ScuGrpcService.INSTANCE.getShiftReport(ListValue.newBuilder().build());
            loader.setControllerFactory(x-> new RevenueReportsController<>(columns, "Shift Report",task,builder -> (List<ShiftReport>) ScuGrpcService.INSTANCE.getShiftReport(builder.build()),filterItems) );
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
        reportsTabPane.setTabMinWidth(120);
        reportsTabPane.setTabMaxWidth(400);
//        reportsTabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.ALL_TABS);
        reportsTabPane.getSelectionModel().select(newTab);
    }


}
