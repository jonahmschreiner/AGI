package Structure;

import java.util.List;

public class SenseDefinition {
	public List<PixelOverallChange> overallChangeDefString; //definition for base senses
	public List<Sense> componentList; //definition for higher senses
	public String senseName; //what the type of object/sense is called    Ex: Guitar, Speaker, Computer, Keyboard, etc.
	public SenseDefinition() {}
	public SenseDefinition(List<PixelOverallChange> changesIn) {
		this.overallChangeDefString = changesIn;
	}
}
