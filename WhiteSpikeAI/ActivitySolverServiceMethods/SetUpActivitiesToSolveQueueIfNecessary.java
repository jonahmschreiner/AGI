package ActivitySolverServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import MainLLF.Constants;

public class SetUpActivitiesToSolveQueueIfNecessary {
	public static List<Integer> setup (List<Integer> queueIn){ //come back and change so it only selects a batch instead
		if (queueIn.size() == 0) {
			try {
				Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
				Statement myState = myConnection.createStatement();
				String sqlCommand = "SELECT id FROM Activity WHERE SolvedStatus=0 ORDER BY id ASC, numOfSolveAttempts ASC LIMIT 10;";
				ResultSet rs = myState.executeQuery(sqlCommand);
				while (true) {
					try {
						rs.next();
						int currId = rs.getInt("id");
						queueIn.add(currId);
					} catch (Exception e) {
						break;
					}
				}
			} catch (Exception e) {
				
			}
		}
		return queueIn;
	}
}
