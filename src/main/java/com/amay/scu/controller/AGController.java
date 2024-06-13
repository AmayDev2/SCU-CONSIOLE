package com.amay.scu.controller;

import com.amay.scu.enums.SLEStatus;
import com.amay.scu.sleobj.LiveSLE;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AGController  implements SLE {
    Logger logger = LoggerFactory.getLogger(getClass());
    @FXML
    private Button ag;

    @FXML
    private Label name;


    // Initial mouse cursor position
    private double initialX;
    private double initialY;
    // Initial button position
    private double initialLayoutX;
    private double initialLayoutY;

    @FXML
    void initialize() {
        ag.setOnMouseClicked(this::handleMouseClick);           //handles the mouse click event

    }
    @Override
    public boolean setScale(float x, float y, float z) {
        logger.debug("Setting scale of EFO to x:{} y:{} z:{}",x,y,z);
        ag.setScaleX(x);
        ag.setScaleY(y);
        ag.setScaleZ(z);
        return false;
    }

    @Override
    public boolean setScale(float x, float y) {
        return false;
    }

    @Override
    public boolean setStatus(SLEStatus status) {
        logger.debug("Setting status of AG to {}",status.getStatus());
        ag.setStyle(status.getStatus());

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

            logger.debug("Moving button {} to x:{} y:{}",ag.getId(),newLayoutX,newLayoutY);
        });

    }

    @Override
    public Object getId() {
        return ag.getId();
    }

    @Override
    public void updateStatus(SLEStatus status) {
        setStatus(status);

    }

    @Override
    public void updatePeripheralStatus(LiveSLE liveTOM) {

    }

    @Override
    public void updateOperationMode(LiveSLE liveTOM) {

    }

    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Handle left click
            logger.debug("Left click detected on button");
        } else if (event.getButton() == MouseButton.SECONDARY) {
            // Handle right click
            logger.debug("Right click detected on button");
        }
    }
}
