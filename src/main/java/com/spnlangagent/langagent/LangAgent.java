package com.spnlangagent.langagent;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.instrument.Instrumentation;

/**
 * Language Agent that is run on startup. Handles instrumentation, injection into the runtime classloader, etc.
 */
public class LangAgent {

    /**
     * Agent premain that runs first.
     * @param agentArgs javaagent arguments that are provided when the agent is run.
     * @param inst Instrumentation instance.
     * @throws Exception
     */
    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        //First, set up our instrumentation
        setupInstrumentation(inst);

        //Now, start up our runtine
        startRuntime(agentArgs);
    }

    private static void setupInstrumentation(Instrumentation inst) throws Exception {
        //TODO
    }

    private static void startRuntime(String agentArgs) throws Exception {
        //TODO
    }
}
