package ActivitySolverServiceMethods;

import java.util.ArrayList;

import EnvAndDatabaseServiceMethods.CreateDeepCopyOfEnv;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.Sense;

public class ExecuteActivityPrep {
	public static ExecuteActivityPrepWrapper exec(ExecuteActivityInputWrapper eaiw) {
		ExecuteActivityPrepWrapper eapw = new ExecuteActivityPrepWrapper();
		eapw.prevEnv = CreateDeepCopyOfEnv.exec(eaiw.envIn);
		eapw.prevEnvSenses2 = new ArrayList<Sense>();
		eapw.prevEnvSenses2.addAll(eaiw.envIn.abstractEnv.senses);
		eapw.activityRS = DatabaseHandler.getActivityForExecution(eaiw.activityId, eaiw.myConnection);
		return eapw;
	}
}
