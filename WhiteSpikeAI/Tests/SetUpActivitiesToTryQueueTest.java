package Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ActivitySolverServiceMethods.SetUpActivitiesToTryQueueIfNecessary;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import MainLLF.Constants;
import Structure.Env;

public class SetUpActivitiesToTryQueueTest {
	public static void main(String[] args) {
		DatabaseHandler.main(null);
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "UPDATE Activity SET SolvedStatus=1 WHERE id=487;";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		List<String> input = new ArrayList<String>();
		input = SetUpActivitiesToTryQueueIfNecessary.setup(input, 488);
		System.out.println("Should be one: " + input.size());
		
		List<String> input2 = new ArrayList<String>();
		input2.add("testNotSizeZero");
		input2 = SetUpActivitiesToTryQueueIfNecessary.setup(input2, 488);
		System.out.println("Should be 1: " + input2.size());
	}
}
