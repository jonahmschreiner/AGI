package com.whitespike.vision;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.whitespike.visionstructure.Sense;
import com.whitespike.visionstructure.SenseDefinition;


/**
 * Service method that extracts imaginary senses from base/higher senses.
 * @author Jonah Schreiner
 *
 */
public class ImaginaryHigherSensesFromSenses {
	/**
	 * Service method that extracts imaginary senses from base/higher senses.
	 * @param sensesIn Senses to extract imaginary senses from.
	 * @return The extracted imaginary senses.
	 */
	public static List<Sense> extract(List<Sense> sensesIn){
		List<Sense> imaginarySensesOut = new ArrayList<Sense>();
		
		//row and columns
		List<Sense> rowSenses = new ArrayList<Sense>();
		List<Sense> columnSenses = new ArrayList<Sense>();
		for (int i = 0; i < sensesIn.size(); i++) {
			Sense currentSense = sensesIn.get(i);
			int currMinY = currentSense.orientation.boundingBox.minY;
			int currMaxY = currentSense.orientation.boundingBox.maxY;
			int currMinX = currentSense.orientation.boundingBox.minX;
			int currMaxX = currentSense.orientation.boundingBox.maxX;
			List<Sense> currRow = new ArrayList<Sense>();
			currRow.add(currentSense);
			List<Sense> currColumn = new ArrayList<Sense>();
			currColumn.add(currentSense);
			for (int j = 0; j < sensesIn.size(); j++) {
				Sense currentCheckingSense = sensesIn.get(j);
				int checkX = currentCheckingSense.orientation.position.x;
				int checkY = currentCheckingSense.orientation.position.y;

				if (checkY > currMinY && checkY < currMaxY && !SenseToHigherSense.containsSense(currentSense.orientation.boundingBox, currentCheckingSense.orientation.boundingBox) && !SenseToHigherSense.containsSense(currentCheckingSense.orientation.boundingBox, currentSense.orientation.boundingBox) && !currentSense.orientation.boundingBox.equals(currentCheckingSense.orientation.boundingBox)) {
					currRow.add(currentCheckingSense);
				}
				if (checkX > currMinX && checkX < currMaxX && !SenseToHigherSense.containsSense(currentSense.orientation.boundingBox, currentCheckingSense.orientation.boundingBox) && !SenseToHigherSense.containsSense(currentCheckingSense.orientation.boundingBox, currentSense.orientation.boundingBox) && !currentSense.orientation.boundingBox.equals(currentCheckingSense.orientation.boundingBox)) {
					currColumn.add(currentCheckingSense);
				}
			}
			
			//sort row and column by x pos and y pos (accordingly) (from smallest to largest)		check to make sure this is small to large
			if (currRow.size() > 1) {
				Collections.sort(currRow, (s1, s2) -> { return s1.orientation.position.x - s2.orientation.position.x; });
				Sense row = new Sense();
				row.components.addAll(currRow);
				row.definition = new SenseDefinition();
				row.definition.componentList = row.components;
				row.orientation = OrientationFromHigherSenseComponents.extract(row.components);
				
				//prevent duplicates
				if (listDoesNotContainASenseWhoseComponentsMatch(rowSenses, row)) {
					rowSenses.add(row);
				}			
			}
			if (currColumn.size() > 1) {
				Collections.sort(currColumn, (s1, s2) -> { return s1.orientation.position.y - s2.orientation.position.y; });
				Sense column = new Sense();
				column.components.addAll(currColumn);
				column.definition = new SenseDefinition();
				column.definition.componentList = column.components;
				column.orientation = OrientationFromHigherSenseComponents.extract(column.components);
				//prevent duplicates
				if (listDoesNotContainASenseWhoseComponentsMatch(columnSenses, column)) {
					columnSenses.add(column);
				}	
			}
		}
		
		//arrays
		List<Sense> arraySenses = new ArrayList<Sense>();
		List<Sense> rowsToLoopThrough = new ArrayList<Sense>();
		rowsToLoopThrough.addAll(rowSenses);
		List<Sense> columnsToLoopThrough = new ArrayList<Sense>();
		columnsToLoopThrough.addAll(columnSenses);
		
		int rowsToLoopThroughCounter = 0;
		while (rowsToLoopThroughCounter < rowsToLoopThrough.size()) {
			Sense currentArray = new Sense();
			Sense currentRow = rowsToLoopThrough.get(rowsToLoopThroughCounter);
			Sense currentSense = currentRow.components.get(0);
			for (int i = 0; i < columnsToLoopThrough.size(); i++) {
				Sense currentColumn = columnsToLoopThrough.get(i);
				if (currentColumn.components.contains(currentSense)) {
					//loop through current column, find all rows associated with column members, sort them by y pos (small to large)
					//and add them to the array as components in order
					for (int j = 0; j < currentColumn.components.size(); j++) {
						Sense currentSenseInColumn = currentColumn.components.get(j);
						for (int k = 0; k < rowsToLoopThrough.size(); k++) {
							Sense currentCheckedRow = rowsToLoopThrough.get(k);
							if (currentCheckedRow.components.contains(currentSenseInColumn)) {
								currentArray.components.add(currentCheckedRow);
							}
						}
					}
				}
			}
			if (currentArray.components.size() > 1) {
				currentArray.definition = new SenseDefinition();
				currentArray.definition.componentList = currentArray.components;
				currentArray.orientation = OrientationFromHigherSenseComponents.extract(currentArray.components);
				Collections.sort(currentArray.components, (s1, s2) -> { return s1.orientation.position.y - s2.orientation.position.y; });
				//prevent duplicates
				if (listDoesNotContainASenseWhoseComponentsMatch(arraySenses, currentArray)) {
					arraySenses.add(currentArray);
				}	
				
			}
			rowsToLoopThroughCounter++;
			
		}
		
		imaginarySensesOut.addAll(rowSenses);
		imaginarySensesOut.addAll(columnSenses);
		imaginarySensesOut.addAll(arraySenses);
		return imaginarySensesOut;
	} 
	/**
	 * Determines if any sense in the sensesList has the same components as senseIn.
	 * @param sensesList
	 * @param senseIn
	 * @return The resulting boolean.
	 */
	private static boolean listDoesNotContainASenseWhoseComponentsMatch(List<Sense> sensesList, Sense senseIn) {
		boolean output = true;
		for (int i = 0; i < sensesList.size(); i++) {
			Sense currentSense = sensesList.get(i);
			if (currentSense.components.equals(senseIn.components)) {
				output = false;
				break;
			}
		}
		return output;
	}
}
