package com.amay.scu.test_grpc_service;

import com.amay.scu.controller.HeaderController;
import com.amay.scu.enums.*;
import com.amay.scu.listenner.impl.MonitoringRightViewListener;
import com.amay.scu.listenner.impl.StationDynamicMapViewListener;
import com.amay.scu.sleobj.*;
import org.network.monitorandcontrol.*;
import org.network.monitorandcontrol.ag.AGDeviceInfo;
import org.network.monitorandcontrol.ag.AGModeControl;
import org.network.monitorandcontrol.ag.AGPeripheralStatus;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;

import org.network.monitorandcontrol.tom.TOMDeviceInfo;
import org.network.monitorandcontrol.tom.TOMModeControl;
import org.network.monitorandcontrol.tom.TOMParameterVersion;
import org.network.monitorandcontrol.tom.TOMPeripheralStatus;
import org.network.monitorandcontrol.tr.TRModeControl;
import org.network.monitorandcontrol.tr.TRPeripheralStatus;
import org.network.monitorandcontrol.tvm.TVMModeControl;
import org.network.monitorandcontrol.tvm.TVMPeripheralStatus;

import java.util.Map;
import java.util.Set;

public class SCUService {
    static int st=0;

    StationDynamicMapViewListener stationDynamicMapViewListener;


    public SCUService() {
        stationDynamicMapViewListener=StationDynamicMapViewListener.getInstance();

    }

    public void updateSLEs(StreamData streamData) {
        switch (streamData.getRequestType()) {
            case DIVICE_DISCONNECT:
                //device is disconnected
                break;
            case DEVICE_INFO:
                //AG Device Info decode and display
                break;
            case PERIPHERAL_STATUS:
                //AG Peripheral Status decode and display

                break;
            case PARAMETER_VERSION:
                //AG Version Check decode and display
                break;
            case ALARMS:
                //Last 10 alarms
            default:
                break;
        }

    }

    public void detectDeviceType(DeviceType deviceType, ConsoleProtocol value) {
        System.out.println("Device Type : "+deviceType);
        switch (deviceType){
            case TOM :{
                this.decodeTOMResponse(value);
                break;
            }
            case AG :{
                this.decodeAGResponse(value);
                break;
            }
            case SCU :{
                System.out.println("SCU Response");
                decodeSCUResponse(value);
                break;
            }
            case TR :{
                System.out.println("TR Response");
                this.decodeTRResponse(value);

                break;
            }
            case TVM :{
                System.out.println("TVM Response");
                this.decodeTVMResponse(value);
                break;
            }
            case CCU:{
                System.out.println("CCU Response");
                if(value.getStreamData().getCommandType().equals(CommandType.GET_DIVICE_VERSIONS)){
                    //disconnect
                    StationSpecialMode.setStationSpecialMode(StationSpecialMode.CCU_DISCONNECT);

                }else if(value.getStreamData().getCommandType().equals(CommandType.GET_DEVICE_INFO)) {
                    //Connect
                    StationSpecialMode.setStationSpecialMode(StationSpecialMode.CCU_CONNECT);
                }
                break;
            }
            default:{
                System.out.println("Device Type not found");
            }
        }
    }

