package ServiceMethods;

import java.io.File;
import java.io.IOException;
import java.nio.FloatBuffer;

import com.nativelibs4java.opencl.*;
import com.nativelibs4java.opencl.CLMem.Usage;
import com.nativelibs4java.util.IOUtils;
import com.nativelibs4java.util.NIOUtils;

public class JavaCLDemo {
	
	public static void runFullExample() throws IOException {
		final int NUM_FLOATS = 64;
		final int NUM_ITEMS = NUM_FLOATS/4;
		CLContext context = JavaCL.createBestContext();
		CLQueue queue = context.createDefaultQueue();
		FloatBuffer dataBuffer = NIOUtils.directFloats(NUM_FLOATS, context.getByteOrder());
		for (int i = 0; i < NUM_FLOATS; i++) {
			dataBuffer.put(i, i * 5.0f);
		}
		CLBuffer<Float> buff = context.createFloatBuffer(Usage.InputOutput, dataBuffer, true);
		String programText = IOUtils.readText(new File("C:/Users/WhiteSpike/eclipse-workspace/AGI/WhiteSpikeAI/src/Structure/JavaCLCSourceCode")); //method source file
		CLProgram program = context.createProgram(programText);
		CLKernel kernel = program.createKernel("root", buff); /*createKernel builds CLProgram, creates CLKernel and makes CLFloatBuffer the kernel's first argument,
																 would use setArg to add more*/
		CLEvent kernelEvent = kernel.enqueueNDRange(queue, new int[] {NUM_ITEMS}, new int[]{NUM_ITEMS});
		buff.read(queue, dataBuffer, true, kernelEvent);
		
		for (int i = 0; i < NUM_FLOATS; i++) {
			System.out.println(i + ": " + dataBuffer.get(i));
		}
		

	}
	
	public static void main(String[] args) throws IOException {
		runFullExample();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
//		//get devices
//		CLPlatform[] platforms = JavaCL.listPlatforms();
//		for(CLPlatform p : platforms) {
//		   CLDevice[] newDevice = p.listAllDevices(true);
//		}
//		
//		//similar to the above, but automatically chooses the device with the most
//			//compute units
//		CLDevice dev = JavaCL.getBestDevice();
//		for (String s : dev.getExtensions()) {
//			System.out.println(s);
//		}
//		
//		
//		//create context (auto-chooses the best one)
//		CLContext context = JavaCL.createBestContext();
//		
//		//create context (more control) technically just the last line, but the other two are set up
//		CLPlatform[] platforms = JavaCL.listPlatforms();
//		CLDevice[] devices = platforms[0].listAllDevices(true);
//		CLContext context = JavaCL.createContext(null, devices);
//		
//		
//		//create queue (use createDefaultProfilingQueue for profiling tasks and createDefaultOutOfOrderQueue
//			// for out-of-order command execution. Think i'll be using the second one in the AGI
//		CLContext context = JavaCL.createBestContext();
//		CLQueue queue = context.createDefaultQueue((CLDevice.QueueProperties[])null);
//		
//		
//		//get source code from file and create program with it, which will execute on the queue
//		String programText = "";
//		try {
//			programText = IOUtils.readText(new File("sourceFile.txt"));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		
//		CLProgram program = context.createProgram(programText);
//		try {
//			program.build();
//		} catch (CLBuildException e) {
//			e.printStackTrace();
//		}
//		
//		//setArg functions allow java arguments to be passed in, objects are the type CLMem (passed by reference)
//		int intArray[] = new int[]{0, 1, 2, 3};
//		LocalSize local = new LocalSize(64);
//		kernel.setArg(0, intArray); //first argument says what the argument index is
//		kernel.setArg(1, local);
//		
//		//CLMEM: To create a CLBuffer object, you can call createXXBuffer, where XX can be replaced by Byte, Char, Short, Int, Long, Float, or Double
//		CLBuffer<Float> buff1 = context.createFloatBuffer(Usage.Output, floatBuffer, true);
//		CLBuffer<Float> buff2 = context.createFloatBuffer(Usage.Output, 512);
//		CLImage2D createImage2D(CLMem.Usage usage, CLImageFormat format, long width, long height, long rowPitch);
//		CLImage3D createImage3D(CLMem.Usage usage, CLImageFormat format, long width, long height, long depth, long rowPitch, long slicePitch);
//		//then use setArg to add these to kernel^
//		
//		//enqueue
//		CLEvent enqueueTask(CLQueue queue, CLEvent... waitList);
//		
//		//another way to enqueue and have control over the work items and work groups. global size = work items
//		CLEvent enqueueNDRange(CLQueue queue, int[] offsets, int[] globalSize, int[] localSize, CLEvent... waitList)
//		//actual usage of the method (12 by 4 and 3 by 2)
//		kernel.enqueueNDRange(queue, new int[]{12, 4}, new int[]{3, 2});
		
//	}
	//author also strongly recommends looking at the NIOUtils class, does a lot of stuff for you
}
