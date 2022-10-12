package Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ActivitySolverServiceMethods.CheckIfActivityWasSolved;
import ActivitySolverServiceMethods.GetSenseAssociatedWithActivity;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.UpdateEnv;
import MainLLF.Constants;
import Structure.Env;
import Structure.Sense;

public class CheckIfActivityWasSolvedTest {
	public static void main(String[] args) {
		//Reset DB
		DatabaseHandler.main(null);
				
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);
		
		env = UpdateEnv.update(env);
		
		Sense s = GetSenseAssociatedWithActivity.execute(env, 464);
		boolean output = CheckIfActivityWasSolved.execute(s, 464);
		
		System.out.println(output);
		//testing update the solved's db entry section
		if (output) {
			//update the solved's db entry
			try {
				Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
				Statement myState = myConnection.createStatement();
				String sqlCommand = "UPDATE Activity SET SubActivities=\"" + "1" + "\", SolvedStatus=1, numOfSolveAttempts=numOfSolveAttempts + 1 WHERE id=" + 464 + ";";
				myState.execute(sqlCommand);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	}
}
