package com.amay.scu.sles;

import com.amay.scu.dto.StationDevicesDTO;
import com.amay.scu.sles.components.SLE;
import javafx.scene.layout.AnchorPane;

import java.util.List;

public class SLEFactory {

        public static SLE getSLEFactory(SLEAbstractFactory sleType, AnchorPane anchorPane,StationDevicesDTO stationDevicesDTO) throws Exception {
            return sleType.createSLE(anchorPane, stationDevicesDTO);
        }

    public static SLE[] getSLEFactory(SLEAbstractFactory sleType, AnchorPane anchorPane, int count, List<StationDevicesDTO> stationDevicesDTO) throws Exception {
        SLE[] sles = new SLE[count];
            for(int i=0;i<count;i++){
                sles[i]=sleType.createSLE(anchorPane,stationDevicesDTO.get(i));
            }
        return sles;

    }
}
