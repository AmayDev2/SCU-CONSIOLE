package com.amay.scu.report.controller;

import com.amay.scu.util.ColumnDefinition;
import com.google.protobuf.ListValue;
import com.google.protobuf.Value;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

public class RevenueReportsController<T> {


    private final ObservableList<T> observableList = FXCollections.observableArrayList();
    private final List<ColumnDefinition<T, ?>> columns;
    private final Callable<List<T>> task;
    @FXML
    private Button refresh;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private TextField filterField;
    @FXML
    private Label reportName;
    private TableHelper tableHelper;
    private final String reportsName;
    @FXML
    private Button btnExcel;
    @FXML
    private Button btnPdf;
    @FXML
    private TableView<T> reportsTable;
    private FilteredList<T> filteredList;
    private SortedList<T> sortedList;
    private final Function<ListValue.Builder, List<T>> grpcFunction;
    private ListValue.Builder filters;


    public RevenueReportsController(List<ColumnDefinition<T, ?>> columns, String reportName, Callable<List<T>> task, Function<ListValue.Builder, List<T>> grpcFunction) {
        this.columns = columns;
        this.reportsName = reportName;
        this.task = task;
        filters = ListValue.newBuilder();
        this.grpcFunction = grpcFunction;
    }

    @FXML
    private void initialize() {
        this.fromDatePicker.setValue(LocalDate.now().minusDays(30));
        this.toDatePicker.setValue(LocalDate.now());

        this.reportName.setText(reportsName);
        Platform.runLater(() -> {
            setupDynamicTable(reportsTable, columns, observableList);
            if (observableList.isEmpty()) {
                fetchData(task);
            }
        });

        this.tableHelper = new TableHelper();
    }

    private void addFilters() {
        LocalDate from = fromDatePicker.getValue();
        LocalDate to = toDatePicker.getValue();

        long fromEpoch = from.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long toEpoch = to.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (from != null && to != null) {
            filters.addValues(Value.newBuilder().setNumberValue(fromEpoch).build());
            filters.addValues(Value.newBuilder().setNumberValue(toEpoch).build());
        }
    }

    private void setupFilter(List<T> list) {
        if (list.isEmpty()) {
            observableList.clear();
            observableList.addAll(list);
            showAlert(Alert.AlertType.INFORMATION, "No Records", "No Revenue Data Available !");
        } else {
            observableList.clear();
            observableList.addAll(list);
            // Step 1: create filtered list
            filteredList = new FilteredList<>(observableList, p -> true);

            // Step 2: bind filter logic to filterField
            filterField.textProperty().addListener((obs, oldVal, newVal) -> {
                filteredList.setPredicate(item -> {
                    System.out.println("Filter  :  " + item.toString());
                    if (newVal == null || newVal.isBlank()) return true;

                    String filter = newVal.toLowerCase();

                    for (ColumnDefinition<T, ?> column : columns) {
                        Object value = column.getter().apply(item);
                        if (value != null && value.toString().toLowerCase().contains(filter)) {
                            return true;
                        }
                    }
                    return false;
                    // Optionally use reflection or better toString()
                });
            });

            // Step 3: wrap in sorted list and bind to table
            sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(reportsTable.comparatorProperty());
            reportsTable.setItems(sortedList);
        }
    }

    private void fetchData(Callable<List<T>> task) {

        try {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Future<List<T>> future = executor.submit(task);
            List<T> list = future.get(); // this will block until result is ready
            executor.shutdown();
            this.setupFilter(list);
        } catch (Exception e) {
            showAlert(Alert.AlertType.INFORMATION, "Server Error", "Oops !!! Something went wrong");
        }
    }
//
//    private void setTable() {
//        reportsTable.setEditable(false);

    private void showAlert(Alert.AlertType alertType, String noRecords, String s) {
        Alert alert = new Alert(alertType);
        alert.setTitle(noRecords);
        alert.setContentText(s);
        alert.showAndWait();
    }

    public <T> void setupDynamicTable(TableView<T> tableView, List<ColumnDefinition<T, ?>> columns, ObservableList<T> data) {
        tableView.getColumns().clear();
        tableView.setEditable(false);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        for (ColumnDefinition<T, ?> def : columns) {
            addColumn(tableView, def);
        }
        tableView.setItems(data);
    }

    private <T, R> void addColumn(TableView<T> tableView, ColumnDefinition<T, R> def) {
        TableColumn<T, R> column = new TableColumn<>(def.header());

        // If no children, it's a leaf column — bind value
        if (def.subColumns().isEmpty()) {
            column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(def.getter().apply(cellData.getValue())));
            column.setPrefWidth(200);
            column.setMinWidth(150);
            column.setEditable(false);
            column.setResizable(true);
        } else {
            // If there are children, add them recursively
            for (ColumnDefinition<T, ?> child : def.subColumns()) {
                addChildColumn(column, child);
            }
        }

        tableView.getColumns().add(column);
    }

    // Helper method to add child columns recursively
    private <T, R> void addChildColumn(TableColumn<T, ?> parent, ColumnDefinition<T, R> def) {
        TableColumn<T, R> column = new TableColumn<>(def.header());

        if (def.subColumns().isEmpty()) {
            column.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(def.getter().apply(cellData.getValue())));
            column.setPrefWidth(150);
            column.setMinWidth(130);
            column.setEditable(false);
            column.setResizable(true);
        } else {
            for (ColumnDefinition<T, ?> child : def.subColumns()) {
                addChildColumn(column, child);
            }
        }

        parent.getColumns().add(column);
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
        event.consume();
    }

    @FXML
    private void onClickRefresh(ActionEvent actionEvent) {
        try {
            this.addFilters();
            ExecutorService executor = Executors.newSingleThreadExecutor();
            filters.build();
            Callable<List<T>> task = () -> grpcFunction.apply(filters);
            Future<List<T>> future = executor.submit(task);
            List<T> list = future.get(); // this will block until result is ready
            executor.shutdown();
            this.setupFilter(list);
        } catch (Exception e) {
            showAlert(Alert.AlertType.INFORMATION, "Server Error", "Oops !!! Something went wrong");
        } finally {
            filters = ListValue.newBuilder(); // Reset filters after fetching
        }

        actionEvent.consume();
    }
}
