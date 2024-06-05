package com.amay.scu.controller;

import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.listenner.StationDynamicMapViewListener;
import com.amay.scu.repository.StationDevicesRepository;
import com.amay.scu.sles.*;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StationDynamicMapController {

    private int tomCount = 0;
    private int efoCount = 0;
    private int readerCount = 0;
    private int gateCount = 0;
    private int arraysCount = 0;
    private int tvmCount = 0;
    List<StationDevicesDTO> stationDevices = null;

    Logger logger = LoggerFactory.getLogger(StationDynamicMapController.class);

    private List<SLE> sles;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    public void initialize() {
        sles = new ArrayList<>();
        StationDynamicMapViewListener.initialize(this);

        try {
            StationDevicesRepository stationDevicesRepository =StationDevicesRepository.getInstance();
            stationDevices=stationDevicesRepository.getStationDevices();
            logger.info("Station Devices: {} ", stationDevices.size());
            logger.info("Station Devices: {} ", stationDevices.get(0));
            for(StationDevicesDTO stationDevice:stationDevices){
                switch (stationDevice.getEquipName()){
                    case "TOM":
                        tomCount++;
                        break;
                    case "EFO":
                        efoCount++;
                        break;
                    case "AG":
                        gateCount++;
                        break;
                    case "READER":
                        readerCount++;
                        break;
                    case "ARRAYS":
                        arraysCount++;
                        break;
                    case "TVM":
                        tvmCount++;
                        break;
                }
            }

            //create the SLE objects based on the count of the devices
            sles.addAll(Arrays.stream(SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane,gateCount)).toList());
            SLEFactory.getSLEFactory(new EFOAbstractFactory(), anchorPane,efoCount);
            sles.addAll(Arrays.stream(SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane,tomCount)).toList());
            SLEFactory.getSLEFactory(new TVMAbstractFactory(), anchorPane,tvmCount);


            //update the status of the devices to  TODO: add logic
            stationDevices.forEach(stationDevice -> {
                logger.debug("Station Device: {}", stationDevice.getEquipId());
                updateSLEStatus(stationDevice.getEquipId(), SLEStatus.OFFLINE);
            });

        }catch (Exception e){
            logger.error("Error in StationDynamicMapController initialize method: {}", e.getMessage());
        }

    }

    //pass the sle id and status to update the status of the sle
    public void updateSLEStatus(String sleId, SLEStatus status) {
        logger.debug("Updating SLE status: {} {}", sleId, status);
        if(sleId == null || status == null) {
            logger.error("SLE ID or status is null");
            return;
        }
        sles.stream().filter(sle -> {
            logger.debug("loop SLE ID: {} {}", sle.getId(), sleId);
            return sle.getId().equals(sleId);}).forEach(sle -> sle.updateStatus(status));
    }

}
