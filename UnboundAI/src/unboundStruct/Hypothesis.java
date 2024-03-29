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
	//following constructor is for testing, please delete after testing is completed
	public Hypothesis(List<SenseRating> senseRatingsIn) {
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
				//properties
				List<SenseRating> instsOfRelRatings = new ArrayList<SenseRating>();
				for (int l = 0; l < this.senseRatings.size(); l++) {
					SenseRating inst = this.senseRatings.get(l);
					if (inst.senseName.equals(currentRating.senseName)) {
						instsOfRelRatings.add(inst);
					}
				}
				try {
					boolean added = false;
					for (int k = 0; k < instsOfRelRatings.size(); k++) {
						SenseRating matchRating = instsOfRelRatings.get(k);
						int successfulMatches = 0;
						for (int j = 0; j < currentRating.propRatings.size(); j++) {
							if (matchRating.propRatings.contains(currentRating.propRatings.get(j))) { //if the property exists in both rating lists
								if (matchRating.propRatings.get(matchRating.propRatings.indexOf(currentRating.propRatings.get(j))).propValue.equals(currentRating.propRatings.get(j).propValue)) { //if the property values match in both rating lists
									//prop exists and value is the same in both senseRatings
									successfulMatches = successfulMatches + 1;
								} else {
									this.senseRatings.add(currentRating);
								}
							} else {
								//add new senseRating to this.senseRatings
								this.senseRatings.add(currentRating);
							}
						}
						if (successfulMatches == currentRating.propRatings.size() && successfulMatches == matchRating.propRatings.size()) { //if all properties matched, update rating of senseRating
							this.senseRatings.get(this.senseRatings.indexOf(currentRating)).rating = this.senseRatings.get(this.senseRatings.indexOf(currentRating)).rating 
									+ currentRating.rating;
							added = true;
							break;
						}
					}
					if (!added) {
						this.senseRatings.add(currentRating);
					}
				} catch (Exception e) {
					this.senseRatings.add(currentRating);
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
	//main is for testing, can prob delete
	public static void main (String[] args) {
		//set up existing SenseRatings in the hypo
		List<SenseRating> sRatings = new ArrayList<SenseRating>();
		Sense sense1 = new Sense();
		List<PropRating> s1PropRatings = new ArrayList<PropRating>();
		PropRating s1PropRating1 = new PropRating("Size", "1", 2, sense1);
		s1PropRatings.add(s1PropRating1);
		PropRating s1PropRating2 = new PropRating("Test", "4", 2, sense1);
		s1PropRatings.add(s1PropRating2);
		Sense sense2 = new Sense();
		List<PropRating> s2PropRatings = new ArrayList<PropRating>();
		PropRating s2PropRating1 = new PropRating("Size", "1", 2, sense2);
		PropRating s2PropRating2 = new PropRating("Color", "3", 1, sense2);
		s2PropRatings.add(s2PropRating1);
		s2PropRatings.add(s2PropRating2);
		Sense sense3 = new Sense();
		List<PropRating> s3PropRatings = new ArrayList<PropRating>();
		PropRating s3PropRating1 = new PropRating("Size", "1", 2, sense3);
		s3PropRatings.add(s3PropRating1);
		SenseRating senseR1 = new SenseRating("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Senses/TestBelief", "TestBelief", 0, s1PropRatings);
		SenseRating senseR2 = new SenseRating("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Senses/TestBelief2", "TestBelief2", 0, s2PropRatings);
		SenseRating senseR3 = new SenseRating("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Senses/TestBelief3", "TestBelief3", 0, s3PropRatings);
		sRatings.add(senseR1);
		sRatings.add(senseR2);
		sRatings.add(senseR3);
		Hypothesis hypo = new Hypothesis(sRatings);
		System.out.println("Before: ");
		for (int i = 0; i < hypo.senseRatings.size(); i++) {
			System.out.println(hypo.senseRatings.get(i).senseName + " " + hypo.senseRatings.get(i).propRatings.size() + ": " 
					+ hypo.senseRatings.get(i).rating);
		}
		//set up sense ratings used to update existing sense ratings
		List<SenseRating> inputSRatings = new ArrayList<SenseRating>();
		Sense iSense1 = new Sense();
		List<PropRating> iS1PropRatings = new ArrayList<PropRating>();
		PropRating iS1PropRating1 = new PropRating("Size", "1", 2, iSense1);
		iS1PropRatings.add(iS1PropRating1);
		Sense iSense2 = new Sense();
		List<PropRating> iS2PropRatings = new ArrayList<PropRating>();
		PropRating iS2PropRating1 = new PropRating("Size", "1", 2, iSense2);
		PropRating iS2PropRating2 = new PropRating("Color", "3", 1, iSense2);
		iS2PropRatings.add(iS2PropRating1);
		iS2PropRatings.add(iS2PropRating2);
		Sense iSense3 = new Sense();
		List<PropRating> iS3PropRatings = new ArrayList<PropRating>();
		PropRating iS3PropRating1 = new PropRating("Size", "1", 2, iSense3);
		iS3PropRatings.add(iS3PropRating1);
		SenseRating iSenseR1 = new SenseRating("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Senses/TestBelief", "TestBelief", 1, iS1PropRatings);
		SenseRating iSenseR2 = new SenseRating("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Senses/TestBelief2", "TestBelief2", 1, iS2PropRatings);
		SenseRating iSenseR3 = new SenseRating("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Senses/TestBelief3", "TestBelief3", 1, iS3PropRatings);
		inputSRatings.add(iSenseR1);
		inputSRatings.add(iSenseR2);
		inputSRatings.add(iSenseR3);
		
		hypo.updateSenseRatings(inputSRatings);
		System.out.println("After: ");
		for (int i = 0; i < hypo.senseRatings.size(); i++) {
			System.out.println(hypo.senseRatings.get(i).senseName + " " + hypo.senseRatings.get(i).propRatings.size() + ": " 
					+ hypo.senseRatings.get(i).rating);
		}
	}
}