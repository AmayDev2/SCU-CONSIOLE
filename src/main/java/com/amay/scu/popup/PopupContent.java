package com.amay.scu.popup;

import com.amay.scu.ViewFactory;
import com.amay.scu.command.CommandTest;
import com.amay.scu.controller.AGCommandController;
import com.amay.scu.controller.TomCommandController;
import com.amay.scu.controller.components.AGWidgetsView;
import com.amay.scu.controller.components.TomWidgetsView;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.DeviceType;

public class PopupContent {
    private Stage popupStage;
    private SleCommandInfo sleCommandInfo;
    private DeviceType deviceType;


    public PopupContent(SleCommandInfo sleCommandInfo, String left) {

        popupStage = new Stage();
        Window window = findActiveWindow();
        if (window instanceof Stage) {
            popupStage.initOwner((Stage) window);
        } else {
            throw new IllegalStateException("Could not find active Stage.");
        }

        popupStage.initModality(Modality.WINDOW_MODAL);// base stage will become inactive
        popupStage.initStyle(StageStyle.UNDECORATED); // Remove window decorations
        popupStage.initStyle(StageStyle.TRANSPARENT); // Set the stage style to transparent

        this.sleCommandInfo = sleCommandInfo;
        if(sleCommandInfo instanceof LiveAG){
            deviceType=DeviceType.AG;
        }else if(sleCommandInfo instanceof LiveTOM){
            deviceType=DeviceType.TOM;
        }

        FXMLLoader loader;
        if(left.equals("Right")) {

            if(deviceType==DeviceType.TOM){
            loader = ViewFactory.getTomWidgets();
            loader.setControllerFactory(c -> new TomWidgetsView(this, sleCommandInfo));}
            else{
                loader = ViewFactory.getAGWidgets();
                loader.setControllerFactory(c -> new AGWidgetsView(this, sleCommandInfo));
            }
        }else {
            if(deviceType==DeviceType.AG){
                loader = ViewFactory.getAGCommand();
                loader.setControllerFactory(c -> new AGCommandController(this, sleCommandInfo));}
            else{
            loader = ViewFactory.getTomCommand();
            loader.setControllerFactory(c -> new TomCommandController(this, sleCommandInfo));}
        }

        try {
            Parent p = loader.load();
            Scene scene = new Scene(p, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            scene.setFill(Color.TRANSPARENT); // Set the scene fill to transparent
            popupStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void Close(){
        popupStage.close();
    }


    // Method to find the current active window
    private Window findActiveWindow() {
        return Stage.getWindows().stream()
                .filter(Window::isFocused)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No active window found."));
    }

    public void show() {
        popupStage.showAndWait();
    }

    public void sendCommand(String id, CommandType command) {
        CommandTest.INSTANCE.sendCommand(command, deviceType, id);
    }
}
