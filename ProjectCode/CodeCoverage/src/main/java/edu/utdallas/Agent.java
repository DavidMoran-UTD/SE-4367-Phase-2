package edu.utdallas;

import java.lang.instrument.Instrumentation;

/**
 * This is our Java Agent that contains the premain method for our project.  It will execute first and will begin the Instrumentation
 */
public class Agent {
	// This is our premain method that will execute first
    public static void premain(String agentArgs, Instrumentation inst) {

    	// Print to the system to let our user know that the Agent has started
        System.out.println("My Java Agent is now executing");

        // Call the method to transform the class file then add it to the instrumentation
        inst.addTransformer(new MyClassFileTransform());

    }
}

