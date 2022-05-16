package unboundStruct;
import java.io.*;
import java.util.*;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import qj.util.lang.DynamicClassLoader;

public class Property {
	Object prop;
	String propLabel;
	public Property(String typeIn, String nameIn, String valuesStringIn) throws Exception {
		this.prop = determineShit(typeIn, nameIn, valuesStringIn);
	}
	
	public Object determineShit (String typeIn, String nameIn, String valuesStringIn) throws Exception {
		Object output = null;
		if (typeIn.equals("int")) {
			output = Integer.valueOf(valuesStringIn);
		} else
		if (typeIn.equals("double")) {
			output = Double.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("char")) {
			output = Character.valueOf(valuesStringIn.charAt(0));
		}	else
		if (typeIn.equals("String")) {
			output = valuesStringIn;
		}	else
		if (typeIn.equals("byte")) {
			output = Byte.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("short")) {
			output = Short.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("long")) {
			output = Long.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("float")) {
			output = Float.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("boolean")) {
			output = Boolean.valueOf(valuesStringIn);
		}	else
		if (typeIn.substring(0, 4).equals("List")) {	
			List<Object> list = new ArrayList<Object>();	
			Scanner scan = new Scanner(valuesStringIn);
			while (scan.hasNext()) {
				String type = scan.next();
				String name = scan.next();
				String value = scan.next();
				Object component = determineShit(type, name, value);
				list.add(component);		
			}
			scan.close();
			output = list;			
		} else {	
			//scan in instance variables
				Scanner scan = new Scanner(valuesStringIn);
				List<String> types = new ArrayList<String>();
				List<String> names = new ArrayList<String>();
				List<String> values = new ArrayList<String>();
				while (scan.hasNext()) {
					String type = scan.next();
					if (scan.hasNext()) {
						String name = scan.next();
						if (scan.hasNext()) {
							String value = scan.next();
							types.add(type);
							names.add(name);
							values.add(value);
						}
							
					}
				}
				scan.close();				
				//use instance variables to write dynamic object source code (set the instance vars to their values "by default" in the source)
				String source = "public class " + typeIn + "{\n";
				for (int i = 0; i < types.size(); i++) {
					source = source + types.get(i) + " " + names.get(i) + " = \"" + values.get(i) + "\";\n";
				}
				source = source + "public " + typeIn + "(){}\n";
				source = source	+ "}";
				Runtime run = Runtime.getRuntime();
				String command = "touch /home/agi/Desktop/eclipse/UnboundAI/bin/" + typeIn + ".java";
				run.exec(command);
				File sourceFile = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/" + typeIn + ".java");
				FileWriter writer = new FileWriter(sourceFile, false);
				writer.write(source);
				writer.close();
				//use source code to create instance of dynamic object
				JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
				compiler.run(null, null, null, sourceFile.getPath());
				Class<?> cls = new DynamicClassLoader("/home/agi/Desktop/eclipse/UnboundAI/bin/").load(typeIn); 
				Object instance = cls.newInstance();
				output = instance;	
				//Remove dynamic object file
				command = "rm /home/agi/Desktop/eclipse/UnboundAI/bin/" + typeIn + ".java";
				run.exec(command);
				command = "rm /home/agi/Desktop/eclipse/UnboundAI/bin/" + typeIn + ".class";
				run.exec(command);
		}
		this.propLabel = nameIn;
		return output;
	}
}
