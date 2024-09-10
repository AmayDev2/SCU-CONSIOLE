package com.amay.scu.sleobj;

import com.amay.scu.enums.OperationMode;
import com.amay.scu.enums.SLEStatus;
import com.amay.scu.enums.TOMOperationMode;
import com.amay.scu.popup.SleCommandInfo;
import com.amay.scu.sleobj.propertyenums.PropertyUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.LinkedList;
import java.util.Queue;


@Data
@NoArgsConstructor
public class LiveTOM implements LiveSLE, SleCommandInfo {
    private final int commandQueueSize = 10;
    private SLEStatus currentStatus = SLEStatus.OFFLINE;
   final Logger logger = LoggerFactory.getLogger(getClass());

   private final Queue<String> commandQueue = new LinkedList<>();


    // PropertyChangeSupport instance
    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private final PropertyChangeSupport peripheralStatusListener = new PropertyChangeSupport(this);
    private final PropertyChangeSupport parameterVersionListener = new PropertyChangeSupport(this);

    // information
    private String equipName;
    private String equipId;
    private String equipIp;
    private String deviceType;
    private String stationId="10";

    public LiveTOM(String equipId,String tomIp,String stationId,String equipName, String deviceType){
        this.equipId=equipId;
        this.equipIp=tomIp;
//        this.stationId=stationId;
        this.equipName=equipName;
        this.deviceType=deviceType;
    }

    // peripherals
    private boolean scu_connected;
    private boolean ccu_connected;
    private boolean reader_connected;
    private boolean scanner_connected;
    private boolean printer_connected;
    private boolean pdu_connected;
    private boolean cash_drawer_connected;
    private boolean ups_connected;

    //all
    private boolean isAllPeripheralConnected;

    // parameter version
    private String fare_table;
    private String users;
    private String tom_software;
    private String blacklist;
    private String calendar;
    private String qr_key;
    private String ticket;


    // current operation mode
    private TOMOperationMode operationMode;

    public void addCommand(String command) {
        if (commandQueue.size() >= 10) {
            commandQueue.poll();  // Remove the first (oldest) element
        }
        commandQueue.add(command);  // Add the new element
    }

    public String getAllCommands() {
        StringBuilder sb = new StringBuilder();
        for (String command : commandQueue) {
            sb.append(command).append("\n");
        }
        return sb.toString();
    }

    public void setCurrentStatus(SLEStatus currentStatus){
        SLEStatus oldValue=this.currentStatus;
        pcs.firePropertyChange(PropertyUpdate.SLE_STATUS_UPDATED.name(), oldValue, currentStatus);

    }

    // Getter and setter methods with property change support
    public void setEquipName(String equipName) {
        String oldEquipName = this.equipName;
        this.equipName = equipName;
        pcs.firePropertyChange("equipName", oldEquipName, equipName);
    }

//    public void setEquipId(String equipId) {
//        String oldEquipId = this.equipId;
//        this.equipId = equipId;
//        pcs.firePropertyChange("equipId", oldEquipId, equipId);
//    }
//
//    public void setTomIp(String tomIp) {
//        String oldTomIp = this.tomIp;
//        this.tomIp = tomIp;
//        pcs.firePropertyChange("tomIp", oldTomIp, tomIp);
//    }

    public void setDeviceType(String deviceType) {
        String oldDeviceType = this.deviceType;
        this.deviceType = deviceType;
        pcs.firePropertyChange("deviceType", oldDeviceType, deviceType);
    }

    public void setScu_connected(boolean scu_connected) {
        boolean oldScu_connected = this.scu_connected;
        this.scu_connected = scu_connected;
        pcs.firePropertyChange("scu_connected", oldScu_connected, scu_connected);
    }

    public void setCcu_connected(boolean ccu_connected) {
        boolean oldCcu_connected = this.ccu_connected;
        this.ccu_connected = ccu_connected;
        pcs.firePropertyChange("ccu_connected", oldCcu_connected, ccu_connected);
    }

    public void setReader_connected(boolean reader_connected) {
        boolean oldReader_connected = this.reader_connected;
        this.reader_connected = reader_connected;
        pcs.firePropertyChange("reader_connected", oldReader_connected, reader_connected);
    }

