package com.amay.scu.sles;

import com.amay.scu.ViewFactory;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.exceptions.SLENotCreatedException;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TOMAbstractFactory extends SLEAbstractFactory {
    static int TOM_COUNT = 1;
    private static final String NAME="TOM";
    Logger logger = LoggerFactory.getLogger(TOMAbstractFactory.class);


    @Override
    public SLE createSLE(AnchorPane anchorPane) {

        try {
            logger.debug("TOM is about to be created");
            FXMLLoader fxmlLoader = ViewFactory.getTOMView();

            Parent root=fxmlLoader.load();
            Button button = (Button) root.lookup("#tom");
            button.setId(getTomId());
            anchorPane.getChildren().add(button);
            logger.debug("TOM created : {}",button.getId());
            SLE controller=fxmlLoader.getController();
            controller.setStatus(SLEStatus.ONLINE);
            controller.setName(button.getId());
            controller.setMovingProperties(button,anchorPane);
            return controller;
        } catch (Exception e) {
            throw new SLENotCreatedException("TOM not created", e);
        }
    }

    private String getTomId() {
        return NAME+TOM_COUNT++;
    }

}
