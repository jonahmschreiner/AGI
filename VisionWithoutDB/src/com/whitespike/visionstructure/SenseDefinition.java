package com.whitespike.visionstructure;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
/**
 * Represents the definition of a sense (determined through the outline).
 * @author Jonah Schreiner
 *
 */
public class SenseDefinition {
	/**
	 * The changes that determine the definition string for base senses (Ex: "Straight; RightTurn;").
	 */
	public List<PixelOverallChange> overallChangeDefString = new ArrayList<PixelOverallChange>(); //definition for base senses
	/**
	 * The components the define higher senses.
	 */
	public List<Sense> componentList = new ArrayList<Sense>(); //definition for higher senses
	/**
	 * What a sense is called (not currently used).
	 */
	public String senseName; //what the type of object/sense is called    Ex: Guitar, Speaker, Computer, Keyboard, etc.
	public SenseDefinition() {}
	public SenseDefinition(List<PixelOverallChange> changesIn) {
		this.overallChangeDefString = changesIn;
	}
	
	@Override
	public String toString() {
		String output = "";
		for (int i = 0; i < overallChangeDefString.size(); i++) {
			output = output + overallChangeDefString.get(i).changeType + ";";
		}
		return output;
	}
	/**
	 * Equal if changeDefs, components, and name are equal.
	 */
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
