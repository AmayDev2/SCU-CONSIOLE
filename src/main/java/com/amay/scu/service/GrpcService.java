package com.amay.scu.service;

import com.amay.scu.grpc.GrpcConfig;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.stub.StreamObserver;
import org.network.monitorandcontrol.MonitorAndControlGrpc;
import org.network.monitorandcontrol.ag.AGDeviceInfo;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GrpcService  {

    Logger logger = LoggerFactory.getLogger(GrpcService.class);

    private MonitorAndControlGrpc.MonitorAndControlStub asyncStub=null;

    private StreamObserver<ConsoleProtocol> requestObserver=null;

    public  GrpcService(MonitorAndControlGrpc.MonitorAndControlStub asyncStub ) {
        this.asyncStub=asyncStub;
        requestObserver = scStreamObserver();

    }

    // Send some ConsoleStream messages
    public void sendMessage(ConsoleProtocol message) {
        logger.info("Sending message to server: {}", message);
            requestObserver.onNext(message);
    }

    public void initialConnectionRequest(StreamData message) {
        logger.info("Sending initial request to server: {}", message);
        requestObserver.onNext(ConsoleProtocol.newBuilder().setConsoleId("imscuconsole").build());
    }

    public void markComplete() {
        requestObserver.onCompleted();
    }

    //  response observer
    private StreamObserver<ConsoleProtocol>  scStreamObserver() {
        return asyncStub.scStream(new StreamObserver<ConsoleProtocol>() {
            @Override
            public void onNext(ConsoleProtocol value) {
                try {
                    if (value.getStreamData().hasRequestData()){
//                    logger.info("Received message from server: {}", value.getStreamData().getRequestData().unpack(AGDeviceInfo.class).toString());
                    System.out.println("Received "+ value.getStreamData().getRequestData().unpack(AGDeviceInfo.class).toString());
                    }
                    else{
                logger.info("Received message from server: {}", value);

                    }
                } catch (InvalidProtocolBufferException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onError(Throwable t){
                try {
                    Thread.sleep(5000);
                }catch(Exception e){
                    logger.error("Error in receiving message from server: {}", t.getMessage());
                }
                initialConnectionRequest(null);
                logger.error("Error in receiving message from server: {}", t.getMessage());
            }

            @Override
            public void onCompleted() {
                logger.info("Server has completed sending messages");
            }
        });
    }

    public void shutdown() {
        requestObserver.onCompleted();
        GrpcConfig.shutdown();
    }






}
