package EnvAndDatabaseServiceMethods;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import Structure.Sense;

public class BaseSensesToHigherSenses {
	public static List<Sense> extractHigherSenses(List<Sense> baseSensesIn){
		List<Sense> higherSensesToReturn = new ArrayList<Sense>();
		for (int i = 0; i < baseSensesIn.size(); i++) {
			Sense higherSense = SenseToHigherSense.extractHigherSense(baseSensesIn.get(i), baseSensesIn);
			if (higherSense != null) {
				higherSensesToReturn.add(higherSense);
			}
		}
		higherSensesToReturn = removeDuplicates(higherSensesToReturn);
		List<Sense> sensesToIterate = new ArrayList<Sense>();
		sensesToIterate.addAll(higherSensesToReturn);
		int numOfSensesAdded = 0;
		do {
			numOfSensesAdded = 0;
			List<Sense> currentHigherSensesLevel = new ArrayList<Sense>();
			for (int i = 0; i < sensesToIterate.size(); i++) {
				Sense currentSense = sensesToIterate.get(i);
				Sense currentHigherSense = SenseToHigherSense.extractHigherSense(currentSense, sensesToIterate);
				if (currentHigherSense != null && !currentHigherSensesLevel.contains(currentHigherSense)) {
					currentHigherSensesLevel.add(currentHigherSense);
				}
			}
			int returningHigherSensesSize = higherSensesToReturn.size();
			currentHigherSensesLevel = removeDuplicates(currentHigherSensesLevel);
			higherSensesToReturn.addAll(currentHigherSensesLevel);
			higherSensesToReturn = removeDuplicates(higherSensesToReturn);
			numOfSensesAdded = higherSensesToReturn.size() - returningHigherSensesSize;
			sensesToIterate = currentHigherSensesLevel;
		} while (numOfSensesAdded > 0);
		
		return higherSensesToReturn;
	}
	
	public static List<Sense> removeDuplicates(List<Sense> sensesIn){
		Set<Sense> set = new LinkedHashSet<Sense>();
		set.addAll(sensesIn);
		sensesIn.clear();
		sensesIn.addAll(set);
		return sensesIn;
	}
}
