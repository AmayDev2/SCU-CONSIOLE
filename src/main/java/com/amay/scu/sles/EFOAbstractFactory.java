package com.amay.scu.sles;

import com.amay.scu.ViewFactory;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.exceptions.SLENotCreatedException;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.sleobj.LiveEFO;
import com.amay.scu.sleobj.LiveTOM;
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
            String name=getEFOId();
            LiveTOM liveTOM=new LiveTOM(stationDevicesDTO.getEquipId(),stationDevicesDTO.getEquipIp(),"01",name, stationDevicesDTO.getEquipType());
            logger.debug("Live Tom  : {} ",liveTOM.hashCode());

            Parent root=fxmlLoader.load();
            Button button = (Button) root.lookup("#tom");
            button.setId(stationDevicesDTO.getEquipId());
            anchorPane.getChildren().add(button);
            logger.debug("EFO created : {}",button.getId());
            SLE controller=fxmlLoader.getController();
//            controller.setStatus(SLEStatus.ONLINE);
            logger.debug("name : {}",name);
            controller.setName(name);

            SLELocationListObject.list.putIfAbsent(name, new SLELocationListObject.SLELocation());
            logger.debug("TOM location set : {} {}",name,SLELocationListObject.list.get(name));
            SLELocationListObject.SLELocation location=new SLELocationListObject.SLELocation();
            location.setXAxis(stationDevicesDTO.getxAxis());
            location.setYAxis(stationDevicesDTO.getyAxis());
            controller.setLocation(location);

            controller.setLiveSLE(liveTOM);
            controller.setMovingProperties(button,anchorPane);
            return controller;
        } catch (Exception e) {
            throw new SLENotCreatedException("EFO not created", e);
        }
    }

    private String getEFOId() {
        return NAME+EFO_COUNT++;
    }

}
