package unboundBeliefRetrievers;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ErrorGoalRetriever {
	public static List<String> retrieve(List<String> tagsIn) throws FileNotFoundException{
		List<String> beliefPaths = new ArrayList<String>();
		String beliefDirPath = "/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/ErrorGoal";
		File directory = new File(beliefDirPath);
		String[] pathnames = directory.list();
		for (String pathname : pathnames) {
			File source = new File(beliefDirPath + "/" + pathname);
			Scanner reader = new Scanner(source);
			reader.useDelimiter(",,,");
			String tagString = reader.next();
			reader.close();
			Scanner tagReader = new Scanner(tagString);
			while (tagReader.hasNext()) {
				tagReader.useDelimiter(":");
				String tag = tagReader.next();
				if (tag.contains(" ")) {
					tag = tag.substring(1);
				}
				if (tagsIn.contains(tag)) {
					beliefPaths.add(beliefDirPath + "/" + pathname);
					break;
				}
				tagReader.useDelimiter(" ");
				tagReader.next();
			}
			tagReader.close();
		}
		
		return beliefPaths;
	}
}
