package edu.utdallas;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/**
 * This class will print out the methods that we are going through
 */
class MethodTransformVisitor extends MethodVisitor implements Opcodes {
    
	// Store the last visited line
    int lastVisitedLine;
    // Store the class name
	String className;
	
	// Constructor
    public MethodTransformVisitor(final MethodVisitor mv, String nameOfclass) {
    	// Call the constructor from the super class
        super(ASM5, mv);
        // Store the class name
        this.className=nameOfclass;

    }


    // Override the visitLineNumber method from the super class that will print out the information with the MethodVisitor
    @Override
    public void visitLineNumber(int line, Label start) {
    	// If the line number is not 0
		if (0 != line) {
			// Store the last line that we visited
	    	lastVisitedLine = line;
	    	// Visit the class
			mv.visitLdcInsn(className);
			// Visit the line
			mv.visitLdcInsn(new Integer(line));
			// Visit the method instruction
			mv.visitMethodInsn(INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
			// Visit the method instruction and store it in the CodeCoverageCollector
			mv.visitMethodInsn(INVOKESTATIC, "edu/utdallas/CodeCoverageCollector", "addLine", "(Ljava/lang/String;Ljava/lang/Integer;)V", false);
	}
		// Call the super class method
    	super.visitLineNumber(line, start);
	}

    // Override the visitEnd method from the super class
    @Override
    public void visitEnd() {
      
    	// Call the super class method 
        super.visitEnd();
    }
}
