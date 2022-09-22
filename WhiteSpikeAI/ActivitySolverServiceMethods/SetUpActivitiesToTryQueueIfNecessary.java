package ActivitySolverServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import MainLLF.Constants;

public class SetUpActivitiesToTryQueueIfNecessary { //come back and change so it only selects a batch instead
	public static List<Integer> setup (List<Integer> queueIn){
		if (queueIn.size() == 0) {
			try {
				Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
				Statement myState = myConnection.createStatement();
				String sqlCommand = "SELECT id FROM Activity WHERE SolvedStatus=1 ORDER BY id ASC;";
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
