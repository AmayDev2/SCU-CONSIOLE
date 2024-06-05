package com.amay.scu.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;


@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AGDevicesDTO implements Serializable {

    private String equipId;
    private String gateType;
    private int gcuStatus;
    private boolean reader1Connected;
    private boolean reader2Connected;
    private boolean scanner1Connected;
    private boolean scanner2Connected;
    private boolean upsConnected;
    private int aisleMode;
    private int flapMode;
    private int specialMode;

}