package EnvAndDatabaseServiceMethods;

import Structure.Orientation;
import Structure.PixelColorRange;
import MainLLF.Constants;

public class ComparisonScoreBasedOnOrientation {
	public static int get(Orientation newSenseOr, Orientation oldSenseOr) {
		int score = Constants.initialSenseComparisonScore;
		
		//size
		score -= Math.abs(newSenseOr.size - oldSenseOr.size);
		

		//position
		score -= Math.abs(newSenseOr.position.x - oldSenseOr.position.x);
		score -= Math.abs(newSenseOr.position.y - oldSenseOr.position.y);
		
		//color
		if (new PixelColorRange(newSenseOr.color).equals(new PixelColorRange(oldSenseOr.color))) {
			score += Constants.SenseComparisonColorMatchBonus;
		}

		
		
		return score;
	}
}
