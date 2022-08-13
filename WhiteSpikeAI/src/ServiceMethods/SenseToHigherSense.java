package ServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import Structure.Blob;
import Structure.BoundingBox;
import Structure.Pixel;
import Structure.Sense;
import Structure.SenseDefinition;

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
	
	private static boolean containsSense(BoundingBox parentSenseBoundingBox, BoundingBox potentialChildSenseBoundingBox) {
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
	
	public static void main(String[] args) {
		Blob blob = new Blob();
		Pixel pixel1 = new Pixel(new Point(100, 97), new Color(100, 50, 150));
		Pixel pixel2 = new Pixel(new Point(100, 98), new Color(110, 30, 140));
		Pixel pixel3 = new Pixel(new Point(101, 98), new Color(105, 55, 155));
		Pixel pixel4 = new Pixel(new Point(102, 98), new Color(102, 52, 132));
		Pixel pixel5 = new Pixel(new Point(102, 99), new Color(103, 58, 152));
		List<Pixel> pixels = new ArrayList<Pixel>();
		pixels.add(pixel1);
		pixels.add(pixel2);
		pixels.add(pixel3);
		pixels.add(pixel4);
		pixels.add(pixel5);
		blob.pixels = pixels;
		Sense sense1 = BlobToSense.getSense(blob);
		
		Blob blob2 = new Blob();
		Pixel pixel9 = new Pixel(new Point(100, 100), new Color(100, 50, 150));
		Pixel pixel10 = new Pixel(new Point(101, 100), new Color(110, 30, 140));
		Pixel pixel15 = new Pixel(new Point(101, 99), new Color(120, 55, 145));
		Pixel pixel16 = new Pixel(new Point(100, 99), new Color(101, 51, 147));
		List<Pixel> pixels2 = new ArrayList<Pixel>();
		pixels2.add(pixel9);
		pixels2.add(pixel10);
		pixels2.add(pixel15);
		pixels2.add(pixel16);
		blob2.pixels = pixels2;
		Sense sense2 = BlobToSense.getSense(blob2);
		
		
		Blob blob3 = new Blob();
		Pixel pixel18 = new Pixel(new Point(101, 97), new Color(110, 30, 140));
		Pixel pixel19 = new Pixel(new Point(102, 97), new Color(105, 55, 155));
		Pixel pixel21 = new Pixel(new Point(102, 96), new Color(103, 58, 152));
		Pixel pixel22 = new Pixel(new Point(101, 96), new Color(112, 62, 132));
		List<Pixel> pixels3 = new ArrayList<Pixel>();
		pixels3.add(pixel18);
		pixels3.add(pixel19);
		pixels3.add(pixel21);
		pixels3.add(pixel22);
		blob3.pixels = pixels3;
		Sense sense3 = BlobToSense.getSense(blob3);
		
		List<Sense> otherSensesInEnv = new ArrayList<Sense>();
		otherSensesInEnv.add(sense2);
		otherSensesInEnv.add(sense3);
		Sense HigherSense = SenseToHigherSense.extractHigherSense(sense1, otherSensesInEnv);
		System.out.println("");
	}
}
