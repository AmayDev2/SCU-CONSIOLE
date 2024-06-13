package com.amay.scu.test_grpc_service;

import com.amay.scu.enums.TOMOperationMode;
import com.amay.scu.listenner.impl.StationDynamicMapViewListener;
import com.amay.scu.sleobj.LiveTOM;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.DeviceType;
import org.network.monitorandcontrol.RequestType;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;

import com.google.protobuf.Any;

import io.grpc.stub.StreamObserver;
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

    private void updatePeripheralStatus(ConsoleProtocol consoleProtocol){
        try {
            TOMPeripheralStatus tomPeripheralStatus= consoleProtocol.getStreamData().getRequestData().unpack(TOMPeripheralStatus.class);
            LiveTOM liveTOM=new LiveTOM();
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
                break;
            case PERIPHERAL_STATUS:
                //TOM Peripheral Status decode and display
                this.updatePeripheralStatus(consoleProtocol);
                break;
            case PARAMETER_VERSION:
                //TOM Version Check decode and display
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

    private void updateOperationMode(ConsoleProtocol consoleProtocol) {
//        TOMPeripheralStatus tomPeripheralStatus= consoleProtocol.getStreamData().getRequestData().unpack(TOMModeControl)
//        LiveTOM liveTOM=new LiveTOM();
//        liveTOM.setOperationMode();



//        stationDynamicMapViewListener.updateTOMPeripheralStatus(consoleProtocol.getStreamData().getEquipId(), liveTOM);


    }
//
//    /*
//     * command_type
//     * */
//
//    public void sendCommand(CommandType command_type, String equip_id, Any data) {
//        StreamData command_data = StreamData.newBuilder().setCommandType(command_type).setEquipId(equip_id).setRequestData(data).build();
//        stream_observer.onNext(ConsoleProtocol.newBuilder().setStreamData(command_data).build());
//    }
}