package com.whitespike.visioncontrol;
/**
 * Encapsulates controls to tweak abstract env generation.
 * @author Jonah Schreiner
 *
 */
public class Constants {
	/**
	 * How many pixels should the height of a parallel-processing row be (the larger, the less combining needs to be done, but the initial blob generation will take longer. vice versa for smaller).
	 */
	static final public int threadHeight = 10;
	/**
	 * The higher the value, the more likely senses will be matched with ones from the previous instance.
	 */
	static final public int initialSenseComparisonScore = 200;
	/**
	 * Affects how much more likely a sense will be matched with ones from the previous instance if they're the same color.
	 */
	static final public int SenseComparisonColorMatchBonus = 100;
	/**
	 * Minimum blob height needed to be considered a sense (either this or thresholdSenseWidth must be reached).
	 */
	static final public int thresholdSenseHeight = 3;
	/**
	 * Minimum blob width needed to be considered a sense (either this or thresholdSenseHeight must be reached).
	 */
	static final public int thresholdSenseWidth = 3;
}
