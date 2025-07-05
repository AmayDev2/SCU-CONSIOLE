package com.amay.scu.controller;

import com.amay.scu.enums.SLEStatus;
import com.amay.scu.enums.TOMOperationMode;
import com.amay.scu.enums.TVMOperationMode;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.popup.PopupContent;
import com.amay.scu.sleobj.LiveSLE;
import com.amay.scu.sleobj.LiveTOM;
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
    private SLELocationListObject.SLELocation location;

    private LiveTVM liveTVM=null;

    @FXML
    void initialize() {
        logger.info("Initializing TVMController");
        tvm.setOnMouseClicked(this::handleMouseClick);



    }
    @Override
    public boolean setScale(float x, float y, float z) {
        logger.debug("Setting scale of TVM to x:{} y:{} z:{}",x,y,z);
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
        logger.debug("Setting status of TVM to {}",status.getStatus());
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
    public void setLocation(SLELocationListObject.SLELocation location) {
        this.location=location;
        tvm.setLayoutX(location.getXAxis());
        tvm.setLayoutY(location.getYAxis());

    }

    @Override
    public String getId() {
        System.out.println("TVM ID : "+tvm.getId());
        return tvm.getId();
    }

    @Override
    public void updateStatus(SLEStatus status) {

    }

    @Override
    public void updatePeripheralStatus(LiveSLE liveTVM) {


    }

    @Override
    public void updateOperationMode(LiveSLE liveTVM) {  //TODO: if send directly LiveTVM object
        logger.info("Setting in live object {}",liveTVM.toString());
        LiveTVM liveTVM1;
        if (liveTVM instanceof LiveTVM) {
            liveTVM1 = (LiveTVM) liveTVM;
            this.liveTVM.setOperationMode(liveTVM1.getOperationMode());
        }

    }


    public boolean setColor(TVMOperationMode status) {
        logger.debug("Setting status of TOM to {}", status.getColor());
        Platform.runLater(() -> tvm.setStyle(status.getColor()));
        return true;
    }

    void updateOperationMode(TVMOperationMode status) {
        logger.info("Changing Operation Mode {}", status.getColor());
        if(!this.setColor(status)){
            throw new IllegalStateException("Operation Mode not set");
        }
    }

    @Override
    public void setLiveSLE(LiveSLE liveTVM) {

            this.liveTVM= (LiveTVM) liveTVM;

            this.liveTVM.addPropertyChangeListener(event -> {

                System.out.println("Property TOM  " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
                logger.info("property updated ");
                if (event.getPropertyName().equals(PropertyUpdate.SLE_STATUS_UPDATED.name())) {
                    logger.debug("listener new value {}", event.getNewValue());
                    this.updateStatus((SLEStatus) event.getNewValue());
                } else if (event.getPropertyName().equals(PropertyUpdate.OPERATION_MODE.name())) {
                    logger.debug("listener new value {}", event.getNewValue());
                    this.updateOperationMode((TVMOperationMode) event.getNewValue());
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

            this.liveTVM.addParameterVersionChangeListener(event -> {
                System.out.println("Property " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
                logger.info("property updated ");
            });

    }

    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Handle left click
            PopupContent popupContent = new PopupContent(liveTVM,"Left");
            popupContent.show();
            logger.debug("Left click detected on button");
        }

    }
}
