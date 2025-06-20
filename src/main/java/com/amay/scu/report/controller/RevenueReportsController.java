package com.amay.scu.report.controller;

import com.amay.scu.util.ColumnDefinition;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

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

    public RevenueReportsController(List<ColumnDefinition<T, ?>> columns, String reportName) {
        this.columns = columns;
        this.reportsName=reportName;

    }


    @FXML
    private void initialize() {
        this.reportName.setText(reportsName);
        Platform.runLater(() -> {
//            setTable();
            setupDynamicTable(reportsTable, columns, observableList);
            if (observableList.isEmpty()) {
                fetchData();
            }
        });

        this.tableHelper= new TableHelper();
    }

    private void fetchData() {
//        try {
//            loadingPane.showLoadingPane();
//            ResponseEntity<RevenueReport[]> entity = helper.get(ApiUrl.REVENUE_REPORT, null, RevenueReport[].class);
//            if (entity.getStatusCode().is2xxSuccessful()) {
//                if (entity.getBody() == null) {
//                    showAlert(Alert.AlertType.INFORMATION, "No Records", "No Revenue Data Available !");
//                } else {
//                    observableList.clear();
//                    observableList.addAll(entity.getBody());
//                }
//            } else {
//                showAlert(Alert.AlertType.INFORMATION, "Server Error", "Oops !!! Something went wrong");
//            }
//            loadingPane.removeLoadingPane();
//        } catch (Exception e) {
//            loadingPane.removeLoadingPane();
//            showAlert(Alert.AlertType.INFORMATION, "Server Error", "Oops !!! Something went wrong");
//        }
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
        column.setPrefWidth(150);
        column.setMinWidth(50);
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

    public void onRefreshDrafts(ActionEvent actionEvent) {
        fetchData();
    }
}
