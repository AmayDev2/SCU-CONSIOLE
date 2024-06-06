package com.amay.scu.test_grpc_service;

import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.RequestType;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;

import com.google.protobuf.Any;

import io.grpc.stub.StreamObserver;

public class SCUService {

    private StreamObserver<ConsoleProtocol> stream_observer;

    public SCUService(StreamObserver<ConsoleProtocol> stream_observer) {
        this.stream_observer = stream_observer;
    }

    public void decodeAGResponse(RequestType requeust_type, Any data) {
        switch (requeust_type) {
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

    public void decodeTOMResponse(RequestType requeust_type, Any data) {
        switch (requeust_type) {
            case DIVICE_DISCONNECT:
                //device is disconnected
                break;
            case DEVICE_INFO:
                //TOM Device Info decode and display
                break;
            case PERIPHERAL_STATUS:
                //TOM Peripheral Status decode and display
                break;
            case PARAMETER_VERSION:
                //TOM Version Check decode and display
                break;
            case ALARMS:
                //last 10 alarms
            default:
                break;
        }
    }

    /*
     * command_type
     * */

    public void sendCommand(CommandType command_type, String equip_id, Any data) {
        StreamData command_data = StreamData.newBuilder().setCommandType(command_type).setEquipId(equip_id).setRequestData(data).build();
        stream_observer.onNext(ConsoleProtocol.newBuilder().setStreamData(command_data).build());
    }
}