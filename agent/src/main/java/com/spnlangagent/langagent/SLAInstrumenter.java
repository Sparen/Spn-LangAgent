package com.spnlangagent.langagent;

import com.google.monitoring.runtime.instrumentation.AllocationInstrumenter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * The Sparen LangAgent Instrumenter utilizes the canRewriteClass method from the Google AllocationInstrumenter
 * to check for classes that cannot be transformed/rewritten.
 */
public class SLAInstrumenter implements ClassFileTransformer {

    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[]
            classfileBuffer) throws IllegalClassFormatException {

        //If not rewritable, then return the buffer as-is, without any transformation
        if (!canRewriteClass(className, loader)) {
            return classfileBuffer;
        }

        //Otherwise, if rewritable, instrument the bytecode with the provided loader
        return AllocationInstrumenter.instrument(classfileBuffer, loader);
    }

    //This function is from:
    // https://github.com/google/allocation-instrumenter/blob/master/src/main/java/com/google/monitoring/runtime/instrumentation/AllocationInstrumenter.java
    public boolean canRewriteClass(String className, ClassLoader loader) {
        // There are two conditions under which we don't rewrite:
        //  1. If className was loaded by the bootstrap class loader and
        //  the agent wasn't (in which case the class being rewritten
        //  won't be able to call agent methods).
        //  2. If it is java.lang.ThreadLocal, which can't be rewritten because the
        //  JVM depends on its structure.
        if ((loader == null) || className.startsWith("java/lang/ThreadLocal")) {
            return false;
        }
        // third_party/java/webwork/*/ognl.jar contains bad class files.
        if (className.startsWith("ognl/")) {
            return false;
        }

        // We must also prevent the agent from being rewritten
        if (className.startsWith("com/spnlangagent/langagent")) {
            return false;
        }

        return true;
    }
}
