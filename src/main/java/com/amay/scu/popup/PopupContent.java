package com.amay.scu.popup;

import com.amay.scu.ViewFactory;
import com.amay.scu.command.CommandTest;
import com.amay.scu.controller.CommandController;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveSLE;
import com.amay.scu.sleobj.LiveTOM;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import org.network.monitorandcontrol.CommandType;
import org.network.monitorandcontrol.DeviceType;
import views.Path;

public class PopupContent {
    private Stage popupStage;
    private SleCommandInfo sleCommandInfo;
    private DeviceType deviceType;


    public PopupContent(SleCommandInfo sleCommandInfo) {

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

        FXMLLoader loader = ViewFactory.getPopupView();
        try {
            Parent p = loader.load();
            CommandController controller = loader.getController();
            Scene scene = new Scene(p, 500, 500);
            scene.setFill(Color.TRANSPARENT); // Set the scene fill to transparent
            popupStage.setScene(scene);
            controller.setInitialData(this, this.sleCommandInfo);
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
