package unboundTopLevel;
import unboundBeliefRetrievers.GoalRetriever;
import unboundBeliefHandling.*;
import unboundStruct.*;
import java.util.*;
import java.io.*;
public class ExecGoals {
	public static void exec(Env currentEnv, Env prevEnv, Context contextIn) throws Exception{
		File goalResult = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/GoalEvalResult.java");
		FileWriter writer2 = new FileWriter(goalResult, false);
		writer2.write("");
		writer2.close();
		List<String> tagsIn = new ArrayList<String>();
		tagsIn.add("Goal");
		List<String> goalPaths = GoalRetriever.retrieve(tagsIn);
		List<Belief> goalBeliefs = BeliefParser.parseBeliefs(goalPaths, 0);
		for (int i = 0; i < goalBeliefs.size(); i++) {
			String source = BeliefFileAssembler.assembleGoal(goalBeliefs.get(i), currentEnv, prevEnv);
			FileCompilerRunner.compile(source, goalBeliefs.get(i).beliefName, contextIn);
		}
	}
}
