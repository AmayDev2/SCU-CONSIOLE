package com.amay.scu.sles;

import com.amay.scu.sles.components.SLE;
import javafx.scene.layout.AnchorPane;

abstract public  class SLEAbstractFactory {

    abstract public SLE  createSLE(AnchorPane anchorPane);

}
