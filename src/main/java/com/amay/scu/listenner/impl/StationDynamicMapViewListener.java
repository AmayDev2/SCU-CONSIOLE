package com.amay.scu.listenner.impl;

import com.amay.scu.controller.StationDynamicMapController;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.sleobj.LiveTR;
import com.amay.scu.sleobj.LiveTVM;
import org.network.monitorandcontrol.tom.TOMPeripheralStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StationDynamicMapViewListener {

    private static StationDynamicMapViewListener Instance ;
    static final Logger logger= LoggerFactory.getLogger(StationDynamicMapViewListener.class);

    private StationDynamicMapController stationDynamicMapController;

    private StationDynamicMapViewListener() {
        throw new IllegalStateException("Utility class");
    }
    private StationDynamicMapViewListener(StationDynamicMapController stationDynamicMapController) {
        this.stationDynamicMapController = stationDynamicMapController;
    }



    public static StationDynamicMapViewListener initialize(StationDynamicMapController stationDynamicMapController) {
        if (Instance == null) {
            logger.debug("instance created");
            Instance = new StationDynamicMapViewListener(stationDynamicMapController);
        }

        Instance.stationDynamicMapController = stationDynamicMapController;
        return Instance;
    }

    public static StationDynamicMapViewListener getInstance() {
        logger.debug("instance called");
        return Instance;
    }

    //TODO:  Deprecated
    public void updateSLEStatus(String sleId, boolean status) {

        stationDynamicMapController.updateSLEStatus(sleId, status? SLEStatus.ONLINE : SLEStatus.OFFLINE);
    }

    public void updatePeripheralStatus(String sleId ){

    }


    public void updateTOMPeripheralStatus(String equipId, LiveTOM liveTOM) {

        stationDynamicMapController.updateTOMPeripheralStatus(equipId,liveTOM);

    }

    public void updateTOMOperationMode(String equipId, LiveTOM liveTOM) {

        stationDynamicMapController.updateTOMOperationMode(equipId,liveTOM);

    }


    public void updateAGPeripheralStatus(String equipId, LiveAG liveAG) {
        stationDynamicMapController.updateAGPeripheralStatus(equipId,liveAG);
    }

    public void updateAGOperationMode(String equipId, LiveAG liveAG) {

        stationDynamicMapController.updateAGOperationMode(equipId,liveAG);

    }


    public void updateTVMPeripheralStatus(String equipId, LiveTVM liveTVM) {

        stationDynamicMapController.updateTVMPeripheralStatus(equipId,liveTVM);

    }

    public void updateTVMOperationMode(String equipId, LiveTVM liveTVM) {

        stationDynamicMapController.updateTVMOperationMode(equipId,liveTVM);

    }


    public void updateTRPeripheralStatus(String equipId, LiveTR liveTR) {
        stationDynamicMapController.updateTRPeripheralStatus(equipId,liveTR);
    }

    public void updateTROperationMode(String equipId, LiveTR liveTR) {

        stationDynamicMapController.updateTROperationMode(equipId,liveTR);

    }
}
