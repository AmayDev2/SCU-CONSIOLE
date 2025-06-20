package com.amay.scu.report.controller;

import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class RidershipReport {

    private String time;
    private String stationId;
    private String equipmentType;
    private String equipmentId;
    private String status;


    public String getTime(){
        if (time == null) return "";
        return new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date(Long.parseLong(time))).toUpperCase();
    }

    public String getEquipmentType(){
        return switch (equipmentType){
            case "01" -> "TOM";
            case "02" -> "EFO";
            case "03" -> "TVM";
            case "04" -> "TR";
            case "05" -> "ENTRY GATE";
            case "06" -> "EXIT GATE";
            case "07" -> "WIDE GATE";
            case "08" -> "REV GATE";
            default -> "UNKNOWN";
        };
    }
}
