package com.amay.scu.grpc;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.network.monitorandcontrol.MonitorAndControlGrpc;
//import org.network.monitorandcontrol.MonitorAndControlGrpc;


public class GrpcConfig {
    static ManagedChannel channel;

    static {
        channel = ManagedChannelBuilder.forAddress("localhost", 8000)
                .usePlaintext()  // No TLS for local development
                .build();
    }

    public static MonitorAndControlGrpc.MonitorAndControlBlockingStub getBlockingStub(){
        return MonitorAndControlGrpc.newBlockingStub(channel);
    }

    public static MonitorAndControlGrpc.MonitorAndControlStub getAsyncStub(){
        return MonitorAndControlGrpc.newStub(channel);
    }

    public static MonitorAndControlGrpc.MonitorAndControlFutureStub getFutureStub(){
        return MonitorAndControlGrpc.newFutureStub(channel);
    }

    public static boolean reconnect() {
    	try {
            channel = ManagedChannelBuilder.forAddress("localhost", 8000)
                    .usePlaintext()  // No TLS for local development
                    .build();
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }


    public static void shutdown(){
        channel.shutdown();
    }
}
