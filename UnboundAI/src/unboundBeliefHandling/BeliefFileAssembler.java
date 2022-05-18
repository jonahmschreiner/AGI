package unboundBeliefHandling;
import java.util.*;
import unboundStruct.*;
public class BeliefFileAssembler {
	public static String assembleGoal(Belief beliefIn, Env currentEnv, Env prevEnv) throws Exception {
		if (beliefIn.beliefType != 0) {
			return "Wrong Belief Type Passed Into AssembleGoal: " + beliefIn.beliefType;
		}
		String neuralPath = "";
		//add in initial myelin
		//imports
		neuralPath = neuralPath + "import java.io.*;\nimport java.util.*;\nimport unboundStruct.*;\n"
				+ "import unboundBeliefHandling.*;\n";
		//class declaration
		neuralPath = neuralPath + "public class " + beliefIn.beliefName + "{ public " + beliefIn.beliefName
				+ "() throws Exception {\n";
		//add in environment/util vars for this type of belief
		neuralPath = neuralPath + "String path = \"/home/agi/Desktop/eclipse/UnboundAI/bin\";\n";
		neuralPath = neuralPath + currentEnv.getEnvVarsString();
		neuralPath = neuralPath + prevEnv.getPrevEnvVarsString();
		neuralPath = neuralPath + "unboundStruct.GoalResult goalEvalResult = new unboundStruct.GoalResult();\n";
		//add in instructions
		for (int i = 0; i < beliefIn.instructions.size(); i++) {
			neuralPath = neuralPath + beliefIn.instructions.get(i).instruction +"\n";
		}		
		//add in ending myelin for this type of belief
		neuralPath = neuralPath + "goalEvalResult.setClassName(\"" + beliefIn.beliefName + "\");\n"
				+ "File goalResult = new File(path + \"/GoalEvalResult.java\");\n"
				+ "FileWriter writer2 = new FileWriter(goalResult, true);\nwriter2.write(goalEvalResult.toString());\nwriter2.close();\n";		
		//add in ending myelin that is used in all types of beliefs
		neuralPath = neuralPath + "}\n}";	
		return neuralPath;
	}
	public static String assembleSense(Belief beliefIn, Env currentEnv, Env prevEnv) throws Exception {
		if (beliefIn.beliefType != 1) {
			return "Wrong Belief Type Passed Into AssembleSense: " + beliefIn.beliefType;
		}
		String neuralPath = "";
		//add in initial myelin
		//imports
		neuralPath = neuralPath + "import java.io.*;\nimport java.util.*;\nimport unboundStruct.*;\n"
				+ "import unboundBeliefHandling.*;\n";
		//class declaration
		neuralPath = neuralPath + "public class " + beliefIn.beliefName + "{ public " + beliefIn.beliefName
				+ "() throws Exception {\n";
		//add in environment/util vars for this type of belief
		neuralPath = neuralPath + "String path = \"/home/agi/Desktop/eclipse/UnboundAI/bin\";\n";
		neuralPath = neuralPath + currentEnv.getEnvVarsString();
		neuralPath = neuralPath + "unboundStruct.Sense sense = new unboundStruct.Sense();\nsense.label = \""+ beliefIn.beliefName + "\";\n";
		neuralPath = neuralPath + "sense.properties = new java.util.ArrayList<unboundStruct.Property>();\n";
		neuralPath = neuralPath + "java.util.List<unboundStruct.Property> properties = new java.util.ArrayList<unboundStruct.Property>();\n";
		//add in instructions
		for (int i = 0; i < beliefIn.instructions.size(); i++) {
			neuralPath = neuralPath + beliefIn.instructions.get(i).instruction +"\n";
		}		
		//add properties to list as determined by rewrite block
		neuralPath = neuralPath + "for (int propsCount = 0; propsCount < properties.size(); propsCount++){\n"
				+ "sense.properties.add(properties.get(propsCount));\n}\n";
		//add in ending myelin for this type of belief
		neuralPath = neuralPath + "File tagsCalled = new File(path + \"/tagsCalled.java\");\n"
				+ "FileWriter writer2 = new FileWriter(tagsCalled, true);\nwriter2.write(tagsRecursivelyCalled);\n"
				+ "writer2.close();\n";	
		neuralPath = neuralPath + "unboundBeliefHandling.SenseWriter.write(sense);\n";
		//add in ending myelin that is used in all types of beliefs
		neuralPath = neuralPath + "}\n}";	
		return neuralPath;
	}	
	public static String assembleAction(Belief beliefIn, Env currentEnv, Env prevEnv) throws Exception { //add util object instance to util/env vars
		if (beliefIn.beliefType != 2) {
			return "Wrong Belief Type Passed Into AssembleAction: " + beliefIn.beliefType;
		}
		String neuralPath = "";
		//add in initial myelin
		//imports
		neuralPath = neuralPath + "import java.io.*;\nimport java.util.*;\nimport unboundStruct.*;\n"
				+ "import unboundBeliefHandling.*;\n";
		//class declaration
		neuralPath = neuralPath + "public class " + beliefIn.beliefName + "{ public " + beliefIn.beliefName
				+ "() throws Exception {\n";
		//add in environment/util vars for this type of belief
		neuralPath = neuralPath + "String path = \"/home/agi/Desktop/eclipse/UnboundAI/bin\";\n";
		neuralPath = neuralPath + currentEnv.getEnvVarsString();
		//add in instructions
		for (int i = 0; i < beliefIn.instructions.size(); i++) {
			neuralPath = neuralPath + beliefIn.instructions.get(i).instruction +"\n";
		}	
		//add in ending myelin for this type of belief
		
		//add in ending myelin that is used in all types of beliefs
		neuralPath = neuralPath + "}\n}";		
		return neuralPath;
	}
	public static void main(String[] args) throws Exception {
		List<Instruction> insts = new ArrayList<Instruction>();
		Instruction inst = new Instruction("TestBelief", 0, "int i = 0;");
		insts.add(inst);
		List<KeyTag> keyTags = new ArrayList<KeyTag>();
		KeyTag tag = new KeyTag("Rect", 50);
		keyTags.add(tag);
		Belief belief = new Belief("TestBelief", 1, insts, keyTags);
		SenseEnv sense = new SenseEnv();
		Env currentEnv = sense.recordEnv();
		Env prevEnv = currentEnv;
		System.out.println(BeliefFileAssembler.assembleSense(belief, currentEnv, prevEnv));
	}
}
