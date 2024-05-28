package com.amay.scu.grpc;


import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.network.monitorandcontrol.MonitorAndControlGrpc;
import org.network.monitorandcontrol.ag.*;

public class GrpcConfig {
    static ManagedChannel channel;

    static{

        channel = ManagedChannelBuilder.forAddress("localhost", 6565)
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


//    public static FtpServiceGrpc.FtpServiceBlockingStub getFileBlockingStub(){
//        return FtpServiceGrpc.newBlockingStub(channel);
//    }
//
//    public static FtpServiceGrpc.FtpServiceStub getFileAsyncStub(){
//        return FtpServiceGrpc.newStub(channel);
//    }
//
//    public static FtpServiceGrpc.FtpServiceFutureStub getFileFutureStub(){
//        return FtpServiceGrpc.newFutureStub(channel);
//    }

    public static void shutdown(){
        channel.shutdown();
    }
}
