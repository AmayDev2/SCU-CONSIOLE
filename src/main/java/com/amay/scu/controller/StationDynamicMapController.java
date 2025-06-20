package com.amay.scu.controller;

import com.amay.scu.auth.AuthService;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.enums.StationSpecialMode;
import com.amay.scu.enums.Zone;
import com.amay.scu.listenner.IStationDynamicMapViewListener;
import com.amay.scu.listenner.impl.StationDynamicMapViewListener;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.repository.StationDevicesRepository;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.sleobj.LiveTR;
import com.amay.scu.sleobj.LiveTVM;
import com.amay.scu.sles.*;
import com.amay.scu.sles.components.SLE;
import com.amay.scu.util.ObjectSerialization;
import com.fasterxml.jackson.core.type.TypeReference;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javafx.scene.paint.Color;

import java.util.*;
import java.util.stream.Collectors;

public class StationDynamicMapController implements IStationDynamicMapViewListener {

    private int tomCount = 0;
    private int efoCount = 0;
    private int readerCount = 0;
    private int gateCount = 0;
    private int arraysCount = 0;
    private int tvmCount = 0;
    List<StationDevicesDTO> stationDevices = null;
    List<StationDevicesDTO>  ag1=new ArrayList<>();
    List<StationDevicesDTO>  ag2=new ArrayList<>();
    List<StationDevicesDTO>  ag3=new ArrayList<>();
    List<StationDevicesDTO>  ag4=new ArrayList<>();
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
        this.setSpecialModeListener();
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
//        SLELocationListObject.list= ObjectSerialization.jsonFromFile("C:/sleLocationList.json",typeRef );


        //initialize the listener
        StationDynamicMapViewListener.initialize(this);
//        boolean tom1 = false,ag1 = false,tvm1 = false,reader1 = false,efo1=false;

