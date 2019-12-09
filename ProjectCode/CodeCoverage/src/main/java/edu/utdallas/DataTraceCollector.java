package edu.utdallas;

import org.objectweb.asm.Type;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/*
 * This class will collect and store the information from the program data trace
 */
public class DataTraceCollector {
	// HashMap for storing the methods and the variable information
    public static HashMap<String, List<Variable>> map = new HashMap<String, List<Variable>>();
    // HashMap for storing the virtual methods
    public static HashMap<String, Boolean> staticmap = new HashMap<String, Boolean>();
    // Store the local name
    public static String localName;
    // Store the type
    public static Type type;
    // Store the method name
    public static String methodName;
    // Store the number of variables
    public static int count = 0;
    
    // This method will add a variable to the list for the method
    public synchronized static void addVariable(Object obj, String className, String methodName, String signature) {
    	// Create a list of variables with the method info
    	List<Variable> list = map.get(className + "/" + methodName + "/" + signature);
    	// If the count is higher than the size of the list
    	if(count >= list.size()) {
    		// Reset the counter
    		count = 0; 
    	}
    	// Store the count in the variable
    	Variable v = list.get(count);
    	// Add a value to the variable
    	v.addValue(obj);
    	// Increment the counter
    	count++;
    	// If the method name doesn't equal "getMaxWidth"
    	if(!methodName.equals("getMaxWidth"))
    		// Print the method and variable info
    		System.out.println(className + "/" + methodName + ": " + v.getType().toString() + " " + v.getName() + " " + obj);
    }
}
