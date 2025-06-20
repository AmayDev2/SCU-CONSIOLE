package com.amay.scu.sles;

import com.amay.scu.ViewFactory;
import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.exceptions.SLENotCreatedException;
import com.amay.scu.model.SLELocationListObject;
import com.amay.scu.sleobj.LiveTOM;
import com.amay.scu.sleobj.LiveTVM;
import com.amay.scu.sles.components.SLE;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TVMAbstractFactory extends SLEAbstractFactory {
    static int TVM_COUNT = 1;
    private static final String NAME="TVM";
    Logger logger = LoggerFactory.getLogger(TVMAbstractFactory.class);


    @Override
    public SLE createSLE(AnchorPane anchorPane, StationDevicesDTO stationDevicesDTO) {

        try {
            logger.debug("TVM is about to be created");
            FXMLLoader fxmlLoader = ViewFactory.getTVMView();
            String name= getTVMId();
            LiveTVM liveTVM=new LiveTVM(stationDevicesDTO.getEquipId(),stationDevicesDTO.getEquipIp(),"01",name, stationDevicesDTO.getEquipType());
            logger.debug("Live TVM  : {} ",liveTVM.hashCode());

            Parent root=fxmlLoader.load();
            Button button = (Button) root.lookup("#tvm");
            button.setId(stationDevicesDTO.getEquipId());

            anchorPane.getChildren().add(button);
            logger.debug("TVM created : {}",button.getId());
            SLE controller=fxmlLoader.getController();
//            controller.setStatus(SLEStatus.ONLINE);
            controller.setName(name);

            SLELocationListObject.list.putIfAbsent(name, new SLELocationListObject.SLELocation());
            logger.debug("TVM location set : {} {}",name,SLELocationListObject.list.get(name));
            SLELocationListObject.SLELocation location=new SLELocationListObject.SLELocation();
            location.setXAxis(stationDevicesDTO.getxAxis());
            location.setYAxis(stationDevicesDTO.getyAxis());
            controller.setLocation(location);

            controller.setLiveSLE(liveTVM);
            controller.setMovingProperties(button,anchorPane);

            controller.setMovingProperties(button,anchorPane);
            return controller;
        } catch (Exception e) {
            throw new SLENotCreatedException("TVM not created", e);
        }
    }

    private String getTVMId() {
        return NAME+TVM_COUNT++;
    }

}
