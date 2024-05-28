package com.amay.scu.controller;

import com.amay.scu.enums.SLEStatus;
import com.amay.scu.sles.EFOAbstractFactory;
import com.amay.scu.sles.SLEFactory;
import com.amay.scu.sles.TOMAbstractFactory;
import com.amay.scu.sles.TVMAbstractFactory;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class StationDynamicMapView {

    private int tomCount = 8;
    private int efoCount = 2;
    private int readerCount = 4;
    private int gateCount = 20;
    private int arraysCount = 4;
    private int tvmCount = 4;

    private List<SLE> sles;

    @FXML
    private AnchorPane anchorPane;

//    @FXML
//    private Button draggableButton, draggableButton2, draggableButton3, draggableButton4;

    private double offsetX;
    private double offsetY;
//    private Button selectedButton;



    @FXML
    public void initialize() {
        sles = new ArrayList<>();
//        draggableButton.setOnMousePressed(this::onMousePressed);
//        draggableButton2.setOnMousePressed(this::onMousePressed);
//        draggableButton3.setOnMousePressed(this::onMousePressed);
//        draggableButton4.setOnMousePressed(this::onMousePressed);
//
//        draggableButton.setOnMouseDragged(this::onMouseDragged);
//        draggableButton2.setOnMouseDragged(this::onMouseDragged);
//        draggableButton3.setOnMouseDragged(this::onMouseDragged);
//        draggableButton4.setOnMouseDragged(this::onMouseDragged);
//
//        draggableButton.setStyle(SLEStatus.ONLINE.getStatus());
//        draggableButton2.setStyle(SLEStatus.UNKNOWN.getStatus());
//        draggableButton3.setStyle(SLEStatus.PERIPHERAL_OFFLINE.getStatus());

        try {
//            SLE tomController =
//                    (TOMController)
         /*                   sles.addAll(Arrays.stream(SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane,tomCount)).toList());
//            tomController.setStatus(SLEStatus.ONLINE);
            sles.get(0).setStatus(SLEStatus.OFFLINE);
            sles.get(1).setStatus(SLEStatus.PERIPHERAL_OFFLINE);*/
//
//            SLE[] efoController =
//                    (EFOController[])
//                            SLEFactory.getSLEFactory(new EFOAbstractFactory(), anchorPane,efoCount);
                            sles.addAll(Arrays.stream(SLEFactory.getSLEFactory(new TVMAbstractFactory(), anchorPane,tvmCount)).toList());
                            sles.get(0).setStatus(SLEStatus.OFFLINE);
                            sles.get(1).setScale(400,200,0);
            Scanner scanner = new Scanner(System.in);
                            new Thread(() -> {
                                while (true) {
                                    System.out.println("Enter the status of the TVM");
                                    sles.get(0).setScale(scanner.nextFloat(), scanner.nextFloat(), scanner.nextFloat());
                                }
                            }).start();




//            efoController.setStatus(SLEStatus.OFFLINE);

//            tomController= (TOMController) SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane);
//            tomController.setStatus(SLEStatus.OFFLINE);

        }catch (Exception e){
            e.printStackTrace();
        }

    }
//
//    private void onMousePressed(MouseEvent event) {
//        selectedButton = (Button) event.getSource();
//        offsetX = event.getSceneX() - selectedButton.getLayoutX();
//        offsetY = event.getSceneY() - selectedButton.getLayoutY();
//    }
//
//    private void onMouseDragged(MouseEvent event) {
//        if (selectedButton != null) {
//            double newX = event.getSceneX() - offsetX;
//            double newY = event.getSceneY() - offsetY;
//
//            // Boundaries check
//            if (newX >= 0 && newX + selectedButton.getWidth() <= anchorPane.getWidth()) {
//                selectedButton.setLayoutX(newX);
//            }
//            if (newY >= 0 && newY + selectedButton.getHeight() <= anchorPane.getHeight()) {
//                selectedButton.setLayoutY(newY);
//            }
//        }
//    }
}
