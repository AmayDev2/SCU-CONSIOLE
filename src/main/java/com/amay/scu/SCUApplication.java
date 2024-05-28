package com.amay.scu;
//import com.amay.scu.sles.TOMAbstract;
import com.amay.scu.controller.TOMController;
import com.amay.scu.sles.SLEFactory;
import com.amay.scu.sles.TOMAbstractFactory;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import views.Path;

public class SCUApplication  extends Application {

     private static Logger logger = LoggerFactory.getLogger(SCUApplication.class);

    private static final String CHANNEL_NAME = "SLE_COMMAND";
    private static final String REDIS_HOST = "redis-19373.c241.us-east-1-4.ec2.cloud.redislabs.com";
    private static final int REDIS_PORT = 19373;

    private TextArea messageArea;

    @Override
    public void start(Stage primaryStage) {
try {

//     new TOMAbstractFactory().createTOM();
//
//    try {
//        TOMController tomController =
//                (TOMController) SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane);
//
//        tomController= (TOMController) SLEFactory.getSLEFactory(new TOMAbstractFactory(), anchorPane);
//
//    }catch (Exception e){
//        e.printStackTrace();
//    }

    FXMLLoader loader = new FXMLLoader(getClass().getResource(Path.SCU_DYNAMIC_VIEW));
    Scene scene=new Scene(loader.load(), 1000, 500);
    scene.getStylesheets().add(getClass().getResource("css/application.css").toExternalForm());

    primaryStage.setScene(scene);
    primaryStage.setTitle("Redis Subscriber");

    primaryStage.setOnCloseRequest(e -> System.exit(0)); // Exit the application when the window is closed

    primaryStage.show();
}catch (Exception e){
    System.out.println("Error in SCUApplication start method : "+e.getMessage());
    e.getMessage();
}

//        subscribeToRedisChannel();
    }

    private void subscribeToRedisChannel() {
        new Thread(() -> {

            try (Jedis jedis = new Jedis(REDIS_HOST, REDIS_PORT)) {
                jedis.auth("3BDsrSjfNJ9MaBy4xIojO8G0NchEfKOK");
                System.out.println("hello");
                jedis.subscribe(new JedisPubSub() {
                    @Override
                    public void onMessage(String channel, String message) {
                        Platform.runLater(() -> messageArea.appendText("Received message from " + channel + ": " + message + "\n"));
                    }

                    @Override
                    public void onSubscribe(String channel, int subscribedChannels) {
                        Platform.runLater(() -> messageArea.appendText("Subscribed to channel: " + channel + "\n"));
                    }

                    @Override
                    public void onUnsubscribe(String channel, int subscribedChannels) {
                        Platform.runLater(() -> messageArea.appendText("Unsubscribed from channel: " + channel + "\n"));
                    }
                }, CHANNEL_NAME, "SLE_NOTIFICATION");
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
