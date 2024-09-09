package com.amay.scu.listenner.impl;

import com.amay.scu.controller.HeaderController;
import com.amay.scu.controller.MonitorRightView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeaderListener {
    private static MonitoringRightViewListener Instance ;
    static final Logger logger= LoggerFactory.getLogger(MonitoringRightViewListener.class);

    private HeaderController headerController;

    private void HeaderController() {
        throw new IllegalStateException("Utility class");
    }
    private HeaderListener(HeaderController headerController) {
        this.headerController = headerController;
    }



}
