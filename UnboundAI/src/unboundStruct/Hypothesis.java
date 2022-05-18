package unboundStruct;
import java.util.*;
import java.io.*;
public class Hypothesis {
	public File actionFile;
	public String actionName;
	public List<Rating> ratings;
	public Hypothesis() {
		this.actionName = "action not set";
		this.ratings = new ArrayList<Rating>();
	}
	public Hypothesis(String actionFilePath, String actionNameIn, List<Rating> ratingsIn) {
		this.actionFile = new File(actionFilePath);
		this.actionName = actionNameIn;
		this.ratings = ratingsIn;
	}
	public Hypothesis(String actionFilePath, String actionNameIn) {
		this.actionFile = new File(actionFilePath);
		this.actionName = actionNameIn;
	}
	public void initRatings() {
		//get all existing goal files and make a rating of 0 for each one
		File goalDir = new File("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Goals");
		File errorGoalDir = new File("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/ErrorGoal");
		String[] goalPaths = goalDir.list();
		String[] errorGoalPath = errorGoalDir.list();
		List<String> paths = new ArrayList<String>();
		for (int i = 0; i < goalPaths.length; i++) {
			paths.add("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Goals/" + goalPaths[i]);
		}
		for (int i = 0; i < errorGoalPath.length; i++) {
			paths.add("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/ErrorGoal/" + errorGoalPath[i]);
		}
		List<Rating> newRatings = new ArrayList<Rating>();
		for (int i = 0; i < paths.size(); i++) {
			File goal = new File(paths.get(i));
			String goalName = goal.getName();
			int ratingValue = 0;
			Rating rating = new Rating(paths.get(i), goalName, ratingValue);
			newRatings.add(rating);
		}
		this.ratings = newRatings;
	}
	public void updateRatings(List<Rating> ratingsIn) {
		//use the ratings in to update the appropriate ratings
		for (int i = 0; i< ratingsIn.size(); i++) {
			Rating currentRating = ratingsIn.get(i);
			if (this.ratings.contains(currentRating)) {
				this.ratings.get(this.ratings.indexOf(currentRating)).rating = this.ratings.get(this.ratings.indexOf(currentRating)).rating 
						+ currentRating.rating;
			} else {
				this.ratings.add(currentRating);
			}
		}
	}
}
