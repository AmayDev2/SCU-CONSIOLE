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

    public RidershipReportPerHour() {
        ridershipReportHours= new java.util.ArrayList<>();
        for( int i = 0; i < 24; i++) {
            ridershipReportHours.add(new RidershipReportHour(0, 0));
        }
    }

}
