package com.amay.scu.controller;

import com.amay.scu.enums.AGOperationMode;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.enums.TOMOperationMode;
import com.amay.scu.popup.PopupContent;
import com.amay.scu.sleobj.LiveAG;
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
    private LiveAG liveAG=null;

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
    public void updatePeripheralStatus(LiveSLE agPeripheralStatus) {
        LiveAG liveAG1;
        if (agPeripheralStatus instanceof LiveAG) {
            liveAG1 = (LiveAG) agPeripheralStatus;
            this.liveAG.setScu_connected(liveAG1.isScu_connected());
            this.liveAG.setCcu_connected(liveAG1.isCcu_connected());
            this.liveAG.setReader_connected(liveAG1.isReader_connected());
            this.liveAG.setScanner_connected(liveAG1.isScanner_connected());
            this.liveAG.setPrinter_connected(liveAG1.isPrinter_connected());
            this.liveAG.setPdu_connected(liveAG1.isPdu_connected());
            this.liveAG.setCash_drawer_connected(liveAG1.isCash_drawer_connected());
            this.liveAG.setUps_connected(liveAG1.isUps_connected());
//            printStatus(tomPeripheralStatus);
            this.liveAG.setDeviceMode(liveAG1.isScu_connected() || liveAG1.isCcu_connected() || liveAG1.isReader_connected() || liveAG1.isScanner_connected() || liveAG1.isPrinter_connected() || liveAG1.isPdu_connected() || liveAG1.isCash_drawer_connected() || liveAG1.isUps_connected());
        }

    }

    @Override
    public void updateOperationMode(LiveSLE liveAG) {
        LiveAG liveAG1;
        if (liveAG instanceof LiveAG) {
            liveAG1 = (LiveAG) liveAG;
            this.liveAG.setOperationMode(( liveAG1).getOperationMode());
        }

    }

    @Override
    public void setLiveSLE(LiveSLE liveAG) {
        this.liveAG= (LiveAG) liveAG;

        this.liveAG.addPropertyChangeListener(event -> {
            System.out.println("Property " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
            logger.info("property updated ");
            if (event.getPropertyName().equals(PropertyUpdate.SLE_STATUS_UPDATED.name())) {
                logger.debug("listener new value {}", event.getNewValue());
                this.updateStatus((SLEStatus) event.getNewValue());
            } else if (event.getPropertyName().equals(PropertyUpdate.OPERATION_MODE.name())) {
                logger.debug("listener new value {}", event.getNewValue());
                this.updateOperationMode((AGOperationMode) event.getNewValue());
            }
        });

        this.liveAG.addParameterVersionChangeListener(event -> {
            System.out.println("Property " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
            logger.info("property updated ");
        });

    }

    // updating the operation mode
    private void updateOperationMode(AGOperationMode agOperationMode) {
        ag.setStyle(agOperationMode.getColor());
        logger.info("Updating Operation Mode Of AG {}",ag);
    }

    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Handle left click
            PopupContent popupContent = new PopupContent(liveAG);
            popupContent.show();
            logger.debug("Left click detected on button");
        } else if (event.getButton() == MouseButton.SECONDARY) {
            // Handle right click
            logger.debug("Right click detected on button");
        }
    }
}
