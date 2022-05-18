package unboundTopLevel;

import java.util.*;

import unboundBeliefHandling.BeliefFileAssembler;
import unboundBeliefHandling.BeliefParser;
import unboundBeliefHandling.FileCompilerRunner;
import unboundBeliefHandling.SenseParser;
import unboundBeliefRetrievers.SenseRetriever;
import unboundStruct.*;
public class ExecSenses {
	public static List<Sense> exec(Context context, Context prevContext) throws Exception {
		List<String> tagsIn = new ArrayList<String>();
		tagsIn.add("Sense");
		List<String> sensePaths = SenseRetriever.retrieve(tagsIn);
		List<Sense> senses = new ArrayList<Sense>();
		List<Belief> currBeliefs = BeliefParser.parseBeliefs(sensePaths, 1);
		Runtime run = Runtime.getRuntime();
		String command = "touch /home/agi/Desktop/eclipse/UnboundAI/bin/sense.txt";
		run.exec(command);
		for (int j = 0; j < currBeliefs.size(); j++) {
			String source = BeliefFileAssembler.assembleSense(currBeliefs.get(j), context.env, prevContext.env);
			FileCompilerRunner.compile(source, currBeliefs.get(j).beliefName, context);
		}
		
		senses = SenseParser.parse("/home/agi/Desktop/eclipse/UnboundAI/bin/sense.txt");
		command = "rm /home/agi/Desktop/eclipse/UnboundAI/bin/sense.txt";
		run.exec(command);    
		return senses;
	}
}
