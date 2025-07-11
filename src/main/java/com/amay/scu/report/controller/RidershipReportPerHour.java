package com.amay.scu.report.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class RidershipReportPerHour {
    private String date;
    private List<RidershipReportHour> ridershipReportHours;
    private int totalEntry;
    private int totalExit;

    public RidershipReportPerHour(String date) {

        this.date = date;
        ridershipReportHours= new java.util.ArrayList<>();
        for( int i = 0; i < 24; i++) {
            String x=(i<9?("0"+i):String.valueOf(i));
            ridershipReportHours.add(new RidershipReportHour(x+":00 - "+x+":59",0, 0));
        }
    }

}
