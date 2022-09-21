package EnvAndDatabaseServiceMethods;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import EnvAndDatabaseServiceMethods.ComparisonScoreBasedOnOrientation;
import Structure.Env;
import Structure.Sense;

public class UpdateSenses {
	public class ComparisonClass{
		public Map<Integer, Sense> comparisons = new HashMap<Integer, Sense>();
		int highestScore = -99999;
		int secondHighestScore = -99999;
	}
	
	
	public static Env update(List<Sense> sensesIn, Env oldEnvIn) {
		
		//split senses up into lists by senseDef
		Map<String, List<Sense>> sensesBySenseDef = new HashMap<String, List<Sense>>();
		for (int i = 0; i < sensesIn.size(); i++) {
			Sense currSense = sensesIn.get(i);
			String currDef = currSense.definition.toString();
			if (sensesBySenseDef.containsKey(currDef)) {
				sensesBySenseDef.get(currDef).add(currSense);
			} else {
				List<Sense> newList = new ArrayList<Sense>();
				newList.add(currSense);
				sensesBySenseDef.put(currDef, newList);
			}
		}
		
		//connect new senses to potential matches from old env
		for (int i = 0; i < oldEnvIn.abstractEnv.senses.size(); i++) {
			Sense currSense = oldEnvIn.abstractEnv.senses.get(i);
			String currDef = currSense.definition.toString();
			if (sensesBySenseDef.containsKey(currDef)) {
				List<Sense> potentialMatches = sensesBySenseDef.get(currDef);
				for (int j = 0; j < potentialMatches.size(); j++) {
					Sense currPMSense = potentialMatches.get(j);
					ComparisonClass cc = currPMSense.comparisonClass;
					int csbooScore = ComparisonScoreBasedOnOrientation.get(currPMSense.orientation, currSense.orientation);
					if (csbooScore > 0) {
						cc.comparisons.put(csbooScore, currSense);
					}	
				}
			}
		}
		
		
		//decide on matches and set env senses to the appropriate new ones
		Set<String> senseDefKeys = sensesBySenseDef.keySet();
		Iterator<String> iter = senseDefKeys.iterator();
		for (int i = 0; i < senseDefKeys.size(); i++) { //iterate over each senseDef
			List<Sense> currSenseDefSenses = sensesBySenseDef.get(iter.next());
			Map<Sense, Sense> newSenseToOldSenseMatches = new HashMap<Sense, Sense>();
			for (int k = 0; k < currSenseDefSenses.size(); k++) { //iterate over each sense for the type of senseDef
				Sense currSense = sensesIn.get(i);
				Set<Integer> keys = currSense.comparisonClass.comparisons.keySet();
				Iterator<Integer> iter2 = keys.iterator();
				for (int j = 0; j < keys.size(); j++) { //iterate over each sense's possible matches and assign the highest score
					int currScore = iter2.next();
					if (currScore > currSense.comparisonClass.highestScore) {
						currSense.comparisonClass.highestScore = currScore;
					} else if (currScore > currSense.comparisonClass.secondHighestScore) {
						currSense.comparisonClass.secondHighestScore = currScore;
					}
				}
				Sense oldMatchingSense = currSense.comparisonClass.comparisons.get(currSense.comparisonClass.highestScore);
				try {
					newSenseToOldSenseMatches.put(oldMatchingSense, currSense);
				} catch (Exception e) {
					Sense blockerSense = newSenseToOldSenseMatches.get(oldMatchingSense);
					if (blockerSense.comparisonClass.highestScore < currSense.comparisonClass.highestScore) {
						newSenseToOldSenseMatches.replace(oldMatchingSense, currSense);
					} else {
						Sense secondOldMatchingSense = currSense.comparisonClass.comparisons.get(currSense.comparisonClass.secondHighestScore);
						try {
							newSenseToOldSenseMatches.put(secondOldMatchingSense, currSense);
						} catch (Exception f) {
							Sense blockerSense2 = newSenseToOldSenseMatches.get(secondOldMatchingSense);
							if (blockerSense2.comparisonClass.secondHighestScore < currSense.comparisonClass.secondHighestScore) {
								newSenseToOldSenseMatches.replace(secondOldMatchingSense, currSense);
							}
						}
					}
				}
				
			}
			
			//apply newSenseToOldSenseMatches to env
			Iterator<Sense> iter3 = newSenseToOldSenseMatches.keySet().iterator();
			int size = newSenseToOldSenseMatches.keySet().size();
			for (int j = 0; j < size; j++) {
				Sense oldSense = iter3.next();
				Sense newSense = newSenseToOldSenseMatches.get(oldSense);
				List<Sense> envSenses = oldEnvIn.abstractEnv.senses;
				envSenses.set(envSenses.indexOf(oldSense), newSense); 		
			}
		}
		
		
		return oldEnvIn;
	}
}
