package com.amay.scu.command;

import com.amay.scu.service.GrpcService;
import com.google.protobuf.Any;
import javafx.scene.Parent;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.DeviceType;
import org.network.monitorandcontrol.RequestType;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;
import org.network.monitorandcontrol.tom.TOMDeviceInfo;

import java.util.Scanner;

public class CommandTest {

    private final GrpcService grpcService;
    private final Scanner scanner;

    public CommandTest(GrpcService grpcService) {
        this.grpcService = grpcService;
        this.scanner = new Scanner(System.in);
        executeSendCommand();
    }

    private void executeSendCommand() {
        Thread thread = new Thread(this::sendCommand,"Command Test");
        thread.start();
    }

    public void sendCommand() {
        int command;
        while (true) {
            try {
                System.out.println("Enter Command (1: GET_DEVICE_INFO, 2: GET_DEVICE_VERSIONS, 3: MODE_CONTROL, 4: GET_PERIPHERAL_STATUS, 0: Exit): ");
                command = Integer.parseInt(scanner.nextLine());

                if (command == 0) {
                    System.out.println("Exiting...");
                    break;
                }
                System.out.println("Enter Device Type (0: TOM, 1: AG): ");
                int deviceTypeInput = Integer.parseInt(scanner.nextLine());
                DeviceType deviceType = (deviceTypeInput == 1) ? DeviceType.AG : DeviceType.TOM;

                System.out.println("Enter Equipment ID: ");
                String equipId = scanner.nextLine();

                ConsoleProtocol consoleProtocol = buildConsoleProtocol(command, deviceType, equipId);
                if (consoleProtocol != null) {
                    grpcService.sendMessage(consoleProtocol);
                } else {
                    System.out.println("Invalid command. Please try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter numeric values.");
            }
        }
    }

    private ConsoleProtocol buildConsoleProtocol(int command, DeviceType deviceType, String equipId) {
        return switch (command) {
            case 1 -> ConsoleProtocol.newBuilder()
                    .setDeviceType(deviceType)
                    .setStreamData(StreamData.newBuilder().setEquipId(equipId).setCommandType(CommandType.GET_DEVICE_INFO).build())
                    .build();
            case 2 -> ConsoleProtocol.newBuilder()
                    .setDeviceType(deviceType)
                    .setStreamData(StreamData.newBuilder().setEquipId(equipId).setCommandType(CommandType.GET_DIVICE_VERSIONS).build())
                    .build();
            case 3 -> ConsoleProtocol.newBuilder()
                    .setDeviceType(deviceType)
                    .setStreamData(StreamData.newBuilder().setEquipId(equipId).setCommandType(CommandType.MODE_CONTROL).build())
                    .build();
            case 4 -> ConsoleProtocol.newBuilder()
                    .setDeviceType(deviceType)
                    .setStreamData(StreamData.newBuilder().setEquipId(equipId).setCommandType(CommandType.GET_PERIPHERAL_STATUS).build())
                    .build();
            default -> null;
        };
    }
}