    private void decodeSCUResponse(ConsoleProtocol value) {
        try {
            StationSpecialMode stationSpecialMode= StationSpecialMode.getSpecialMode(value.getErrorMsg());
            System.out.println("Station Special Mode about to set : "+stationSpecialMode.getModeName());
            StationSpecialMode.setStationSpecialMode(stationSpecialMode);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void decodeAGResponse(ConsoleProtocol consoleProtocol) {

        try {
            System.out.println((++st)+" : "+consoleProtocol.toString());
        }catch (Exception e){
            e.printStackTrace();
        }

        switch (consoleProtocol.getStreamData().getRequestType()) {
            case DIVICE_DISCONNECT:
                //device is disconnected
                this.deviceDisconnected(consoleProtocol, new LiveAG());
                System.out.println("Equip Id disconnected : "+consoleProtocol.getStreamData().getEquipId());
                break;
            case DEVICE_INFO:
                //AG Device Info decode and display
                this.checkAGInfo(consoleProtocol,new LiveAG());
                break;
            case PERIPHERAL_STATUS:
                //AG Peripheral Status decode and display
                this.updatePeripheralStatus(consoleProtocol,new LiveAG());
                break;
            case PARAMETER_VERSION:
                //AG Version Check decode and display
                break;
            case RESPONSE:
                this.updateAGOperationMode(consoleProtocol);
                break;
            case ALARMS:
                //Last 10 alarms
            default:
                break;
        }
    }

    private void checkAGInfo(ConsoleProtocol consoleProtocol, LiveAG liveAG) {
        try {
            System.out.println("Equip Id in set : "+consoleProtocol.getStreamData().getEquipId());
            System.out.println(consoleProtocol.getStreamData().getRequestData().unpack(AGDeviceInfo.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updatePeripheralStatus(ConsoleProtocol consoleProtocol,LiveTOM liveTOM){
        try {
            TOMPeripheralStatus tomPeripheralStatus= consoleProtocol.getStreamData().getRequestData().unpack(TOMPeripheralStatus.class);

//            private boolean scu_connected;
//            private boolean ccu_connected;
//            private boolean reader_connected;
//            private boolean scanner_connected;
//            private boolean printer_connected;
//            private boolean pdu_connected;
//            private boolean cash_drawer_connected;
//            private boolean ups_connected;
            liveTOM.setScu_connected(tomPeripheralStatus.getScuConnected());
            liveTOM.setCcu_connected(tomPeripheralStatus.getCcuConnected());
            liveTOM.setReader_connected(tomPeripheralStatus.getReaderConnected());
            liveTOM.setScanner_connected(tomPeripheralStatus.getScannerConnected());
            liveTOM.setPrinter_connected(tomPeripheralStatus.getPrinterConnected());
            liveTOM.setPdu_connected(tomPeripheralStatus.getPduConnected());
            liveTOM.setCash_drawer_connected(tomPeripheralStatus.getCashDrawerConnected());
            liveTOM.setUps_connected(tomPeripheralStatus.getUpsConnected());

            stationDynamicMapViewListener.updateTOMPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveTOM);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updatePeripheralStatus(ConsoleProtocol consoleProtocol,LiveTVM liveTVM){
        try {
            TVMPeripheralStatus tomPeripheralStatus= consoleProtocol.getStreamData().getRequestData().unpack(TVMPeripheralStatus.class);

//            private boolean scu_connected;
//            private boolean ccu_connected;
//            private boolean reader_connected;
//            private boolean scanner_connected;
//            private boolean printer_connected;
//            private boolean pdu_connected;
//            private boolean cash_drawer_connected;
//            private boolean ups_connected;
            liveTVM.setScu_connected(tomPeripheralStatus.getScuConnected());
            liveTVM.setCcu_connected(tomPeripheralStatus.getCcuConnected());
            liveTVM.setReader_connected(tomPeripheralStatus.getReaderConnected());
            liveTVM.setScanner_connected(tomPeripheralStatus.getScannerConnected());
            liveTVM.setPrinter_connected(tomPeripheralStatus.getPrinterConnected());
            liveTVM.setPdu_connected(tomPeripheralStatus.getPduConnected());
            liveTVM.setCash_drawer_connected(tomPeripheralStatus.getCashDrawerConnected());
            liveTVM.setUps_connected(tomPeripheralStatus.getUpsConnected());

            stationDynamicMapViewListener.updateTVMPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveTVM);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updatePeripheralStatus(ConsoleProtocol consoleProtocol,LiveTR liveTR){
        try {
            TRPeripheralStatus trPeripheralStatus= consoleProtocol.getStreamData().getRequestData().unpack(TRPeripheralStatus.class);
//            private boolean scu_connected;
//            private boolean ccu_connected;
//            private boolean reader_connected;
//            private boolean scanner_connected;
//            private boolean printer_connected;
//            private boolean pdu_connected;
//            private boolean cash_drawer_connected;
//            private boolean ups_connected;
            liveTR.setScu_connected( trPeripheralStatus.getScuConnected());
            liveTR.setCcu_connected( trPeripheralStatus.getCcuConnected());
            liveTR.setReader_connected( trPeripheralStatus.getReaderConnected());
            liveTR.setScanner_connected( trPeripheralStatus.getScannerConnected());
//            liveTR.setPrinter_connected( trPeripheralStatus.getPrinterConnected());
//            liveTR.setPdu_connected( trPeripheralStatus.getPduConnected());
//            liveTR.setCash_drawer_connected( trPeripheralStatus.getCashDrawerConnected());
//            liveTR.setUps_connected( trPeripheralStatus.getUpsConnected());

            stationDynamicMapViewListener.updateTRPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveTR);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updatePeripheralStatus(ConsoleProtocol consoleProtocol,LiveAG liveAG){
        try {
            AGPeripheralStatus tomPeripheralStatus= consoleProtocol.getStreamData().getRequestData().unpack(AGPeripheralStatus.class);

//            private boolean scu_connected;
//            private boolean ccu_connected;
//            private boolean reader_connected;
//            private boolean scanner_connected;
//            private boolean printer_connected;
//            private boolean pdu_connected;
//            private boolean cash_drawer_connected;
//            private boolean ups_connected;
            liveAG.setScu_connected(tomPeripheralStatus.getScuConnected());
            liveAG.setCcu_connected(tomPeripheralStatus.getCcuConnected());
//            liveAG.setReader_connected(tomPeripheralStatus.getReaderConnected());
//            liveAG.setScanner_connected(tomPeripheralStatus.getScannerConnected());
//            liveAG.setPrinter_connected(tomPeripheralStatus.getPrinterConnected());
//            liveAG.setPdu_connected(tomPeripheralStatus.getPduConnected());
//            liveAG.setCash_drawer_connected(tomPeripheralStatus.getCashDrawerConnected());
            liveAG.setUps_connected(tomPeripheralStatus.getUpsConnected());

            stationDynamicMapViewListener.updateAGPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveAG);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void decodeTOMResponse(ConsoleProtocol consoleProtocol) {
        try {
            System.out.println(consoleProtocol.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        switch (consoleProtocol.getStreamData().getRequestType()) {
            case DIVICE_DISCONNECT:
                //device is disconnected
                this.deviceDisconnected(consoleProtocol, new LiveTOM());
                System.out.println("Equip Id disconnected : "+consoleProtocol.getStreamData().getEquipId());
                break;
            case DEVICE_INFO:
                //TOM Device Info decode and display
                checkTOMInfo(consoleProtocol, new LiveTOM());
                break;
            case PERIPHERAL_STATUS:
                //TOM Peripheral Status decode and display
                this.updatePeripheralStatus(consoleProtocol,new LiveTOM());
                break;
            case PARAMETER_VERSION:
                //TOM Version Check decode and display
                updateTOMParameterVersion(consoleProtocol);
                break;
            case RESPONSE:
                this.updateOperationMode(consoleProtocol);
                break;
            case ALARMS:
                pushAlarms(consoleProtocol);
                break;
            case OPERATION_MODE:

                break;
            default:
                break;
        }
    }

    public void decodeTVMResponse(ConsoleProtocol consoleProtocol) {
        try {
            System.out.println(consoleProtocol.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        switch (consoleProtocol.getStreamData().getRequestType()) {
            case DIVICE_DISCONNECT:
                //device is disconnected
                this.deviceDisconnected(consoleProtocol, new LiveTVM());
                System.out.println("Equip Id disconnected : "+consoleProtocol.getStreamData().getEquipId());
                break;
            case DEVICE_INFO:
                //TOM Device Info decode and display
                checkTVMInfo(consoleProtocol, new LiveTVM());
                break;
            case PERIPHERAL_STATUS:
                //TOM Peripheral Status decode and display
                this.updatePeripheralStatus(consoleProtocol,new LiveTVM());
                break;
            case PARAMETER_VERSION:
                //TOM Version Check decode and display TODO: fix this for specific device type
                updateTOMParameterVersion(consoleProtocol);
                break;
            case RESPONSE:
                this.updateTVMOperationMode(consoleProtocol);
                break;
            case ALARMS:
                this.pushAlarms(consoleProtocol);
                break;
            case OPERATION_MODE:

                break;
            default:
                break;
        }
    }

    public void decodeTRResponse(ConsoleProtocol consoleProtocol) {
        try {
            System.out.println(consoleProtocol.toString());
        }catch (Exception e){
            e.printStackTrace();
        }
        switch (consoleProtocol.getStreamData().getRequestType()) {
            case DIVICE_DISCONNECT:
                //device is disconnected
                this.deviceDisconnected(consoleProtocol, new LiveTR());
                System.out.println("Equip Id disconnected : "+consoleProtocol.getStreamData().getEquipId());
                break;
            case DEVICE_INFO:
                //TOM Device Info decode and display
                checkTRInfo(consoleProtocol, new LiveTR());
                break;
            case PERIPHERAL_STATUS:
                //TOM Peripheral Status decode and display
                this.updatePeripheralStatus(consoleProtocol,new LiveTR());
                break;
            case PARAMETER_VERSION:
                //TOM Version Check decode and display
                updateTOMParameterVersion(consoleProtocol);
                break;
            case RESPONSE:
                this.updateTROperationMode(consoleProtocol);
                break;
            case ALARMS:
                pushAlarms(consoleProtocol);
                break;
            case OPERATION_MODE:

                break;
            default:
                break;
        }
    }

    private void deviceDisconnected(ConsoleProtocol consoleProtocol, LiveTOM liveTOM) {
        try {
            System.out.println("Equip Id in set : "+consoleProtocol.getStreamData().getEquipId());
            liveTOM.setEquipId(consoleProtocol.getStreamData().getEquipId());
            liveTOM.setOperationMode(TOMOperationMode.DISCONNECTED);
            stationDynamicMapViewListener.updateTOMOperationMode(consoleProtocol.getStreamData().getEquipId(), liveTOM);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deviceDisconnected(ConsoleProtocol consoleProtocol, LiveTVM liveTVM) {
        try {
            System.out.println("Equip Id in set : "+consoleProtocol.getStreamData().getEquipId());
            liveTVM.setEquipId(consoleProtocol.getStreamData().getEquipId());
            liveTVM.setOperationMode(TVMOperationMode.DISCONNECTED);
            stationDynamicMapViewListener.updateTVMOperationMode(consoleProtocol.getStreamData().getEquipId(), liveTVM);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deviceDisconnected(ConsoleProtocol consoleProtocol, LiveTR liveTR) {
        try {
            System.out.println("Equip Id in set : "+consoleProtocol.getStreamData().getEquipId());
            liveTR.setEquipId(consoleProtocol.getStreamData().getEquipId());
            liveTR.setOperationMode(TROperationMode.DISCONNECTED);
            stationDynamicMapViewListener.updateTROperationMode(consoleProtocol.getStreamData().getEquipId(), liveTR);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deviceDisconnected(ConsoleProtocol consoleProtocol, LiveAG liveAG) {
        try {
            System.out.println("Equip Id in set : "+consoleProtocol.getStreamData().getEquipId());
            liveAG.setEquipId(consoleProtocol.getStreamData().getEquipId());
            liveAG.setOperationMode(AGOperationMode.DISCONNECTED);
            stationDynamicMapViewListener.updateAGOperationMode(consoleProtocol.getStreamData().getEquipId(), liveAG);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void pushAlarms(ConsoleProtocol consoleProtocol) {
        try {
            System.out.println(consoleProtocol.getStreamData().getRequestData().unpack(Alarms.class));

            var s=consoleProtocol.getStreamData().getRequestData().unpack(Alarms.class);
            System.out.println(s.getAlarmsMap());
            Map<Integer,String> map=s.getAlarmsMap();
            MonitoringRightViewListener.getInstance().sendAlarm(map,consoleProtocol.getStreamData().getEquipId(),"TOM");
        }catch (Exception e){
            e.printStackTrace();
        }
    }



    private void checkTOMInfo(ConsoleProtocol consoleProtocol, LiveTOM liveTOM) {
        try {
            System.out.println("Equip Id in set : "+consoleProtocol.getStreamData().getEquipId());
            System.out.println(consoleProtocol.getStreamData().getRequestData().unpack(TOMDeviceInfo.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkTVMInfo(ConsoleProtocol consoleProtocol, LiveTVM liveTVM) {
        try {
            System.out.println("Equip Id in set : "+consoleProtocol.getStreamData().getEquipId());
//            System.out.println(consoleProtocol.getStreamData().getRequestData().unpack(TOMDeviceInfo.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void checkTRInfo(ConsoleProtocol consoleProtocol, LiveTR liveTR) {
        try {
            System.out.println("Equip Id in set : "+consoleProtocol.getStreamData().getEquipId());
//            System.out.println(consoleProtocol.getStreamData().getRequestData().unpack(TOMDeviceInfo.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateTOMParameterVersion(ConsoleProtocol consoleProtocol) {
        try {
            System.out.println(consoleProtocol.getStreamData().getRequestData().unpack(TOMParameterVersion.class));
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void updateOperationMode(ConsoleProtocol consoleProtocol) {
        try{
            TOMModeControl tomModeControl= consoleProtocol.getStreamData().getRequestData().unpack(TOMModeControl.class);
            LiveTOM liveTOM = new LiveTOM();
            TOMOperationMode tomOperationMode=null;
            if(!tomModeControl.toString().contains("special_mode")) {
                switch (tomModeControl.getOperationMode()) {
                    case IN_SERVICE -> {
                        tomOperationMode = TOMOperationMode.IN_SERVICE;
                        tomOperationMode.updateQRSaleMode(tomModeControl.getQrSaleMode());
                        tomOperationMode.updateCardProcessingMode(tomModeControl.getCardProcessMode());
                    }
                    case OUT_OF_SERVICE -> {
                        tomOperationMode = TOMOperationMode.OUT_OF_SERVICE;
                    }
                    case MAINTENANCE -> {
                        tomOperationMode = TOMOperationMode.MAINTENANCE;
                    }
                    case UNRECOGNIZED -> {
                        tomOperationMode = TOMOperationMode.OTHER;
                    }
                    case TEST -> {
                        tomOperationMode = TOMOperationMode.TEST;
                    }
                }
                liveTOM.setOperationMode(tomOperationMode);
//            stationDynamicMapViewListener.updateTOMPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveTOM);
                stationDynamicMapViewListener.updateTOMOperationMode(consoleProtocol.getStreamData().getEquipId(), liveTOM);
            }
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void updateAGOperationMode(ConsoleProtocol consoleProtocol) {
        try{
            System.out.println("AG Operation Mode console"+consoleProtocol);
            AGModeControl agModeControl= consoleProtocol.getStreamData().getRequestData().unpack(AGModeControl.class);
            System.out.println("AG Operation Mode "+agModeControl.getOperationMode());
            LiveAG liveAG = new LiveAG();
            AGOperationMode agOperationMode=null;
            if(!agModeControl.toString().contains("special_mode"))switch(agModeControl.getOperationMode()){
                case  IN_SERVICE -> {
                    agOperationMode=AGOperationMode.IN_SERVICE;
//                    agOperationMode.updateQRSaleMode(agModeControl.getQrSaleMode());
//                    tomOperationMode.updateCardProcessingMode(tomModeControl.getCardProcessMode());
                }
                case OUT_OF_SERVICE ->{
                    agOperationMode=AGOperationMode.OUT_OF_SERVICE;
                }
                case MAINTENANCE ->  {
                    agOperationMode=AGOperationMode.MAINTENANCE;
                }
                case UNRECOGNIZED -> {
                    agOperationMode=AGOperationMode.OTHER;
                }
                case TEST -> {
                    agOperationMode=AGOperationMode.TEST;
                }
            }
            liveAG.setOperationMode(agOperationMode);
            liveAG.setCurrentStatus(SLEStatus.ONLINE);
//            stationDynamicMapViewListener.updateTOMPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveTOM);
            stationDynamicMapViewListener.updateAGOperationMode(consoleProtocol.getStreamData().getEquipId(), liveAG);
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void updateTVMOperationMode(ConsoleProtocol consoleProtocol) {
        try{
            System.out.println("TVM Operation Mode console"+consoleProtocol);
//            TOMModeControl tomModeControl= consoleProtocol.getStreamData().getRequestData().unpack(TOMModeControl.class);
            TVMModeControl tvmModeControl= consoleProtocol.getStreamData().getRequestData().unpack(TVMModeControl.class);
            System.out.println("TVM Operation Mode "+ tvmModeControl.getOperationMode());
            LiveTVM liveTVM = new LiveTVM();
            TVMOperationMode tvmOperationMode=null;
            if(!tvmModeControl.toString().contains("special_mode"))switch(tvmModeControl.getOperationMode()){
                case  IN_SERVICE -> {
                    tvmOperationMode=TVMOperationMode.IN_SERVICE;
//                    agOperationMode.updateQRSaleMode(agModeControl.getQrSaleMode());
//                    tomOperationMode.updateCardProcessingMode(tomModeControl.getCardProcessMode());
                }
                case OUT_OF_SERVICE ->{
                    tvmOperationMode=TVMOperationMode.OUT_OF_SERVICE;
                }
                case MAINTENANCE ->  {
                    tvmOperationMode=TVMOperationMode.MAINTENANCE;
                }
                case UNRECOGNIZED -> {
                    tvmOperationMode=TVMOperationMode.OTHER;
                }
                case TEST -> {
                    tvmOperationMode=TVMOperationMode.TEST;
                }
            }
            liveTVM.setOperationMode(tvmOperationMode);
            liveTVM.setCurrentStatus(SLEStatus.ONLINE);
//            stationDynamicMapViewListener.updateTOMPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveTOM);
            stationDynamicMapViewListener.updateTVMOperationMode(consoleProtocol.getStreamData().getEquipId(), liveTVM);
        }catch (Exception e){
            e.printStackTrace();

        }
    }

    private void updateTROperationMode(ConsoleProtocol consoleProtocol) {
        try{
            System.out.println("TR Operation Mode console"+consoleProtocol);
            TRModeControl trModeControl= consoleProtocol.getStreamData().getRequestData().unpack(TRModeControl.class);
            System.out.println("TR Operation Mode "+trModeControl.getOperationMode());
            LiveTR liveTR = new LiveTR();
            TROperationMode trOperationMode=null;
            if(!trModeControl.toString().contains("special_mode"))switch(trModeControl.getOperationMode()){
                case  IN_SERVICE -> {
                    trOperationMode=TROperationMode.IN_SERVICE;
//                    agOperationMode.updateQRSaleMode(agModeControl.getQrSaleMode());
//                    tomOperationMode.updateCardProcessingMode(tomModeControl.getCardProcessMode());
                }
                case OUT_OF_SERVICE ->{
                    trOperationMode=TROperationMode.OUT_OF_SERVICE;
                }
                case MAINTENANCE ->  {
                    trOperationMode=TROperationMode.MAINTENANCE;
                }
                case UNRECOGNIZED -> {
                    trOperationMode=TROperationMode.OTHER;
                }
                case TEST -> {
                    trOperationMode=TROperationMode.TEST;
                }
            }
            liveTR.setOperationMode(trOperationMode);
            liveTR.setCurrentStatus(SLEStatus.ONLINE);
//            stationDynamicMapViewListener.updateTOMPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveTOM);
            stationDynamicMapViewListener.updateTROperationMode(consoleProtocol.getStreamData().getEquipId(), liveTR);
        }catch (Exception e){
            e.printStackTrace();

        }
    }

}