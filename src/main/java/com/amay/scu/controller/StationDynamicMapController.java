package com.amay.scu.controller;

import com.amay.scu.auth.AuthService;
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
    List<StationDevicesDTO>  efo=new ArrayList<>();
    List<StationDevicesDTO>  tvm=new ArrayList<>();
    List<StationDevicesDTO>  tr=new ArrayList<>();

    Logger logger = LoggerFactory.getLogger(StationDynamicMapController.class);

    private List<SLE> sles;

    @FXML
    private AnchorPane anchorPane;

    private final AuthService authService;

    public StationDynamicMapController(AuthService authService){

        this.authService = authService;
    }

    @FXML
    public void initialize() {
        this.anchorPane.setDisable(true); // Initially disable the emergency button
        authService.isAuthenticated().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
            // User is authenticated
            logger.info("User is authenticated");
            // Enable the emergency button
            anchorPane.setDisable(false);
        } else {
            // User is not authenticated
            logger.info("User is not authenticated");
            // Disable the emergency button
            anchorPane.setDisable(true);
        }
    });

        sles = new ArrayList<>();
        //load the location list from the json file
        TypeReference<Map<String, SLELocationListObject.SLELocation>> typeRef = new TypeReference<>() {};
        SLELocationListObject.list= ObjectSerialization.jsonFromFile("sleLocationList.json",typeRef );


        //initialize the listener
        StationDynamicMapViewListener.initialize(this);
        boolean tom1 = false,ag1 = false,tvm1 = false,reader1 = false,efo1=false;

        try {
            StationDevicesRepository stationDevicesRepository = StationDevicesRepository.getInstance();
            stationDevices = stationDevicesRepository.getStationDevices();

            logger.info("Station Devices: {} ", stationDevices.size());
            logger.info("Station Devices: {} ", stationDevices.get(0));
            for (StationDevicesDTO stationDevice : stationDevices) {
                switch (stationDevice.getEquipName()) {
                    case "TOM":
                        if(!tom1) {
                            tomCount++;
                            tom.add(stationDevice);
                        }
                        tom1=true;
                        break;
                    case "EFO":
                        efoCount++;
                        if(!efo1) {
                            efo.add(stationDevice);
                            efo1 = true;
                        }
                        break;

                    case "AG":
                        if(!ag1) {
                            gateCount++;
                            ag.add(stationDevice);
                        }
                        ag1=true;
                        break;
                    case "TR":
                        if(!reader1) {
                            readerCount++;
                        tr.add(stationDevice);
                        }
                        reader1=true;
                        break;
                    case "ARRAYS":
                        arraysCount++;
                        break;
                    case "TVM":
                        if(!tvm1) {
                            tvmCount++;
                            tvm.add(stationDevice);
                        }
                        tvm1=true;
                        break;
                }
            }



//            efoCount=1;

//            //create the SLE objects based on the count of the devices
//            sles.addAll(Arrays.stream(SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane, gateCount,ag)).toList());
              sles.add(SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane,  ag.get(0)));
              sles.add(SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane,  tom.get(0)));
              sles.add(SLEFactory.getSLEFactory(new EFOAbstractFactory(), anchorPane,  efo.get(0)));
              sles.add(SLEFactory.getSLEFactory(new TVMAbstractFactory(), anchorPane,  tvm.get(0)));
              sles.add(SLEFactory.getSLEFactory(new TRAbstractFactory(), anchorPane,  tr.get(0)));
//            sles.addAll(Arrays.stream(SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane, tomCount, tom)).toList());
//            SLEFactory.getSLEFactory(new TVMAbstractFactory(), anchorPane, tvmCount, ag);


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
