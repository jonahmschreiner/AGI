package ActivitySolverServiceMethods;

import EnvAndDatabaseServiceMethods.CreateDeepCopyOfEnv;
import MainLLF.Constants;
import Structure.ExecutionPrepWrapper;
import Structure.LFInputWrapper;

public class PrepForExecution {
	public static ExecutionPrepWrapper exec(LFInputWrapper LFInput) {
		ExecutionPrepWrapper epw = new ExecutionPrepWrapper();
		epw.conditionEnv = CreateDeepCopyOfEnv.exec(LFInput.sw.env);
		System.out.println("deep copy of env created");
		try {
			LFInput.sw.fw.append(" deep copy of env created\n");
			LFInput.sw.fw.flush();
		} catch (Exception e ) {
			
		}
		boolean coreActionIsTopAction;
		if (LFInput.queues.actionQueue.get(0) <= Constants.numOfCoreActions) {
			if (LFInput.queues.actionQueue.size() > 1) {
				if (LFInput.queues.actionQueue.get(1) <= Constants.numOfCoreActions) {
					coreActionIsTopAction = true;
				} else {
					coreActionIsTopAction = false;
				}
			} else {
				coreActionIsTopAction = true;
			}
		} else {
			coreActionIsTopAction = false;
		}
		epw.coreActionIsTopAction = coreActionIsTopAction;
		return epw;
	}
}
