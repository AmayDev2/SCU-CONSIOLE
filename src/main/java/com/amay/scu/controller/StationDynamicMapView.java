package com.amay.scu.controller;

import com.amay.scu.enums.SLEStatus;
import com.amay.scu.listenner.StationDynamicMapViewListener;
import com.amay.scu.sles.*;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    Logger logger = LoggerFactory.getLogger(StationDynamicMapView.class);

    private List<SLE> sles;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    public void initialize() {
        sles = new ArrayList<>();
        StationDynamicMapViewListener.initialize(this);

        try {
            SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane,gateCount);
            SLEFactory.getSLEFactory(new EFOAbstractFactory(), anchorPane,efoCount);
            sles.addAll(Arrays.stream(SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane,tomCount)).toList());
            SLEFactory.getSLEFactory(new TVMAbstractFactory(), anchorPane,tvmCount);


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void updateSLEStatus(String sleId, SLEStatus status) {
        if(sleId == null || status == null) {
            logger.error("SLE ID or status is null");
            return;
        }
        sles.stream().filter(sle -> sle.getId().equals(sleId)).forEach(sle -> sle.updateStatus(status));
    }

}
