package com.amay.scu.report.controller;

import com.amay.scu.ViewFactory;
import com.amay.scu.popup.PopupWindow;
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
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

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
    private GridPane filtersGridView;
    @FXML
    private FlowPane chipperBucket;
    @FXML
    private Button filter;
    @FXML
    private Button refresh;
    @FXML
    private DatePicker fromDatePicker;
    @FXML
    private DatePicker toDatePicker;
    @FXML
    private TextField filterField;
//    @FXML
//    private Label reportName;
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
    private final List<FilterItem> filterItems;



    /**
     * Constructor for RevenueReportsController.
     *
     * @param columns      List of column definitions for the table.
     * @param reportName   Name of the report to be displayed.
     * @param task         Callable task to fetch data for the report.
     * @param grpcFunction Function to convert ListValue.Builder to List<T> for gRPC calls.
     */
    public RevenueReportsController(List<ColumnDefinition<T, ?>> columns,
                                    String reportName,
                                    Callable<List<T>> task,
                                    Function<ListValue.Builder,
                                    List<T>> grpcFunction,
                                    List<FilterItem> filterItems
    ) {
        this.columns = columns;
        this.reportsName = reportName;
        this.task = task;
        filters = ListValue.newBuilder();
        this.grpcFunction = grpcFunction;
        this.filterItems = filterItems;
        for(int i = 0; i < 10; i++) {
            filters.addValues(Value.newBuilder().build());
        }
    }

    @FXML
    private void initialize() {
        this.fromDatePicker.setValue(LocalDate.now().minusDays(30));
        this.toDatePicker.setValue(LocalDate.now());
        this.setFilters();
        this.updateFilters(filterItems);

        Platform.runLater(() -> {
            setupDynamicTable(reportsTable, columns, observableList);
            if (observableList.isEmpty()) {
                fetchData(task);
            }
        });

        this.tableHelper = new TableHelper();
    }

    private void setFilters() {
        try {
            FXMLLoader fxmlLoader;
            fxmlLoader = ViewFactory.getFilterView();
            fxmlLoader.setControllerFactory(c -> new FiltersControllerIn((filters -> {
                this.updateFilters(filterItems);
            }), this.filterItems));
            filtersGridView.add(fxmlLoader.load(), 1, 0);
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to load filters view.");
        }
    }

    private void addFilters() {
        LocalDate from = fromDatePicker.getValue();
        LocalDate to = toDatePicker.getValue();

        long fromEpoch = from.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long toEpoch = to.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        if (from != null && to != null) {
            filters.setValues(0,Value.newBuilder().setNumberValue(fromEpoch).build());
            filters.setValues(1,Value.newBuilder().setNumberValue(toEpoch).build());
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
        }
        actionEvent.consume();
    }

  void onClickFilterDep(ActionEvent actionEvent) {
        PopupWindow popupWindow = new PopupWindow();
        FXMLLoader fxmlLoader;

        fxmlLoader = ViewFactory.getFilterView();
        fxmlLoader.setControllerFactory(c -> new FiltersController((filters->{this.updateFilters(filterItems);popupWindow.Close();}),popupWindow, this.filterItems));

        popupWindow.show(fxmlLoader);
        actionEvent.consume();
    }

    private void updateFilters(List<FilterItem> filterItems) {
        chipperBucket.getChildren().clear();
        filters.addValues(Value.newBuilder().setStringValue("from").build());
        for (FilterItem item : filterItems) {
            if(item.inputProperty().get().isBlank()){
                item.selectedProperty().set(false);
                continue;
            }

            if(item.selectedProperty().get()) {
                System.out.println("✔ " + item.titleProperty().get() + " " + item.titleValueProperty().get() + " Input: " + item.inputProperty().get());
                HBox chip = createChip(item);
                chipperBucket.getChildren().add(chip);
                filters.setValues(item.indexProperty().get(),Value.newBuilder().setStringValue( item.inputProperty().get()).build());
            }

        }
        refresh.fire();

    }



    private HBox createChip(FilterItem filter) {
        Label title = new Label(filter.titleProperty().get()); // or filter.getInput()
        Button close = new Button("×");

        close.setOnAction(e -> {
            // Remove chip visually
            chipperBucket.getChildren().remove(close.getParent());
            // Deselect the filter in the table
            filter.selectedProperty().set(false);
        });

        HBox chip = new HBox(title, close);
        chip.setAlignment(Pos.CENTER);
        chip.setSpacing(5);
        chip.setPadding(new Insets(5, 10, 5, 10));
        chip.setStyle("-fx-background-color: #e0e0e0; -fx-background-radius: 15;");
        title.setStyle("-fx-font-size: 12px;");
        close.setStyle("-fx-background-color: transparent; -fx-font-size: 12px;");

        return chip;
    }


/*    PopupWindow popupWindow = new PopupWindow();
    FXMLLoader fxmlLoader;

    List<String> columnHeaders = new ArrayList<>();
        for (TableColumn<?, ?> column : reportsTable.getColumns()) {
        collectColumnHeaders(column, columnHeaders);
    }

    fxmlLoader = ViewFactory.getFilterView();
        fxmlLoader.setControllerFactory(c -> new FiltersController(popupWindow, columnHeaders));

        popupWindow.show(fxmlLoader);
        actionEvent.consume();*/


    private void collectColumnHeaders(TableColumn<?, ?> column, List<String> list, String... parentHeaders) {
        String headerPrefix = String.join(" > ", parentHeaders);

        if (column.getColumns().isEmpty()) {
            String fullHeader = headerPrefix.isEmpty() ? column.getText() : headerPrefix + " > " + column.getText();
            list.add(fullHeader);
        } else {
            for (TableColumn<?, ?> child : column.getColumns()) {
                collectColumnHeaders(child, list, append(parentHeaders, column.getText()));
            }
        }
    }

    // Utility method to append to varargs
    private String[] append(String[] arr, String newItem) {
        String[] result = new String[arr.length + 1];
        System.arraycopy(arr, 0, result, 0, arr.length);
        result[arr.length] = newItem;
        return result;
    }


}
