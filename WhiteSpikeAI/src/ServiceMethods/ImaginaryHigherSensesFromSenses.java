package ServiceMethods;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Structure.Sense;

public class ImaginaryHigherSensesFromSenses {
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

				if (checkY > currMinY && checkY < currMaxY) {
					currRow.add(currentCheckingSense);
				}
				if (checkX > currMinX && checkX < currMaxX) {
					currColumn.add(currentCheckingSense);
				}
			}
			
			//sort row and column by x pos and y pos (accordingly) (from smallest to largest)		check to make sure this is small to large
			if (currRow.size() > 1) {
				Collections.sort(currRow, (s1, s2) -> { return s1.orientation.position.x - s2.orientation.position.x; });
				Sense row = new Sense();
				row.components.addAll(currRow);
				rowSenses.add(row);
			}
			if (currColumn.size() > 1) {
				Collections.sort(currColumn, (s1, s2) -> { return s1.orientation.position.y - s2.orientation.position.y; });
				Sense column = new Sense();
				column.components.addAll(currColumn);
				columnSenses.add(column);
			}
		}
		
		//arrays
		List<Sense> arraySenses = new ArrayList<Sense>();
		List<Sense> rowsToLoopThrough = new ArrayList<Sense>();
		rowsToLoopThrough.addAll(rowSenses);
		List<Sense> columnsToLoopThrough = new ArrayList<Sense>();
		columnsToLoopThrough.addAll(columnSenses);
		while (rowsToLoopThrough.size() > 0) {
			Sense currentArray = new Sense();
			Sense currentRow = rowsToLoopThrough.get(0);
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
			arraySenses.add(currentArray);
		}
		return imaginarySensesOut;
	} 
}
