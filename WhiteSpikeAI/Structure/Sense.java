package Structure;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class Sense implements Comparable<Sense>{
	public List<Sense> components = new ArrayList<Sense>();
	public Orientation orientation = new Orientation();
	public OrientationChanges orientationChanges = new OrientationChanges();
	public SenseDefinition definition = new SenseDefinition();
	public ComparisonClass comparisonClass = new ComparisonClass();
	public int dbId;
	public Blob blob;
	public List<Pixel> sharedPixels = new ArrayList<Pixel>();
	
	public Sense () {}
	
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof Sense){
			Sense c = (Sense) o;
			boolean output = false;
			Set<Sense> otherComponentSet = new LinkedHashSet<Sense>(c.components);
			Set<Sense> thisComponentSet = new LinkedHashSet<Sense>(this.components);
			if (otherComponentSet.equals(thisComponentSet) 
					&& c.orientation.equals(this.orientation) 
					&& c.definition.equals(this.definition)){ //variables (that describe conditions) are the same
				output = true;
			} else {
				output = false;
			}
			return output;
		} else {
			return false;
		}
	}

	@Override
	public int compareTo(Sense o) {
		return this.sharedPixels.size() - o.sharedPixels.size();
	}
}
