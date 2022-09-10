package Structure;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SenseDefinition {
	public List<PixelOverallChange> overallChangeDefString = new ArrayList<PixelOverallChange>(); //definition for base senses
	public List<Sense> componentList = new ArrayList<Sense>(); //definition for higher senses
	public String senseName; //what the type of object/sense is called    Ex: Guitar, Speaker, Computer, Keyboard, etc.
	public SenseDefinition() {}
	public SenseDefinition(List<PixelOverallChange> changesIn) {
		this.overallChangeDefString = changesIn;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof SenseDefinition){
			SenseDefinition c = (SenseDefinition) o;
			boolean output = false;
			Set<Sense> otherComponentSet = new LinkedHashSet<Sense>(c.componentList);
			Set<Sense> thisComponentSet = new LinkedHashSet<Sense>(this.componentList);
			boolean changeDefCheck = false;
			if (c.overallChangeDefString == null || this.overallChangeDefString == null) {
				if (c.overallChangeDefString == null && this.overallChangeDefString == null) {
					changeDefCheck = true;
				}
			} else {
				if (c.overallChangeDefString.equals(this.overallChangeDefString)) {
					changeDefCheck = true;
				}
			}
			boolean nameCheck = false;
			if (c.senseName == null || this.senseName == null) {
				if (c.senseName == null && this.senseName == null) {
					nameCheck = true;
				}
			} else {
				if (c.senseName.equals(this.senseName)) {
					nameCheck = true;
				}
			}
			if (changeDefCheck 
					&& otherComponentSet.equals(thisComponentSet) 
					&& nameCheck){ //variables (that describe conditions) are the same
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
