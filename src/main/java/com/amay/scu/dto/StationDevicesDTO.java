package com.amay.scu.dto;


import lombok.ToString;

import java.io.Serializable;


@ToString
public class StationDevicesDTO implements Serializable {

    private String equipName;
    private String equipType;
    private String equipId;
    private String equipIp;
    private int scuConnected;
    private int ccuConnected;
    private String fareTableVer;
    private String usersVer;
    private String softwareVer;
    private String blacklistVer;
    private String calendarVer;
    private String qrKeyVer;
    private String ticketVer;
    private int operationMode;
    private String lastTxn;


    // Getters and setters for all fields

    public String getEquipName() {
        return equipName;
    }

    public void setEquipName(String equipName) {
        this.equipName = equipName;
    }

    public String getEquipType() {
        return equipType;
    }

    public void setEquipType(String equipType) {
        this.equipType = equipType;
    }

    public String getEquipId() {
        return equipId;
    }

    public void setEquipId(String equipId) {
        this.equipId = equipId;
    }

    public String getEquipIp() {
        return equipIp;
    }

    public void setEquipIp(String equipIp) {
        this.equipIp = equipIp;
    }

    public int getScuConnected() {
        return scuConnected;
    }

    public void setScuConnected(int scuConnected) {
        this.scuConnected = scuConnected;
    }

    public int getCcuConnected() {
        return ccuConnected;
    }

    public void setCcuConnected(int ccuConnected) {
        this.ccuConnected = ccuConnected;
    }

    public String getFareTableVer() {
        return fareTableVer;
    }

    public void setFareTableVer(String fareTableVer) {
        this.fareTableVer = fareTableVer;
    }

    public String getUsersVer() {
        return usersVer;
    }

    public void setUsersVer(String usersVer) {
        this.usersVer = usersVer;
    }

    public String getSoftwareVer() {
        return softwareVer;
    }

    public void setSoftwareVer(String softwareVer) {
        this.softwareVer = softwareVer;
    }

    public String getBlacklistVer() {
        return blacklistVer;
    }

    public void setBlacklistVer(String blacklistVer) {
        this.blacklistVer = blacklistVer;
    }

    public String getCalendarVer() {
        return calendarVer;
    }

    public void setCalendarVer(String calendarVer) {
        this.calendarVer = calendarVer;
    }

    public String getQrKeyVer() {
        return qrKeyVer;
    }

    public void setQrKeyVer(String qrKeyVer) {
        this.qrKeyVer = qrKeyVer;
    }

    public String getTicketVer() {
        return ticketVer;
    }

    public void setTicketVer(String ticketVer) {
        this.ticketVer = ticketVer;
    }

    public int getOperationMode() {
        return operationMode;
    }

    public void setOperationMode(int operationMode) {
        this.operationMode = operationMode;
    }

    public String getLastTxn() {
        return lastTxn;
    }

    public void setLastTxn(String lastTxn) {
        this.lastTxn = lastTxn;
    }

}
