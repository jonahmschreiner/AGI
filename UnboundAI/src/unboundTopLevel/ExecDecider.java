package unboundTopLevel;
import unboundStruct.*;
public class ExecDecider {
	public static String exec(Context context) {
		String actionDecided = "";
		int maxTotal = -1000000000;
		for (int i = 0; i < context.hypothesi.size(); i++) {
			int currentTotal = 0;
			Hypothesis currentHypo = context.hypothesi.get(i);
			for (int j = 0; j < currentHypo.ratings.size(); j++) {
				currentTotal = currentTotal + currentHypo.ratings.get(j).rating;
				
				//add totals of all abstract senses that are present that the action has a senseRating for
				if () {
					
				}
				currentTotal = currentTotal + currentHypo.senseRatings
			}
			if (currentTotal > maxTotal) {
				maxTotal = currentTotal;
				actionDecided = currentHypo.actionName;
			}
		}
		return actionDecided;
	}
}