package com.whitespike.vision;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.whitespike.vision.ComparisonScoreBasedOnOrientation;
import com.whitespike.visionstructure.*;
public class UpdateSenses {
	public static Env update(List<Sense> sensesIn, Env oldEnvIn) throws SQLException {
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
			} else {
				List<Sense> potentialMatches = addCompleteSenseMapToList(sensesBySenseDef);
				for (int j = 0; j < potentialMatches.size(); j++) {
					Sense currPMSense = potentialMatches.get(j);
					ComparisonClass cc = currPMSense.comparisonClass;
					int csbooScore = ComparisonScoreBasedOnOrientation.get(currPMSense.orientation, currSense.orientation);
					if (csbooScore > 50) {
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
				Sense currSense = currSenseDefSenses.get(k);
				Set<Integer> keys = currSense.comparisonClass.comparisons.keySet();
				Iterator<Integer> iter2 = keys.iterator();
				for (int j = 0; j < keys.size(); j++) { //iterate over each sense's possible matches and assign the highest score
					int currScore = iter2.next();
					if (currScore > currSense.comparisonClass.highestScore) {
						currSense.comparisonClass.secondHighestScore = currSense.comparisonClass.highestScore;
						currSense.comparisonClass.highestScore = currScore;
						
					} else if (currScore > currSense.comparisonClass.secondHighestScore) {
						currSense.comparisonClass.secondHighestScore = currScore;
					}
				}
				Sense oldMatchingSense = currSense.comparisonClass.comparisons.get(currSense.comparisonClass.highestScore);
				if (oldMatchingSense != null) {
					try {
						if (newSenseToOldSenseMatches.keySet().contains(oldMatchingSense)) {
							throw new Exception();
						}
						newSenseToOldSenseMatches.put(oldMatchingSense, currSense);
						currSense.dbId = oldMatchingSense.dbId;
					} catch (Exception e) {
						Sense blockerSense = newSenseToOldSenseMatches.get(oldMatchingSense);
						if (blockerSense.comparisonClass.highestScore < currSense.comparisonClass.highestScore) {
							Sense senseToReUp = newSenseToOldSenseMatches.get(oldMatchingSense);
							currSenseDefSenses.add(senseToReUp);
							newSenseToOldSenseMatches.replace(oldMatchingSense, currSense);
							currSense.dbId = oldMatchingSense.dbId;
							newSenseToOldSenseMatches.remove(null, senseToReUp);
						} else {
							Sense secondOldMatchingSense = currSense.comparisonClass.comparisons.get(currSense.comparisonClass.secondHighestScore);
							if (secondOldMatchingSense != null) {
								try {
									if (newSenseToOldSenseMatches.keySet().contains(secondOldMatchingSense)) {
										throw new Exception();
									}
									newSenseToOldSenseMatches.put(secondOldMatchingSense, currSense);
									currSense.dbId = secondOldMatchingSense.dbId;
								} catch (Exception f) {
									Sense blockerSense2 = newSenseToOldSenseMatches.get(secondOldMatchingSense);
									if (blockerSense2.comparisonClass.secondHighestScore < currSense.comparisonClass.secondHighestScore) {
										Sense senseToReUp = newSenseToOldSenseMatches.get(oldMatchingSense);
										currSenseDefSenses.add(senseToReUp);
										newSenseToOldSenseMatches.replace(secondOldMatchingSense, currSense);
										currSense.dbId = secondOldMatchingSense.dbId;
										newSenseToOldSenseMatches.remove(null, senseToReUp);
									}
								}
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
				newSense.orientationChanges = extractOrientationChanges(newSense.orientation, oldSense.orientation);
				List<Sense> envSenses = oldEnvIn.abstractEnv.senses;
				
				
					sensesIn.remove(newSense);
					int index = envSenses.indexOf(oldSense);
					envSenses.set(index, newSense);
					oldEnvIn.abstractEnv.recentlyChangedOldSenses.add(newSense.dbId);//changed from index to newSense.dbId
					newSense.dbId = oldSense.dbId;
				
			}
		}
		//handle new senses that did not replace old ones
		for (int i = 0; i < sensesIn.size(); i++) {
			Sense currentSense = sensesIn.get(i);
			oldEnvIn.abstractEnv.senses.add(currentSense);
			oldEnvIn.abstractEnv.recentlyAddedSenses.add(currentSense);
		}
		return oldEnvIn;
	}
	
	public static List<Sense> addCompleteSenseMapToList(Map<String, List<Sense>> mapIn){
		List<Sense> output = new ArrayList<Sense>();
		Iterator<String> mapIter = mapIn.keySet().iterator();
		while (mapIter.hasNext()) {
			List<Sense> currList = mapIn.get(mapIter.next());
			output.addAll(currList);
		}
		return output;
	}
	
	public static OrientationChanges extractOrientationChanges(Orientation newSenseOrIn, Orientation oldSenseOrIn) {
		OrientationChanges oc = new OrientationChanges();
		//height
		int heightChange = newSenseOrIn.height - oldSenseOrIn.height;
		if (heightChange > 0) {
			oc.heightChange = 1;
		} else if (heightChange < 0) {
			oc.heightChange = -1;
		} else {
			oc.heightChange = 0;
		}
		
		//width
		int widthChange = newSenseOrIn.width - oldSenseOrIn.width;
		if (widthChange > 0) {
			oc.widthChange = 1;
		} else if (widthChange < 0) {
			oc.widthChange = -1;
		} else {
			oc.widthChange = 0;
		}
		//rotation
		double rotationChange = newSenseOrIn.rotation - oldSenseOrIn.rotation;
		if (rotationChange > 0) {
			oc.rotationChange = 1;
		} else if (rotationChange < 0) {
			oc.rotationChange = -1;
		} else {
			oc.rotationChange = 0;
		}
		//r
		
		PixelColorRange pcr1 = new PixelColorRange(newSenseOrIn.color);
		PixelColorRange pcr2 = new PixelColorRange(oldSenseOrIn.color);
		String colorChange = pcr1.color;
		if (pcr1.color.equals(pcr2.color)) {
			colorChange = "";
		}
		oc.colorChange = colorChange;
		//x
		int xChange = newSenseOrIn.position.x - oldSenseOrIn.position.x;
		if (xChange > 0) {
			oc.xChange = 1;
		} else if (xChange < 0) {
			oc.xChange = -1;
		} else {
			oc.xChange = 0;
		}
		//y
		int yChange = newSenseOrIn.position.y - oldSenseOrIn.position.y;
		if (yChange > 0) {
			oc.yChange = 1;
		} else if (yChange < 0) {
			oc.yChange = -1;
		} else {
			oc.yChange = 0;
		}
		
		return oc;
	}
}
