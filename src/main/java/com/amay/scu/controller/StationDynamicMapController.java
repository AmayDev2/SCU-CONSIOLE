package com.amay.scu.controller;

import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.listenner.IStationDynamicMapViewListener;
import com.amay.scu.listenner.impl.StationDynamicMapViewListener;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.repository.StationDevicesRepository;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.sles.*;
import com.amay.scu.sles.components.SLE;
import com.amay.scu.util.ObjectSerialization;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class StationDynamicMapController implements IStationDynamicMapViewListener {

    private int tomCount = 0;
    private int efoCount = 0;
    private int readerCount = 0;
    private int gateCount = 0;
    private int arraysCount = 0;
    private int tvmCount = 0;
    List<StationDevicesDTO> stationDevices = null;
    List<StationDevicesDTO>  ag=new ArrayList<>();
    List<StationDevicesDTO>  tom=new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(StationDynamicMapController.class);

    private List<SLE> sles;

    @FXML
    private AnchorPane anchorPane;

    @FXML
    public void initialize() {
        sles = new ArrayList<>();
        //load the location list from the json file
        TypeReference<Map<String, SLELocationListObject.SLELocation>> typeRef = new TypeReference<>() {};
        SLELocationListObject.list= ObjectSerialization.jsonFromFile("sleLocationList.json",typeRef );


        //initialize the listener
        StationDynamicMapViewListener.initialize(this);

        try {
            StationDevicesRepository stationDevicesRepository = StationDevicesRepository.getInstance();
            stationDevices = stationDevicesRepository.getStationDevices();
            logger.info("Station Devices: {} ", stationDevices.size());
            logger.info("Station Devices: {} ", stationDevices.get(0));
            for (StationDevicesDTO stationDevice : stationDevices) {
                switch (stationDevice.getEquipName()) {
                    case "TOM":
                        tomCount++;
                        tom.add(stationDevice );
                        break;
                    case "EFO":
                        efoCount++;
                        break;
                    case "AG":
                        gateCount++;
                        ag.add(stationDevice );
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
            sles.addAll(Arrays.stream(SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane, gateCount,ag)).toList());
            SLEFactory.getSLEFactory(new EFOAbstractFactory(), anchorPane, efoCount, ag);
            sles.addAll(Arrays.stream(SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane, tomCount, tom)).toList());
            SLEFactory.getSLEFactory(new TVMAbstractFactory(), anchorPane, tvmCount, ag);


            //update the status of the devices to  TODO: add logic
//            stationDevices.forEach(stationDevice -> {
//                logger.debug("Station Device: {}", stationDevice.getEquipId());
//                updateSLEStatus(stationDevice.getEquipId(), SLEStatus.PERIPHERAL_OFFLINE);
//            });

        } catch (Exception e) {
            logger.error("Error in StationDynamicMapController initialize method: {}", e.getMessage());
        }

    }

    //pass the sle id and status to update the status of the sle
    public void updateSLEStatus(String sleId, SLEStatus status) {
        logger.debug("Updating SLE status: {} {}", sleId, status);
        if (sleId == null || status == null) {
            logger.error("SLE ID or status is null");
            return;
        }
        sles.stream().filter(sle -> {
            logger.debug("loop SLE ID: {} {}", sle.getId(), sleId);
            return sle.getId().equals(sleId);
        }).forEach(sle -> sle.updateStatus(status));
    }

    //TOM
    public void updateTOMPeripheralStatus(String equipId, LiveTOM liveTOM) {
        sles.stream().filter(tom->tom.getId().equals(equipId)).forEach(filteredTom->{
            filteredTom.updatePeripheralStatus(liveTOM);
        });
    }

    public void updateTOMOperationMode(String equipId, LiveTOM liveTOM) {
        sles.stream().filter(tom->tom.getId().equals(equipId)).forEach(filteredTom->{
            filteredTom.updateOperationMode(liveTOM);
        });
    }

    //AG
    public void updateAGPeripheralStatus(String equipId, LiveAG liveAG) {
        sles.stream().filter(tom->tom.getId().equals(equipId)).forEach(filteredTom->{
            filteredTom.updatePeripheralStatus(liveAG);
        });
    }

    public void updateAGOperationMode(String equipId, LiveAG liveAG) {
        sles.stream().filter(tom->tom.getId().equals(equipId)).forEach(filteredTom->{
            filteredTom.updateOperationMode(liveAG);
        });
    }

}
