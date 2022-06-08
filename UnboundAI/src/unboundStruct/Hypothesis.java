package unboundStruct;
import java.util.*;
import java.io.*;
public class Hypothesis {
	public File actionFile;
	public String actionName;
	public List<Rating> ratings;
	public List<SenseRating> senseRatings;
	public Hypothesis() {
		this.actionName = "action not set";
	}
	public Hypothesis(String actionFilePath, String actionNameIn, List<Rating> ratingsIn, List<SenseRating> senseRatingsIn) {
		this.actionFile = new File(actionFilePath);
		this.actionName = actionNameIn;
		this.ratings = ratingsIn;
		this.senseRatings = senseRatingsIn;
	}
	public Hypothesis(String actionFilePath, String actionNameIn, Context context) {
		this.actionFile = new File(actionFilePath);
		this.actionName = actionNameIn;
		initRatings(context);
	}
	public void initRatings(Context context) {
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
		
		//sense ratings
		File senseDir = new File("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Senses");
		String[] sensePaths1 = senseDir.list();
		List<String> sensePaths = new ArrayList<String>();
		for (int i = 0; i < goalPaths.length; i++) {
			sensePaths.add("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Senses/" + sensePaths1[i]);
		}
		
		List<SenseRating> newSenseRatings = new ArrayList<SenseRating>();
		for (int i = 0; i < sensePaths.size(); i++) {
			File sense = new File(sensePaths.get(i));
			String senseName = sense.getName();
			int ratingValue = 0;
			Sense compareSense = new Sense();
			compareSense.label = senseName;
			if (context.env.abstractEnv.contains(compareSense)) {
				List<PropRating> propRatings = new ArrayList<PropRating>();
				for (int j = 0; j < context.env.abstractEnv.get(context.env.abstractEnv.indexOf(compareSense)).properties.size(); j++) {
					String pName = context.env.abstractEnv.get(context.env.abstractEnv.indexOf(compareSense)).properties.get(j).propLabel;
					String pValue = context.env.abstractEnv.get(context.env.abstractEnv.indexOf(compareSense)).properties.get(j).valuesString;
					Sense parentSense = context.env.abstractEnv.get(context.env.abstractEnv.indexOf(compareSense));
					PropRating pRate = new PropRating(pName, pValue, ratingValue, parentSense);
					propRatings.add(pRate);
				}
			
			SenseRating rating = new SenseRating(sensePaths.get(i), senseName, ratingValue, propRatings);
			newSenseRatings.add(rating);
			}
		}
		this.senseRatings = newSenseRatings;
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
	public void updateSenseRatings(List<SenseRating> ratingsIn) {
		//use the ratings in to update the appropriate ratings
		for (int i = 0; i< ratingsIn.size(); i++) {
			SenseRating currentRating = ratingsIn.get(i);
			if (this.senseRatings.contains(currentRating)) {
				//switch if to loop to identify all SenseRating objects that are "equal" to the name of the senseRating (as they can have different
				//properties
				List<SenseRating> copyOfSenseRatings = this.senseRatings;
				List<SenseRating> instsOfRelRatings = new ArrayList<SenseRating>();
				while (copyOfSenseRatings.contains(currentRating)) {
					SenseRating inst = copyOfSenseRatings.get(copyOfSenseRatings.indexOf(currentRating));
					instsOfRelRatings.add(inst);
					copyOfSenseRatings = this.senseRatings.subList(copyOfSenseRatings.indexOf(currentRating), this.senseRatings.size());
				}			
				for (int k = 0; k < instsOfRelRatings.size(); k++) {
					SenseRating matchRating = instsOfRelRatings.get(k);
					int successfulMatches = 0;
					for (int j = 0; j < matchRating.propRatings.size(); j++) {
						if (matchRating.propRatings.contains(currentRating.propRatings.get(j))) { //if the property exists in both rating lists
							if (matchRating.propRatings.get(matchRating.propRatings.indexOf(currentRating.propRatings.get(j))).propValue.equals(currentRating.propRatings.get(j).propValue)) { //if the property values match in both rating lists
								//prop exists and value is the same in both senseRatings
								successfulMatches = successfulMatches + 1;
							}
						} else {
							//add new senseRating to this.senseRatings
							this.senseRatings.add(currentRating);
						}
					}
					if (successfulMatches == currentRating.propRatings.size()) { //if all properties matched, update rating of senseRating
						this.senseRatings.get(this.senseRatings.indexOf(currentRating)).rating = this.senseRatings.get(this.senseRatings.indexOf(currentRating)).rating 
								+ currentRating.rating;
						break;
					}
				}
			} else {
				this.senseRatings.add(currentRating);
			}
		}
	}
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Hypothesis){
			Hypothesis c = (Hypothesis) o;
			boolean output = false;
			if (c.actionName.equals(this.actionName)){
				output = true;
			} else {
				output = false;
			}
			return output;
		} else {
			return false;
		}	
	}
}