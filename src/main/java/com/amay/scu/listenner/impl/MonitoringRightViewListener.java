package com.amay.scu.listenner.impl;

import com.amay.scu.controller.MonitorRightView;
import com.amay.scu.controller.StationDynamicMapController;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
import org.network.monitorandcontrol.Alarms;
import org.network.monitorandcontrol.RequestType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class MonitoringRightViewListener {

    private static MonitoringRightViewListener Instance ;
    static final Logger logger= LoggerFactory.getLogger(MonitoringRightViewListener.class);

    private MonitorRightView monitorRightView;

    private MonitoringRightViewListener() {
        throw new IllegalStateException("Utility class");
    }
    private MonitoringRightViewListener(MonitorRightView stationDynamicMapController) {
        this. monitorRightView = stationDynamicMapController;
    }



    public static MonitoringRightViewListener initialize(MonitorRightView monitorRightView) {
        if (Instance == null) {
            logger.debug("instance created");
            Instance = new MonitoringRightViewListener(monitorRightView);
        }

        Instance.monitorRightView = monitorRightView;
        return Instance;
    }

    public static MonitoringRightViewListener getInstance() {
        logger.debug("instance called");
        return Instance;
    }



    public void sendAlarm(Map<Integer, String> alarms, String equipId, String equipType) {
        monitorRightView.sendAlarm(alarms, equipId, equipType);
    }
}
