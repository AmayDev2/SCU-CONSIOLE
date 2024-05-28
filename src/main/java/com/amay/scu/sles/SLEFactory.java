package com.amay.scu.sles;

import com.amay.scu.sles.components.SLE;
import javafx.scene.layout.AnchorPane;

public class SLEFactory {

        public static SLE getSLEFactory(SLEAbstractFactory sleType, AnchorPane anchorPane) throws Exception {
            return sleType.createSLE(anchorPane);
        }

    public static SLE[] getSLEFactory(SLEAbstractFactory sleType, AnchorPane anchorPane,int count) throws Exception {
        SLE[] sles = new SLE[count];
            for(int i=0;i<count;i++){
                sles[i]=sleType.createSLE(anchorPane);
            }
        return sles;

    }
}
