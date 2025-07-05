package com.amay.scu.controller;

import com.amay.scu.enums.SLEStatus;
import com.amay.scu.enums.TROperationMode;
import com.amay.scu.enums.TVMOperationMode;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.popup.PopupContent;
import com.amay.scu.sleobj.LiveSLE;
import com.amay.scu.sleobj.LiveTR;
import com.amay.scu.sleobj.LiveTVM;
import com.amay.scu.sleobj.propertyenums.PropertyUpdate;
import com.amay.scu.sles.components.SLE;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TRController implements SLE {

     @FXML
    private Button tr;

    @FXML
    private Label name;

    Logger logger = LoggerFactory.getLogger(getClass());

    // Initial mouse cursor position
    private double initialX;
    private double initialY;
    // Initial button position
    private double initialLayoutX;
    private double initialLayoutY;
    private  SLELocationListObject.SLELocation location;

    private LiveTR liveTR;

    @FXML
    void initialize() {
        tr.setOnMouseClicked(this::handleMouseClick);           //handles the mouse click event
    }
    @Override
    public boolean setScale(float x, float y, float z) {
        logger.debug("Setting scale of TR to x:{} y:{} z:{}",x,y,z);
        tr.setScaleX(x);
        tr.setScaleY(y);
        tr.setScaleZ(z);
        return false;
    }

    @Override
    public boolean setScale(float x, float y) {
        return false;
    }

    @Override
    public boolean setStatus(SLEStatus status) {
        logger.debug("Setting status of TR to {}",status.getStatus());
        tr.setStyle(status.getStatus());
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
    public void setLocation(SLELocationListObject.SLELocation location) {
        this.location=location;
        tr.setLayoutX(location.getXAxis());
        tr.setLayoutY(location.getYAxis());

    }

    @Override
    public String getId() {
        return tr.getId();
    }

    @Override
    public void updateStatus(SLEStatus status) {

    }

    @Override
    public void updatePeripheralStatus(LiveSLE liveTOM) {

    }

    @Override
    public void updateOperationMode(LiveSLE liveTR) {
        logger.info("Setting in live object {}",liveTR.toString());
        LiveTR liveTR1;
        if (liveTR instanceof LiveTR) {
            liveTR1 = (LiveTR) liveTR;
            this.liveTR.setOperationMode(liveTR1.getOperationMode());
        }

    }

    public boolean setColor(TROperationMode status) {
        logger.debug("Setting status of TOM to {}", status.getColor());
        Platform.runLater(() -> tr.setStyle(status.getColor()));
        return true;
    }

    void updateOperationMode(TROperationMode status) {
        logger.info("Changing Operation Mode {}", status.getColor());
        tr.setStyle(status.getColor());

    }

    @Override
    public void setLiveSLE(LiveSLE liveTR) {

        this.liveTR= (LiveTR) liveTR;

        this.liveTR.addPropertyChangeListener(event -> {

            System.out.println("Property TOM  " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
            logger.info("property updated ");
            if (event.getPropertyName().equals(PropertyUpdate.SLE_STATUS_UPDATED.name())) {
                logger.debug("listener new value {}", event.getNewValue());
                this.updateStatus((SLEStatus) event.getNewValue());
            } else if (event.getPropertyName().equals(PropertyUpdate.OPERATION_MODE.name())) {
                logger.debug("listener new value {}", event.getNewValue());
                this.updateOperationMode((TROperationMode) event.getNewValue());
            }

//            System.out.println("Property " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
//            logger.info("property updated ");
//            if (event.getPropertyName().equals(PropertyUpdate.SLE_STATUS_UPDATED.name())) {
//                logger.debug("listener new value {}", event.getNewValue());
//                this.updateStatus((SLEStatus) event.getNewValue());
//            } else if (event.getPropertyName().equals(PropertyUpdate.OPERATION_MODE.name())) {
//                logger.debug("listener new value {}", event.getNewValue());
//                this.updateOperationMode((TOMOperationMode) event.getNewValue());
//            }
        });

        this.liveTR.addParameterVersionChangeListener(event -> {
            System.out.println("Property " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
            logger.info("property updated ");
        });

    }

    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Handle left click
            PopupContent popupContent = new PopupContent(liveTR,"Left");
            popupContent.show();
            logger.debug("Left click detected on button");
        }
//        else if (event.getButton() == MouseButton.SECONDARY) {
//            // Handle right click
//            PopupContent popupContent = new PopupContent(liveTOM,"Right");
//            popupContent.show();
//            logger.debug("Right click detected on button");
//        }
    }
}
