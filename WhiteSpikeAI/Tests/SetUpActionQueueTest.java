package Tests;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ActivitySolverServiceMethods.SetUpActionQueueIfNecessary;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import MainLLF.Constants;

public class SetUpActionQueueTest {
	public static void main(String[] args) {
		DatabaseHandler.main(null);
		
		//extract from core action/activity
		List<Integer> output1 = SetUpActionQueueIfNecessary.extractCoreActionListFromActivity(1);
		System.out.println("Should be 1: " + output1.size());
		
		//extract from activity
			//set up higher activity
			try {
				Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
				Statement myState = myConnection.createStatement();
				String sqlCommand = "INSERT INTO Activity (CoreActivity, SubActivities) VALUES (-1, \"1 4 12 9\");";
				myState.execute(sqlCommand);
			} catch (Exception e) {
				
			}
			
		List<Integer> output2 = new ArrayList<Integer>();
		List<String> los = new ArrayList<String>();
		los.add("445");
		output2 = SetUpActionQueueIfNecessary.setup(output2, los);
		System.out.println("Should be 4: " + output2.size());
	}
}
