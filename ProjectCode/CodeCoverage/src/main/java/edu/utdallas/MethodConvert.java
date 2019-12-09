package edu.utdallas;

import com.sun.org.apache.xerces.internal.impl.dv.ValidatedInfo;


import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.MethodNode;
import java.util.List;
/*
 * This class will be used to help store the data that we will be gathering as we trace through the program
 */
public class MethodConvert extends MethodNode implements Opcodes{
	// Store the class name
	String className;
	// Store the method name
	String methodName;
	// Store the parameter types
	Type[] paramTypes;
	// Store the length of the arguments
	int argLength;
	// Store the signature of the method
	String signature;
	// Store the access flags for the method
	int access;
	// Store the Method Visitor
	MethodVisitor mv;
	
	// Constructor
	public MethodConvert (int access, String name, String desc, String signature, String[] exceptions, MethodVisitor mv, 
							Type[] paramTypes, int argLength) {
		// Call the constructor for the super class
		super(ASM5, access, name, desc, signature, exceptions);
		// Set the className
		this.className = className;
		// Set the methodName
		this.methodName = methodName;
		// Set the parameter types
		this.paramTypes = paramTypes;
		// Set the argument length
		this.argLength = argLength;
		// Set the signature of the method
		this.signature = signature;
		// Set the access flags for the method
		this.access = access;
		// Set the Method Visitor
		this.mv = mv;
	}
	
	// Override the super class method for visitEnd() which visits the end of the method
	@Override 
	public void visitEnd() { 
		// Pass in the MethodVisitor
		accept(mv);
	}

}
