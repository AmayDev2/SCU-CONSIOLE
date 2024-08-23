package com.amay.scu.grpc;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.amaytechnosystems.SCUServiceGrpc;
//import org.network.monitorandcontrol.MonitorAndControlGrpc;


public class ScuGrpcConfig {
    static ManagedChannel channel;

    static {
        channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()  // No TLS for local development
                .build();
    }

    public static org.amaytechnosystems.SCUServiceGrpc.SCUServiceBlockingStub getBlockingStub(){
        return org.amaytechnosystems.SCUServiceGrpc.newBlockingStub(channel);
    }

//    public static MonitorAndControlGrpc.MonitorAndControlStub getAsyncStub(){
//        return MonitorAndControlGrpc.newStub(channel);
//    }
//
//    public static MonitorAndControlGrpc.MonitorAndControlFutureStub getFutureStub(){
//        return MonitorAndControlGrpc.newFutureStub(channel);
//    }

    public static SCUServiceGrpc.SCUServiceBlockingStub reconnect() {
    	try {
            shutdown();
            channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                    .usePlaintext()  // No TLS for local development
                    .build();
    		return getBlockingStub();
    	} catch (Exception e) {
            e.printStackTrace();
    		return null;
    	}
    }


    public static void shutdown(){
        channel.shutdown();
    }
}
