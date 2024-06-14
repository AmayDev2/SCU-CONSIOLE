package com.amay.scu.sles;

import com.amay.scu.ViewFactory;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.exceptions.SLENotCreatedException;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
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
    public SLE createSLE(AnchorPane anchorPane, StationDevicesDTO stationDevicesDTO) {

        try{
            logger.debug("AG is about to be created");
            FXMLLoader fxmlLoader = ViewFactory.getAGView();
            LiveAG liveAG=new LiveAG(stationDevicesDTO.getEquipId(),stationDevicesDTO.getEquipIp(),"01",stationDevicesDTO.getEquipName(), stationDevicesDTO.getEquipType());


            Parent root=fxmlLoader.load();
            Button button = (Button) root.lookup("#ag");
            button.setId(stationDevicesDTO.getEquipId());
            anchorPane.getChildren().add(button);
            logger.debug("AG created : {}",stationDevicesDTO.getEquipId());
            SLE controller=fxmlLoader.getController();
            controller.setStatus(SLEStatus.PERIPHERAL_OFFLINE);
            controller.setName(getAGId());
            controller.setLiveSLE(liveAG);
            controller.setMovingProperties(button,anchorPane);
            return controller;
        } catch (Exception e) {
            throw new SLENotCreatedException("AG not created", e);
        }
    }

    private String getAGId() {
        return NAME+TOM_COUNT++;
    }

}
