package com.amay.scu.report.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Accessors(chain = true)
public class ShiftReport {
    private String date;
    private String shiftId;
    private String loginTime;
    private String logoutTime;
    private String operatorId;
    private String equipmentId;
    private String equipmentType;
    private int QR;
    private int UPI,CASH,POS;
    private int NCMC;
    private int revenue;
}
