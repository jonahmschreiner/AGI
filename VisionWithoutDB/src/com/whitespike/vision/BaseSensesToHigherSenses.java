package com.whitespike.vision;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.whitespike.visionstructure.Sense;


/**
 * Service method that generates higher senses from base senses passed in.
 * @author Jonah Schreiner
 *
 */
public class BaseSensesToHigherSenses {
	/**
	 * Service method that generates higher senses from base senses passed in.
	 * @param baseSensesIn Base senses to extract higher senses from.
	 * @return The higher senses found using the base senses.
	 */
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
	/**
	 * Removes duplicate senses from a list.
	 * @param sensesIn List of senses to remove duplicates from.
	 * @return The list without duplicates.
	 */
	public static List<Sense> removeDuplicates(List<Sense> sensesIn){
		Set<Sense> set = new LinkedHashSet<Sense>();
		set.addAll(sensesIn);
		sensesIn.clear();
		sensesIn.addAll(set);
		return sensesIn;
	}
}
