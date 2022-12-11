package EnvAndDatabaseServiceMethods;

import java.awt.Color;

public class CreateColorFromRange {
	public static Color exec(String rangeIn) {
		Color output = null;
		if (rangeIn.equals("Red")) {
			output = new Color(255, 0, 0);
		} else if (rangeIn.equals("White")) {
			output = new Color(255, 255, 255);
		} else if (rangeIn.equals("Grey")) {
			output = new Color(96, 96, 96);
		} else if (rangeIn.equals("Black")) {
			output = new Color(0, 0, 0);
		} else if (rangeIn.equals("Brown")) {
			output = new Color(102, 51, 0);
		} else if (rangeIn.equals("Orange")) {
			output = new Color(255, 128, 0);
		} else if (rangeIn.equals("Yellow")) {
			output = new Color(255, 255, 0);
		} else if (rangeIn.equals("Green")) {
			output = new Color(0, 255, 0);
		} else if (rangeIn.equals("Teal")) {
			output = new Color(0, 255, 255);
		} else if (rangeIn.equals("Blue")) {
			output = new Color(0, 0, 255);
		} else if (rangeIn.equals("Purple")) {
			output = new Color(102, 0, 204);
		} else if (rangeIn.equals("Pink")) {
			output = new Color(255, 102, 255);
		}	
		return output;
	}
}
