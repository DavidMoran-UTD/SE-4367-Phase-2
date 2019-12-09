package edu.utdallas;

import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import java.lang.reflect.Modifier;

import java.util.ArrayList;

/**
 * This class will print out the methods that we are going through
 */
class MethodTransformVisitor extends MethodVisitor implements Opcodes {
    
	// Store the last visited line
    int lastVisitedLine;
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
	
	// Constructor
    public MethodTransformVisitor(final MethodVisitor mv, String className, String methodName, String signature, Type[] paramTypes, 
    								int argLength, int access) {
    	// Call the superclass constructor
		super(ASM5, mv);
		// Set the class name
		this.className = className;
		// Set the method name
		this.methodName = methodName;
		// Set the parameter types
		this.paramTypes = paramTypes;
		// Set the argument length
		this.argLength = argLength;
		// Set the signature of the method
		this.signature = signature;
		// Set the access flags for the method
		this.access = access;
    }
    
    
    // Override the superclass method that will trace through the code
    @Override
    public void visitCode() {
    	// If the method name is not a construct method
    	if(!methodName.equals("<init>")) {
    		// Get the length of the parameter types array
    		int paramLength = paramTypes.length;
    		// Print out the class, method, signature, and parameter length
    		System.out.println(className + "." + methodName + "." + signature +": paramLength = " + paramLength);
    		// Set the counter
    		int i = 1;
    		// If the method we are trying to visit is a static method, print that and set the counter to 0
    		if((this.access & Opcodes.ACC_STATIC) != 0) {
    			// Print that the method is a static method
    			System.out.println("in visitCode " + className + "/" + methodName + "/" + signature + "is a static method");
    			// Set the counter to 0
    			i = 0;
    		}
    		// As long as there is a type in the parameter type array
    		for(Type type : paramTypes) {
    			// Print out the class name for the type
    			System.out.println("type.getClassName() = " + type.getClassName());
    			// If it is a boolean type
    			if (type.equals(Type.BOOLEAN_TYPE)) {
    				// Call visitVarInsn
    				mv.visitVarInsn(Opcodes.ILOAD, i);
    				// Call visitMethodInsn for the corresponding type
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;", false);
                } 
    			// If it is a byte type
    			else if (type.equals(Type.BYTE_TYPE)) {
    				// Call visitVarInsn
    				mv.visitVarInsn(Opcodes.ILOAD, i);
    				// Call visitMethodInsn for the corresponding type
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Byte", "valueOf", "(B)Ljava/lang/Byte;", false);
                } 
    			// If it is a character type
    			else if (type.equals(Type.CHAR_TYPE)) {
    				// Call visitVarInsn
    				mv.visitVarInsn(Opcodes.ILOAD, i);
    				// Call visitMethodInsn for the corresponding type
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Character", "valueOf", "(C)Ljava/lang/Character;", false);
                }
    			// If it is a short type
    			else if (type.equals(Type.SHORT_TYPE)) {
    				// Call visitVarInsn
    				mv.visitVarInsn(Opcodes.ILOAD, i);
    				// Call visitMethodInsn for the corresponding type
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Short", "valueOf", "(S)Ljava/lang/Short;", false);
                } 
    			// If it is an int type
    			else if (type.equals(Type.INT_TYPE)) {
    				// Call visitVarInsn
    				mv.visitVarInsn(Opcodes.ILOAD, i);
    				// Call visitMethodInsn for the corresponding type
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;", false);
                } 
    			// If it is a long type
    			else if (type.equals(Type.LONG_TYPE)) {
    				// Call visitVarInsn
    				mv.visitVarInsn(Opcodes.LLOAD, i);
    				// Call visitMethodInsn for the corresponding type
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;", false);
                    // Increment twice because a long may need two indices
                    i++;
                } 
    			// If it is a float type
    			else if (type.equals(Type.FLOAT_TYPE)) {
    				// Call visitVarInsn
                    mv.visitVarInsn(Opcodes.FLOAD, i);
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Float", "valueOf", "(F)Ljava/lang/Float;", false);
                }
    			// If it is a double type
    			else if (type.equals(Type.DOUBLE_TYPE)) {
    				// Call visitVarInsn
                    mv.visitVarInsn(Opcodes.DLOAD, i);
    				// Call visitMethodInsn for the corresponding type
                    mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;", false);
                    // Increment twice because a double may need two indices
                    i++;
                } else // If it is not any of the previous types
                    mv.visitVarInsn(Opcodes.ALOAD, i);
    			// Call visitLdcInsn for className
                mv.visitLdcInsn(className);
    			// Call visitLdcInsn for methodName
                mv.visitLdcInsn(methodName);
    			// Call visitLdcInsn for signature
                mv.visitLdcInsn(signature);
    			// Call visitMethodInsn to add the variable to the DataTraceCollector
                mv.visitMethodInsn(Opcodes.INVOKESTATIC, "edu/utdallas/DataTraceCollector", "addVariable", "(Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", false);
                // Increment the counter
                i++;
            }   
    	}
    	// Call the superclass method
    	super.visitCode();
    }    
    
    // Override the superclass method that will visit the variables within the method
    @Override 
    public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
    	// If the method is a virtual method, print that and add the method to the DataTraceCollector
    	if("this".equals(name)) {
    		// Add the method to the DataTraceCollector
            DataTraceCollector.staticmap.put(className + "/" + methodName + "/" + this.signature, false);
            // Print that it is a virtual method
            System.out.println(className + "/" + methodName + "/" + this.signature + "is a virtual method");    	
        }
    	// If there are more than 2 arguments
    	else if (argLength -- > 0) {
    		// Store the method info in a string
    		String string = className + "/" + methodName + "/" + this.signature;
    		// If the method is not already in the DataTraceCollector
    		if(!DataTraceCollector.map.containsKey(string)) {
    			// Add the method to the DataTraceCollector
    			DataTraceCollector.map.put(string, new ArrayList<Variable>());
    		}
    		// Get the information of the variables in the method
    		Variable variable = new Variable(Type.getType(desc), name, index);
    		// Add the varable to the DataTraceCollector
    		DataTraceCollector.map.get(string).add(variable);
    	}
    	// Call the superclass method
    	super.visitLocalVariable(name, desc, signature, start, end, index);
    }

    // Override the visitEnd method from the super class
    @Override
    public void visitEnd() {
      
    	// Call the super class method 
        super.visitEnd();
    }
}
