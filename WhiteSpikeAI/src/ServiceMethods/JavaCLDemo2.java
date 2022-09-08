package ServiceMethods;

import com.nativelibs4java.opencl.*;
import com.nativelibs4java.opencl.CLMem.Usage;
import com.nativelibs4java.opencl.util.*;
import com.nativelibs4java.util.*;
import JavaCLStuff.Pointer;

import java.nio.ByteOrder;

import static java.lang.Math.*;

public class JavaCLDemo2 { 
	public static void main(String[] args) { 
		CLContext context = JavaCL.createBestContext();
		CLQueue queue = context.createDefaultQueue();
		ByteOrder byteOrder = context.getByteOrder();

    int n = 1024;
    Pointer<Float>
        aPtr = allocateFloats(n).order(byteOrder),
        bPtr = allocateFloats(n).order(byteOrder);

    for (int i = 0; i < n; i++) {
        aPtr.set(i, (float)cos(i));
        bPtr.set(i, (float)sin(i));
    }

    // Create OpenCL input buffers (using the native memory pointers aPtr and bPtr) :
    CLBuffer<Float> 
        a = context.createBuffer(Usage.Input, aPtr),
        b = context.createBuffer(Usage.Input, bPtr);

    // Create an OpenCL output buffer :
    CLBuffer<Float> out = context.createFloatBuffer(Usage.Output, n);

    // Read the program sources and compile them :
    String src = IOUtils.readText(JavaCLDemo2.class.getResource("TutorialKernels.cl"));
    CLProgram program = context.createProgram(src);

    // Get and call the kernel :
    CLKernel addFloatsKernel = program.createKernel("add_floats");
    addFloatsKernel.setArgs(a, b, out, n);
    CLEvent addEvt = addFloatsKernel.enqueueNDRange(queue, new int[] { n });

    Pointer<Float> outPtr = out.read(queue, addEvt); // blocks until add_floats finished

    // Print the first 10 output values :
    for (int i = 0; i < 10 && i < n; i++)
        System.out.println("out[" + i + "] = " + outPtr.get(i));

}
}