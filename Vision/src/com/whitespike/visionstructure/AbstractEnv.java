package com.whitespike.visionstructure;

import java.util.ArrayList;
import java.util.List;

public class AbstractEnv {
	public List<Sense> senses = new ArrayList<Sense>();
	public String dbSenseList = "";
	public List<Integer> recentlyChangedOldSenses = new ArrayList<Integer>(); //index of senses in the abstract env senses list whose orientation changed
	public List<Sense> recentlyAddedSenses = new ArrayList<Sense>(); //senses that are brand new upon this update iteration
	public List<Sense> recentlyRemovedSenses = new ArrayList<Sense>(); //senses that existed in previous iteration but disappeared in this one
	public AbstractEnv() {}
	public AbstractEnv(List<Sense> sensesIn) {
		this.senses = sensesIn;
	}
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
