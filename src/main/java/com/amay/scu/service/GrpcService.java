package com.amay.scu.service;

import com.amay.scu.command.CommandTest;
import com.amay.scu.grpc.GrpcConfig;
import com.amay.scu.test_grpc_service.SCUService;
import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import io.grpc.stub.StreamObserver;
import org.network.monitorandcontrol.MonitorAndControlGrpc;
import org.network.monitorandcontrol.ag.AGDeviceInfo;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

import static java.util.concurrent.CompletableFuture.runAsync;


public class GrpcService  {

    Logger logger = LoggerFactory.getLogger(GrpcService.class);

    private MonitorAndControlGrpc.MonitorAndControlStub asyncStub=null;

    private StreamObserver<ConsoleProtocol> requestObserver=null;
    private SCUService scuService=null;
    private Set<String> set =null;

    public  GrpcService(MonitorAndControlGrpc.MonitorAndControlStub asyncStub ) {
        this.asyncStub=asyncStub;
        requestObserver = scStreamObserver();
        this.set= new HashSet<>();
        scuService=new SCUService(set);

    }

    // Send some ConsoleStream messages
    public void sendMessage(ConsoleProtocol message) {
        logger.info("Sending message to server: {}", message);
            requestObserver.onNext(message);
    }



    public void initialConnectionRequest(StreamData message) {
        logger.info("Sending initial request to server: {}", message);
        requestObserver.onNext(ConsoleProtocol.newBuilder().setConsoleId("imscuconsole").build());
        CommandTest.INSTANCE.initializeCommandTest(this,set);
    }

    private void reconnect(){
        try{
            Thread.sleep(5000);
        }catch (InterruptedException e){
            e.printStackTrace();}

        requestObserver = scStreamObserver();
        initialConnectionRequest(null);
    }
    public void markComplete() {
        requestObserver.onCompleted();
    }

    //  response observer
    private StreamObserver<ConsoleProtocol>  scStreamObserver() {
        return asyncStub.scStream(new StreamObserver<ConsoleProtocol>() {
            @Override
            public void onNext(ConsoleProtocol value) {
                System.out.println("INCOMING: "+value.toString());
                if (value.hasStreamData()){

                    scuService.detectDeviceType(value.getDeviceType(),value);
                }
                else{
                    logger.info("Received message from server: {}", value);
                }
            }

            @Override
            public void onError(Throwable t){
                logger.error("message from server: {}", t.getMessage());
                runAsync(()->reconnect());

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
