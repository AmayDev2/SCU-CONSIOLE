package com.amay.scu.popup;

import com.amay.scu.ViewFactory;
import com.amay.scu.command.CommandTest;
import com.amay.scu.controller.AGCommandController;
import com.amay.scu.controller.TomCommandController;
import com.amay.scu.controller.components.*;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.sleobj.LiveTR;
import com.amay.scu.sleobj.LiveTVM;
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
import org.network.monitorandcontrol.ag.AGModeControl;
import org.network.monitorandcontrol.tom.TOMModeControl;
import org.network.monitorandcontrol.tr.TRModeControl;
import org.network.monitorandcontrol.tvm.TVMModeControl;

public class PopupContent {
    private final Stage popupStage;
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
        } else if(sleCommandInfo instanceof LiveTR){
            deviceType=DeviceType.TR;
        } else if(sleCommandInfo instanceof LiveTVM){
            deviceType=DeviceType.TVM;
        }
        FXMLLoader loader=null;

        if(deviceType==DeviceType.TOM || deviceType==DeviceType.EFO){
            loader = ViewFactory.getTomWidgets();
            loader.setControllerFactory(c -> new TomWidgetsViewV2(this, sleCommandInfo));
        } else if(deviceType==DeviceType.AG){
                loader = ViewFactory.getAGWidgets();
                loader.setControllerFactory(c -> new AGWidgetsViewV2(this, sleCommandInfo));
        } else if(deviceType==DeviceType.TR){
            loader = ViewFactory.getTRWidgets();
            loader.setControllerFactory(c -> new TRWidgetsView(this, sleCommandInfo));
        } else if(deviceType==DeviceType.TVM){
            loader = ViewFactory.getTVMWidgets();
            loader.setControllerFactory(c -> new TVMWidgetsView(this, sleCommandInfo));
        } else {
            throw new IllegalArgumentException("Unsupported Device Type: " + deviceType);

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

    public void sendCommand(String id, CommandType command, TOMModeControl tomModeControl) {
        System.out.println("Command sent to SLE"+id+command.getValueDescriptor()+tomModeControl.getSpecialMode().getValueDescriptor());
        CommandTest.INSTANCE.sendCommand(command, deviceType, id,tomModeControl);
    }
    public void sendCommand(String id, CommandType command, TVMModeControl tomModeControl) {
        System.out.println("Command sent to SLE"+id+command.getValueDescriptor()+tomModeControl.getSpecialMode().getValueDescriptor());
        CommandTest.INSTANCE.sendCommand(command, deviceType, id,tomModeControl);
    }
    public void sendCommand(String id, CommandType command, TRModeControl tomModeControl) {
        System.out.println("Command sent to SLE"+id+command.getValueDescriptor()+tomModeControl.getSpecialMode().getValueDescriptor());
        CommandTest.INSTANCE.sendCommand(command, deviceType, id,tomModeControl);
    }
    public void sendCommand(String id, CommandType command, AGModeControl tomModeControl) {
        System.out.println("Command sent to SLE"+id+command.getValueDescriptor()+tomModeControl.getSpecialMode().getValueDescriptor());
        CommandTest.INSTANCE.sendCommand(command, deviceType, id,tomModeControl);
    }
}
