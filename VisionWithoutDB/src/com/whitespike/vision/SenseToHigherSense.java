package com.whitespike.vision;

import java.util.List;

import com.whitespike.visionstructure.*;

public class SenseToHigherSense {
	public static Sense extractHigherSense(Sense currentSenseIn, List<Sense> otherSensesInEnv) {
		Sense HigherSenseToReturn = new Sense();
		BoundingBox currentSenseBoundingBox = currentSenseIn.orientation.boundingBox;
		for (Sense otherSense : otherSensesInEnv) {
			if (containsSense(currentSenseBoundingBox, otherSense.orientation.boundingBox) && !otherSense.equals(currentSenseIn)) {
				HigherSenseToReturn.components.add(otherSense);
			}
		}
		if (HigherSenseToReturn.components.size() > 0) {
			HigherSenseToReturn.components.add(currentSenseIn);
			
			//add higher sense orientation code
			HigherSenseToReturn.orientation = OrientationFromHigherSenseComponents.extract(HigherSenseToReturn.components);
			//add higher sense definition code
			SenseDefinition senseDef = new SenseDefinition();
			senseDef.componentList = HigherSenseToReturn.components;
			HigherSenseToReturn.definition = senseDef;
		} else {
			return null;
		}
		return HigherSenseToReturn;
	}
	
	public static boolean containsSense(BoundingBox parentSenseBoundingBox, BoundingBox potentialChildSenseBoundingBox) {
		boolean output = false;	
		Pixel minXMinY = potentialChildSenseBoundingBox.MinXMinY;
		Pixel minXMaxY = potentialChildSenseBoundingBox.MinXMaxY;
		Pixel maxXMinY = potentialChildSenseBoundingBox.MaxXMinY;
		Pixel maxXMaxY = potentialChildSenseBoundingBox.MaxXMaxY;
		if (minXMinY.position.x > parentSenseBoundingBox.minX 
				&& minXMinY.position.x < parentSenseBoundingBox.maxX 
				&& minXMinY.position.y < parentSenseBoundingBox.maxY 
				&& minXMinY.position.y > parentSenseBoundingBox.minY ) {
			output = true;
		} else
		if (minXMaxY.position.x > parentSenseBoundingBox.minX 
				&& minXMaxY.position.x < parentSenseBoundingBox.maxX 
				&& minXMaxY.position.y < parentSenseBoundingBox.maxY 
				&& minXMaxY.position.y > parentSenseBoundingBox.minY ) {
			output = true;
		} else
		if (maxXMinY.position.x > parentSenseBoundingBox.minX 
				&& maxXMinY.position.x < parentSenseBoundingBox.maxX 
				&& maxXMinY.position.y < parentSenseBoundingBox.maxY 
				&& maxXMinY.position.y > parentSenseBoundingBox.minY ) {
			output = true;
		} else
		if (maxXMaxY.position.x > parentSenseBoundingBox.minX 
				&& maxXMaxY.position.x < parentSenseBoundingBox.maxX 
				&& maxXMaxY.position.y < parentSenseBoundingBox.maxY 
				&& maxXMaxY.position.y > parentSenseBoundingBox.minY ) {
			output = true;
		}
		return output;
	}
}
