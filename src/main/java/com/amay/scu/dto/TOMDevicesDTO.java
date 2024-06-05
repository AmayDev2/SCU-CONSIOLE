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
public class TOMDevicesDTO implements Serializable {

    private String equipId;
    private String deviceType;
    private boolean readerConnected;
    private boolean printerConnected;
    private boolean scannerConnected;
    private boolean pduConnected;
    private boolean upsConnected;
    private boolean cashDrawerConnected;
    private int cardProcessMode;
    private int saleMode;

}
