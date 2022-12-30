package com.whitespike.vision;

import com.whitespike.visionstructure.Sense;

public class UpdateComponentProps {
	
	/*
	 * What are the props:
	 * difference in x pos
	 * difference in y pos
	 * difference in distance (pythagorean)
	 * are the same color
	 * difference in rotation ?
	 * difference in height
	 * difference in width
	 * this is further up than the comparison sense
	 * this is further left than the comparison sense
	 * this is taller than the comparison sense
	 * this is wider than the comparison sense
	 * bounding boxes are overlapping
	 */
	public static void exec(Sense senseIn) {
		for (int i = 0; i < senseIn.components.size(); i++) {
			Sense currSense = senseIn.components.get(i);
			for (int j = 0; j < senseIn.components.size(); j++) {
				
			}
		}
	}
}
