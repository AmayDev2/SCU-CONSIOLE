package com.amay.scu.listenner;

import com.amay.scu.controller.StationDynamicMapController;
import com.amay.scu.enums.SLEStatus;

public class StationDynamicMapViewListener {

    private static StationDynamicMapViewListener Instance ;

    private StationDynamicMapController stationDynamicMapController;

    private StationDynamicMapViewListener() {
        throw new IllegalStateException("Utility class");
    }
    private StationDynamicMapViewListener(StationDynamicMapController stationDynamicMapController) {
        this.stationDynamicMapController = stationDynamicMapController;
    }



    public static StationDynamicMapViewListener initialize(StationDynamicMapController stationDynamicMapController) {
        if (Instance == null) {
            Instance = new StationDynamicMapViewListener(stationDynamicMapController);
        }
        Instance.stationDynamicMapController = stationDynamicMapController;
        return Instance;
    }

    public static StationDynamicMapViewListener getInstance() {
        return Instance;
    }

    public void updateSLEStatus(String sleId, boolean status) {

        stationDynamicMapController.updateSLEStatus(sleId, status? SLEStatus.ONLINE : SLEStatus.OFFLINE);
    }


}
