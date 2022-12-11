package ActivitySolverServiceMethods;

import java.util.ArrayList;
import java.util.List;

import EnvAndDatabaseServiceMethods.UpdateSenses;
import Structure.AbstractEnvClosingPrepWrapper;
import Structure.CreateEnvInJavaFromConditionEnvInDBWrapper;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteCurrentActivityWrapper;
import Structure.Sense;

public class AbstractEnvClosingPrep {
	public static AbstractEnvClosingPrepWrapper exec(ExecuteCurrentActivityWrapper ecaw, CreateEnvInJavaFromConditionEnvInDBWrapper ceijWrapper, ExecuteActivityInputWrapper eaiw) {
		ecaw.currPrevEnv.abstractEnv.recentlyAddedSenses.clear();
		ecaw.currPrevEnv.abstractEnv.recentlyChangedOldSenses.clear();
		List<Sense> currPrevEnvSensesList = new ArrayList<Sense>();
		currPrevEnvSensesList.addAll(ecaw.currPrevEnv.abstractEnv.senses);
		int conditionEnvSensesSize = ceijWrapper.conditionEnvSenses.size();
		try {
			ecaw.currPrevEnv = UpdateSenses.update(ceijWrapper.conditionEnvSenses, ecaw.currPrevEnv, false, eaiw.myConnection); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		AbstractEnvClosingPrepWrapper aecpw = new AbstractEnvClosingPrepWrapper();
		aecpw.conditionEnvSensesSize = conditionEnvSensesSize;
		aecpw.ecaw = ecaw;
		aecpw.currPrevEnvSensesList = currPrevEnvSensesList;
		return aecpw;
	}
}
