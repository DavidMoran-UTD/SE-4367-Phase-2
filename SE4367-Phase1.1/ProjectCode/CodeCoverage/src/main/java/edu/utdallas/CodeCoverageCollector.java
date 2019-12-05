package edu.utdallas;

/**
 * This is the class where the coverage collection occurs.  It is where we will store the coverage data that will later be printed
 */
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;

public class CodeCoverageCollector {
	// We use these hash maps to map the Test Case to the class to the statement coverage.  This ensures that the data will be printed 
	// correctly to the output file
	public static Object2ObjectOpenHashMap<String, Object2ObjectOpenHashMap<String, IntSet>> Coverages_testCase; // This stores the coverage for all of the test cases
	public static Object2ObjectOpenHashMap<String, IntSet> Coverage_testCase; // This stores the coverage for a single test case
	public static String Name_testCase; // Stores the name of the test case

    // This method is called when we need to add a line to the coverage
    public static void addLine(String className, Integer line){
    	if (Coverage_testCase == null) {
    		return;
    	}
    	
    	IntSet lines = Coverage_testCase.get(className);
        if (lines != null) {
        	lines.add(line);
        }
        else {
        	lines = new IntOpenHashSet(new int[]{line});
            Coverage_testCase.put(className, lines);
        }
    }
}
