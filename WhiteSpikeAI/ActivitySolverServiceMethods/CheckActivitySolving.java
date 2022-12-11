package ActivitySolverServiceMethods;

import MainLLF.Constants;
import Structure.CheckActivitySolvingWrapper;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.ExecuteCurrentActivityWrapper;
import Structure.HandleActivitySolutionWrapper;
import Structure.Sense;

public class CheckActivitySolving {
	public static CheckActivitySolvingWrapper exec(ExecuteActivityInputWrapper eaiw, ExecuteActivityPrepWrapper eapw, ExecuteCurrentActivityWrapper ecaw) {
		if (eaiw.currActivityId > (Constants.numOfCoreActions + Constants.numOfRawPropActivities)) { //ensure the sub-activity isn't a core action to avoid errors in env-closing
			try {
				eaiw.fw.append(" EXECACT: Executed Sub-Activity is not a core action\n");
				eaiw.fw.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}

			Sense s = GetSenseAssociatedWithActivity.execute(eaiw.envIn, eaiw.currActivityId, eaiw.myConnection);
			if (CheckIfActivityWasSolved.execute(s, eaiw.currActivityId, eaiw.envIn, eaiw.myConnection)) { //activity solution worked
				HandleActivitySolutionWrapper hasw = HandleActivitySolutionWorking.exec(eaiw, eapw, ecaw);
				eaiw = hasw.eaiw;
				eapw = hasw.eapw;
				ecaw = hasw.ecaw;
			} else {
				HandleActivitySolutionWrapper hasw = HandleActivitySolutionNotWorking.exec(eaiw, ecaw, eapw, s);
				eaiw = hasw.eaiw;
				eapw = hasw.eapw;
				ecaw = hasw.ecaw;
			}
		}
		CheckActivitySolvingWrapper casw = new CheckActivitySolvingWrapper();
		casw.eaiw = eaiw;
		casw.eapw = eapw;
		casw.ecaw = ecaw;
		return casw;
	}
}
