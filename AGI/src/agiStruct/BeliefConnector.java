package agiStruct;
import agiStruct.KeyTag;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

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

				List<Instruction> instructions = parseInstructions(lineReader.next(), belief.getClassName(), 0, 0);
				String neural = "";
				if (instructions != null) {
					for(int i = 0; i < instructions.size(); i++) {
						//System.out.println("INST: " + instructions.get(i).getInstruction());
						neural = neural + instructions.get(i).getInstruction();
						neural = neural + "\n";
					}
				}
				//System.out.println("Neural: " + neural);
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
	
	public boolean isAsciiPrintable(char c) {
	    Character.UnicodeBlock block = Character.UnicodeBlock.of( c );
	    return (!Character.isISOControl(c)) &&
	            c != KeyEvent.CHAR_UNDEFINED &&
	            block != null &&
	            block != Character.UnicodeBlock.SPECIALS;
	}
	
	public String sanitize(String input) {
		String output = "";
		if (input != null) {
			Scanner scan = new Scanner(input);
			scan.useDelimiter("\"");
			if (input.contains("\"")) {
				while(scan.hasNext()) {
					String addition = scan.next();
					if (addition.contains("\"")) {
						output = output + addition + "\"";
					} else {
						output = output + addition;
					}
					
				}
			} else {
				output = input;
			}

			scan.close();
			return output;
		} else {
			return null;
		}
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
			if (currentString.substring(0, 5).equals("block") && currentString.length() < 8) {
				break;
			}
			//remove excess shit
			while (!Character.isLetterOrDigit(currentString.charAt(0))) {
				currentString = currentString.substring(1);
			}
		
			//determine the type of instruction
			if (currentString.endsWith(";")) { //normal instruction

				
				//check for and remove block# leftovers
				if (currentString.substring(0, 5).equals("block")) {
					currentString = currentString.substring(6);
				}
				//remove excess white space
				while (!isAsciiPrintable(currentString.charAt(0)) || currentString.charAt(0) == ' ') {
					currentString = currentString.substring(1);
				}

				
				//add instruction
				if (currentString.substring(0, 5).equals("block")) {
					currentString = currentString.substring(6);
				}
			//	System.out.println("CURSTR: " + currentString);
				Instruction inst = new Instruction(classNameIn, instNumIn, currentString, null);
				decodedInst.add(inst);
				instNumIn++;
				
			}else if(currentString.endsWith("block" + codeBlockIn)) { //instruction with sub (if without else)
				//add instruction
				int sizeToRemove = 6 + (codeBlockIn/10);
				currentString = currentString.substring(0, currentString.length() - sizeToRemove);
		//	System.out.println("CURSTR: " + currentString);
			Instruction inst = new Instruction(classNameIn, instNumIn, currentString, null);
		//	System.out.println("BinstParentClass: " + inst.getParentClass());
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
					subcode = subcode.substring(8, subcode.length());
					//remove excess shit
					while (!Character.isLetterOrDigit(subcode.charAt(0))) {
						subcode = subcode.substring(1);
					}
			//		System.out.println("Subcode: " + subcode);
				List<Instruction> subInsts = parseInstructions(subcode, classNameIn, instNumIn, codeBlockIn + 1);
				decodedInst.addAll(subInsts);
				
				
				//add close bracket
				Instruction closeInst = new Instruction(classNameIn, instNumIn, "}", null);
				decodedInst.add(closeInst);
				instNumIn++;
				
				//switch delimiter back
				instReader.useDelimiter("eNdBrEaK");
			} else { //error
			//	System.out.println("Code Block Error");
			}
		}
		
		//System.out.println("exited");
			

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
//		this.envHardcodedVariables = output.envHardcodedVariables;
//		this.prevEnvHardcodedVariables = output.prevEnvHardcodedVariables;
	}
	
	
	//main method is for testing
	public static void main(String[] args) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException{
		BeliefConnector test = new BeliefConnector();
		List<Instruction> instructionsIn = new ArrayList<Instruction>();
		Instruction inst1 = new Instruction("EXAMPLECLASSNAME", 0, "int i = 1;", null);
		instructionsIn.add(inst1);
		test.setInstructions(instructionsIn);
		test.Fire("");
	}
	
	//Neural Pathway Fire (Execution of belief based on stimuli)
	public void Fire(String fireType) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		//Write neural path to .java file
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		path = path.substring(5);
		File beliefSource = new File(path + this.className + ".java");
		FileWriter writer = new FileWriter(beliefSource);
		String pathVar = "String path = \"" + path + "\";";
		String writeErrorsCode = "for (int i = 0; i < currentErrorLocations.size(); i++) {File contextSource"
				+ "= new File(\"" + path + "contextSource.txt\"); FileWriter errorWriter = "
						+ "new FileWriter(contextSource, true); "
						+ "errorWriter.write(currentErrorLocations.get(i).getParentClass() "
						+ "+ \", \" + currentErrorLocations.get(i).getInstructionNumber() + \", \" "
						+ "+ currentErrorLocations.get(i).getInstruction());}";
		//the line below is only for testing/reference purposes
		//this.NeuralPath = "Runtime run = Runtime.getRuntime();String command = \"touch /home/agi/Desktop/eclipse/AGI/bin/hahaha.java\";run.exec(command);";
		if (fireType.equals("Sense")) {
			writer.write(this.BegMylein + this.envHardcodedVariables + pathVar + this.NeuralPath 
					+ this.SenseEndishMylein + writeErrorsCode + this.EndMylein);
		} else if (fireType.equals("Goal")){
			writer.write(sanitize(this.BegMylein) + sanitize(this.envHardcodedVariables) + sanitize(pathVar) 
					+ sanitize(this.prevEnvHardcodedVariables) + sanitize(this.Goal2ndBegMylein) + sanitize(this.NeuralPath) 
					+ "goalEvalResult.setClassName(\""+ this.className +"\");" + sanitize(this.GoalEndishMylein) 
					+ sanitize(writeErrorsCode) + sanitize(this.EndMylein));
		} else {
			writer.write(this.BegMylein + this.envHardcodedVariables + pathVar + this.NeuralPath + writeErrorsCode + this.EndMylein);
		}
		writer.close();

		Runtime run = Runtime.getRuntime();
		JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
		try {
		//	System.out.println("About to compile");
			//Compile it
			String errorString = "";
			File errorLogFile = new File(path + "errorLog.txt");
			FileWriter errorClearer = new FileWriter(errorLogFile, false);
			errorClearer.write("");
			errorClearer.close();
			OutputStream errOutStream = new FileOutputStream(errorLogFile);
			compiler.run(null, null, errOutStream, beliefSource.getPath());
			Scanner errorScan = new Scanner(errorLogFile);
			if (errorScan.hasNext()) {
			
				//write out goal eval result if necessary
				if (fireType.equals("Goal")) {
					File goalResult = new File(path + "GoalEvalResult.java");
					FileWriter writer2 = new FileWriter(goalResult, false);
					GoalResult errorGoalResult = new GoalResult(this.className,-100,false);
					writer2.write(errorGoalResult.toString());
					writer2.close();
				}
				
				
				//parse error log to get an error Instruction and connect it to LLF
				
				//what do I need? className, instructionNum, instruction
				errorScan.useDelimiter("/");
				String section = "";
				while (errorScan.hasNext()) {
					section = errorScan.next();
					if (section.contains(".")) {
						break;
					}
				}
				System.out.println("sect: " + section);
				Scanner scanSection = new Scanner(section);
				scanSection.useDelimiter(".j");
				String className = scanSection.next();
				System.out.println("className: " + className);
				//instructionNum (line num to start and then add code to convert to instructionNum
				scanSection.useDelimiter(":");
				scanSection.next();
				int lineNumber = scanSection.nextInt();
				
				
				//instruction
				scanSection.useDelimiter("\n");
				scanSection.nextLine();
				scanSection.useDelimiter("\n^");
				String instruction = "";
				instruction = instruction + scanSection.next();
				System.out.println("INST: " + instruction);
				
				File contextSource = new File(path + "errorContext.txt");
				FileWriter writer2 = new FileWriter(contextSource, false);
				writer2.write(className + ":" + lineNumber + ":" + instruction + ",");
				writer2.close();
				System.out.println("error found and read in: " + className + ":" 
				+ lineNumber + ":" + instruction + ":");
				
				scanSection.close();
				
			} else {
			//	System.out.println("About to run");
				//Run it
				Class<?> cls = new DynamicClassLoader(beliefSource.getPath().substring(0, 33)).load(this.getClassName()); //beliefSource
				@SuppressWarnings("unused")
				Object instance = cls.newInstance();
			}

			errorScan.close();
			
		} catch (Exception e) {
			//String trace = e.toString();
			System.out.println("etoString: " + e.toString());
			System.out.println("egetMessage: " + e.getMessage());
			System.out.println("egetLocalizedMessage: " + e.getLocalizedMessage());
		}
		


		//System.out.println("About to delete");
		//Delete the .java and .class files
		String command = "rm " + path + this.getClassName() + ".class";
		run.exec(command);
		command = "rm " + path + this.getClassName() + ".java";
		run.exec(command);
		
		
	}
	
}
