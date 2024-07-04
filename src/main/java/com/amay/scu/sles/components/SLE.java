package com.amay.scu.sles.components;

import com.amay.scu.enums.SLEStatus;
import com.amay.scu.enums.TOMOperationMode;
import com.amay.scu.sleobj.LiveSLE;
import com.amay.scu.sleobj.LiveTOM;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

public interface SLE {
    public boolean setScale(float x, float y, float z);
    public boolean setScale(float x, float y);
    public boolean setStatus(SLEStatus status);
    public void setName(String id);
    public void setMovingProperties(Button button, AnchorPane anchorPane);
    public Object getId();
    public void updateStatus(SLEStatus status);
    void updatePeripheralStatus(LiveSLE liveSLE);
    void updateOperationMode(LiveSLE liveSLE);
    void setLiveSLE(LiveSLE liveSLE);
}
