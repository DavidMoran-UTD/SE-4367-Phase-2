package edu.utdallas;
import java.io.*;
import java.util.Arrays;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;


/**
 * THis class is the JUnit Listerner that will listen for the beginning and end of the JUnit test methods
 */
public class Listener extends RunListener {

	// This method is called before the tests start
	public void testRunStarted(Description description) throws Exception {		
		// Let the user know that the testing has begun
        System.out.println("\n\nTesting Started!!!");
    }
	
	// This method is called before each test case
    public void testStarted(Description description) {
    	// Print the information for the test case we just started
        System.out.println(description.getClassName() + ":" + description.getMethodName() + " Started\n");    }
    
    // This method is called after each test case
    public void testFinished(Description description) {
    	// Print the information for the test case we just finished
        System.out.println(description.getClassName() + ":" + description.getMethodName() + " Finished\n");    }
    
    // This method is called after all of the tests have finished and will print the results to the file
    public void testRunFinished(Result result) throws IOException {
    	// Print that we have finished the test run
    	System.out.println("Test Run Finished\n");
        try {
        	// Create a new FileWriter to write the data trace
            FileWriter fileWriter = new FileWriter("datatrace.txt",true);
            // Create a string buffer to help print to the file
            StringBuffer stringBuffer = new StringBuffer();
            // As long as there are still methods in the DataTraceCollector, keep printing data
            for(String methodName : DataTraceCollector.map.keySet()) {
                // Add the method name to the string buffer
                stringBuffer.append("Method Name: " + methodName + "\n");
                // Create a list to hold all of the variables in the DataTraceCollector
                List<Variable> variables = DataTraceCollector.map.get(methodName);
                // As long as there are still variables in the DataTraceCollector
                for(Variable variable : variables){
                	// Add the variable to the string buffer
                    stringBuffer.append("Variable: " + variable.getType().toString() + " " + variable.getName() + "\n");
                    // Add potential values to the string buffer
                    for(Object obj : variable.getValues()){
                        stringBuffer.append("Value: " + obj + "\n");
                    }
                }
            }
            // Write the data in the string buffer to the file
            fileWriter.write(stringBuffer.toString());
            // Close the file writer
            fileWriter.close();
        } 
        // Catch exceptions
        catch (IOException ex) {
            System.err.println("Couldn't log this");
        }
    }
}
