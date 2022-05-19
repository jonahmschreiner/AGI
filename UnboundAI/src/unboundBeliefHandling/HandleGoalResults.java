package unboundBeliefHandling;

import java.util.*;

import unboundStruct.*;

public class HandleGoalResults {
	public static List<Rating> handle(List<GoalResult> resultsIn) {
		List<Rating> ratings = new ArrayList<Rating>();
		for (int i = 0; i < resultsIn.size(); i++) {
			GoalResult currResult = resultsIn.get(i);
			if (currResult.className.equals("HardwiredAvoidErrors")) {
				Rating rating = new Rating("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/ErrorGoal/" + currResult.className, currResult.className, currResult.result);
				ratings.add(rating);
			} else {
				Rating rating = new Rating("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Goals/" + currResult.className, currResult.className, currResult.result);
				ratings.add(rating);
			}
		}
		return ratings;
	}
}