        try {
            StationDevicesRepository stationDevicesRepository = StationDevicesRepository.getInstance();
            stationDevices = stationDevicesRepository.getStationDevices();

            logger.info("Station Devices: {} ", stationDevices.size());
            logger.info("Station Devices: {} ", stationDevices.get(0));
            for (StationDevicesDTO stationDevice : stationDevices) {
                switch (stationDevice.getEquipName()) {
                    case "TOM":
                        tomCount++;
                        if (stationDevice.getZone().equals(Zone.TOM_ZONE_ONE)) {
                            stationDevice.setxAxis(stationDevice.getZone().getX() + (((int) stationDevice.getZone().getCount() / 4) * 100));
                            stationDevice.setyAxis(stationDevice.getZone().getY() + stationDevice.getZone().getCount() % 4);
                        } else if (stationDevice.getZone().equals(Zone.TOM_ZONE_TWO)) {
                            stationDevice.setxAxis(stationDevice.getZone().getX() - (((int) stationDevice.getZone().getCount() / 4) * 100));
                            stationDevice.setyAxis(stationDevice.getZone().getY() + stationDevice.getZone().getCount() % 4);

                        } else if (stationDevice.getZone().equals(Zone.TOM_ZONE_THREE)) {
                            stationDevice.setxAxis(stationDevice.getZone().getX() + (((int) stationDevice.getZone().getCount() / 4) * 100));
                            stationDevice.setyAxis(stationDevice.getZone().getY() + stationDevice.getZone().getCount() % 4);

                        } else if (stationDevice.getZone().equals(Zone.TOM_ZONE_FOUR)) {
                            stationDevice.setxAxis(stationDevice.getZone().getX() - (((int) stationDevice.getZone().getCount() / 4) * 100));
                            stationDevice.setyAxis(stationDevice.getZone().getY() + stationDevice.getZone().getCount() % 4);

                        }
                        stationDevice.getZone().setCount();
                        tom.add(stationDevice);

                        break;
                    case "EFO":
                        efoCount++;
                        stationDevice.setxAxis(stationDevice.getZone().getX());
                        stationDevice.setyAxis(stationDevice.getZone().getY() - (stationDevice.getZone().getCount() * (stationDevice.getEquipType().equals("07") ? 70 : 60)));
                        stationDevice.getZone().setCount();
//                        if(!efo1) {
                        efo.add(stationDevice);
//                            efo1 = true;
//                        }
                        break;

                    case "AG":
//                        if(!ag1) {
                        gateCount++;
                        if (stationDevice.getZone().equals(Zone.AG_ZONE_ONE)) {
                            stationDevice.setxAxis(stationDevice.getZone().getX());
                            stationDevice.setyAxis(stationDevice.getZone().getY() - (stationDevice.getZone().getCount() * (stationDevice.getEquipType().equals("07") ? 70 : 60)));
                            stationDevice.getZone().setCount();
                            ag1.add(stationDevice);

                        } else if (stationDevice.getZone().equals(Zone.AG_ZONE_TWO)) {
                            stationDevice.setxAxis(stationDevice.getZone().getX());
                            stationDevice.setyAxis(stationDevice.getZone().getY() - (stationDevice.getZone().getCount() * (stationDevice.getEquipType().equals("07") ? 70 : 60)));
                            stationDevice.getZone().setCount();
                            ag2.add(stationDevice);

                        }
                        if (stationDevice.getZone().equals(Zone.AG_ZONE_THREE)) {
                            stationDevice.setxAxis(stationDevice.getZone().getX());
                            stationDevice.setyAxis(stationDevice.getZone().getY() + (stationDevice.getZone().getCount() * (stationDevice.getEquipType().equals("07") ? 70 : 60)));
                            stationDevice.getZone().setCount();
                            ag3.add(stationDevice);

                        }
                        if (stationDevice.getZone().equals(Zone.AG_ZONE_FOUR)) {
                            stationDevice.setxAxis(stationDevice.getZone().getX());
                            stationDevice.setyAxis(stationDevice.getZone().getY() + (stationDevice.getZone().getCount() * (stationDevice.getEquipType().equals("07") ? 70 : 60)));
                            stationDevice.getZone().setCount();
                            ag4.add(stationDevice);

                        }

//                        }
//                        ag1=true;
                        break;
                    case "TR":
                        stationDevice.setxAxis(stationDevice.getZone().getX());
                        stationDevice.setyAxis(stationDevice.getZone().getY() - (stationDevice.getZone().getCount() * (stationDevice.getEquipType().equals("07") ? 70 : 60)));
                        stationDevice.getZone().setCount();
                        tr.add(stationDevice);

                        break;
                    case "ARRAYS":
                        arraysCount++;
                        break;
                    case "TVM":
                        tvmCount++;
                        if(stationDevice.getZone().equals(Zone.UNPAID_ZONE_ONE)){
                        stationDevice.setxAxis(stationDevice.getZone().getX());
                        stationDevice.setyAxis(stationDevice.getZone().getY() - (stationDevice.getZone().getCount() * (stationDevice.getEquipType().equals("07") ? 70 : 60)));
                        } else if(stationDevice.getZone().equals(Zone.UNPAID_ZONE_TWO)){

                        }
                        stationDevice.getZone().setCount();
                            tvm.add(stationDevice);

                        break;
                }
            }


//            efoCount=1;

//            //create the SLE objects based on the count of the devices

            sles.addAll(List.of(SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane, tom.size(), tom.stream().toList())));
            sles.addAll(List.of(SLEFactory.getSLEFactory(new EFOAbstractFactory(), anchorPane, efo.size(), efo.stream().toList())));
            sles.addAll(List.of(SLEFactory.getSLEFactory(new TVMAbstractFactory(), anchorPane, tvm.size(), tvm.stream().toList())));
            sles.addAll(List.of(SLEFactory.getSLEFactory(new TRAbstractFactory(), anchorPane, tr.size(), tr.stream().toList())));

            sles.addAll(List.of(SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane, ag1.size(), ag1.stream().toList())));
            List<StationDevicesDTO> reversedAg2 = new ArrayList<>(ag2.stream().toList());
