package unboundBeliefHandling;
import unboundStruct.*;

import java.io.File;
import java.util.*;
public class InitializeNewHypothesi {
	public static List<Hypothesis> init(){
		List<Hypothesis> hypos = new ArrayList<Hypothesis>();
		File actionDir = new File("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Actions");
		String[] actionPaths = actionDir.list();
		for (int i = 0; i < actionPaths.length; i++) {
			hypos.add(new Hypothesis("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Actions/" + actionPaths[i], actionPaths[i]));
		}
		return hypos;
	}
}
