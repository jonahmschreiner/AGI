package com.whitespike.visionstructure;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.whitespike.visionstructure.ComparisonClass;
/**
 * Represents an abstract object found using the raw env.
 * @author Jonah Schreiner
 *
 */
public class Sense implements Comparable<Sense>{
	/**
	 * List of senses that make up this sense (if it's a higher sense, otherwise will be empty).
	 */
	public List<Sense> components = new ArrayList<Sense>();
	/**
	 * The sense's properties encapsulated into an orientation object.
	 */
	public Orientation orientation = new Orientation();
	/**
	 * The sense's property changes from the previous instance encapsulated into an orientationchanges object.
	 */
	public OrientationChanges orientationChanges = new OrientationChanges();
	/**
	 * The sense definition (determined from its outline) of the sense. (Ex: a square's is "Straight;RightTurn;Straight;RightTurn;Straight;RightTurn;Straight;RightTurn;").
	 */
	public SenseDefinition definition = new SenseDefinition();
	/**
	 * The sense's comparisonclass object used in matching it to previous env senses when updating an existing env.
	 */
	public ComparisonClass comparisonClass = new ComparisonClass();
	/**
	 * The database id of this sense (not used in this non-db release).
	 */
	public int dbId;
	/**
	 * The blob that contains the pixel info about the sense.
	 */
	public Blob blob;
	/**
	 * Used in the updating of an env.
	 */
	public List<Pixel> sharedPixels = new ArrayList<Pixel>();
	
	public Sense () {}
	/**
	 * Equal if components, orientation, and definition are equal.
	 */
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
	/**
	 * Used in the updating of an env.
	 */
	@Override
	public int compareTo(Sense o) {
		return this.sharedPixels.size() - o.sharedPixels.size();
	}
}
