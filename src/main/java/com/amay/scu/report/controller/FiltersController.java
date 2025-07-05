package com.amay.scu.report.controller;

import com.amay.scu.auth.functional.UpdateFilter;
import com.amay.scu.popup.PopupWindow;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FiltersController implements Initializable {

    @FXML private TableView<FilterItem> tableView;
    @FXML private TableColumn<FilterItem, Boolean> selectColumn;
    @FXML private TableColumn<FilterItem, String> titleColumn;
    @FXML private TableColumn<FilterItem, String> inputColumn;

    @FXML private Button applyButton;
    @FXML private Button resetButton;
    @FXML private Button closeButton;

    private final PopupWindow popupWindow;
//    private final List<String> filters;

    private List<FilterItem> selectedItems = new ArrayList<>();
    private final UpdateFilter updateFilter;

    public FiltersController(UpdateFilter updateFilter,PopupWindow popupWindow, List<FilterItem> filters) {
        this.popupWindow = popupWindow;
        this.selectedItems= filters;
        this.updateFilter = updateFilter;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Enable table editing
        tableView.setEditable(true);

        // Select (Checkbox) column
        selectColumn.setEditable(true);
        selectColumn.setCellValueFactory(cellData -> cellData.getValue().selectedProperty());
        selectColumn.setCellFactory(tc -> {
            CheckBoxTableCell<FilterItem, Boolean> cell = new CheckBoxTableCell<>();
            cell.setEditable(true);
            return cell;
        });

        // Title column (non-editable)
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());

        // Input column (TextField)
        inputColumn.setCellValueFactory(cellData -> cellData.getValue().inputProperty());
        inputColumn.setCellFactory(column -> new TableCell<>() {
            private TextField textField;
            private boolean isUpdating = false;

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                } else {
                    if (textField == null) {
                        textField = new TextField();
                        textField.setAlignment(Pos.CENTER); // Align text to the left
                        textField.setNodeOrientation(NodeOrientation.INHERIT); // Ensure LTR direction
                        textField.setText(item); // Set initial value without triggering loop
                        textField.getStyleClass().add("underline-textfield");
                        textField.textProperty().addListener((obs, oldVal, newVal) -> {
                            if (isUpdating) return; // Skip listener when updating from code

                            FilterItem filter = getTableView().getItems().get(getIndex());
                            if (filter != null) {
                                isUpdating = true;
                                filter.inputProperty().set(newVal); // Also update model
                                isUpdating = false;
                            }
                        });
                    }


                    setGraphic(textField);
                }
            }
        });



        // Add input filters
        for (FilterItem filter : selectedItems) {
            tableView.getItems().add(filter);
        }

        // Buttons
        applyButton.setOnAction(e -> {
            selectedItems = getSelectedFilters();
            this.updateFilter.execute(selectedItems);
        });



        resetButton.setOnAction(e -> resetTable());
        closeButton.setOnAction(e -> popupWindow.Close());
    }

    public String reverseString(String input) {
        char[] chars = input.toCharArray();
        StringBuilder reversed = new StringBuilder();
        for (int i = chars.length - 1; i >= 0; i--) {
            reversed.append(chars[i]);
        }
        return reversed.toString();
    }



    public List<FilterItem> getSelectedItems() {
        return selectedItems;
    }

    public List<String> getSelectedFilterTitles() {
        List<String> titles = new ArrayList<>();
        for (FilterItem item : selectedItems) {
            titles.add(item.titleProperty().get());
        }
        return titles;
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

    private void resetTable() {
        for (FilterItem item : tableView.getItems()) {
            item.selectedProperty().set(false);
            item.inputProperty().set("");
        }
    }
}
