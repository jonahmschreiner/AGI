package unboundStruct;
import java.util.*;
public class Sense {
	public String label = "";
	public List<Property> properties;
	public Sense() {}
	public Sense(String labelIn, List<Property> propertiesIn) {
		this.label = labelIn;
		this.properties = propertiesIn;
	}
	@Override
	public String toString(){
		String output = "Label: " + this.label;
		for (int i = 0; i < properties.size(); i++) {
			output += properties.get(i).toString();
		}
		return output;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Sense){
			Sense c = (Sense) o;
			boolean output = false;
			if (c.label.equals(this.label)){
				output = true;
			} else {
				output = false;
			}
			return output;
		} else {
			return false;
		}
		
	}
}
