package com.amay.scu.sles;

import com.amay.scu.ViewFactory;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.exceptions.SLENotCreatedException;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AGAbstractFactory extends SLEAbstractFactory {
    static int TOM_COUNT = 1;
    private static final String NAME="AG";
    Logger logger = LoggerFactory.getLogger(AGAbstractFactory.class);


    @Override
    public SLE createSLE(AnchorPane anchorPane) {

        try{
            logger.debug("AG is about to be created");
            FXMLLoader fxmlLoader = ViewFactory.getAGView();

            Parent root=fxmlLoader.load();
            Button button = (Button) root.lookup("#ag");
            button.setId(getTomId());
            anchorPane.getChildren().add(button);
            logger.debug("AG created : {}",button.getId());
            SLE controller=fxmlLoader.getController();
            controller.setStatus(SLEStatus.PERIPHERAL_OFFLINE);
            controller.setName(button.getId());
            controller.setMovingProperties(button,anchorPane);
            return controller;
        } catch (Exception e) {
            throw new SLENotCreatedException("AG not created", e);
        }
    }

    private String getTomId() {
        return NAME+TOM_COUNT++;
    }

}
