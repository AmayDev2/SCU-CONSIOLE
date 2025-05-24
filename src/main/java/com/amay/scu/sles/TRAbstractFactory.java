package com.amay.scu.sles;

import com.amay.scu.ViewFactory;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.exceptions.SLENotCreatedException;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.sleobj.LiveTR;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TRAbstractFactory extends SLEAbstractFactory {
    static int TOM_COUNT = 1;
    private static final String NAME="TR";
    Logger logger = LoggerFactory.getLogger(TRAbstractFactory.class);



    @Override
    public SLE createSLE(AnchorPane anchorPane, StationDevicesDTO stationDevicesDTO) {

        try {
            logger.debug("TR is about to be created");
            FXMLLoader fxmlLoader = ViewFactory.getTRView();
            String name=getTRId();
            LiveTR liveTR=new LiveTR(stationDevicesDTO.getEquipId(),stationDevicesDTO.getEquipIp(),"01",name, stationDevicesDTO.getEquipType());
            logger.debug("Live Tom  : {} ",liveTR.hashCode());

            Parent root=fxmlLoader.load();
            Button button = (Button) root.lookup("#tr");
            button.setId(stationDevicesDTO.getEquipId());
            anchorPane.getChildren().add(button);
            logger.debug("TR created : {}",stationDevicesDTO.getEquipId());
            SLE controller=fxmlLoader.getController();
//            controller.setStatus(SLEStatus.PERIPHERAL_OFFLINE);



            logger.debug("name : {}",name);
            controller.setName(name);
            SLELocationListObject.list.putIfAbsent(name, new SLELocationListObject.SLELocation());
            logger.debug("TR location set : {} {}",name,SLELocationListObject.list.get(name));
            controller.setLocation(SLELocationListObject.list.get(name));

            controller.setLiveSLE(liveTR);
            controller.setMovingProperties(button,anchorPane);
            return controller;
        } catch (Exception e) {
            throw new SLENotCreatedException("TR not created", e);
        }
    }

    private String getTRId() {
        return NAME+TOM_COUNT++;
    }

}
