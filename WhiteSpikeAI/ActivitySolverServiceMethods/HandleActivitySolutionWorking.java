package ActivitySolverServiceMethods;

import EnvAndDatabaseServiceMethods.CreateEnvInJavaFromConditionEnvInDB;

import LogsAndTestingUtils.WriteNarrowedDownConditionEnvLog;
import LogsAndTestingUtils.WriteSubActivityWorkedLog;
import Structure.AbstractEnvClosingPrepWrapper;
import Structure.AbstractEnvClosingWrapper;
import Structure.CreateEnvInJavaFromConditionEnvInDBWrapper;
import Structure.Env;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.ExecuteCurrentActivityWrapper;
import Structure.HandleActivitySolutionWrapper;
import Structure.RawEnvClosingPrepWrapper;

public class HandleActivitySolutionWorking {
	public static HandleActivitySolutionWrapper exec(ExecuteActivityInputWrapper eaiw, ExecuteActivityPrepWrapper eapw, ExecuteCurrentActivityWrapper ecaw) {
		//create and assign condition env that only contains elements that are common to existing condition env and the current env where it just worked
		try {
			WriteSubActivityWorkedLog.exec(eaiw);
			
			CreateEnvInJavaFromConditionEnvInDBWrapper ceijWrapper = CreateEnvInJavaFromConditionEnvInDB.exec(eaiw, eapw);
			eaiw = ceijWrapper.eaiw;
			eapw = ceijWrapper.eapw;
			
			RawEnvClosingPrepWrapper recpw = RawEnvClosingPrep.exec(eaiw, eapw);
			
			Env newConditionEnv = RawEnvClosing.exec(eapw, recpw, eaiw);
			
			AbstractEnvClosingPrepWrapper aecpw = AbstractEnvClosingPrep.exec(ecaw, ceijWrapper, eaiw);
			ecaw = aecpw.ecaw;
			
			
			AbstractEnvClosingWrapper aecw = AbstractEnvClosing.exec(newConditionEnv, ecaw, eapw, aecpw, eaiw);
			ecaw = aecw.ecaw;
			eapw = aecw.eapw;
			aecpw = aecw.aecpw;
			eaiw = aecw.eaiw;
			newConditionEnv = aecw.newConditionEnv;
			
			WriteNarrowedDownConditionEnvLog.exec(eaiw);
			
			ConditionEnvClosingCleanup.exec(eaiw, newConditionEnv);
			
		} catch (Exception f) {
			f.printStackTrace();
		}
		HandleActivitySolutionWrapper hasw = new HandleActivitySolutionWrapper();
		hasw.eaiw = eaiw;
		hasw.eapw = eapw;
		hasw.ecaw = ecaw;
		return hasw;
	}
}
