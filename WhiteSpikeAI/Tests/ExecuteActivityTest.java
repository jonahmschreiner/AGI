package Tests;

import java.io.File;
import java.io.FileWriter;
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
//		String listOfSubActivities = "";
//		String[] arrayOfSubActivityStrings = listOfSubActivities.split(" ");
		try {
			Thread.sleep(2000);
		} catch (Exception e) {
			
		}
		//DatabaseHandler.main(null);
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			File file = new File("/home/agi/Desktop/test.txt");
			FileWriter fw = new FileWriter(file);
			Env env = new Env();
			DatabaseHandler.uploadEnvToDatabase(env, myConnection);
//			Env conditionEnv = CreateDeepCopyOfEnv.exec(env);
//			conditionEnv = UploadConditionEnvToDB.exec(conditionEnv, myConnection);
			env = UpdateEnv.update(env, myConnection);
			//Statement myState = myConnection.createStatement();
//			String sqlCommand = "INSERT INTO Activity (ConditionEnv, CoreActivity, SubActivities, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + conditionEnv.dbId + ", -1, \"48 49\", 2, 3, 1);"; //goal: increase sense 1's x pos
//			myState.execute(sqlCommand);
//			DBObjectCountResults dbocr = new DBObjectCountResults(myConnection);
//			int activityId = dbocr.activityCount;
//			env = ExecuteActivity.execByDBId(env, activityId, fw, myConnection);
			env = ExecuteActivity.execByDBId(env, 8, fw, myConnection);
			System.out.println();

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
}
