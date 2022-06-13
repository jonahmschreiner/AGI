package unboundTopLevel;
import java.util.ArrayList;
import java.util.List;

import unboundStruct.*;
public class ExecDecider {
	public static String exec(Context context) {
		String actionDecided = "";
		int maxTotal = -1000000000;
		for (int i = 0; i < context.hypothesi.size(); i++) { //loop through all actions
			int currentTotal = 0;
			Hypothesis currentHypo = context.hypothesi.get(i);
			for (int j = 0; j < currentHypo.ratings.size(); j++) { //loop through all goals and add together their ratings
				currentTotal = currentTotal + currentHypo.ratings.get(j).rating;
				
				//add totals of all abstract senses that are present that the action has a senseRating for (action = currentHypo) (senses = abstractEnv)
				//for every senseRating the action has, see if that element is present (with the same props) and add the rating if so
				
				for (int k = 0; k < currentHypo.senseRatings.size(); k++) { //for each goal, loop through all senseRatings (rating modifiers based on abstractEnv)
					Sense placeholder = new Sense();
					placeholder.label = currentHypo.senseRatings.get(k).senseName;
					
					//map properties from propRating to props
//					List<Property> props = new ArrayList<Property>();
//					for (int m = 0; m < currentHypo.senseRatings.get(k).propRatings.size(); m++) {
//						PropRating pRating = currentHypo.senseRatings.get(k).propRatings.get(m);
//						Property prop = new Property(pRating.);
//						props.add(prop);
//					}
//					placeholder.properties = currentHypo.senseRatings.get(k).propRatings;
					
					
					if (context.env.abstractEnv.contains(placeholder)) { //if the senseRating's sense is present in the current abstractEnv
						for (int l = 0; l < context.env.abstractEnv.size(); l++) { //loop in case there are multiple senses of the same label
							Sense sense = context.env.abstractEnv.get(l);
							if (sense.equals(placeholder) && sense.properties.equals(currentHypo)) {
								
							}
						}
					}
				}
				
				//delete this shit after completing the above
				Sense placeholderSense = new Sense(); //converts the action's sense ratings into senses
				placeholderSense.label = currentHypo.senseRatings.get(j).senseName;
				if (context.env.abstractEnv.contains(placeholderSense)) {
					for (int l = 0; l < context.env.abstractEnv.size(); l++) {
						Sense inst = context.env.abstractEnv.get(l);
						if (inst.label.equals(placeholderSense.label) && inst.properties.equals(placeholderSense.properties)) {
							currentTotal = currentTotal + currentHypo.senseRatings.get(j).rating;
						}
					}				
				}
			}
			if (currentTotal > maxTotal) {
				maxTotal = currentTotal;
				actionDecided = currentHypo.actionName;
			}
		}
		return actionDecided;
	}
}