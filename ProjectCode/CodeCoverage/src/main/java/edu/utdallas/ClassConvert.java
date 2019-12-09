package edu.utdallas;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;

import java.util.ArrayList;
import java.util.List;
/*
 * This class will be used to get the class info that we need while we are tracing through the program
 */
public class ClassConvert extends ClassNode implements Opcodes {
	// Constructor
	public ClassConvert(ClassVisitor cv) {
		// Call the super class contructor
		super(ASM5);
		// Set the class visitor equal to the argument
		this.cv = cv;
	}
	
	// Override the superclass visitMethod() method.  This method will get information about the methods we are tracing through and then call the MethodVisitor
	@Override
	public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
		// Store the name of the method
		DataTraceCollector.methodName = name;
		// Store the type for the method descriptor
		Type methodType = Type.getMethodType(desc);
		// Store the number of arguments in the method
		int length = methodType.getArgumentTypes().length;
		// Call the MethodVisitor
		MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
		// If the MethodVisitor is not null
		if(mv != null) {
			// Convert the method 
			return new MethodConvert(access, name, desc, signature, exceptions, mv, methodType.getArgumentTypes(), length);
		} else return null;
	}
	
	// Override the super class method for visitEnd() which visits the end of the method
	@Override
	public void visitEnd() {
		// Call the visitEnd() method from the superclass
		super.visitEnd();
		// Pass in the class visitor
		accept(cv);
	}
}
