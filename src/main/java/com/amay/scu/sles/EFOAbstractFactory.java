package com.amay.scu.sles;

import com.amay.scu.ViewFactory;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.exceptions.SLENotCreatedException;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EFOAbstractFactory extends SLEAbstractFactory {
    static int EFO_COUNT = 1;
    private static final String NAME="EFO";
    Logger logger = LoggerFactory.getLogger(EFOAbstractFactory.class);


    @Override
    public SLE createSLE(AnchorPane anchorPane, StationDevicesDTO stationDevicesDTO) {

        try {
            logger.debug("EFO is about to be created");
            FXMLLoader fxmlLoader = ViewFactory.getEFOView();

            Parent root=fxmlLoader.load();
            Button button = (Button) root.lookup("#efo");
            button.setId(getTomId());
            anchorPane.getChildren().add(button);
            logger.debug("EFO created : {}",button.getId());
            SLE controller=fxmlLoader.getController();
            controller.setStatus(SLEStatus.ONLINE);
            controller.setName(button.getId());
            controller.setMovingProperties(button,anchorPane);
            return controller;
        } catch (Exception e) {
            throw new SLENotCreatedException("EFO not created", e);
        }
    }

    private String getTomId() {
        return NAME+EFO_COUNT++;
    }

}
