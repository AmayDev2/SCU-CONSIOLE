package com.amay.scu.report.controller;

import com.amay.scu.auth.functional.UpdateFilter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FiltersControllerIn implements Initializable {

    @FXML private TableView<FilterItem> tableView;
    @FXML private TableColumn<FilterItem, String> titleColumn;
    @FXML private TableColumn<FilterItem, String> inputColumn;

    private List<FilterItem> selectedItems = new ArrayList<>();
    private final UpdateFilter updateFilter;

    public FiltersControllerIn(UpdateFilter updateFilter, List<FilterItem> filters) {
        this.updateFilter = updateFilter;
        this.selectedItems = filters;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setEditable(true);

        // Title column: display only
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        // Input column: editable TextField
        inputColumn.setCellValueFactory(cellData -> cellData.getValue().inputProperty());
        inputColumn.setCellFactory(column -> new TableCell<>() {
            private TextField textField;

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() >= tableView.getItems().size()) {
                    setGraphic(null);
                } else {
                    FilterItem filter = tableView.getItems().get(getIndex());

                    if (textField == null) {
                        textField = new TextField();
                        textField.setAlignment(Pos.CENTER_LEFT);
                        textField.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                        textField.getStyleClass().add("underline-textfield");

                        // Listener to update model
                        textField.textProperty().addListener((obs, oldVal, newVal) -> {
                            filter.inputProperty().set(newVal);
                        });
                    }

                    textField.setText(filter.inputProperty().get());
                    setGraphic(textField);
                }
            }
        });

        // Load existing filters into table
        tableView.getItems().setAll(selectedItems);
    }

}
