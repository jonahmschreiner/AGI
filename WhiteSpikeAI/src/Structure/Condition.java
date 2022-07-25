package Structure;

public class Condition {
	Object senseProp1;
	Object senseProp2;
	String operator;
	
	public boolean check() {
		boolean result = false;
		if (operator.equals("=")) {
			if (senseProp1.equals(senseProp2)) {
				result = true;
			} else {
				result = false;
			}
		}
		if (operator.equals("!=")) {
			if (!senseProp1.equals(senseProp2)) {
				result = true;
			} else {
				result = false;
			}
		}
		if (operator.equals("<")) {
			if ((Double) senseProp1 < (Double) senseProp2) {
				result = true;
			} else {
				result = false;
			}
		}
		if (operator.equals(">")) {
			if ((Double) senseProp1 > (Double) senseProp2) {
				result = true;
			} else {
				result = false;
			}
		}
		if (operator.equals("!>")) {
			if ((Double) senseProp1 > (Double) senseProp2) {
				result = false;
			} else {
				result = true;
			}
		}
		if (operator.equals("!<")) {
			if ((Double) senseProp1 < (Double) senseProp2) {
				result = false;
			} else {
				result = true;
			}
		}
		return result;
	}
}
