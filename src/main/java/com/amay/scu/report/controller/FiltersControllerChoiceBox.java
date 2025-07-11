package com.amay.scu.report.controller;

import com.amay.scu.auth.functional.UpdateFilter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FiltersControllerChoiceBox implements Initializable {

    @FXML private TableView<FilterItem> tableView;
    @FXML private TableColumn<FilterItem, String> titleColumn;
    @FXML private TableColumn<FilterItem, String> inputColumn;

    private List<FilterItem> selectedItems = new ArrayList<>();
    private final UpdateFilter updateFilter;

    public FiltersControllerChoiceBox(UpdateFilter updateFilter, List<FilterItem> filters) {
        this.updateFilter = updateFilter;
        this.selectedItems = filters;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        tableView.setEditable(true);

        // Title column: display only
        titleColumn.setCellValueFactory(cellData ->
                cellData.getValue().titleProperty()
        );


        inputColumn.setCellValueFactory(cellData -> cellData.getValue().inputProperty());
        inputColumn.setCellFactory(column -> new TableCell<>() {
            private TextField textField;
            private ChoiceBox<String> choiceBox;

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getIndex() >= tableView.getItems().size()) {
                    setGraphic(null);
                    return;
                }

                FilterItem filter = tableView.getItems().get(getIndex());

                List<String> choices = filter.getChoices();

                if (choices == null || choices.isEmpty()) {
                    // Use TextField when no choices
                    if (textField == null) {
                        textField = new TextField();
                        textField.setAlignment(Pos.CENTER_LEFT);
                        textField.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                        textField.getStyleClass().add("underline");
                        textField.setMaxWidth(Double.MAX_VALUE);
                        textField.setText(filter.inputProperty().get());
                        filter.inputProperty().set(textField.getText());
                        // Listen for changes
                        textField.textProperty().addListener((obs, oldVal, newVal) -> {
                             filter.inputProperty().set(newVal);
                        });
                    }


                    setGraphic(textField);
                } else {
                    // Use ChoiceBox when choices are present
                    choiceBox = new ChoiceBox<>();
                    choiceBox.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);
                    choiceBox.setMaxWidth(Double.MAX_VALUE);
                    choiceBox.getStyleClass().add("underline");

                    choiceBox.getItems().setAll(choices);
                    choiceBox.getSelectionModel().select(filter.inputProperty().get());

                    // Listen for changes
                    choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                        if (newVal != null) {
                            filter.inputProperty().set(newVal);
                        }
                    });

                    setGraphic(choiceBox);
                }
            }
        });



        // Load existing filters into table
        tableView.getItems().setAll(selectedItems);
    }


    public List<FilterItem> getSelectedFilters() {
        List<FilterItem> selected = new ArrayList<>();
        for (FilterItem item : tableView.getItems()) {
            if (item.selectedProperty().get()) {
                selected.add(item);
            }
        }
        return selected;
    }

    public void applyFilters() {
        selectedItems = getSelectedFilters();
        this.updateFilter.execute(selectedItems);
    }

    public void resetTable() {
            for (FilterItem item : tableView.getItems()) {
                item.selectedProperty().set(false);
                Platform.runLater(()->item.inputProperty().set(""));
            }
        }

}
