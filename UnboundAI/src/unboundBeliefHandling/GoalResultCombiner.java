package unboundBeliefHandling;
import java.util.*;
import unboundStruct.*;
public class GoalResultCombiner {
	public static int combine(List<GoalResult> resultsIn) {
		int total = 0;
		for (int i = 0; i < resultsIn.size(); i++) {
			total = total + resultsIn.get(i).result;
		}
		return total;
	}
}
