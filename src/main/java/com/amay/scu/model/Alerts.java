package com.amay.scu.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;


public class Alerts {
    private static List<Alert> alerts = new ArrayList<>();

    public static void addAlert(Alert alert) {
        alerts.add(alert);
    }

    public static void clearAlerts() {
        alerts.clear();
    }


    public static List<String> getAlertsByDeviceId(String deviceId) {
        List<String> alertsByDeviceId = new ArrayList<>();
        for (Alert alert : alerts) {
            if (alert.getDeviceId().equals(deviceId)) {
                alertsByDeviceId.add("("+alert.getAlertCode()+")"+" "+alert.getAlertMessage()+"\t["+alert.getTimestamp()+"]");
            }
        }
        return alertsByDeviceId;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Accessors(chain = true)
    public static class Alert {
        String deviceId;
        String alertCode;
        String alertMessage;
//        String deviceType;
        String timestamp;
    }

}