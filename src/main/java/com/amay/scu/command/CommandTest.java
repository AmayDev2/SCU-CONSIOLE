package com.amay.scu.command;

import com.amay.scu.enums.StationSpecialMode;
import com.amay.scu.service.GrpcService;
import com.google.protobuf.Any;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.DeviceType;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;
import org.network.monitorandcontrol.tom.TOMModeControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public enum CommandTest{
    INSTANCE;

    final Logger logger= LoggerFactory.getLogger(CommandTest.class);
    private  GrpcService grpcService;
    Set<String> set;

    public void initializeCommandTest(GrpcService grpcService, Set<String> set) {
        this.grpcService = grpcService;
        this.set=set;

    }

    public void sendCommand(CommandType command,DeviceType deviceType, String equipId,TOMModeControl tomModeControl) {

            try{
                ConsoleProtocol consoleProtocol = createCommandRequest(command, deviceType, equipId, tomModeControl);
                if (consoleProtocol != null) {
                    grpcService.sendMessage(consoleProtocol);
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            }
    }

    //station level command
    public void sendStationCommand(StationSpecialMode specialMode) {
        System.out.println("Selected command: " + specialMode.name());
        TOMModeControl tomModeControl=TOMModeControl.newBuilder().setSpecialMode(specialMode.getGrpcMode()).build();

        for(String equipId:set) {
            try {
                char[] arr=equipId.toCharArray();
                DeviceType deviceTypes=arr[6]=='2'?DeviceType.AG:DeviceType.TOM;
                ConsoleProtocol consoleProtocol = ConsoleProtocol.newBuilder()
                        .setDeviceType(deviceTypes)
                        .setStreamData(StreamData.newBuilder()
                                .setEquipId(equipId)
                                .setCommandType(CommandType.MODE_CONTROL)
                                .setRequestData(Any.pack(tomModeControl))
                                .build())
                        .build();
                if (consoleProtocol != null) {
                    logger.info("Sending command to device: {}", equipId);
                    grpcService.sendMessage(consoleProtocol);
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            }
        }
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

}