package unboundTopLevel;
import java.util.*;

import unboundBeliefHandling.BeliefFileAssembler;
import unboundBeliefHandling.BeliefParser;
import unboundBeliefHandling.FileCompilerRunner;
import unboundBeliefRetrievers.ActionRetriever;
import unboundStruct.*;
public class ExecAction {
	public static String exec(String actionName, Env prevEnv, Context context) throws Exception{
		String actionPath = ActionRetriever.retrieve(actionName);
		List<String> beliefPaths = new ArrayList<String>();
		beliefPaths.add(actionPath);
		List<Belief> actions = BeliefParser.parseBeliefs(beliefPaths, 2);
		for (int i = 0; i < actions.size(); i++) {
			String source = BeliefFileAssembler.assembleAction(actions.get(i), context.env, prevEnv);
			FileCompilerRunner.compile(source, actions.get(i).beliefName, context);
		}		
		return actionName;
	}
}
