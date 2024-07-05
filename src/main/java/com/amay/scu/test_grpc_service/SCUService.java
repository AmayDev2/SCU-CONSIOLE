package com.amay.scu.test_grpc_service;

import com.amay.scu.enums.TOMOperationMode;
import com.amay.scu.listenner.impl.StationDynamicMapViewListener;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.DeviceType;
import org.network.monitorandcontrol.OperationMode;
import org.network.monitorandcontrol.RequestType;
import org.network.monitorandcontrol.ag.AGPeripheralStatus;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;

import com.google.protobuf.Any;

import io.grpc.stub.StreamObserver;
import org.network.monitorandcontrol.tom.TOMDeviceInfo;
import org.network.monitorandcontrol.tom.TOMModeControl;
import org.network.monitorandcontrol.tom.TOMParameterVersion;
import org.network.monitorandcontrol.tom.TOMPeripheralStatus;

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
                decodeTOMResponse(value);
                break;
            }
            case AG :{
                decodeAGResponse(value);
                break;
            }
            default:{

            }
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
                break;
            case DEVICE_INFO:
                //AG Device Info decode and display
                break;
            case PERIPHERAL_STATUS:
                //AG Peripheral Status decode and display
                this.updatePeripheralStatus(consoleProtocol,new LiveAG());
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
                break;
            case DEVICE_INFO:
                //TOM Device Info decode and display
                checkTOMInfo(consoleProtocol);
                break;
            case PERIPHERAL_STATUS:
                //TOM Peripheral Status decode and display
                this.updatePeripheralStatus(consoleProtocol,new LiveTOM());
                break;
            case PARAMETER_VERSION:
                //TOM Version Check decode and display
                updateTOMParameterVersion(consoleProtocol);
                break;
//            case OPERATION_MODE:
//                this.updateOperationMode(consoleProtocol);
//                break;
            case ALARMS:
                //last 10 alarms
            default:
                break;
        }
    }

    private void checkTOMInfo(ConsoleProtocol consoleProtocol) {
        try {
            System.out.println(consoleProtocol.getStreamData().getRequestData().unpack(TOMDeviceInfo.class));
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
            TOMOperationMode tomOperationMode=TOMOperationMode.DISCONNECTED;
            switch(tomModeControl.getOperationMode()){
                case  IN_SERVICE -> {
                    tomOperationMode=TOMOperationMode.IN_SERVICE;
                    tomOperationMode.updateQRSaleMode(tomModeControl.getQrSaleMode());
                    tomOperationMode.updateCardProcessingMode(tomModeControl.getCardProcessMode());
                }
                case OUT_OF_SERVICE ->{
                    tomOperationMode=TOMOperationMode.OUT_OF_SERVICE;
                }
                case MAINTENANCE ->  {
                    tomOperationMode=TOMOperationMode.MAINTENANCE;
                }
                case UNRECOGNIZED -> {
                    tomOperationMode=TOMOperationMode.OTHER;
                }
                case TEST -> {
                    tomOperationMode=TOMOperationMode.TEST;
                }
            }
            liveTOM.setOperationMode(tomOperationMode);
            stationDynamicMapViewListener.updateTOMPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveTOM);
        }catch (Exception e){

        }
    }

}