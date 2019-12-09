package edu.utdallas;

import org.objectweb.asm.Type;

import java.util.ArrayList;
import java.util.List;
/*
 * This class will be used to store all of the information for the variables we find during the program trace
 */
public class Variable {
	// Store the type of the variable
	Type type;
	// Store the name of the variable
	String name;
	// Store the index for the variable
	int index;
	// Store the potential values for the variable
	List<Object> values;
	
	// Constructor
	public Variable(Type type, String name, int index) {
		// Set the variable type
		this.type = type;
		// Set the variable name
		this.name = name;
		// Set the variable values
		this.values = new ArrayList<Object>();
		// Set the variable index
		this.index = index;
	}
	
	// Set the index
	public void setIndex(int index) {
		this.index = index;
	}
	
	// Get the index
	public int getIndex() {
		return index;
	}
	
	// Set the type
	public void setType(Type type) {
		this.type = type;
	}
	
	// Get the type
	public Type getType() {
		return type;
	}
	
	// Set the name
	public void setName(String name) {
		this.name = name;
	}
	
	// Get the name
	public String getName() {
		return name;
	}
	
	// Set the values
	public void setValues(List<Object> values) {
		this.values = values;
	}
	
	// Get the values
	public List<Object> getValues(){
		return values;
	}
	
	// Set the values
	public void addValue (Object obj) {
		this.values.add(obj);
	}
	
}
