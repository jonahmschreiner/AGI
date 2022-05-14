package unboundBeliefRetrievers;

import java.io.*;
public class ActionRetriever {
	public static String retrieve(String actionIn) throws FileNotFoundException{
		String beliefDirPath = "/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Actions";
		String beliefPath = beliefDirPath + "/";
		File directory = new File(beliefDirPath);
		String[] pathnames = directory.list();
		for (String pathname : pathnames) {
			if (pathname.equals(actionIn)) {
				beliefPath = beliefPath + pathname;
				break;
			}
		}	
		return beliefPath;
	}
}
