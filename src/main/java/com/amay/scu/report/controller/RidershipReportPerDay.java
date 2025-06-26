package com.amay.scu.report.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RidershipReportPerDay {

    private String date;
    private String station;

    private int entryQR;
    private int exitQR;
    private int entryNCMC;
    private int exitNCMC;
    private int entryMQR;
    private int exitMQR;

    private int totalEntry;
    private int totalExit;

    private int totalRidership;



//    public String getTime(){
//        if (time == null || time.isEmpty()) return "";
//        return new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date(Long.parseLong(time))).toUpperCase();
//    }

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
