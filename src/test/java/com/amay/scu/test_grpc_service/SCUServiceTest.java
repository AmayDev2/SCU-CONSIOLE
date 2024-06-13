package com.amay.scu.test_grpc_service;

import com.amay.scu.grpc.GrpcConfig;
import com.amay.scu.service.GrpcService;
import com.google.protobuf.Any;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.network.monitorandcontrol.DeviceType;
import org.network.monitorandcontrol.RequestType;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;
import org.network.monitorandcontrol.tom.TOMPeripheralStatus;

import static org.junit.jupiter.api.Assertions.*;

class SCUServiceTest {

    @BeforeEach
    void setUp() {
//        GrpcService grpcService = new GrpcService(GrpcConfig.getAsyncStub());
//        grpcService.initialConnectionRequest(null);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    public void updateSLE(){
        SCUService scuService=new SCUService();
        ConsoleProtocol consoleProtocol=ConsoleProtocol.newBuilder()
                        .setDeviceType(DeviceType.TOM)
                                .setStreamData(StreamData.newBuilder().setEquipId("TOM1").setRequestType(RequestType.PERIPHERAL_STATUS).setRequestData(Any.pack(TOMPeripheralStatus.newBuilder().build())).build())
                                        .build();

        scuService.detectDeviceType(DeviceType.TOM,consoleProtocol);

    }
}