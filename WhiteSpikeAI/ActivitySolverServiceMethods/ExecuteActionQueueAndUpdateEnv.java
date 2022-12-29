package ActivitySolverServiceMethods;

import EnvAndDatabaseServiceMethods.UpdateEnv;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecutionPrepWrapper;
import Structure.LFInputWrapper;

public class ExecuteActionQueueAndUpdateEnv {
	public static LFInputWrapper exec(LFInputWrapper LFInput, ExecutionPrepWrapper epw) {
		try {
			while (LFInput.queues.actionQueue.size() > 0) {
				ExecuteActivityInputWrapper eaiw = new ExecuteActivityInputWrapper();
				eaiw.activityId = LFInput.queues.actionQueue.get(0);
				eaiw.envIn = LFInput.sw.env;
				eaiw.fw = LFInput.sw.fw;
				eaiw.myConnection = LFInput.sw.myConnection;
				LFInput = ExecuteActivity.execByDBId(eaiw, LFInput);
				System.out.println("individual action executed");
				LFInput.sw.fw.append(" individual action " + LFInput.queues.actionQueue.get(0) + " finished executing from actionQueue\n");
				LFInput.sw.fw.flush();
				LFInput.queues.actionQueue.remove(0);
			}
			
			if (epw.coreActionIsTopAction) {
				LFInput.sw.env = UpdateEnv.update(LFInput.sw.env, LFInput.sw.myConnection);
			}
			
			System.out.println("actions executed");
			LFInput.sw.fw.append(" action queue finished executing\n");
			LFInput.sw.fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return LFInput;
	}
}
