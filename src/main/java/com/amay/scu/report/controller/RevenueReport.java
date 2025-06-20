package com.amay.scu.report.controller;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RevenueReport {
    private String ticketId;
    private String station;
    private String equipmentType;
    private String equipmentId;
    private String tripType;
    private String fareMedia;
    private String paymentMode;
    private double amount;
    private long ticketTime;
    private String transactionType;

    public String getTicketTime(){
        if (ticketTime == 0) return "";
        return new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date(ticketTime)).toUpperCase();
    }
}
