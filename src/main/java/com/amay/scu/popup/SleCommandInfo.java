package com.amay.scu.popup;

import com.amay.scu.enums.OperationMode;

public interface SleCommandInfo {
    public OperationMode getOperationMode();
    public String getEquipName();
    public String getEquipId();
    public String getEquipIp();
    public String getAllCommands();
    public void addCommand(String command);


}
