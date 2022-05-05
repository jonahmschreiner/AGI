package agiStruct;
import agiStruct.KeyTag;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.util.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import qj.util.lang.DynamicClassLoader;
import AGIUtil.randomAction;
public class BeliefConnector {
	private LinkedList<KeyTag> tags = new LinkedList<KeyTag>();
	private List<Instruction> instructions;
	private String NeuralPath = "";
	private String className = "";
	private String beliefFilePath = "/home/agi/Desktop/eclipse/AGI/BeliefStorage/";
	private String BegMylein = "import java.io.*;import java.util.*;import agiStruct.*;import AGIUtil.*;import coreMethods.*; import topLevelMethods.*; public class " + this.className + "{ public " + this.className + "() throws Exception {  ";
	final private String Goal2ndBegMylein = "agiStruct.GoalResult goalEvalResult = new agiStruct.GoalResult();";
	final private String EndMylein = "} }";
	final private String SenseEndishMylein = "File tagsCalled = new File(path + \"tagsCalled.java\");FileWriter writer2 = new FileWriter(tagsCalled, true);writer2.write(tagsRecursivelyCalled);writer2.close();";
	final private String GoalEndishMylein = "File goalResult = new File(path + \"GoalEvalResult.java\");FileWriter writer2 = new FileWriter(goalResult, false);writer2.write(goalEvalResult.toString());writer2.close();";
	private String envHardcodedVariables = "";
	private String prevEnvHardcodedVariables = "";
	private randomAction randActObj;
	public BeliefConnector (){
	
	}
	public BeliefConnector(String pathIn) {
		this.NeuralPath = pathIn;
	}
	public BeliefConnector(String pathIn, randomAction randActObjIn) {
		this.NeuralPath = pathIn;
		this.randActObj = randActObjIn;
	}
	public BeliefConnector(LinkedList<KeyTag> tagsIn) {
		this.tags = tagsIn;
	}
	public BeliefConnector(LinkedList<KeyTag> tagsIn, randomAction randActObjIn) {
		this.tags = tagsIn;
		this.randActObj = randActObjIn;
	}
	//instructions management
	public List<Instruction> getInstructions(){
		return this.instructions;
	}
	
	public void setInstructions(List<Instruction> instructionsIn) {
		this.instructions = instructionsIn;
	}
	
	public void addInstruction(Instruction instIn) {
		this.instructions.add(instIn);
	}
	//className management
	public String getClassName() {
		return this.className;
	}
	public void setClassName(String nameIn) {
		this.className = nameIn;
		this.BegMylein = "import java.io.*;import java.util.*;public class " + this.className + "{ public " + this.className + "() throws Exception {  ";
	}
	//envHardcodedVariables management
	public String getEnvHardcodedVariables() {
		return this.envHardcodedVariables;
	}
	public void setEnvHardcodedVariables(String envVarsIn) {
		this.envHardcodedVariables = envVarsIn;
	}
	//prevEnvHardcodedVariables management
	public String getPrevEnvHardcodedVariables() {
		return this.prevEnvHardcodedVariables;
	}
	public void setPrevEnvHardcodedVariables(String prevEnvVarsIn) {
		this.prevEnvHardcodedVariables = prevEnvVarsIn;
	}
	//randActObj management
	public void setRandomActionObj(randomAction raIn) {
		this.randActObj = raIn;
	}
	public randomAction getRandomActionObj() {
		return this.randActObj;
	}
	
	//NeuralPath management
	public void setNeuralPath(String neuralPathIn) {
		this.NeuralPath = neuralPathIn;
	}
	public String getNeuralPath() {
		return this.NeuralPath;
	}
	//tag management
	public void addTag(KeyTag tag){
		tags.add(tag);
	}
	
