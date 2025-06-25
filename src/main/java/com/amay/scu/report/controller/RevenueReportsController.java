package com.amay.scu.report.controller;

import com.amay.scu.service.ScuGrpcService;
import com.amay.scu.util.ColumnDefinition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RevenueReportsController<T> {


    @FXML
    private  Label reportName;
    private TableHelper tableHelper;
    private String reportsName;
//    private final HttpHelper helper;
//    private final LoadingPane loadingPane;

    @FXML
    private Button btnExcel;

    @FXML
    private Button btnPdf;

    @FXML
    private TableView<T> reportsTable;

    private final ObservableList<T> observableList = FXCollections.observableArrayList();
    private final List<ColumnDefinition<T, ?>> columns;
    private final Callable<List<T>> task;

    public RevenueReportsController(List<ColumnDefinition<T, ?>> columns, String reportName,Callable<List<T>> task) {
        this.columns = columns;
        this.reportsName=reportName;
        this.task= task;

    }


    @FXML
    private void initialize() {
        this.reportName.setText(reportsName);
        Platform.runLater(() -> {
//            setTable();
            setupDynamicTable(reportsTable, columns, observableList);
            if (observableList.isEmpty()) {
                    fetchData(task);
            }
        });

        this.tableHelper= new TableHelper();
    }

    private void fetchData(Callable<List<T>> task) {

        try {

            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<List<T>> future = executor.submit(task);
            List<T> list = future.get(); // this will block until result is ready
            executor.shutdown();
                if (list.isEmpty()) {
                    showAlert(Alert.AlertType.INFORMATION, "No Records", "No Revenue Data Available !");
                } else {
                    observableList.clear();
                    observableList.addAll( list);
                }
        } catch (Exception e) {
            showAlert(Alert.AlertType.INFORMATION, "Server Error", "Oops !!! Something went wrong");
        }
    }

    private void showAlert(Alert.AlertType alertType, String noRecords, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(noRecords);
        alert.setContentText(s);
        alert.showAndWait();
    }
//
//    private void setTable() {
//        reportsTable.setEditable(false);
////        reportsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_FLEX_LAST_COLUMN);
//
//        TableColumn<RevenueReport, String> revTicketId = tableHelper.createNonEditableColumn("Ticket Id", RevenueReport::getTicketId);
//        TableColumn<RevenueReport, String> revEquipId = tableHelper.createNonEditableColumn("Equipment Id", RevenueReport::getEquipmentId);
//        TableColumn<RevenueReport, String> revStation = tableHelper.createNonEditableColumn("Station", RevenueReport::getStation);
//        TableColumn<RevenueReport, String> revEquipType = tableHelper.createNonEditableColumn("Equipment Type", RevenueReport::getEquipmentType);
//        TableColumn<RevenueReport, String> revTripType = tableHelper.createNonEditableColumn("Trip Type", RevenueReport::getTripType);
//        TableColumn<RevenueReport, String> revFareMedia = tableHelper.createNonEditableColumn("Fare Media", RevenueReport::getFareMedia);
//        TableColumn<RevenueReport, String> revPaymentMode = tableHelper.createNonEditableColumn("Payment Mode", RevenueReport::getPaymentMode);
//        TableColumn<RevenueReport, Double> revAmount = tableHelper.createNonEditableColumn("Payment Mode", RevenueReport::getAmount);
//        TableColumn<RevenueReport, String> revTicketType = tableHelper.createNonEditableColumn("Ticket Time", RevenueReport::getTicketTime);
//        TableColumn<RevenueReport, String> revTransType = tableHelper.createNonEditableColumn("Transaction Type", RevenueReport::getTransactionType);
//
//        reportsTable.getColumns().addAll(revTicketId, revEquipId, revStation, revEquipType, revTripType, revFareMedia, revPaymentMode, revAmount, revTicketType, revTransType);
//        reportsTable.setItems(observableList);
//    }

    public <T> void setupDynamicTable(TableView<T> tableView, List<ColumnDefinition<T, ?>> columns, ObservableList<T> data) {
        tableView.getColumns().clear();
        tableView.setEditable(false);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (ColumnDefinition<T, ?> def : columns) {
            addColumn(tableView, def);
        }
        tableView.setItems(data);
    }

    private  <T, R> void addColumn(TableView<T> tableView, ColumnDefinition<T, R> def) {
        TableColumn<T, R> column = new TableColumn<>(def.header());
        column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(def.getter().apply(cellData.getValue())));
        tableView.getColumns().add(column);
        column.setPrefWidth(200);
        column.setMinWidth(170);
        column.setEditable(false);
    }

    @FXML
    void onExport(ActionEvent event) {
//        Button clickedBtn = (Button) event.getSource();
//        JasperHelper<RevenueReport> jasperHelper = new JasperHelper<>("/revenue_report.jrxml", reportsTable.getItems());
//
//        if (clickedBtn == btnPdf) {
//            jasperHelper.exportPdfReport();
//        }
//        if (clickedBtn == btnExcel) {
//            jasperHelper.exportExcelReport();
//        }
    }

}
