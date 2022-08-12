package ServiceMethods;

import java.awt.Color;
import java.awt.Point;
import java.util.List;

import Structure.BoundingBox;
import Structure.Orientation;
import Structure.Sense;

public class OrientationFromHigherSenseComponents {
	public static Orientation extract(List<Sense> componentsIn) {
		Orientation output = new Orientation();
		int maxX = 0;
		int minX = 100000;
		int maxY = 0;
		int minY = 100000;
		int totalR = 0;
		int totalG = 0;
		int totalB = 0;
		int totalX = 0;
		int totalY = 0;
		for (Sense sense : componentsIn) {
			int senseMaxY = sense.orientation.position.y + (sense.orientation.height / 2);
			int senseMinY = sense.orientation.position.y - (sense.orientation.height / 2);
			int senseMaxX = sense.orientation.position.x + (sense.orientation.width / 2);
			int senseMinX = sense.orientation.position.x - (sense.orientation.width / 2);
			totalR = totalR + sense.orientation.color.getRed();
			totalG = totalG + sense.orientation.color.getGreen();
			totalB = totalB + sense.orientation.color.getBlue();
			totalX = totalX + sense.orientation.position.x;
			totalY = totalY + sense.orientation.position.y;
			if (senseMaxX > maxX){
				maxX = senseMaxX;
			}
			if (senseMinX < minX){
				minX = senseMinX;
			}
			if (senseMaxY > maxY){
				maxY = senseMaxY;
			}
			if (senseMinY < minY){
				minY = senseMinY;
			}
		}
		int componentListSize = componentsIn.size();
		output.height = maxY - minY;
		output.width = maxX - minX;
		output.color = new Color(totalR/componentListSize, totalG/componentListSize, totalB/componentListSize);
		output.boundingBox = new BoundingBox(minX, minY, maxX, maxY);
		output.position = new Point(totalX/componentListSize, totalY/componentListSize);
		output.rotation = (componentsIn.get(0).orientation.position.y - componentsIn.get(1).orientation.position.y)/(componentsIn.get(0).orientation.position.x - componentsIn.get(1).orientation.position.x);
	
		return output;
	}
}