	public LinkedList<KeyTag> getTags() {
		return tags;
	}
	public void removeTag(KeyTag tag) {
		tags.remove(tag);
	}
	@SuppressWarnings({ "null", "unused" })
	public LinkedList<BeliefConnector> retrieveBeliefs(LinkedList<String> tagsIn) throws FileNotFoundException {
		LinkedList<BeliefConnector> beliefOutput = new LinkedList<BeliefConnector>();
		String beliefDirPath;
		try {
			URI beliefDirPath1 = this.getClass().getProtectionDomain().getCodeSource().getLocation().toURI();
			beliefDirPath = (beliefDirPath1.getPath().endsWith("/") ? beliefDirPath1.resolve("..") : beliefDirPath1.resolve(".")).toString();
			//beliefDirPath = (beliefDirPath2.getPath().endsWith("/") ? beliefDirPath2.resolve("..") : beliefDirPath2.resolve(".")).toString();
			beliefDirPath = beliefDirPath + "BeliefStorage/";
			beliefDirPath = beliefDirPath.substring(5);
		} catch (Exception e) {
			beliefDirPath = this.beliefFilePath;
		}
		File directory = new File(beliefDirPath);
		String[] pathnames = directory.list();
		for (String pathname : pathnames) {
			File source = new File(beliefDirPath + pathname);
			Scanner reader = new Scanner(source);
			reader.useDelimiter("uuuuu");
			while(reader.hasNext()) {
				BeliefConnector belief = new BeliefConnector();
				String currentString = reader.next();
				Scanner lineReader = new Scanner(currentString);
				lineReader.useDelimiter(",,,");
				String tags = lineReader.next();
				//read in tags for current belief
				Scanner tagReader = new Scanner(tags);
				tagReader.useDelimiter(" ");
				while (tagReader.hasNext()) {
					String tagCode = tagReader.next();
					KeyTag tag = new KeyTag();
				
					//read in properties of individual tag
					Scanner propertyReader = new Scanner(tagCode);
					propertyReader.useDelimiter(":");
					while(propertyReader.hasNext()) {
						String tagProp = propertyReader.next();
						try {
							tag.setConfidenceRating(Integer.valueOf(tagProp));
						} catch (Exception e) {
							tag.setLabel(tagProp);
						}				
					}
					belief.addTag(tag);
					propertyReader.close();
				}
				//read in className
				String classNameIn = lineReader.next();
				belief.setClassName(classNameIn);
				//read in dynamic code, pipe it to instruction list and use that to generate the document
					//instReader is looking through the belief's source code
//				Scanner instReader = new Scanner(lineReader.next());
//				instReader.useDelimiter("uuuuu");	
//				int instNumber = 0;
//				List<Instruction> insts = new ArrayList<Instruction>();
//				while (instReader.hasNext()) {
//					String next = instReader.next();
//					
//					if (instructions != null) {
//						insts.addAll(instructions);
//					}					
//				}
				List<Instruction> instructions = parseInstructions(lineReader.next(), this.getClassName(), 0, 0);
				//instReader.close();
				String neural = "";
				if (instructions != null) {
					for(int i = 0; i < instructions.size(); i++) {
						neural = neural + instructions.get(i).getInstruction();
					}
				}

				belief.setNeuralPath(neural);
				lineReader.close();
				tagReader.close();
				for (int i = 0; i < belief.getTags().size(); i++) {
					if (tagsIn.contains(belief.getTags().get(i).getLabel())) {
						beliefOutput.add(belief);
						break;
					}
				}			
			}
		reader.close();
		}
		return beliefOutput;
	}
	
