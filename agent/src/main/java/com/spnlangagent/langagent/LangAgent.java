package com.spnlangagent.langagent;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.lang.reflect.Field;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

import com.google.monitoring.runtime.instrumentation.AllocationRecorder;

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
     * @throws Exception if something goes wrong.
     */
    public static void premain(String agentArgs, Instrumentation inst) throws Exception {
        //First, set up our instrumentation
        setupInstrumentation(agentArgs, inst);

        //Now, start up our runtine
        startRuntime(agentArgs);
    }

    private static void setupInstrumentation(String agentArgs, Instrumentation inst) throws Exception {
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

        // Get the set of already loaded classes that can be rewritten.
        Class<?>[] classes = inst.getAllLoadedClasses();
        ArrayList<Class<?>> classList = new ArrayList<Class<?>>();
        for (int i = 0; i < classes.length; i++) {
            if (inst.isModifiableClass(classes[i])) {
                classList.add(classes[i]);
            }
        }

        // Reload classes, if possible.
        Class<?>[] workaround = new Class<?>[classList.size()];
        try {
            inst.retransformClasses(classList.toArray(workaround));
        } catch (UnmodifiableClassException e) {
            System.err.println("Instrumenter(s) were unable to retransform early loaded classes.");
        }

        /* END Code adapted from Google's Allocation Instrumenter */

    }

    //Extract our runtime from the jar, and then run it.
    //Code adapted from InsightAgent.java
    private static void startRuntime(String agentArgs) throws Exception {
        //Create a temporary file for the jar
        File output = File.createTempFile("insight-runtime", "jar");
        //Attempt to load the runtime jar
        try (InputStream inputStream = ClassLoader.getSystemClassLoader().getResource("target/Spn-LangAgent-Runtime-0.0.jar").openStream()) {
            Files.copy(inputStream, output.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        //Handle classloaders. Directly copied from Insight version.
        //Note: The system class loader takes care of loading all the application level classes into the JVM.
        ClassLoader extClassLoader = ClassLoader.getSystemClassLoader().getParent(); //obtain the native bootstrap class loader
        URL[] runtimejarurl = {output.toURI().toURL()};
        ClassLoader runtimeClassLoader = new URLClassLoader(runtimejarurl, null);

        //Replaces the native bootstrap class loader with ours
        Field f = ClassLoader.class.getDeclaredField("parent");
        f.setAccessible(true);
        f.set(extClassLoader, runtimeClassLoader);
        f.setAccessible(false);

        //Have the system class loader load our runtime agent class and run the bootstrap method.
        Class<?> runtimeClass = ClassLoader.getSystemClassLoader().loadClass("com.spnlangagent.langagent.LangagentApplication");
        runtimeClass.getDeclaredMethod("bootstrap", String.class).invoke(null, agentArgs);
    }

}
