package com.amay.scu;
//import com.amay.scu.sles.TOMAbstract;
//import com.amay.scu.grpc.GrpcConfig;
//import com.amay.scu.service.GrpcService;

import com.amay.scu.controller.SCUController;
import com.amay.scu.controller.StationDynamicMapController;
import com.amay.scu.enums.AGOperationMode;
import com.amay.scu.enums.TOMOperationMode;
import com.amay.scu.grpc.GrpcConfig;
//import com.amay.scu.service.GrpcService;
//import com.amay.scu.service.GrpcService;
import com.amay.scu.grpc.ScuGrpcConfig;
import com.amay.scu.listenner.impl.StationDynamicMapViewListener;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.popup.PopupContent;
import com.amay.scu.service.GrpcService;
import com.amay.scu.service.ScuGrpcService;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.test_grpc_service.SCUService;
import com.amay.scu.util.ObjectSerialization;
import com.fasterxml.jackson.core.type.TypeReference;
import com.google.protobuf.Any;
import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
        import javafx.stage.Stage;
import org.network.monitorandcontrol.*;
import org.network.monitorandcontrol.scu_console.ConsoleProtocol;
import org.network.monitorandcontrol.scu_console.StreamData;
import org.network.monitorandcontrol.tom.TOMModeControl;
import org.network.monitorandcontrol.tom.TOMPeripheralStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
        import views.Path;

import java.util.Map;

public class SCUApplication  extends Application {

     private static final Logger logger = LoggerFactory.getLogger(SCUApplication.class);


    @Override
    public void init() {
      logger.debug("SCUApplication init method called");

    }

    @Override
    public void start(Stage primaryStage) {
try {
//
//    GrpcService grpcService = new GrpcService(GrpcConfig.getAsyncStub());
//    grpcService.initialConnectionRequest(null);
//    grpcService.initialConnectionRequest(null);
//    grpcService.initialConnectionRequest(null);


    FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.SCU_VIEW));
    Scene scene=new Scene(loader.load(), (double) 1920 /2, (double) 1080 /2);
//    scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());
    primaryStage.setMaximized(true);

    primaryStage.setScene(scene);
    primaryStage.show();

//    primaryStage.setTitle("Redis Subscriber");

   GrpcService grpcService = new GrpcService(GrpcConfig.getAsyncStub());
    grpcService.initialConnectionRequest(null);
//
//    //1-command test
//    grpcService.sendMessage(ConsoleProtocol.newBuilder()
//            .setDeviceType(DeviceType.TOM)
//            .setStreamData(StreamData.newBuilder().setEquipId("TOM1").setCommandType(CommandType.MODE_CONTROL).setRequestData(Any.pack(TOMModeControl.newBuilder().setOperationMode(OperationMode.IN_SERVICE).build())).build())
//            .build());

   ScuGrpcService.INSTANCE.ScuGrpcService(ScuGrpcConfig.getBlockingStub());
    //TODO: Add the below line to the SCUController
    ScuGrpcConfig.watchConnectivityState();
    ScuGrpcService.INSTANCE.sendMessage(null);


/*
//    StationDynamicMapController scu=loader.getController();
    StationDynamicMapViewListener stationDynamicMapViewListener= StationDynamicMapViewListener.getInstance();
    LiveTOM liveTOM=new LiveTOM();
    liveTOM.setOperationMode(TOMOperationMode.DEFICIENT);
    LiveTOM liveTOM1=new LiveTOM();
    liveTOM1.setOperationMode(TOMOperationMode.DISCONNECTED);
//    LiveTOM liveTOM2=new LiveTOM();
//    liveTOM2.setOperationMode(TOMOperationMode.MAINTENANCE);
//    LiveAG liveAG=new LiveAG();

    stationDynamicMapViewListener.updateTOMOperationMode("TOM4", liveTOM);
//    stationDynamicMapViewListener.updateAGOperationMode("AG4", liveAG);
    stationDynamicMapViewListener.updateTOMOperationMode("TOM3", liveTOM1);
//    stationDynamicMapViewListener.updateTOMOperationMode("TOM2", liveTOM2);*/






//    SCUService scuService=new SCUService();
//    ConsoleProtocol consoleProtocol=ConsoleProtocol.newBuilder()
//            .setDeviceType(DeviceType.TOM)
//            .setStreamData(StreamData.newBuilder().setEquipId("TOM1").setRequestType(RequestType.PERIPHERAL_STATUS).setRequestData(Any.pack(TOMPeripheralStatus.newBuilder().setScuConnected(true).build())).build())
//            .build();
//
//    scuService.detectDeviceType(DeviceType.TOM,consoleProtocol);

    primaryStage.setOnCloseRequest(e -> {
//        SLELocationListObject
//        grpcService.shutdown();
        System.out.println("Application closed");
        ObjectSerialization.saveJsonToFile(ObjectSerialization.objectToJson(SLELocationListObject.list),"sleLocationList.json");

        System.exit(0);}); // Exit the application when the window is closed


}catch (Exception e){
    System.out.println("Error in SCUApplication start method : "+e.getMessage());
    e.printStackTrace();
}


    }



    public static void main(String[] args) {
        launch(args);
    }

}
