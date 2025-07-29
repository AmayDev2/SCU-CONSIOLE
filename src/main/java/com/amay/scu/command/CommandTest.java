package com.amay.scu.command;

import com.amay.scu.enums.StationSpecialMode;
import com.amay.scu.service.GrpcService;
import com.google.protobuf.Any;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.DeviceType;
import org.network.monitorandcontrol.SpecialMode;
import org.network.monitorandcontrol.ag.AGModeControl;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;
import org.network.monitorandcontrol.tom.TOMModeControl;
import org.network.monitorandcontrol.tr.TRModeControl;
import org.network.monitorandcontrol.tvm.TVMModeControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public enum CommandTest{
    INSTANCE;

    final Logger logger= LoggerFactory.getLogger(CommandTest.class);
    private  GrpcService grpcService;

    public void initializeCommandTest(GrpcService grpcService) {
        this.grpcService = grpcService;

    }

    public void sendCommand(CommandType command,DeviceType deviceType, String equipId,TOMModeControl tomModeControl) {

            try{
                ConsoleProtocol consoleProtocol = createCommandRequest(command, deviceType, equipId, tomModeControl);
                grpcService.sendMessage(consoleProtocol);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            }
    }
    public void sendCommand(CommandType command, DeviceType deviceType, String equipId, TVMModeControl tomModeControl) {

        try{
            ConsoleProtocol consoleProtocol = createCommandRequest(command, deviceType, equipId, tomModeControl);
            grpcService.sendMessage(consoleProtocol);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values.");
        }
    }
    public void sendCommand(CommandType command, DeviceType deviceType, String equipId, AGModeControl agModeControl) {

        try{
            ConsoleProtocol consoleProtocol = createCommandRequest(command, deviceType, equipId, agModeControl);
            grpcService.sendMessage(consoleProtocol);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values.");
        }
    }
    public void sendCommand(CommandType command, DeviceType deviceType, String equipId, TRModeControl tomModeControl) {

        try{
            ConsoleProtocol consoleProtocol = createCommandRequest(command, deviceType, equipId, tomModeControl);
            grpcService.sendMessage(consoleProtocol);
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values.");
        }
    }

    //station level command
    public void sendStationCommand(StationSpecialMode stationSpecialMode) {

        System.out.println("Selected command: " + stationSpecialMode.name());

        TOMModeControl tomModeControl=TOMModeControl.newBuilder().setSpecialMode(stationSpecialMode.getSpecialMode()).build();
            try {
                //CREATED Stream data object
                StreamData streamData=StreamData.newBuilder()
                        .setDeviceType(DeviceType.ALL)
                        .clearEquipId()
                        .setCommandType(CommandType.MODE_CONTROL)
                        .setRequestData(Any.pack(tomModeControl))
                        .build();

                //Create console protocol
                ConsoleProtocol consoleProtocol = ConsoleProtocol.newBuilder()
                        .setStreamData(streamData)
                        .build();
                grpcService.sendMessage(consoleProtocol);
                StationSpecialMode.setStationSpecialMode(stationSpecialMode);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            }
//        }
    }



    private ConsoleProtocol createCommandRequest(CommandType command, DeviceType deviceType, String equipId, TOMModeControl tOMModeControl) {
        return ConsoleProtocol.newBuilder()
                .setDeviceType(deviceType)
                .setStreamData(StreamData.newBuilder()
                        .setEquipId(equipId)
                        .setCommandType(command)
                        .setRequestData(Any.pack(tOMModeControl))
                        .build())
                .build();
    }
    private ConsoleProtocol createCommandRequest(CommandType command, DeviceType deviceType, String equipId, TVMModeControl tOMModeControl) {
        return ConsoleProtocol.newBuilder()
                .setDeviceType(deviceType)
                .setStreamData(StreamData.newBuilder()
                        .setEquipId(equipId)
                        .setCommandType(command)
                        .setDeviceType(deviceType)
                        .setRequestData(Any.pack(tOMModeControl))
                        .build())
                .build();
    }

    private ConsoleProtocol createCommandRequest(CommandType command, DeviceType deviceType, String equipId, TRModeControl tOMModeControl) {
        return ConsoleProtocol.newBuilder()
                .setDeviceType(deviceType)
                .setStreamData(StreamData.newBuilder()
                        .setEquipId(equipId)
                        .setCommandType(command)
                        .setDeviceType(deviceType)
                        .setRequestData(Any.pack(tOMModeControl))
                        .build())
                .build();
    }
    private ConsoleProtocol createCommandRequest(CommandType command, DeviceType deviceType, String equipId, AGModeControl agModeControl) {
        return ConsoleProtocol.newBuilder()
                .setDeviceType(deviceType)
                .setStreamData(StreamData.newBuilder()
                        .setEquipId(equipId)
                        .setCommandType(command)
                        .setDeviceType(deviceType)
                        .setRequestData(Any.pack(agModeControl))
                        .build())
                .build();
    }

}