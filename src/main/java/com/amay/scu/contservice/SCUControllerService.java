package com.amay.scu.contservice;

import com.amay.scu.controller.SCUController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class SCUControllerService {
    private Logger logger = LoggerFactory.getLogger(SCUControllerService.class);

    InnerListener scuControllerListener;

    public SCUControllerService( InnerListener scuControllerListener) {
        this.scuControllerListener = scuControllerListener;
    }

    public void login() {
        // login logic
    }

    public void logout() {
        // logout logic
    }

    public void onMenuClick() {
        // onMenuClick logic
        logger.debug("Menu Clicked Service");
    }

    public void onMonitorClick() {
        // onMonitorClick logic
        logger.debug("Monitor Clicked Service");
    }

    public void onReportClick() {
        // onReportClick logic
        logger.debug("Report Clicked Service");
    }
}