//            Collections.reverse(reversedAg2);
            sles.addAll(List.of(SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane, reversedAg2.size(), reversedAg2)));

            List<StationDevicesDTO> reversedAg3 = new ArrayList<>(ag3.stream().toList());
            Collections.reverse(reversedAg3);
            sles.addAll(List.of(
                    SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane, reversedAg3.size(), reversedAg3)
            ));

            List<StationDevicesDTO> reversedAg4 = new ArrayList<>(ag4.stream().toList());
            Collections.reverse(reversedAg4);
            sles.addAll(List.of(SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane, ag4.size(), reversedAg4)));


//              sles.add(SLEFactory.getSLEFactory(new AGAbstractFactory(), anchorPane,  ag.get(0)));
//              sles.add(SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane,  tom.get(0)));
//              sles.add(SLEFactory.getSLEFactory(new EFOAbstractFactory(), anchorPane,  efo.get(0)));
//              sles.add(SLEFactory.getSLEFactory(new TVMAbstractFactory(), anchorPane,  tvm.get(0)));
//              sles.add(SLEFactory.getSLEFactory(new TRAbstractFactory(), anchorPane,  tr.get(0)));

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

    private void setSpecialModeListener() {
        StationSpecialMode.StationSpecialModeListener listener = newMode -> {
            logger.debug("New special mode: {}", newMode);

            if (newMode.equals(StationSpecialMode.STATION_NORMAL) ) {
                Platform.runLater(() -> {
                    // Set red border
                    anchorPane.setBorder(null);
                });

            }else if(newMode.equals(StationSpecialMode.EMERGENCY) ){
                Platform.runLater(() -> {
                    // Set red border
                    anchorPane.setBorder(new Border(new BorderStroke(
                            Color.RED,                        // Border color
                            BorderStrokeStyle.SOLID,         // Border style
                            CornerRadii.EMPTY,               // No rounded corners
                            BorderWidths.DEFAULT             // Default width (1px)
                    )));
                });


            }
        };

        StationSpecialMode.addStationSpecialModeListener(listener);
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
        sles.stream().filter(tom->null!=tom.getId() && tom.getId().equals(equipId)).forEach(filteredTom->{
            filteredTom.updatePeripheralStatus(liveTOM);
        });
    }

    public void updateTOMOperationMode(String equipId, LiveTOM liveTOM) {
        sles.stream().filter(tom->null!=tom.getId() && tom.getId().equals(equipId)).forEach(filteredTom->{
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

    public void updateTVMPeripheralStatus(String equipId, LiveTVM liveTVM) {
        System.out.println("TVM PERIPHERAL STATUS "+equipId+" "+liveTVM);
        sles.stream().filter(tvm->null!=tvm.getId() && tvm.getId().equals(equipId)).forEach(filteredTvm->{
            filteredTvm.updatePeripheralStatus(liveTVM);
        });
    }

    public void updateTVMOperationMode(String equipId, LiveTVM liveTVM) {
        System.out.println("TVM OPERATION MODE "+equipId+" "+liveTVM);
        sles.stream()
                .filter(tvm-> null!=tvm.getId() && tvm.getId().equals(equipId))
                .forEach(filteredTvm->{
            filteredTvm.updateOperationMode(liveTVM);
        });
    }

    public void updateTRPeripheralStatus(String equipId, LiveTR liveTR) {
        System.out.println("TR PERIPHERAL STATUS "+equipId+" "+liveTR);
        sles.stream().filter(tr->tr.getId().equals(equipId)).forEach(filteredTom->{
            filteredTom.updatePeripheralStatus(liveTR);
        });
    }

    public void updateTROperationMode(String equipId, LiveTR liveTR) {
        System.out.println("TR PERIPHERAL STATUS "+equipId+" "+liveTR);
        sles.stream().filter(tr->tr.getId().equals(equipId)).forEach(filteredTom->{
            filteredTom.updateOperationMode(liveTR);
        });
    }
}
