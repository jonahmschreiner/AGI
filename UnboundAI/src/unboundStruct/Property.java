package unboundStruct;

import java.util.List;
import java.util.Scanner;

public class Property {
	Object prop;
	public Property(String typeIn, String valuesStringIn) {
		this.prop = determineShit(typeIn, valuesStringIn);
	}
	
	public Object determineShit (String typeIn, String valuesStringIn) {
		Object output = null;
		if (typeIn.equals("int")) {
			output = Integer.valueOf(valuesStringIn);
		} else
		if (typeIn.equals("double")) {
			output = Double.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("char")) {
			output = Character.valueOf(valuesStringIn.charAt(0));
		}	else
		if (typeIn.equals("String")) {
			output = valuesStringIn;
		}	else
		if (typeIn.equals("byte")) {
			output = Byte.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("short")) {
			output = Short.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("long")) {
			output = Long.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("float")) {
			output = Float.valueOf(valuesStringIn);
		}	else
		if (typeIn.equals("boolean")) {
			output = Boolean.valueOf(valuesStringIn);
		}	else
		if (typeIn.substring(0, 4).equals("List<")) {		
			List<?> list = null;
			Scanner scan = new Scanner(valuesStringIn);
			while (scan.hasNext()) {
				String type = scan.next();
				String value = scan.next();
				Object component = determineShit(type, value);
				list.add(component);		
			}
			scan.close();
			output = list;
		}	
		return output;
	}

	
	
	public static void main(String[] args) {
		String type = "int";
		String valuesStringIn = "1";
		Property prop = new Property(type, valuesStringIn);
		int test = 1;
		System.out.println(prop.prop.equals(test));
	}
}
