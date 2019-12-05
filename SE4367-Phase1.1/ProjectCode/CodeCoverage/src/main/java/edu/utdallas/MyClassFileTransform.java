package edu.utdallas;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/*
 * This class will be used to transform the class files
 */
class MyClassFileTransform implements ClassFileTransformer {

	// Override the superclass method with our own transform method
	@Override 
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // If the classes are from the commons-dbutils library or joda-time, we can transform them
		if (className.startsWith("org/apache/commons/dbutils") || className.startsWith("org/joda/time")){
			// Reader to read in the class file in bytes
            ClassReader cr = new ClassReader(classfileBuffer);
            // Writer to wrtie the class file we just read
            ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
            // Visitor to print the classes that we have transformed
            ClassTransformVisitor ca = new ClassTransformVisitor(cw);
            // Parse the class file
            cr.accept(ca, 0);
            // Return the tranformed class file
            return cw.toByteArray();
        }
		// If we hit this it means we didn't transform the class file so just return the bytes that were passed into the method
        return classfileBuffer;
        
    }
}
