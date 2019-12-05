package edu.utdallas;
import java.io.*;
import java.util.Arrays;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;

/**
 * THis class is the JUnit Listerner that will listen for the beginning and end of the JUnit test methods
 */
public class Listener extends RunListener {

	// This method is called before the tests start
	public void testRunStarted(Description description) throws Exception {
		// If there is not already a CodeCoverageCollector, make a new one
		if (null == CodeCoverageCollector.Coverages_testCase)
		{
			// Create a new CodeCoverageCollector 
			CodeCoverageCollector.Coverages_testCase = new Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, IntSet>>();
		}
		
		// Let the user know that the testing has begun
        System.out.println("\n\nTesting Started!!!");
    }
	
	// This method is called before each test case
    public void testStarted(Description description) {
    	// Pass in the name of the test case 
    	CodeCoverageCollector.Name_testCase = "[TEST] " + description.getClassName() + ":" + description.getMethodName();
    	// Create a HashMap to store the coverage data
    	CodeCoverageCollector.Coverage_testCase = new Object2ObjectOpenHashMap<String, IntSet>();
    }
    
    // This method is called after each test case
    public void testFinished(Description description) {
    	// Store the Code Coverage for the test case
    	CodeCoverageCollector.Coverages_testCase.put(CodeCoverageCollector.Name_testCase, CodeCoverageCollector.Coverage_testCase);
    }
    
    // This method is called after all of the tests have finished and will print the results to the file
    public void testRunFinished(Result result) throws IOException {
    	// Let the user know that the testing has finished
        System.out.println("Testing Finished!!!\n\n");
        
        // Write our coverage data to the file
        // Create the file
        File outFile = new File("stmt-cov.txt");
        // Create the output stream
        FileOutputStream outStream = new FileOutputStream(outFile);
        // Create the buffer to write to 
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outStream));
        // Create a StringBuilder to work with the buffer
        StringBuilder builder = new StringBuilder();
        // While there are still test cases in the stored code coverage
        for (String testCaseName : CodeCoverageCollector.Coverages_testCase.keySet()) {
        	// Add the test case name to the string builder
        	builder.append(testCaseName + "\n");
        	// Create a hashmap for the test case name
        	Object2ObjectOpenHashMap<String, IntSet> caseCoverage = 
        			CodeCoverageCollector.Coverages_testCase.get(testCaseName);
        	// While there are still lines in the code coverage data
            for (String className : caseCoverage.keySet()) {
            	// Store the lines from the code coverage for the test case
            	int[] lines = caseCoverage.get(className).toIntArray();
            	// Sort the covered lines
            	Arrays.sort(lines);
            	// Add all of the lines to the string builder
            	for (int i = 0; i < lines.length; i++) {
                	builder.append(className + ":" + lines[i] + "\n");
				}
            }
        }
        // Write the data in the string builder to the file 
        writer.write(builder.toString());
        // Close the writer
        writer.close();
    }
}
