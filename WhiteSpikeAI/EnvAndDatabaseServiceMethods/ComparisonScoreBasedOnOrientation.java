package EnvAndDatabaseServiceMethods;

import Structure.Orientation;
import Structure.PixelColorRange;
import MainLLF.Constants;

public class ComparisonScoreBasedOnOrientation {
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
