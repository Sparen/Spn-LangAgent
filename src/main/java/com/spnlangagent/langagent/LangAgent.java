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
        System.out.println("LangAgent: premain now running");
        //First, set up our instrumentation
        setupInstrumentation(agentArgs, inst);

        //Now, start up our runtine
        startRuntime(agentArgs);
    }

    private static void setupInstrumentation(String agentArgs, Instrumentation inst) throws Exception {
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

    //Extract our runtime from the jar, and then run it.
    //Code adapted from InsightAgent.java
    private static void startRuntime(String agentArgs) throws Exception {
        System.out.println("startRuntime: now running with agentArgs: " + agentArgs);
        LangagentApplication.main(null);

        /*
        //Create a temporary file for the jar
        File output = File.createTempFile("Spn-LangAgent-Runtime-0.0", "jar");

        //DEBUG AREA
        URL test1 = ClassLoader.getSystemClassLoader().getResource("target/Spn-LangAgent-Runtime-0.0.jar");
        URL test2 = ClassLoader.getSystemClassLoader().getResource("Spn-LangAgent-Runtime-0.0.jar");
        URL test3 = ClassLoader.getSystemClassLoader().getResource("libs/Spn-LangAgent-Runtime-0.0.jar");
        URL test4 = ClassLoader.getSystemClassLoader().getResource("target/Spn-LangAgent-Agent-0.0.jar");
        URL test5 = ClassLoader.getSystemClassLoader().getResource("Spn-LangAgent-Agent-0.0.jar");
        URL test6 = ClassLoader.getSystemClassLoader().getResource("runtime/target/Spn-LangAgent-Runtime-0.0.jar");
        URL test7 = ClassLoader.getSystemClassLoader().getResource("agent/target/Spn-LangAgent-Agent-0.0.jar");
        if (test1 == null) {System.out.println("test 1 failed");}
        if (test2 == null) {System.out.println("test 2 failed");}
        if (test3 == null) {System.out.println("test 3 failed");}
        if (test4 == null) {System.out.println("test 4 failed");}
        if (test5 == null) {System.out.println("test 5 failed");}
        if (test6 == null) {System.out.println("test 6 failed");}
        if (test7 == null) {System.out.println("test 7 failed");}
        System.out.println("Classpath: " +  System.getProperty("java.class.path", "."));
        for (URL u : ((URLClassLoader) ClassLoader.getSystemClassLoader()).getURLs()) {
            System.out.println(u.getFile());
        }


        //Attempt to load the runtime jar
        try (InputStream inputStream = ClassLoader.getSystemClassLoader().getResource("target/Spn-LangAgent-Runtime-0.0.jar").openStream()) {
            Files.copy(inputStream, output.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch(NullPointerException npe) {
            System.out.println("Null Pointer Exception when trying to get runtime agent jar. Since it was not found, aborting.");
            npe.printStackTrace();
            System.exit(1);
        }

        System.out.println("startRuntime: now creating runtime class loader");
        //Handle classloaders. Directly copied from Insight version, which in turn may have been based on https://stackoverflow.com/questions/60764
        //Note: The system class loader takes care of loading all the application level classes into the JVM.
        ClassLoader extClassLoader = ClassLoader.getSystemClassLoader().getParent(); //obtain the native bootstrap class loader
        URL[] runtimejarurl = {output.toURI().toURL()};
        ClassLoader runtimeClassLoader = new URLClassLoader(runtimejarurl, null);

        System.out.println("startRuntime: now replacing class loader parent field");
        //Replaces the native bootstrap class loader with ours
        Field f = ClassLoader.class.getDeclaredField("parent");
        f.setAccessible(true);
        f.set(extClassLoader, runtimeClassLoader);
        f.setAccessible(false);

        System.out.println("startRuntime: now running Langagent Application main");
        //Have the system class loader load our runtime agent class.
        Class<?> runtimeClass = ClassLoader.getSystemClassLoader().loadClass("com.spnlangagent.langagent.LangagentApplication");
        //Run the main method of the runtime agent
        runtimeClass.getDeclaredMethod("main", String.class).invoke(null, agentArgs);
        */
    }

}