    public void setScanner_connected(boolean scanner_connected) {
        boolean oldScanner_connected = this.scanner_connected;
        this.scanner_connected = scanner_connected;
        pcs.firePropertyChange("scanner_connected", oldScanner_connected, scanner_connected);
    }

    public void setPrinter_connected(boolean printer_connected) {
        boolean oldPrinter_connected = this.printer_connected;
        this.printer_connected = printer_connected;
        pcs.firePropertyChange("printer_connected", oldPrinter_connected, printer_connected);
    }

    public void setPdu_connected(boolean pdu_connected) {
        boolean oldPdu_connected = this.pdu_connected;
        this.pdu_connected = pdu_connected;
        pcs.firePropertyChange("pdu_connected", oldPdu_connected, pdu_connected);
    }

    public void setCash_drawer_connected(boolean cash_drawer_connected) {
        boolean oldCash_drawer_connected = this.cash_drawer_connected;
        this.cash_drawer_connected = cash_drawer_connected;
        pcs.firePropertyChange("cash_drawer_connected", oldCash_drawer_connected, cash_drawer_connected);
    }

    public void setUps_connected(boolean ups_connected) {
        boolean oldUps_connected = this.ups_connected;
        this.ups_connected = ups_connected;
        pcs.firePropertyChange("ups_connected", oldUps_connected, ups_connected);
    }

    public void setFare_table(String fare_table) {
        String oldFare_table = this.fare_table;
        this.fare_table = fare_table;
        parameterVersionListener.firePropertyChange("fare_table", oldFare_table, fare_table);
    }

    public void setUsers(String users) {
        String oldUsers = this.users;
        this.users = users;
        parameterVersionListener.firePropertyChange("users", oldUsers, users);
    }

    public void setTom_software(String tom_software) {
        String oldTom_software = this.tom_software;
        this.tom_software = tom_software;
        parameterVersionListener.firePropertyChange("tom_software", oldTom_software, tom_software);
    }

    public void setBlacklist(String blacklist) {
        String oldBlacklist = this.blacklist;
        this.blacklist = blacklist;
        parameterVersionListener.firePropertyChange("blacklist", oldBlacklist, blacklist);
    }

    public void setCalendar(String calendar) {
        String oldCalendar = this.calendar;
        this.calendar = calendar;
       parameterVersionListener.firePropertyChange("calendar", oldCalendar, calendar);
    }

    public void setQr_key(String qr_key) {
        String oldQr_key = this.qr_key;
        this.qr_key = qr_key;
        parameterVersionListener.firePropertyChange("qr_key", oldQr_key, qr_key);
    }

    public void setTicket(String ticket) {
        String oldTicket = this.ticket;
        this.ticket = ticket;
        parameterVersionListener.firePropertyChange("ticket", oldTicket, ticket);
    }

    public void setOperationMode(TOMOperationMode operationMode) {
        logger.info("Changing Operation Mode {} {}", operationMode,this.hashCode());
        TOMOperationMode oldOperationMode = this.operationMode;
        this.operationMode = operationMode;
        logger.info("Operation Mode changed {} to {}", oldOperationMode, operationMode);
        pcs.firePropertyChange(PropertyUpdate.OPERATION_MODE.name(), oldOperationMode, operationMode);
    }

    // Methods to add and remove property change listeners
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        pcs.removePropertyChangeListener(listener);
    }

    public void addParameterVersionChangeListener(PropertyChangeListener listener) {
        parameterVersionListener.addPropertyChangeListener(listener);
    }

    public void removeParameterVersionChangeListener(PropertyChangeListener listener) {
        parameterVersionListener.removePropertyChangeListener(listener);
    }


    //  if peripherals are not connected properly
    @Override
    public void setDeviceMode(boolean isAllPeripheralConnected) {

        logger.info("Is all Peripherals connected {}",isAllPeripheralConnected);
        if(!isAllPeripheralConnected ){
            this.setOperationMode(TOMOperationMode.DEFICIENT);
        }else{
            this.setOperationMode(TOMOperationMode.IN_SERVICE);
        }

    }
}
