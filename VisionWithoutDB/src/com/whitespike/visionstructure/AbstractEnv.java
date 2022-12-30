package com.whitespike.visionstructure;

import java.util.ArrayList;
import java.util.List;
/**
 * Represents an abstract version of the raw data environment received.
 * @author Jonah Schreiner
 *
 */
public class AbstractEnv {
	/**
	 * List of objects that make up the abstract environment.
	 */
	public List<Sense> senses = new ArrayList<Sense>();
	public String dbSenseList = "";
	/**
	 * List of senses that changed between the previous environment iteration and the current one.
	 */
	public List<Integer> recentlyChangedOldSenses = new ArrayList<Integer>(); //index of senses in the abstract env senses list whose orientation changed
	/**
	 * List of senses that appeared between the previous environment iteration and the current one.
	 */
	public List<Sense> recentlyAddedSenses = new ArrayList<Sense>(); //senses that are brand new upon this update iteration
	/**
	 * List of senses that disappeared between the previous environment iteration and the current one.
	 */
	public List<Sense> recentlyRemovedSenses = new ArrayList<Sense>(); //senses that existed in previous iteration but disappeared in this one
	public AbstractEnv() {}
	public AbstractEnv(List<Sense> sensesIn) {
		this.senses = sensesIn;
	}
	/**
	 * Two abstract envs are considered equal if their sense lists are equal.
	 */
	@Override
	public boolean equals(Object o) {
		if (o == this){
			return true;
		}
		if (o instanceof AbstractEnv){
			AbstractEnv c = (AbstractEnv) o;
			boolean output = false;
			if (c.senses.equals(this.senses)){ //variables (that describe conditions) are the same
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