	//Parse instructions from source string
	public List<Instruction> parseInstructions(String source, String classNameIn, int instNumIn, int codeBlockIn){
		List<Instruction> decodedInst = new ArrayList<Instruction>();
		Scanner instReader = new Scanner(source);
		instReader.useDelimiter("eNdBrEaK");
		while (instReader.hasNext()) {
			String currentString = instReader.next();
			int numOfLetters = 0;
			int charLocation1 = 0;
			while (numOfLetters == 0 && charLocation1 < currentString.length()) {
				if (Character.isLetter(currentString.charAt(charLocation1))) {
					numOfLetters++;
				}
				charLocation1++;
			}
			if (currentString == null || currentString.isEmpty() || numOfLetters == 0) {
				instReader.close();
				return decodedInst;
			}
			System.out.println("Current String: " + currentString);
			if (currentString.substring(0, 5).equals("block") && currentString.length() < 8) {
				break;
			}
		//	System.out.println("currentString:" + currentString);
		
			//determine the type of instruction
			if (currentString.endsWith(";")) { //normal instruction
				//add try
				Instruction tryInst = new Instruction(classNameIn, instNumIn, "try {", null);
				decodedInst.add(tryInst);
				instNumIn++;
				
				//add instruction
				Instruction inst = new Instruction(classNameIn, instNumIn, currentString, null);
				decodedInst.add(inst);
				instNumIn++;
				
				//add catch
				String catchString = "} catch (Exception e) {" + "}";
				Instruction catchInst = new Instruction(classNameIn, instNumIn, catchString, null);
				decodedInst.add(catchInst);
				instNumIn++;
			} else if(currentString.endsWith("tcblock" + codeBlockIn)) { //instruction with sub and no catch
				//source = "if(test==6)cblock0endtest=5;endblock0else if(test==5)tcblock0endtest=8;endblock0"
						//+ "elsetblock0endtest=9;endblock0";
				//add instruction
					//remove modifiers
					int sizeToRemove = 8 + (codeBlockIn/10);
					currentString = currentString.substring(sizeToRemove - 2, currentString.length() - sizeToRemove);
				Instruction inst = new Instruction(classNameIn, instNumIn, currentString, null);
				decodedInst.add(inst);
				instNumIn++;
				
				
				//add open bracket
				Instruction openInst = new Instruction(classNameIn, instNumIn, "{", null);
				decodedInst.add(openInst);
				instNumIn++;
				
				
				//add subcode
				instReader.useDelimiter("block" + codeBlockIn);
				String subcode = instReader.next();
					//remove modifiers
					subcode = subcode.substring(3, subcode.length());
				List<Instruction> subInsts = parseInstructions(subcode, classNameIn, instNumIn, codeBlockIn + 1);
				decodedInst.addAll(subInsts);
					
				//add close bracket
				Instruction closeInst = new Instruction(classNameIn, instNumIn, "}", null);
				decodedInst.add(closeInst);
				instNumIn++;
				
				//switch delimiter back
				instReader.useDelimiter("eNdBrEaK");
			} else if(currentString.endsWith("cblock" + codeBlockIn)) { //instruction with sub and no catch
				//add try
				Instruction tryInst = new Instruction(classNameIn, instNumIn, "try {", null);
				decodedInst.add(tryInst);
				instNumIn++;
				
				
				//add instruction
					//remove modifiers
					int sizeToRemove = 7 + (codeBlockIn/10);
					currentString = currentString.substring(0, currentString.length() - sizeToRemove);
				Instruction inst = new Instruction(classNameIn, instNumIn, currentString, null);
				decodedInst.add(inst);
				instNumIn++;
				
				
				//add open bracket
				Instruction openInst = new Instruction(classNameIn, instNumIn, "{", null);
				decodedInst.add(openInst);
				instNumIn++;
				

				//add subcode
				instReader.useDelimiter("block" + codeBlockIn);
				String subcode = instReader.next();
					//remove modifiers
					subcode = subcode.substring(3, subcode.length());
				List<Instruction> subInsts = parseInstructions(subcode, classNameIn, instNumIn, codeBlockIn + 1);
				decodedInst.addAll(subInsts);
					
				//add close bracket
				Instruction closeInst = new Instruction(classNameIn, instNumIn, "}", null);
				decodedInst.add(closeInst);
				instNumIn++;
				
				//switch delimiter back
				instReader.useDelimiter("eNdBrEaK");
			} else if (currentString.endsWith("tblock" + codeBlockIn)){ //instruction with sub and no try
				//add instruction
				int sizeToRemove = 7 + (codeBlockIn/10);
				currentString = currentString.substring(sizeToRemove - 1, currentString.length() - sizeToRemove);
			Instruction inst = new Instruction(classNameIn, instNumIn, currentString, null);
			decodedInst.add(inst);
			instNumIn++;
				//add open bracket
			Instruction openInst = new Instruction(classNameIn, instNumIn, "{", null);
			decodedInst.add(openInst);
			instNumIn++;
				//add subcode
			instReader.useDelimiter("block" + codeBlockIn);
			String subcode = instReader.next();
				//remove modifiers
				subcode = subcode.substring(3, subcode.length());
			List<Instruction> subInsts = parseInstructions(subcode, classNameIn, instNumIn, codeBlockIn + 1);
			decodedInst.addAll(subInsts);
				//add close bracket
			Instruction closeInst = new Instruction(classNameIn, instNumIn, "}", null);
			decodedInst.add(closeInst);
			instNumIn++;
				//add catch
				String catchString = "} catch (Exception e) {" + "}";
				Instruction catchInst = new Instruction(classNameIn, instNumIn, catchString, null);
				decodedInst.add(catchInst);
				instNumIn++;
				
				//switch delimiter back
				instReader.useDelimiter("eNdBrEaK");
			} else if(currentString.endsWith("block" + codeBlockIn)) { //instruction with sub (if without else)
				//add try
				Instruction tryInst = new Instruction(classNameIn, instNumIn, "try {", null);
				decodedInst.add(tryInst);
				instNumIn++;
				//add instruction
				int sizeToRemove = 6 + (codeBlockIn/10);
				currentString = currentString.substring(0, currentString.length() - sizeToRemove);
			Instruction inst = new Instruction(classNameIn, instNumIn, currentString, null);
			decodedInst.add(inst);
			instNumIn++;
				//add open bracket
				Instruction openInst = new Instruction(classNameIn, instNumIn, "{", null);
				decodedInst.add(openInst);
				instNumIn++;
				
				
				//add subcode
				instReader.useDelimiter("block" + codeBlockIn);
				String subcode = instReader.next();
					//remove modifiers
					subcode = subcode.substring(3, subcode.length());
				List<Instruction> subInsts = parseInstructions(subcode, classNameIn, instNumIn, codeBlockIn + 1);
				decodedInst.addAll(subInsts);
				
				
				//add close bracket
				Instruction closeInst = new Instruction(classNameIn, instNumIn, "}", null);
				decodedInst.add(closeInst);
				instNumIn++;
				
				
				//add catch
				String catchString = "} catch (Exception e) {" + "}";
				Instruction catchInst = new Instruction(classNameIn, instNumIn, catchString, null);
				decodedInst.add(catchInst);
				instNumIn++;
				
				//switch delimiter back
				instReader.useDelimiter("eNdBrEaK");
			} else { //error
				System.out.println("Code Block Error");
			}
		}
		
	
			

		instReader.close();
		return decodedInst;
	}
		//select belief with the highest total confidence level
	public void updateNeuralToHighestConf(LinkedList<BeliefConnector> beliefOutput, LinkedList<String> tagsIn) {
		BeliefConnector output = new BeliefConnector();
		int maxSum = 0;
		for (int i = 0; i < beliefOutput.size(); i++) {
			int currentSum = 0;
			for (int j = 0; j < beliefOutput.get(i).getTags().size(); j++) {
				if (tagsIn.contains(beliefOutput.get(i).getTags().get(j).getLabel())) {
					currentSum += beliefOutput.get(i).getTags().get(j).getConfidenceRating();
				}
				
			}
			if (currentSum > maxSum) {
				maxSum = currentSum;
				output = beliefOutput.get(i);
			}
		}
		this.NeuralPath = output.getNeuralPath();
		this.BegMylein = output.BegMylein;
		this.beliefFilePath = output.beliefFilePath;
		this.className = output.className;
		this.randActObj = output.randActObj;
		this.envHardcodedVariables = output.envHardcodedVariables;
		this.prevEnvHardcodedVariables = output.prevEnvHardcodedVariables;
	}
	
	
	//main method is for testing
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		BeliefConnector test = new BeliefConnector();
//		LinkedList<String> tagsIn = new LinkedList<String>();
//		tagsIn.add("Rect");
//		tagsIn.add("Alpacka");
//		tagsIn.add("TestingExtra");
//		test.updateNeuralToHighestConf(test.retrieveBeliefs(tagsIn), tagsIn);
//		System.out.println("Neural: " + test.getNeuralPath());
//		test.Fire("Behavior");
		test.setClassName("testClass");
		//modifiers: c = no catch | t = no try|   block# = subcode block value  | end = instruction finisher
		String source = "if(test==6)cblock0eNdBrEaKtest=5;eNdBrEaKblock0else if(test==5)tcblock0eNdBrEaKtest=8;eNdBrEaKblock0"
				+ "elsetblock0eNdBrEaKtest=9;eNdBrEaKblock0";
		//String source = "test=6;endtest=7;end";
		//String source = "if(test==6)block0endtest=5;endblock0";
		List<Instruction> testInsts = test.parseInstructions(source, "EXAMPLECLASSNAME", 0, 0);
		
