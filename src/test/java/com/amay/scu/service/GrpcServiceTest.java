package com.amay.scu.service;

import com.amay.scu.grpc.GrpcConfig;
import com.google.protobuf.Any;
import org.junit.jupiter.api.Test;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.DeviceType;
import org.network.monitorandcontrol.ag.AGDeviceInfo;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;



class GrpcServiceTest {

    Scanner scanner = new Scanner(System.in);


    @Test
    public void sendCommandTest() throws InterruptedException {

        GrpcService grpcService = new GrpcService(GrpcConfig.getAsyncStub());
        grpcService.initialConnectionRequest(null);

        Thread.sleep(2000);
        System.out.println("Command selection");

        int input = 1;
        do {

            System.out.println("Enter selection");
            input = scanner.nextInt();
            switch (input) {
                case 1 -> {

                    ConsoleProtocol consoleProtocol= ConsoleProtocol.newBuilder()
                                    .setStreamData(StreamData.newBuilder()
                                            .setCommandType(CommandType.GET_DEVICE_INFO)
                                            .setEquipId("1234")
                                             .build())
                                            .build();

                    grpcService.sendMessage(consoleProtocol);
                    // Code for case 1
                    System.out.println("You selected option 1");
                }
                case 2 -> {
                    // Code for case 2
                    System.out.println("You selected option 2");
                }
                case 3 -> {
                    // Code for case 3
                    System.out.println("You selected option 3");
                }
                default -> {
                    // Code for default case
                    System.out.println("Invalid selection");
                }


            }

        } while (input != 1306);

        Thread.sleep(10000);
        grpcService.shutdown();
    }

}
