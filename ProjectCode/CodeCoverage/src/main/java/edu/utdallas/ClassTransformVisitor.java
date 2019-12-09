package edu.utdallas;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;

/**
 * This class will be used to print out the classes that we are visiting 
 */
class ClassTransformVisitor extends ClassVisitor implements Opcodes {
	// Stores the class name
    String className;
    // Constructor
    public ClassTransformVisitor(final ClassVisitor cv) {
        super(ASM5, cv);
    }

    // Override the superclass method for visit(int, int, String, String, String, String[])
    @Override
    public void visit(int version, int access, String name, String signature, String superName, String[] interfaces) {
    	// Call the superclass method with the parameters passed into the method
        super.visit(version, access, name, signature, superName, interfaces);
        // Set the className equal to the name of the class that we are currently visiting.  It is the value that is passed through the arguments
        this.className=name;

    }

    // Override the superclass method for visitMethod(int, String, String, String, String[])
    @Override
    public MethodVisitor visitMethod(final int access, final String name,
                                     final String desc, final String signature, final String[] exceptions) {
    	// Create a method visitor with the superclass method using the arguments passed into our method
        MethodVisitor mv = cv.visitMethod(access, name, desc, signature, exceptions);
        // If the DataTraceCollector doesn't contain the method that we are visiting
        if(!DataTraceCollector.staticmap.containsKey(className + "/" + name + "/" + signature)) {
        	// Add the method to the DataTraceCollector
        	DataTraceCollector.staticmap.put(className + "/" + name + "/" + signature, true);
        }
        // Set the name of the method in the DataTraceCollector
        DataTraceCollector.methodName = name;
        // Get the type of the method using the descriptor
        Type methodType = Type.getMethodType(desc);
        // Get the length of the argument types
        int length = methodType.getArgumentTypes().length;
        // Return null if the MethodVisitor mv is null else we will return a new MethodTransformVisitor that we will create with
        // Our method visitor that we just created and the stored class name
        return mv == null ? null : new MethodTransformVisitor(mv,className, name, signature, methodType.getArgumentTypes(), length, access);
    }
}
