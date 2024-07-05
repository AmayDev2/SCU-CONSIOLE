package com.amay.scu.sles;

import com.amay.scu.ViewFactory;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.exceptions.SLENotCreatedException;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TOMAbstractFactory extends SLEAbstractFactory {
    static int TOM_COUNT = 1;
    private static final String NAME="TOM";
    Logger logger = LoggerFactory.getLogger(TOMAbstractFactory.class);



    @Override
    public SLE createSLE(AnchorPane anchorPane, StationDevicesDTO stationDevicesDTO) {

        try {
            logger.debug("TOM is about to be created");
            FXMLLoader fxmlLoader = ViewFactory.getTOMView();
            LiveTOM liveTOM=new LiveTOM(stationDevicesDTO.getEquipId(),stationDevicesDTO.getEquipIp(),"01",stationDevicesDTO.getEquipName(), stationDevicesDTO.getEquipType());

            Parent root=fxmlLoader.load();
            Button button = (Button) root.lookup("#tom");
            button.setId(stationDevicesDTO.getEquipId());
            anchorPane.getChildren().add(button);
            logger.debug("TOM created : {}",stationDevicesDTO.getEquipId());
            SLE controller=fxmlLoader.getController();
            controller.setStatus(SLEStatus.PERIPHERAL_OFFLINE);


            String name=getTomId();
            logger.debug("name : {}",name);
            controller.setName(name);
            SLELocationListObject.list.putIfAbsent(name, new SLELocationListObject.SLELocation());
            logger.debug("TOM location set : {} {}",name,SLELocationListObject.list.get(name));
            controller.setLocation(SLELocationListObject.list.get(name));

            controller.setLiveSLE(liveTOM);
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
