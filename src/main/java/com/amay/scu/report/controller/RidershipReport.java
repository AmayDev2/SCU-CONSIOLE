package com.amay.scu.report.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RidershipReport {

    private String time;
    private String stationId;
    private String equipmentType;
    private String equipmentId;
    private String status;
    private String ticketId;


    public String getTime(){
        if (time == null || time.isEmpty()) return "";
        return new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date(Long.parseLong(time))).toUpperCase();
    }

//    public String getEquipmentType(){
//        return switch (equipmentType){
//            case "01" -> "TOM";
//            case "02" -> "EFO";
//            case "03" -> "TVM";
//            case "04" -> "TR";
//            case "05" -> "ENTRY GATE";
//            case "06" -> "EXIT GATE";
//            case "07" -> "WIDE GATE";
//            case "08" -> "REV GATE";
//            default -> "UNKNOWN";
//        };
//    }
}
