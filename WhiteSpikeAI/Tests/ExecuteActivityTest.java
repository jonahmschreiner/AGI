package Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import ActivitySolverServiceMethods.ExecuteActivity;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.Util;
import MainLLF.Constants;
import Structure.DBObjectCountResults;
import Structure.Env;

public class ExecuteActivityTest {
	public static void main(String[] args) {		
		
		//Reset DB
		DatabaseHandler.main(null);
						
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);
		
		//49, 48
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "INSERT INTO Activity (ConditionEnv, CoreActivity, SubActivities, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + env.dbId + ", -1, \"48 49\", 1, 3, 1);";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DBObjectCountResults dbocr = new DBObjectCountResults();
		int activityId = dbocr.activityCount;
		env = ExecuteActivity.execByDBId(env, activityId);
		System.out.println();

		
	}
}
