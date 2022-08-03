package Structure;

import java.util.List;

public class SenseDefinition {
	public List<PixelOverallChange> overallChangeDefString;
	
	public SenseDefinition() {}
	public SenseDefinition(List<PixelOverallChange> changesIn) {
		this.overallChangeDefString = changesIn;
	}
}
