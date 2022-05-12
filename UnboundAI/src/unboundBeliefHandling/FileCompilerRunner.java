package unboundBeliefHandling;
import java.util.*;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import qj.util.lang.DynamicClassLoader;
import unboundContextHandling.ContextWriter;
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
			compiler.run(null, null, null, beliefSource.getPath());
			Class<?> cls = new DynamicClassLoader(beliefSource.getPath().substring(0, 39)).load(beliefName); //beliefSource
			@SuppressWarnings("unused")
			Object instance = cls.newInstance();
		} catch (Exception e) {
			for (int i = 0; i < e.getStackTrace().length; i++) {
				//check if bin contains the filename
				File directory = new File("/home/agi/Desktop/eclipse/UnboundAI/bin");
				String[] pathnames = directory.list();
				boolean isBeliefClass = false;
				String thisClassName = "nonononononononononno";
				for (int k = 0; k < pathnames.length; k++) {
					Scanner scanThis = new Scanner(e.getStackTrace()[i].getClassName());
					System.out.println("FUCK: " + e.getStackTrace()[i].getClassName());
					scanThis.useDelimiter("$.");
					while (scanThis.hasNext()) {
						thisClassName = scanThis.next();
						System.out.println("NEXT: " + thisClassName);
					}	
					System.out.println("PName: " + pathnames[k]);
					System.out.println("FName: " + thisClassName);
					if (pathnames[k].equals(thisClassName)) {
						isBeliefClass = true;
					}
				}
				if (isBeliefClass) {
					File errFile = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/" + thisClassName);
					Scanner scanErr = new Scanner(errFile);
					for (int j = 0; j < e.getStackTrace()[i].getLineNumber(); j++) {
						scanErr.nextLine();
					}
					Instruction error = new Instruction(e.getStackTrace()[i].getClassName(), e.getStackTrace()[i].getLineNumber(), scanErr.nextLine());
					scanErr.close();
					//pass error back up to context error List
					contextIn.errors.add(error);
				}
			}	
			System.out.println("contextERR: " + contextIn.errors.size());
			ContextWriter.writeContext(contextIn, false);
		}
		Runtime run = Runtime.getRuntime();
		String command = "rm " + path + beliefName + ".class";
		run.exec(command);
		command = "rm " + path + beliefName + ".java";
		run.exec(command);
	}
}
