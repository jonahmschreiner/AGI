package EnvAndDatabaseServiceMethods;

import java.util.ArrayList;
import java.util.List;
import EnvAndDatabaseServiceMethods.ComparisonScoreBasedOnOrientation;
import Structure.Env;
import Structure.Sense;

public class UpdateSenses {
	public class ComparisonClass{
		public List<Sense> potentialMatchesList = new ArrayList<Sense>();
		public List<Integer> comparisonScores = new ArrayList<Integer>();
	}
	
	public class DupClass{
		public List<Sense> dupSenses = new ArrayList<Sense>();
		public String senseDef = "";
	}
	
	public static Env update(List<Sense> sensesIn, Env oldEnvIn) {
		List<ComparisonClass> potentialMatches = new ArrayList<ComparisonClass>(sensesIn.size());
		List<String> senseDefStrings = new ArrayList<String>(sensesIn.size());
		for (int i = 0; i < sensesIn.size(); i++) {
			senseDefStrings.set(i, sensesIn.get(i).toString());
		}
		
		for (int i = 0; i < oldEnvIn.abstractEnv.senses.size(); i++) {
			Sense currSense = oldEnvIn.abstractEnv.senses.get(i);
			String currSenseDef = oldEnvIn.abstractEnv.senses.get(i).definition.overallChangeDefString.toString();
			for (int j = 0; j < senseDefStrings.size(); j++) {
				if (senseDefStrings.get(j).equals(currSenseDef)) {
					ComparisonClass cc = potentialMatches.get(j);
					cc.potentialMatchesList.add(currSense);
					cc.comparisonScores.add(ComparisonScoreBasedOnOrientation.get(sensesIn.get(j).orientation, currSense.orientation));
				}
			}
		}
		
		//create deep copy of senseDefStrings
		List<String> senseDefStrings2 = new ArrayList<String>();
		for (String str : senseDefStrings) {
			senseDefStrings2.add(str);
		}
		
		//check for multiple new senses of the same sense def (CONTINUE TOMORROW)
		
		while (senseDefStrings2.size() > 1) {
			String currStr = senseDefStrings2.get(0);
			while (true) {
				try {
					int firstOccurence = senseDefStrings2.indexOf(currStr);
					senseDefStrings2.remove(firstOccurence);
					currStr = currStr.substring(firstOccurence);
				} catch (Exception e) {
					break;
				}
			}
		}
		
		
		return oldEnvIn;
	}
}
