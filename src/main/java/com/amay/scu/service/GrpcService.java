package com.amay.scu.service;

import com.amay.scu.grpc.GrpcConfig;
import com.amay.scu.listenner.StationDynamicMapViewListener;
import io.grpc.stub.StreamObserver;
import org.network.monitorandcontrol.MonitorAndControlGrpc;
import org.network.monitorandcontrol.scu_console.ConsoleStream;
import org.network.monitorandcontrol.scu_console.PushUpdates;

public class GrpcService  {



    private MonitorAndControlGrpc.MonitorAndControlStub asyncStub;

    StreamObserver<ConsoleStream> requestObserver;



    public  GrpcService(MonitorAndControlGrpc.MonitorAndControlStub asyncStub ) {

        this.asyncStub=asyncStub;
    }

    public void scStream() {
        requestObserver = asyncStub.sCStream(new StreamObserver<PushUpdates>() {
            @Override
            public void onNext(PushUpdates response) {
                System.out.println("Received PushUpdate:");
                System.out.println("IP: " + response.getIp());
                System.out.println("Device ID: " + response.getDeviceId());
                System.out.println("Device Name: " + response.getDeviceName());
                System.out.println("Is Connected: " + response.getIsconnected());
                    String st=response.getDeviceId().equals("451278")?"TOM1":"TOM2";
                StationDynamicMapViewListener.getInstance().updateSLEStatus(st, response.getIsconnected());

                //response.getIsconnected()
            }

            @Override
            public void onError(Throwable t) {

                System.err.println("Error in SCStream: " + t.getMessage());
            }

            @Override
            public void onCompleted() {
                System.out.println("SCStream completed.");
            }
        });

        // Send some ConsoleStream messages
        try {
            ConsoleStream message1 = ConsoleStream.newBuilder()
                    .setIp("192.168.1.1")
                    .setConsoleId("console_1")
                    .setStatusCode(200)
                    .setErrorMsg("")
                    .build();
            requestObserver.onNext(message1);

//            ConsoleStream message2 = ConsoleStream.newBuilder()
//                    .setIp("192.168.1.2")
//                    .setConsoleId("console_2")
//                    .setStatusCode(404)
//                    .setErrorMsg("Not Found")
//                    .build();
//            requestObserver.onNext(message2);

            // Mark the end of requests
//            requestObserver.onCompleted();
        } catch (RuntimeException e) {
            requestObserver.onError(e);
            throw e;
        }
    }

    public void shutdown() {
        requestObserver.onCompleted();
        GrpcConfig.shutdown();
    }

}