		//printing out results to console
//		System.out.println(testInsts.size());
//		for (int i = 0; i < testInsts.size(); i++) {
//			System.out.println(testInsts.get(i).getParentClass() + testInsts.get(i).getInstructionNumber()
//					+ ": " + testInsts.get(i).getInstruction() + " | " + testInsts.get(i).getSubCodeBlock());
//		}
		
		
		//writing the code output to test file
		String testSource2 = "";
		for (int i = 0; i < testInsts.size(); i++) {
			testSource2 = testSource2 + testInsts.get(i).getInstruction() + "\n";
		}
		String path = test.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		path = path.substring(5);
		File testSource = new File(path + test.className + ".java");
		FileWriter writer = new FileWriter(testSource);
		writer.write(testSource2);
		writer.close();
	}
	
	//Neural Pathway Fire (Execution of belief based on stimuli)
	public void Fire(String fireType) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		//Write neural path to .java file
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		path = path.substring(5);
		File beliefSource = new File(path + this.className + ".java");
		FileWriter writer = new FileWriter(beliefSource);
		String pathVar = "String path = \"" + path + "\";";
		String writeErrorsCode = "for (int i = 0; i < errorLocationsVar.size(); i++) {File contextSource"
				+ "= new File(\"" + path + "contextSource.txt\"); FileWriter errorWriter = "
						+ "new FileWriter(contextSource, true); "
						+ "errorWriter.write(errorLocationsVar.get(i).getParentClass() "
						+ "+ \", \" + errorLocationsVar.get(i).getInstructionNumber() + \", \" "
						+ "+ errorLocationsVar.get(i).getInstruction());}";
		//the line below is only for testing/reference purposes
		//this.NeuralPath = "Runtime run = Runtime.getRuntime();String command = \"touch /home/agi/Desktop/eclipse/AGI/bin/hahaha.java\";run.exec(command);";
		if (fireType.equals("Sense")) {
			writer.write(this.BegMylein + this.envHardcodedVariables + pathVar + this.NeuralPath 
					+ this.SenseEndishMylein + writeErrorsCode + this.EndMylein);
		} else if (fireType.equals("Goal")){
			writer.write(this.BegMylein + this.envHardcodedVariables + pathVar 
					+ this.prevEnvHardcodedVariables + this.Goal2ndBegMylein + this.NeuralPath 
					+ "goalEvalResult.setClassName(\""+ this.className +"\");" + this.GoalEndishMylein 
					+ writeErrorsCode + this.EndMylein);
		} else {
			writer.write(this.BegMylein + this.NeuralPath + writeErrorsCode + this.EndMylein);
		}
		writer.close();
		//System.out.println("About to compile");
		//Compile it
		Runtime run = Runtime.getRuntime();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		compiler.run(null, null, null, beliefSource.getPath());
		//System.out.println("About to run");
		//Run it
		Class<?> cls = new DynamicClassLoader(beliefSource.getPath().substring(0, 33)).load(this.getClassName()); //beliefSource
		@SuppressWarnings("unused")
		Object instance = cls.newInstance();
//		URL[] url = new URL[] {beliefSource.toURI().toURL()};
//		URLClassLoader loader = new URLClassLoader(url);	
//
//			Class<?> cls = Class.forName(this.getClassName(), false, loader);
//			@SuppressWarnings("unused")
//			Object instance = cls.newInstance();
		
		
		//System.out.println("About to delete");
		//Delete the .java and .class files
		String command = "rm " + path + this.getClassName() + ".class";
		run.exec(command);
		command = "rm " + path + this.getClassName() + ".java";
		run.exec(command);
		
		
	}
	
}
