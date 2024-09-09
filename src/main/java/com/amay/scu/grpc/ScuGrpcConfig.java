package com.amay.scu.grpc;


import com.amay.scu.interceptor.AuthClientInterceptor;
import com.amay.scu.interceptor.ClientIdInterceptor;
import com.amay.scu.interceptor.ClientLoggingInterceptor;
import io.grpc.ConnectivityState;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.amaytechnosystems.SCUServiceGrpc;
//import org.network.monitorandcontrol.MonitorAndControlGrpc;


public class ScuGrpcConfig {
    static ManagedChannel channel;

    static {
        channel = ManagedChannelBuilder.forAddress("localhost", 9000)
                .usePlaintext()  // No TLS for local development
                .intercept(new ClientLoggingInterceptor()) // Apply the interceptor
                .intercept(new AuthClientInterceptor("1234")) // Pass the token to the interceptor
                .intercept(new ClientIdInterceptor("03100902"))
                .build();
        System.out.println("Channel created "+channel.toString());
    }

    public static void watchConnectivityState() {
        ConnectivityState currentState = channel.getState(false);
        System.out.println("Initial state: " + currentState);

        channel.notifyWhenStateChanged(currentState, new Runnable() {
            @Override
            public void run() {
                ConnectivityState newState = channel.getState(false);
                System.out.println("State changed to: " + newState);

                // Continue watching for changes
                watchConnectivityState();
            }
        });
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
