package com.amay.scu.controller;

import com.amay.scu.enums.AGOperationMode;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.enums.Zone;
import com.amay.scu.images.ImagePath;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.popup.PopupContent;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveSLE;
import com.amay.scu.sleobj.propertyenums.PropertyUpdate;
import com.amay.scu.sles.components.SLE;
import com.amay.scu.util.ImageLoaderUtil;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AGController implements SLE {


    @FXML private RowConstraints middle;
    @FXML
    private Rectangle cabinet;
    @FXML
    private Button arrowLeft;
    @FXML
    private Button arrowRight;
    @FXML
    private ImageView imageView;
    private Logger logger = LoggerFactory.getLogger(getClass());

    @FXML
    private Button ag;

    @FXML
    private VBox agBackground;

    @FXML
    private Label name;
    SLELocationListObject.SLELocation location;

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
        logger.debug("Setting scale of AG to x:{} y:{} z:{}",x,y,z);
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
            this.location.setXAxis(newLayoutX);
            this.location.setYAxis(newLayoutY);
            logger.debug("Moving button {} to x:{} y:{}",ag.getId(),newLayoutX,newLayoutY);
        });

    }

    @Override
    public void setLocation(SLELocationListObject.SLELocation location) {
        this.location=location;
        ag.setLayoutX(location.getXAxis());
        ag.setLayoutY(location.getYAxis());
    }

    @Override
    public String getId() {
        return ag.getId();
    }

    @Override
    public void updateStatus(SLEStatus status) {
        this.setStatus(status);

    }

    @Override
    public void updatePeripheralStatus(LiveSLE agPeripheralStatus) {
        LiveAG liveAG1;
        if (agPeripheralStatus instanceof LiveAG) {
            liveAG1 = (LiveAG) agPeripheralStatus;
            this.liveAG.setScu_connected(liveAG1.isScu_connected());
            this.liveAG.setCcu_connected(liveAG1.isCcu_connected());
//            this.liveAG.setReader_connected(liveAG1.isReader_connected());
//            this.liveAG.setScanner_connected(liveAG1.isScanner_connected());
//            this.liveAG.setPrinter_connected(liveAG1.isPrinter_connected());
//            this.liveAG.setPdu_connected(liveAG1.isPdu_connected());
//            this.liveAG.setCash_drawer_connected(liveAG1.isCash_drawer_connected());
            this.liveAG.setUps_connected(liveAG1.isUps_connected());
//            printStatus(tomPeripheralStatus);
            this.liveAG.setDeviceMode(liveAG1.isScu_connected() || liveAG1.isCcu_connected() || /*liveAG1.isReader_connected() || liveAG1.isScanner_connected() || liveAG1.isPrinter_connected() || liveAG1.isPdu_connected() || liveAG1.isCash_drawer_connected() ||*/ liveAG1.isUps_connected());
        }

    }

    @Override
    public void updateOperationMode(LiveSLE liveAG) {
        logger.info("Update AG Mode");
        LiveAG liveAG1;
        if (liveAG instanceof LiveAG) {
            liveAG1 = (LiveAG) liveAG;
            this.liveAG.setOperationMode(( liveAG1).getOperationMode());
        }

    }

    public void addCabinetRectangle() {
        Rectangle cabinet = new Rectangle(130, 20);
        cabinet.getStyleClass().add("cabinet");
        agBackground.getChildren().add(0, cabinet); // Insert at the top
    }

    @Override
    public void setLiveSLE(LiveSLE liveAG) {
        this.liveAG= (LiveAG) liveAG;
        this.identifyAGSubtype(this.liveAG);
        if(this.liveAG.getStationDevicesDTO().isLast()){
            addCabinetRectangle();
        }

        this.liveAG.addPropertyChangeListener(event -> {
            System.out.println("Property AG " + event.getPropertyName() + " changed from " + event.getOldValue() + " to " + event.getNewValue());
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


    private void identifyAGSubtype(LiveAG liveAG) {

        // load from enum
        String deviceType= liveAG.getDeviceType();
        switch(deviceType){
            case "05" -> {
                logger.info("AG identified as Entry");
                if(liveAG.getStationDevicesDTO().getZone().getZoneNumber()% 2 == 0){
                    logger.info("AG is in Zone Two");
                    arrowLeft.setVisible(false);
                    arrowRight.setVisible(true);
                }else{
                    logger.info("AG is in Zone One");
                    arrowLeft.setVisible(true);
                    arrowRight.setVisible(false);
                }

            }case "06"->{
                logger.info("AG identified as Exit");
                if(liveAG.getStationDevicesDTO().getZone().getZoneNumber()% 2 == 0) {
                    logger.info("AG is in Zone Four");
                    arrowLeft.setVisible(true);
                    arrowRight.setVisible(false);
                }else {
                    logger.info("AG is in Zone Three");
                    arrowRight.setVisible(true);
                    arrowLeft.setVisible(false);
                }
            }case "07" -> {
                logger.info("AG identified as Wide");
                arrowRight.setVisible(true);
                arrowLeft.setVisible(true);
                this.wide();
            }case "08" -> {
                logger.info("AG identified as Bi-Directional");
//                arrowRight.setVisible(true);
//                arrowLeft.setVisible(true);
            }default -> {
                logger.warn("AG identified as Unknown Type: {}", deviceType);
            }

        }


    }

    // updating the operation mode
    private void updateOperationMode(AGOperationMode agOperationMode) {
        logger.info("Updating Operation Mode Of AG {} {}",ag,agOperationMode.getColor());
        agBackground.setStyle(agOperationMode.getColor());

        //TODO: Remove these logics pass the images path in enum itself instead of color
//        if(AGOperationMode.IN_SERVICE.equals(agOperationMode)){
//        imageView.setImage(ImageLoaderUtil.loadImage(ImagePath.BI_DIRECTIONAL_IN_SERVICE));
//        }
        /*else if(AGOperationMode.DEFICIENT.equals(agOperationMode)){
            imageView.setImage(ImageLoaderUtil.loadImage(ImagePath.BI_DIRECTIONAL_DEFICIENT));
        }
        else if(AGOperationMode.OUT_OF_SERVICE.equals(agOperationMode)){
            imageView.setImage(ImageLoaderUtil.loadImage(ImagePath.BI_DIRECTIONAL_OUT_OF_SERVICE));
        }
        else if(AGOperationMode.MAINTENANCE.equals(agOperationMode)){
            imageView.setImage(ImageLoaderUtil.loadImage(ImagePath.BI_DIRECTIONAL_MAINTENANCE));
        }else {
            imageView.setImage(ImageLoaderUtil.loadImage(ImagePath.BI_DIRECTIONAL_NOT_WORKING));
        }*/

    }

    public void wide(){
        Platform.runLater(() -> {
        double actualHeight = ag.getBoundsInParent().getHeight();
        logger.info("Setting AG to wide mode  {}", actualHeight);
        middle.setPrefHeight(middle.getPrefHeight()*(1.5));
        actualHeight = ag.getBoundsInParent().getHeight();
        logger.info("Setting AG to wide mode after {}", actualHeight);
        });
    }

    private void handleMouseClick(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            // Handle left click
            PopupContent popupContent = new PopupContent(liveAG, "Left");
            popupContent.show();
            logger.debug("Left click detected on button");
        } else if (event.getButton() == MouseButton.SECONDARY) {
            // Handle right click
            PopupContent popupContent = new PopupContent(liveAG, "Right");
            popupContent.show();
            logger.debug("Right click detected on button");
        }
    }
}
