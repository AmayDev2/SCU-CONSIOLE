package com.amay.scu.controller;

import com.amay.scu.enums.SLEStatus;
import com.amay.scu.sles.components.SLE;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TVMController implements SLE {
    Logger logger = LoggerFactory.getLogger(getClass());

    @FXML
    private Label name;

    @FXML
    private Button tvm;

    // Initial mouse cursor position
    private double initialX;
    private double initialY;
    // Initial button position
    private double initialLayoutX;
    private double initialLayoutY;

    @FXML
    void initialize() {


    }
    @Override
    public boolean setScale(float x, float y, float z) {
        logger.debug("Setting scale of EFO to x:{} y:{} z:{}",x,y,z);
        Platform.runLater(() -> {
            tvm.setLayoutX(x);
            tvm.setLayoutX(y);
        });

        return false;
    }

    @Override
    public boolean setScale(float x, float y) {
        return false;
    }

    @Override
    public boolean setStatus(SLEStatus status) {
        logger.debug("Setting status of TOM to {}",status.getStatus());
        tvm.setStyle(status.getStatus());

        return false;
    }

    @Override
    public void setName(String id) {
        name.setText(id);
    }

    @Override
    public void setMovingProperties(Button button, AnchorPane anchorPane) {


        button.setOnMousePressed(event -> {
            // Save initial mouse cursor position
            initialX = event.getSceneX();
            initialY = event.getSceneY();
            // Save initial button position
            initialLayoutX = button.getLayoutX();
            initialLayoutY = button.getLayoutY();

        });

        button.setOnMouseDragged(event -> {
            // Calculate new button position
            double offsetX = event.getSceneX() - initialX;
            double offsetY = event.getSceneY() - initialY;
            double newLayoutX = initialLayoutX + offsetX;
            double newLayoutY = initialLayoutY + offsetY;
            // Set new button position (within bounds)
            if (newLayoutX >= 0 && newLayoutX + button.getWidth() <= anchorPane.getWidth()) {
                button.setLayoutX(newLayoutX);
            }
            if (newLayoutY >= 0 && newLayoutY + button.getHeight() <= anchorPane.getHeight()) {
                button.setLayoutY(newLayoutY);
            }
        });

    }

    @Override
    public Object getId() {
        return null;
    }

    @Override
    public void updateStatus(SLEStatus status) {

    }
}
