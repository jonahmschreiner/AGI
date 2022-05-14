package unboundTopLevel;

import java.util.ArrayList;
import java.util.List;

import unboundBeliefHandling.*;
import unboundBeliefRetrievers.ErrorGoalRetriever;
import unboundStruct.*;

public class ExecErrorGoals {
	public static int exec(Env currentEnv, Env prevEnv, Context contextIn) throws Exception{
		List<String> tagsIn = new ArrayList<String>();
		tagsIn.add("Goal");
		List<String> goalPaths = ErrorGoalRetriever.retrieve(tagsIn);
		List<Belief> goalBeliefs = BeliefParser.parseBeliefs(goalPaths, 0);
		for (int i = 0; i < goalBeliefs.size(); i++) {
			String source = BeliefFileAssembler.assembleGoal(goalBeliefs.get(i), currentEnv, prevEnv);
			FileCompilerRunner.compile(source, goalBeliefs.get(i).beliefName, contextIn);
		}
		List<GoalResult> goalResults = GoalResultParser.parseResults();
		Runtime run = Runtime.getRuntime();
		String command = "rm /home/agi/Desktop/eclipse/UnboundAI/bin/GoalEvalResult.java";
		run.exec(command);
		int satisfaction = GoalResultCombiner.combine(goalResults);
		return satisfaction;
	}
}
