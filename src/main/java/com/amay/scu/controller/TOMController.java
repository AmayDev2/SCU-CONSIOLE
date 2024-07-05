package com.amay.scu.controller;

import com.amay.scu.enums.SLEStatus;
import com.amay.scu.enums.TOMOperationMode;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.popup.PopupContent;
import com.amay.scu.popup.SleCommandInfo;
import com.amay.scu.sleobj.LiveSLE;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.sleobj.propertyenums.PropertyUpdate;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.network.monitorandcontrol.OperationMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TOMController implements SLE {

    Logger logger = LoggerFactory.getLogger(TOMController.class);
    @FXML
    private Label name;
    @FXML
    private Button tom;

    SLELocationListObject.SLELocation location;
    // Initial mouse cursor position
    private double initialX;
    private double initialY;
    // Initial button position
    private double initialLayoutX;
    private double initialLayoutY;

    //live object
    private LiveTOM liveTOM = null;

    @FXML
    void initialize() {
        tom.setOnMouseClicked(this::handleMouseClick);           //handles the mouse click event
    }

    @Override
    public boolean setScale(float x, float y, float z) {
        logger.debug("Setting scale of EFO to x:{} y:{} z:{}", x, y, z);
        tom.setLayoutX(x);
        tom.setLayoutX(y);

        return false;
    }

    @Override
    public boolean setScale(float x, float y) {
        return false;
    }

    @Deprecated
    @Override
    public boolean setStatus(SLEStatus status) {
        logger.debug("Setting status of TOM to {}", status.getStatus());
        tom.setStyle(status.getStatus());
        return false;
    }


    public boolean setColor(TOMOperationMode status) {
        logger.debug("Setting status of TOM to {}", status.getColor());
        tom.setStyle(status.getColor());
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

            // Set new button position (within bounds)
            this.location.setXAxis(newLayoutX);
            this.location.setYAxis(newLayoutY);

            logger.debug("Moving button {} to x:{} y:{}",tom.getId(),newLayoutX,newLayoutY);
        });

    }

    @Override
    public void setLocation(SLELocationListObject.SLELocation location) {
        this.location=location;
        tom.setLayoutX(location.getXAxis());
        tom.setLayoutY(location.getYAxis());
    }

    @Override
    public Object getId() {
        return name.getText();
    }

    @Override
    public void updateStatus(SLEStatus status) {
        setStatus(status);
    }

   void updateOperationMode(TOMOperationMode status) {
        setColor(status);
    }

    @Override
    public void updatePeripheralStatus(LiveSLE tomPeripheralStatus) {
        LiveTOM liveTOM1;
        if (tomPeripheralStatus instanceof LiveTOM) {
            liveTOM1 = (LiveTOM) tomPeripheralStatus;
            this.liveTOM.setScu_connected(liveTOM1.isScu_connected());
            this.liveTOM.setCcu_connected(liveTOM1.isCcu_connected());
            this.liveTOM.setReader_connected(liveTOM1.isReader_connected());
            this.liveTOM.setScanner_connected(liveTOM1.isScanner_connected());
            this.liveTOM.setPrinter_connected(liveTOM1.isPrinter_connected());
            this.liveTOM.setPdu_connected(liveTOM1.isPdu_connected());
            this.liveTOM.setCash_drawer_connected(liveTOM1.isCash_drawer_connected());
            this.liveTOM.setUps_connected(liveTOM1.isUps_connected());
            printStatus(tomPeripheralStatus);
            this.liveTOM.setDeviceMode(liveTOM1.isScu_connected() || liveTOM1.isCcu_connected() || liveTOM1.isReader_connected() || liveTOM1.isScanner_connected() || liveTOM1.isPrinter_connected() || liveTOM1.isPdu_connected() || liveTOM1.isCash_drawer_connected() || liveTOM1.isUps_connected());
        }
    }

   void printStatus(LiveSLE tomPeripheralStatus) {
        System.out.println("SCU Connected: " + (liveTOM.isScu_connected() ? "Yes" : "No"));
        System.out.println("CCU Connected: " + (liveTOM.isCcu_connected() ? "Yes" : "No"));
        System.out.println("Reader Connected: " + (liveTOM.isReader_connected() ? "Yes" : "No"));
        System.out.println("Scanner Connected: " + (liveTOM.isScanner_connected() ? "Yes" : "No"));
        System.out.println("Printer Connected: " + (liveTOM.isPrinter_connected() ? "Yes" : "No"));
        System.out.println("PDU Connected: " + (liveTOM.isPdu_connected() ? "Yes" : "No"));
        System.out.println("Cash Drawer Connected: " + (liveTOM.isCash_drawer_connected() ? "Yes" : "No"));
        System.out.println("UPS Connected: " + (liveTOM.isUps_connected() ? "Yes" : "No"));
    }

    @Override
    public void updateOperationMode(LiveSLE liveTOM) {
        LiveTOM liveTOM1;
        if (liveTOM instanceof LiveTOM) {
            liveTOM1 = (LiveTOM) liveTOM;
            this.liveTOM.setOperationMode(( liveTOM1).getOperationMode());
        }
    }

    @Override
    public void setLiveSLE(LiveSLE liveTOM) {
        this.liveTOM= (LiveTOM) liveTOM;

        this.liveTOM.addPropertyChangeListener(event -> {
            System.out.println("Property " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
            logger.info("property updated ");
            if (event.getPropertyName().equals(PropertyUpdate.SLE_STATUS_UPDATED.name())) {
                logger.debug("listener new value {}", event.getNewValue());
                this.updateStatus((SLEStatus) event.getNewValue());
            } else if (event.getPropertyName().equals(PropertyUpdate.OPERATION_MODE.name())) {
                logger.debug("listener new value {}", event.getNewValue());
                this.updateOperationMode((TOMOperationMode) event.getNewValue());
            }
        });

        this.liveTOM.addParameterVersionChangeListener(event -> {
            System.out.println("Property " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
            logger.info("property updated ");
        });
    }

    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Handle left click
            PopupContent popupContent = new PopupContent(liveTOM);
            popupContent.show();
            logger.debug("Left click detected on button");
        } else if (event.getButton() == MouseButton.SECONDARY) {
            // Handle right click
            logger.debug("Right click detected on button");
        }
    }
}
