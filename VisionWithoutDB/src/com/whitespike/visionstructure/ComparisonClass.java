package com.whitespike.visionstructure;

import java.util.HashMap;
import java.util.Map;

import com.whitespike.visionstructure.Sense;
/**
 * Represents an object used to store contenders when attempting to match a newly discovered sense to one from a previous iteration (in order to see if the same sense has moved, etc.).
 * @author Jonah Schreiner
 *
 */
public class ComparisonClass{
	/**
	 * Map that relates the scores of this sense comparing to the old sense to the old sense it was compared with.
	 */
	public Map<Integer, Sense> comparisons = new HashMap<Integer, Sense>();
	/**
	 * The highest comparison score (which is used with the map to identify the old sense that the new sense most likely is).
	 */
	public int highestScore = -99999;
	/**
	 * The second highest comparison score (which is used similarly to highestScore, except only in the case a different new sense more closely matches the old sense than this sense does).
	 */
	public int secondHighestScore = -99999;
	public ComparisonClass() {}
}
