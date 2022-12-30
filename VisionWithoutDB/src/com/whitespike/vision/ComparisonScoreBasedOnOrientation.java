package com.whitespike.vision;

import com.whitespike.visionstructure.*;
import com.whitespike.visioncontrol.Constants;
/**
 * Compares two senses, returns a score based on how likely they are to be the same sense.
 * @author Jonah Schreiner
 *
 */
public class ComparisonScoreBasedOnOrientation {
	/**
	 * Compares two senses, returns a score based on how likely they are to be the same sense.
	 * @param newSenseOr The new sense we're attempting to find a previous env match for.
	 * @param oldSenseOr The current old sense we're seeing how likely it matches.
	 * @return The score result.
	 */
	public static int get(Orientation newSenseOr, Orientation oldSenseOr) {
		int score = Constants.initialSenseComparisonScore;
		
		//size
		score -= Math.abs(newSenseOr.height - oldSenseOr.height);
		score -= Math.abs(newSenseOr.width - oldSenseOr.width);

		//position
		score -= Math.abs(newSenseOr.position.x - oldSenseOr.position.x);
		score -= Math.abs(newSenseOr.position.y - oldSenseOr.position.y);
		
		//color
		PixelColorRange newRange = new PixelColorRange(newSenseOr.color);
		PixelColorRange oldRange = new PixelColorRange(oldSenseOr.color);
		if (newRange.equals(oldRange)) {
			score += Constants.SenseComparisonColorMatchBonus;
		}

		
		
		return score;
	}
}
