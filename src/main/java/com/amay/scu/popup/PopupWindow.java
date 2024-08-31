package com.amay.scu.popup;

import com.amay.scu.command.CommandTest;
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

public class PopupWindow {
    private Stage popupStage;
    private SleCommandInfo sleCommandInfo;
    private DeviceType deviceType;


    public PopupWindow() {

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

    public void show(FXMLLoader fxmlLoader) {
        try {
            Parent p = fxmlLoader.load();
            Scene scene = new Scene(p, Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
            scene.setFill(Color.TRANSPARENT); // Set the scene fill to transparent
            popupStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }

        popupStage.showAndWait();
    }

    public void sendCommand(String id, CommandType command) {
        CommandTest.INSTANCE.sendCommand(command, deviceType, id,null);
    }
}
