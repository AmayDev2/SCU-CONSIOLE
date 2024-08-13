package com.amay.scu.command;

import com.amay.scu.service.GrpcService;
import com.google.protobuf.Any;
import javafx.scene.Parent;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.DeviceType;
import org.network.monitorandcontrol.OperationMode;
import org.network.monitorandcontrol.RequestType;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;
import org.network.monitorandcontrol.tom.TOMDeviceInfo;
import org.network.monitorandcontrol.tom.TOMModeControl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public enum CommandTest{
    INSTANCE;

    Logger logger= LoggerFactory.getLogger(CommandTest.class);
    private  GrpcService grpcService;
//    private final Scanner scanner;
//    ExecutorService singleThreadExecutor = null;
//    public static CommandTest getInstance(){
//        if(null!=this){
//
//        }
//    }


    public void initializeCommandTest(GrpcService grpcService) {
        this.grpcService = grpcService;
//        this.scanner = new Scanner(System.in);
//        singleThreadExecutor = Executors.newSingleThreadExecutor();
//        this.executeSendCommand();
    }
//    public CommandTest(){
//
//    }

//    private void executeSendCommand() {
//        singleThreadExecutor.submit(this::sendCommand,"Command Test");
//    }

//    public void sendCommand() {
//        int command;
//        while (true) {
//            try {
//                System.out.println("Enter Command (1: GET_DEVICE_INFO, 2: GET_DEVICE_VERSIONS, 3: MODE_CONTROL, 4: GET_PERIPHERAL_STATUS, 0: Exit): ");
//                command = Integer.parseInt(scanner.nextLine());
//
//                if (command == 0) {
//                    System.out.println("Exiting...");
//                    break;
//                }
//                System.out.println("Enter Device Type (0: TOM, 1: AG): ");
//                int deviceTypeInput = Integer.parseInt(scanner.nextLine());
//                DeviceType deviceType = (deviceTypeInput == 1) ? DeviceType.AG : DeviceType.TOM;
//
//                System.out.println("Enter Equipment ID: ");
//                String equipId = scanner.nextLine();
//
//                ConsoleProtocol consoleProtocol = buildConsoleProtocol(command, deviceType, equipId);
//                if (consoleProtocol != null) {
//                    grpcService.sendMessage(consoleProtocol);
//                } else {
//                    System.out.println("Invalid command. Please try again.");
//                }
//            } catch (NumberFormatException e) {
//                System.out.println("Invalid input. Please enter numeric values.");
//            }
//        }
//    }

        public void sendCommand(CommandType command,DeviceType deviceType, String equipId) {

            try{
                ConsoleProtocol consoleProtocol = createCommandRequest(command, deviceType, equipId);
                if (consoleProtocol != null) {
                    grpcService.sendMessage(consoleProtocol);
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            }
        }

    private ConsoleProtocol createCommandRequest(CommandType command, DeviceType deviceType, String equipId) {
        return ConsoleProtocol.newBuilder()
                .setDeviceType(deviceType)
                .setStreamData(StreamData.newBuilder()
                        .setEquipId(equipId)
                        .setCommandType(command)
                        .setRequestData(Any.pack(TOMModeControl.newBuilder().setOperationMode(OperationMode.OUT_OF_SERVICE).build()))
                        .build())
                .build();
    }


//    public ConsoleProtocol buildConsoleProtocol(int command, DeviceType deviceType, String equipId) {
//        logger.debug("command {} {} {}",command,deviceType,equipId);
//        return switch (command) {
//            case 1 -> ConsoleProtocol.newBuilder()
//                    .setDeviceType(deviceType)
//                    .setStreamData(StreamData.newBuilder().setEquipId(equipId).setCommandType(CommandType.GET_DEVICE_INFO).build())
//                    .build();
//            case 2 -> ConsoleProtocol.newBuilder()
//                    .setDeviceType(deviceType)
//                    .setStreamData(StreamData.newBuilder().setEquipId(equipId).setCommandType(CommandType.GET_DIVICE_VERSIONS).build())
//                    .build();
//            case 3 -> ConsoleProtocol.newBuilder()
//                    .setDeviceType(deviceType)
//                    .setStreamData(StreamData.newBuilder().setEquipId(equipId).setCommandType(CommandType.MODE_CONTROL).build())
//                    .build();
//            case 4 -> ConsoleProtocol.newBuilder()
//                    .setDeviceType(deviceType)
//                    .setStreamData(StreamData.newBuilder().setEquipId(equipId).setCommandType(CommandType.GET_PERIPHERAL_STATUS).build())
//                    .build();
//            default -> null;
//        };
//    }

}