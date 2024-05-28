package com.amay.scu.exceptions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLENotCreatedException extends RuntimeException{
    Logger logger = LoggerFactory.getLogger(SLENotCreatedException.class);
    int code = 500;
    public SLENotCreatedException(String message, Throwable cause) {
        super(message);

        logger.error(message, cause);
    }
}
