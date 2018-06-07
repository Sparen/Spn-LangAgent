package com.spnlangagent.langagent;

import java.util.ArrayList;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Language Agent that is run on startup. Handles instrumentation, injection into the runtime classloader, etc.
 * The majority of the code in this file is based off of:
 * https://github.com/google/allocation-instrumenter/blob/master/src/main/java/com/google/monitoring/runtime/instrumentation/AllocationInstrumenter.java
 */
public class LangAgent {

    /**
     * Agent premain that runs first.
     * @param agentArgs javaagent arguments that are provided when the agent is run.
     * @param inst Instrumentation instance.
     */
    public static void premain(String agentArgs, Instrumentation inst) {
        System.out.println("LangAgent: premain now running");
        //First, set up our instrumentation
        setupInstrumentation(agentArgs, inst);

        //Now, start up our runtine
        startRuntime(agentArgs);
    }

    private static void setupInstrumentation(String agentArgs, Instrumentation inst) {
        System.out.println("setupInstrumentation: now running with agentArgs: " + agentArgs);
        //Add Transformers to the Instrumentation
        inst.addTransformer(new SLAInstrumenter(), true);

        /* START Code adpated from Google's Allocation Instrumenter */
        // Load classes needed to perform instrumentation in advance to avoid ClassCircularityError later
        try {
            Class.forName("sun.security.provider.PolicyFile");
            Class.forName("java.util.ResourceBundle");
            Class.forName("java.util.Date");
            Class.forName("java.util.logging.LogManager");
        } catch (Throwable t) {
            // NOP
        }

        System.out.println("setupInstrumentation: now getting loaded classes");
        // Get the set of already loaded classes that can be rewritten.
        Class<?>[] classes = inst.getAllLoadedClasses();
        ArrayList<Class<?>> classList = new ArrayList<Class<?>>();
        for (int i = 0; i < classes.length; i++) {
            if (inst.isModifiableClass(classes[i])) {
                classList.add(classes[i]);
            }
        }

        System.out.println("setupInstrumentation: now reloading classes");
        // Reload classes, if possible.
        Class<?>[] workaround = new Class<?>[classList.size()];
        try {
            inst.retransformClasses(classList.toArray(workaround));
        } catch (UnmodifiableClassException e) {
            System.err.println("Instrumenter(s) were unable to retransform early loaded classes.");
        }

        /* END Code adapted from Google's Allocation Instrumenter */

    }

    /**
     * Begins the runtime.
     * @param agentArgs javaagent arguments that are provided when the agent is run.
     */
    private static void startRuntime(String agentArgs) {
        System.out.println("startRuntime: now running with agentArgs: " + agentArgs);
        SLARuntime runtime = new SLARuntime();
    }

}
