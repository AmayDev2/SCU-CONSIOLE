package com.amay.scu.report.controller;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.StringConverter;
import org.controlsfx.control.ToggleSwitch;
import org.kordamp.ikonli.javafx.FontIcon;

import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Function;

public class TableHelper {

    public <T, V> TableColumn<T, V> createEditableColumn(
            String title,
            Function<T, V> getter,
            BiConsumer<T, V> setter,
            TableView<T> table,
            Set<T> editedSet,
            StringConverter<V> converter
    ) {
        TableColumn<T, V> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(getter.apply(cellData.getValue())));
        col.setCellFactory(TextFieldTableCell.forTableColumn(converter));

        col.setOnEditCommit(event -> {
            T model = event.getRowValue();
            V newValue = event.getNewValue();
            setter.accept(model, newValue);
            editedSet.add(model);
            table.refresh();
        });

        col.setPrefWidth(150);
        col.setMinWidth(50);
        return col;
    }

    public  <T, R> TableColumn<T, R> createNonEditableColumn(
            String title,
            Function<T, R> getter
    ) {
        TableColumn<T, R> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(getter.apply(cellData.getValue())));
        col.setEditable(false);
        col.setPrefWidth(150);
        col.setMinWidth(50);
        return col;
    }


    public <T, E extends Enum<E>> TableColumn<T, E> createEnumComboBoxColumn(
            String title,
            Function<T, E> getter,
            BiConsumer<T, E> setter,
            TableView<T> table,
            Set<T> editedSet,
            E[] enumValues
    ) {
        TableColumn<T, E> col = new TableColumn<>(title);
        col.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(getter.apply(cellData.getValue())));
        col.setCellFactory(ComboBoxTableCell.forTableColumn(enumValues));
        col.setOnEditCommit(event -> {
            T model = event.getRowValue();
            setter.accept(model, event.getNewValue());
            editedSet.add(model);
            table.refresh();
        });

        col.setPrefWidth(150);
        col.setMinWidth(50);
        return col;
    }

    public <T> TableColumn<T, Void> createDeleteActionColumn(
            TableView<T> table,
            ObservableList<T> observableItems
    ) {
        TableColumn<T, Void> actionCol = new TableColumn<>("Action");

        actionCol.setCellFactory(col -> new TableCell<>() {
            private final Button deleteBtn = new Button();

            {
//                FontIcon deleteIcon = new FontIcon(Material2OutlinedAL.DELETE);
//                deleteBtn.setGraphic(deleteIcon);
//                deleteBtn.getStyleClass().addAll(Styles.DANGER, Styles.FLAT);
                deleteBtn.setTooltip(new Tooltip("Delete row"));

                deleteBtn.setOnAction(e -> {
                    int index = getIndex();
                    if (index >= 0 && index < table.getItems().size()) {
                        T item = table.getItems().get(index);
                        observableItems.remove(item);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : deleteBtn);
            }
        });

        actionCol.setPrefWidth(70);
        return actionCol;
    }


    public <M, P> TableColumn<M, Boolean> createToggleSwitchCol(
            String title,
            Function<M, P> permissionGetter,
            Function<P, Boolean> valueGetter,
            BiConsumer<P, Boolean> valueSetter
    ) {
        TableColumn<M, Boolean> col = new TableColumn<>(title);

        col.setCellValueFactory(cellData -> {
            M model = cellData.getValue();
            P permissionObj = permissionGetter.apply(model);
            Boolean value = valueGetter.apply(permissionObj);
            return new SimpleBooleanProperty(value).asObject();
        });

        col.setCellFactory(colDef -> new TableCell<>() {
            private final ToggleSwitch toggle = new ToggleSwitch();

            {
                toggle.selectedProperty().addListener((obs, oldVal, newVal) -> {
                    M model = getTableRow().getItem();
                    if (model != null) {
                        P permissionObj = permissionGetter.apply(model);
                        valueSetter.accept(permissionObj, newVal);
                    }
                });
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    toggle.setSelected(item);
                    setGraphic(toggle);
                }
            }
        });

        col.setPrefWidth(150);
        col.setMinWidth(50);
        return col;
    }

}
