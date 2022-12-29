package ActivitySolverServiceMethods;

import EnvAndDatabaseServiceMethods.UpdateEnv;
import Structure.Env;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteCurrentActivityPrepWrapper;
import Structure.ExecuteCurrentActivityWrapper;
import Structure.LFInputWrapper;

public class ExecuteCurrentActivity {
	public static ExecuteCurrentActivityWrapper exec(ExecuteActivityInputWrapper eaiw, ExecuteCurrentActivityPrepWrapper ecapw, LFInputWrapper LFInput) {
		ExecuteCurrentActivityWrapper ecaw = new ExecuteCurrentActivityWrapper();
		ecaw.eaiw = eaiw;
		String currStr = ecapw.arrayOfSubActivityStrings[ecapw.i];
		eaiw.currActivityId = Integer.valueOf(currStr);
		ecaw.currPrevEnv = new Env(0);
		ecaw.currPrevEnv.rawEnv.currentCpuUsage = eaiw.envIn.rawEnv.currentCpuUsage;
		ecaw.currPrevEnv.rawEnv.currentDisplay = eaiw.envIn.rawEnv.currentDisplay;
		try {
			eaiw.envIn = ExecuteActivity.execByDBId(eaiw, LFInput);
			eaiw.fw.append(" EXECACT: Execution of a subactivity finished\n");
			eaiw.fw.flush();
			ecaw.currPrevEnv.abstractEnv.senses.addAll(eaiw.envIn.abstractEnv.senses);
			eaiw.envIn = UpdateEnv.update(eaiw.envIn, eaiw.myConnection);
			eaiw.fw.append(" EXECACT: Updating Env after Sub-Activity Execution Finished\n");
			eaiw.fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ecaw;
	}
}
