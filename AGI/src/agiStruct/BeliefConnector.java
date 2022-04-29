package agiStruct;
import agiStruct.KeyTag;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import qj.util.lang.DynamicClassLoader;
import AGIUtil.randomAction;
public class BeliefConnector {
	private LinkedList<KeyTag> tags = new LinkedList<KeyTag>();
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
				//read in neural path (dynamic code)
				belief.setNeuralPath(lineReader.next());
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
		LinkedList<String> tagsIn = new LinkedList<String>();
		tagsIn.add("Rect");
		tagsIn.add("Alpacka");
		tagsIn.add("TestingExtra");
		test.updateNeuralToHighestConf(test.retrieveBeliefs(tagsIn), tagsIn);
		System.out.println("Neural: " + test.getNeuralPath());
		test.Fire("Behavior");
	}
	
	//Neural Pathway Fire (Execution of belief based on stimuli)
	public void Fire(String fireType) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException {
		//Write neural path to .java file
		String path = this.getClass().getProtectionDomain().getCodeSource().getLocation().toString();
		path = path.substring(5);
		File beliefSource = new File(path + this.className + ".java");
		FileWriter writer = new FileWriter(beliefSource);
		String pathVar = "String path = \"" + path + "\";";
		//the line below is only for testing/reference purposes
		//this.NeuralPath = "Runtime run = Runtime.getRuntime();String command = \"touch /home/agi/Desktop/eclipse/AGI/bin/hahaha.java\";run.exec(command);";
		if (fireType.equals("Sense")) {
			writer.write(this.BegMylein + this.envHardcodedVariables + pathVar + this.NeuralPath + this.SenseEndishMylein + this.EndMylein);
		} else if (fireType.equals("Goal")){
			writer.write(this.BegMylein + this.envHardcodedVariables + pathVar + this.prevEnvHardcodedVariables + this.Goal2ndBegMylein + this.NeuralPath + "goalEvalResult.setClassName(\""+ this.className +"\");" + this.GoalEndishMylein + this.EndMylein);
		} else {
			writer.write(this.BegMylein + this.NeuralPath + this.EndMylein);
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
