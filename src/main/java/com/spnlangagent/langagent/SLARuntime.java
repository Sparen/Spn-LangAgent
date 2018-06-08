package com.spnlangagent.langagent;

import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Class that handles interfacing with Google's Allocation Recorder and handles logging.
 */
public class SLARuntime {

    /**
     * Logger for the SLA Runtime.
     */
    public static Logger logger = LogManager.getLogger(SLARuntime.class);

    /**
     * Constructor for SLARuntime, initializing key components
     */
    public SLARuntime() {
        //First, setup Allocation Recorder
        setupAllocationRecorder();

        //Next, setup storing the RequestLogs

        //Next, configure logging
        configLogging();
    }

    /**
     * Sets up Allocation Recorder and any events that need to be handled during runtime.
     */
    private static void setupAllocationRecorder() {

    }

    /**
     * Configures logging for the SLARuntime.
     */
    private static void configLogging() {
        //If any additional configuration is needed for the logger, do it here.
        logger.info("Sparen Language Agent now running.");
    }

}
