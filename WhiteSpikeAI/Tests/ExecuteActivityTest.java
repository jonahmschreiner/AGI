package Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import ActivitySolverServiceMethods.ExecuteActivity;
import EnvAndDatabaseServiceMethods.CreateDeepCopyOfEnv;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.UpdateEnv;
import EnvAndDatabaseServiceMethods.UploadConditionEnvToDB;
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
		Env conditionEnv = CreateDeepCopyOfEnv.exec(env);
		conditionEnv = UploadConditionEnvToDB.exec(conditionEnv);
		env = UpdateEnv.update(env);
		//49, 48
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			//sqlCommand is currently intentionally incorrect (tries to change x pos of background instead of sense 2)
			String sqlCommand = "INSERT INTO Activity (ConditionEnv, CoreActivity, SubActivities, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + conditionEnv.dbId + ", -1, \"48 49\", 1, 3, 1);"; //goal: increase sense 1's x pos
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
