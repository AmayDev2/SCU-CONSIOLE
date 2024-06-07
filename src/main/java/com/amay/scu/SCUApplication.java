package com.amay.scu;
//import com.amay.scu.sles.TOMAbstract;
//import com.amay.scu.grpc.GrpcConfig;
//import com.amay.scu.service.GrpcService;

import com.amay.scu.grpc.GrpcConfig;
//import com.amay.scu.service.GrpcService;
//import com.amay.scu.service.GrpcService;
import com.amay.scu.service.GrpcService;
import javafx.application.Application;
        import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
        import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
        import views.Path;

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
    GrpcService grpcService = new GrpcService(GrpcConfig.getAsyncStub());
    grpcService.initialConnectionRequest(null);
//    grpcService.initialConnectionRequest(null);
//    grpcService.initialConnectionRequest(null);


    FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.SCU_DYNAMIC_VIEW));
    Scene scene=new Scene(loader.load(), 1000, 500);
    scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());

    primaryStage.setScene(scene);
//    primaryStage.setTitle("Redis Subscriber");

    primaryStage.setOnCloseRequest(e -> {
//        grpcService.shutdown();
        System.exit(0);}); // Exit the application when the window is closed

    primaryStage.show();
}catch (Exception e){
    System.out.println("Error in SCUApplication start method : "+e.getMessage());
    e.getMessage();
}


    }

    public static void main(String[] args) {
        launch(args);
    }

}
