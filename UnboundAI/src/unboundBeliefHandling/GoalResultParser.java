package unboundBeliefHandling;
import java.io.*;
import java.util.*;
import unboundStruct.*;
public class GoalResultParser {
	public static List<GoalResult> parseResults() throws Exception{
		File resultFile = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/GoalEvalResult.java");
		Scanner scan = new Scanner(resultFile);
		scan.useDelimiter(":");
		List<GoalResult> results = new ArrayList<GoalResult>();
		while (scan.hasNext()) {
			String goalName = scan.next();
			if (scan.hasNextInt()) {
				int goalResult = scan.nextInt();
				if (scan.hasNext()) {
					boolean goalComplete = Boolean.valueOf(scan.next());
					GoalResult result = new GoalResult(goalName, goalResult, goalComplete);
					results.add(result);
				}
			}			
		}
		scan.close();
		return results;
	}
}
