package com.amay.scu.listenner;

import com.amay.scu.controller.StationDynamicMapView;
import com.amay.scu.enums.SLEStatus;

public class StationDynamicMapViewListener {

    private static StationDynamicMapViewListener Instance ;

    private StationDynamicMapView stationDynamicMapView;

    private StationDynamicMapViewListener() {
        throw new IllegalStateException("Utility class");
    }
    private StationDynamicMapViewListener(StationDynamicMapView stationDynamicMapView) {
        this.stationDynamicMapView = stationDynamicMapView;
    }



    public static StationDynamicMapViewListener initialize(StationDynamicMapView stationDynamicMapView) {
        if (Instance == null) {
            Instance = new StationDynamicMapViewListener(stationDynamicMapView);
        }
        Instance.stationDynamicMapView = stationDynamicMapView;
        return Instance;
    }

    public static StationDynamicMapViewListener getInstance() {
        return Instance;
    }

    public void updateSLEStatus(String sleId, boolean status) {

        stationDynamicMapView.updateSLEStatus(sleId, status? SLEStatus.ONLINE : SLEStatus.OFFLINE);
    }


}
