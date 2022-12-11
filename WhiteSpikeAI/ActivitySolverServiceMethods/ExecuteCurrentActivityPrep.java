package ActivitySolverServiceMethods;

import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.ExecuteCurrentActivityPrepWrapper;

public class ExecuteCurrentActivityPrep {
	public static ExecuteCurrentActivityPrepWrapper exec(ExecuteActivityInputWrapper eaiw, ExecuteActivityPrepWrapper eapw) {
		ExecuteCurrentActivityPrepWrapper ecapw = new ExecuteCurrentActivityPrepWrapper();
		try {
			eaiw.fw.append(" EXECACT: Activity with subactivities found\n");
			eaiw.fw.flush();
			String listOfSubActivities = eapw.activityRS.getString("SubActivities");
			String[] arrayOfSubActivityStrings = listOfSubActivities.split(" ");
			ecapw.arrayOfSubActivityStrings = arrayOfSubActivityStrings;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ecapw;
	}
}
