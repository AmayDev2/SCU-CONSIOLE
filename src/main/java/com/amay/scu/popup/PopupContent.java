package com.amay.scu.popup;

import com.amay.scu.ViewFactory;
import com.amay.scu.controller.CommandController;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveSLE;
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
import views.Path;

public class PopupContent {
    private Stage popupStage;
    private LiveSLE liveSLE;
//
//    public void xyz(LiveAG liveAG,PopupContent popupContent){
//        liveSLE=liveAG;
//        FXMLLoader loader = ViewFactory.getPopupView();
//        try {
//            Parent p=loader.load();
//            CommandController controller=loader.getController();
//            popupStage.setScene(new Scene(p,500,500));
//            controller.setInitialData(popupContent,liveSLE);
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//    }
    public PopupContent(LiveSLE liveAG) {

        popupStage = new Stage();
        Window window = findActiveWindow();
        if (window instanceof Stage) {
            popupStage.initOwner((Stage) window);
        } else {
            throw new IllegalStateException("Could not find active Stage.");
        }


        popupStage.initModality(Modality.WINDOW_MODAL);
        popupStage.initStyle(StageStyle.UNDECORATED); // Remove window decorations

        popupStage.initStyle(StageStyle.TRANSPARENT); // Set the stage style to transparent

        liveSLE = liveAG;
        FXMLLoader loader = ViewFactory.getPopupView();
        try {
            Parent p = loader.load();
            CommandController controller = loader.getController();
            Scene scene = new Scene(p, 500, 500);
//            scene.getStylesheets().add(ViewFactory.loadStylesheet());
            scene.setFill(Color.TRANSPARENT); // Set the scene fill to transparent

            // Apply rounded corners to the scene
//            Region root = (Region) scene.getRoot();
//            root.setClip(new Rectangle(500, 500, 20, 20));

            popupStage.setScene(scene);
            controller.setInitialData(this, liveSLE);
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
}
