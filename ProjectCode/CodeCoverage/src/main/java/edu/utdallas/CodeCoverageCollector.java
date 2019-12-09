package edu.utdallas;

/**
 * This is the class where the coverage collection occurs.  It is where we will store the coverage data that will later be printed
 */
import java.util.HashMap;
import java.util.LinkedHashSet;

public class CodeCoverageCollector {
	// We use these hash maps to map the Test Case to the class to the statement coverage.  This ensures that the data will be printed 
	// correctly to the output file
    public static HashMap<String,HashMap<String, LinkedHashSet<Integer>>> testSuiteCoverage;
    public static HashMap<String, LinkedHashSet<Integer>> testCaseCoverage;
    public static String testCaseName;

    // This method is called when we need to add a line to the coverage
    public static void addLine(String className, Integer line){
    	if (testCaseCoverage == null || className == null) {
    		return;
    	}
    	LinkedHashSet<Integer> lines = testCaseCoverage.get(className);
        if(lines == null) {
        	lines = new LinkedHashSet<Integer>();
        }
        lines.add(line);
        testCaseCoverage.put(className, lines);
    }
}
