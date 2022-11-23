package ActivitySolverServiceMethods;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import MainLLF.Constants;

public class SetUpActivitiesToSolveQueueIfNecessary {
	public static List<Integer> setup (List<Integer> queueIn, FileWriter fw, Connection myConnection) throws IOException{ //come back and change so it only selects a batch instead
		if (queueIn.size() == 0) {
			try {
				Statement myState = myConnection.createStatement();
				String sqlCommand = "SELECT id FROM Activity WHERE SolvedStatus=0 ORDER BY id ASC, numOfSolveAttempts ASC LIMIT 10;";
				ResultSet rs = myState.executeQuery(sqlCommand);
				while (true) {
					try {
						rs.next();
						int currId = rs.getInt("id");
						queueIn.add(currId);
					} catch (Exception e) {
						fw.append("Break");
						fw.flush();
						break;
					}
				}
			} catch (Exception e) {
				fw.append(e.getMessage());
				fw.flush();
			}
			
		}
		return queueIn;
	}
}
