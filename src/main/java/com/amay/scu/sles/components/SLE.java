package com.amay.scu.sles.components;

import com.amay.scu.enums.SLEStatus;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public interface SLE {
    static int TOM_COUNT = 1;
    static int AG_COUNT = 2;


    public boolean setScale(float x, float y, float z);
    public boolean setScale(float x, float y);
    public boolean setStatus(SLEStatus status);
    public void setName(String id);

    public void setMovingProperties(Button button, AnchorPane anchorPane);
}
