package com.amay.scu.sles;

import com.amay.scu.ViewFactory;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.exceptions.SLENotCreatedException;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.sleobj.LiveAG;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* @author: risab.7088@gmail.com
* stationDevicesDTO:- is the object that we are getting from database
* liveAG :- a real object to contain realtime data/status of sle
*
*
*
*
*
*
* */

public class AGAbstractFactory extends SLEAbstractFactory {
    static int TOM_COUNT = 1;
    private static final String NAME="AG";
    Logger logger = LoggerFactory.getLogger(AGAbstractFactory.class);


    @Override
    public SLE createSLE(AnchorPane anchorPane, StationDevicesDTO stationDevicesDTO) {

        try{
            logger.debug("AG is about to be created");
            FXMLLoader fxmlLoader = ViewFactory.getAGView();
            String name=getAGId();
            LiveAG liveAG=new LiveAG(stationDevicesDTO.getEquipId(),stationDevicesDTO.getEquipIp(),"01",name, stationDevicesDTO.getEquipType());


            Parent root=fxmlLoader.load();
            Button button = (Button) root.lookup("#ag");
            button.setId(stationDevicesDTO.getEquipId());
            anchorPane.getChildren().add(button);
            logger.debug("AG created : {}",stationDevicesDTO.getEquipId());
            SLE controller=fxmlLoader.getController();
//            controller.setStatus(SLEStatus.);


            logger.debug("name : {}",name);
            controller.setName(name);
            SLELocationListObject.list.putIfAbsent(name, new SLELocationListObject.SLELocation());
            logger.debug("AG location set : {} {}",name,SLELocationListObject.list.get(name));
            controller.setLocation(SLELocationListObject.list.get(name));

            controller.setLiveSLE(liveAG);
            controller.setMovingProperties(button,anchorPane);
            return controller;
        } catch (Exception e) {
            throw new SLENotCreatedException("AG not created", e);
        }
    }

    // auto increment  the  name
    private String getAGId() {
        return NAME+TOM_COUNT++;
    }

}
