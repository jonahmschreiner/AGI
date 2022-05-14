package unboundBeliefHandling;
import java.util.*;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import qj.util.lang.DynamicClassLoader;
import unboundContextHandling.ContextWriter;
import unboundContextHandling.CheckCompilerErrors;
import unboundStruct.*;


import java.io.*;
public class FileCompilerRunner {
	public static void compile(String sourceIn, String beliefName, Context contextIn) throws Exception {
		String path = "/home/agi/Desktop/eclipse/UnboundAI/bin/";
		File beliefSource = new File(path + beliefName + ".java");
		FileWriter writer = new FileWriter(beliefSource);
		writer.write(sourceIn);
		writer.close();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		try {
			FileOutputStream test = new FileOutputStream("/home/agi/Desktop/eclipse/UnboundAI/bin/errorFile.txt");
			compiler.run(null, null, test, beliefSource.getPath());
			List<Instruction> errorInsts = CheckCompilerErrors.pull(contextIn);
			if (errorInsts.isEmpty()) {
				Class<?> cls = new DynamicClassLoader(beliefSource.getPath().substring(0, 39)).load(beliefName); //beliefSource
				@SuppressWarnings("unused")
				Object instance = cls.newInstance();
			} else {
				contextIn.env.errorLocations.addAll(errorInsts);
				ContextWriter.writeContext(contextIn, false);
			}

		} catch (Exception e) {		
			e.printStackTrace();
		}
		Runtime run = Runtime.getRuntime();
		String command = "rm " + path + beliefName + ".class";
		run.exec(command);
		command = "rm " + path + beliefName + ".java";
		run.exec(command);
	}
}
