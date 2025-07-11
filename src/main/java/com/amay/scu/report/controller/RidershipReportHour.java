package com.amay.scu.report.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RidershipReportHour {
    private String timeSlot; // e.g., "00:00 - 01:00"
    private int entryCount;
    private int exitCount;
}
