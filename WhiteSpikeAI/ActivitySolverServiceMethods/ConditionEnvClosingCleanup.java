package ActivitySolverServiceMethods;

import java.sql.Statement;

import EnvAndDatabaseServiceMethods.DropConditionEnvUsingActivityId;
import Structure.Env;
import Structure.ExecuteActivityInputWrapper;

public class ConditionEnvClosingCleanup {
	public static void exec(ExecuteActivityInputWrapper eaiw, Env newConditionEnv) {
		DropConditionEnvUsingActivityId.exec(eaiw.activityId, eaiw.myConnection);
		String sqlCommand = "UPDATE Activity SET ConditionEnv=" + newConditionEnv.dbId + "WHERE Activity.id=" + eaiw.activityId + ";";
		try {
			Statement myState = eaiw.myConnection.createStatement();
			myState.execute(sqlCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
