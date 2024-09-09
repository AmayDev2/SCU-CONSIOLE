package com.amay.scu.grpc;


import com.amay.scu.interceptor.AuthClientInterceptor;
import com.amay.scu.interceptor.ClientIdInterceptor;
import com.amay.scu.interceptor.ClientLoggingInterceptor;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.network.monitorandcontrol.MonitorAndControlGrpc;
//import org.network.monitorandcontrol.MonitorAndControlGrpc;


public class GrpcConfig {
    static ManagedChannel channel;

    static {
        channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .intercept(new ClientLoggingInterceptor()) // Apply the interceptor
                .intercept(new AuthClientInterceptor("1234")) // Pass the token to the interceptor
                .intercept(new ClientIdInterceptor("03100902"))
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
        shutdown();
    	try {
            channel = ManagedChannelBuilder.forAddress("localhost", 9000)
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
