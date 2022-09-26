package ActivitySolverServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import MainLLF.Constants;

public class SetUpActivitiesToTryQueueIfNecessary { //come back and change so it only selects a batch instead
	public static List<String> setup (List<String> queueIn){
		if (queueIn.size() == 0) {
			List<Integer> allIndis = new ArrayList<Integer>();
			try {
				Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
				Statement myState = myConnection.createStatement();
				String sqlCommand = "SELECT id FROM Activity WHERE SolvedStatus=1 ORDER BY id ASC;";
				ResultSet rs = myState.executeQuery(sqlCommand);
				//individual activities
				while (true) {
					try {
						rs.next();
						int currId = rs.getInt("id");
						queueIn.add(String.valueOf(currId));
						allIndis.add(currId);
					} catch (Exception e) {
						break;
					}
				}
				queueIn.addAll(combosFromIndis(allIndis, 0)); //need this to go back to 2 at some point

			} catch (Exception e) {
				
			}
		}
		return queueIn;
	}
	
	public static List<String> combosFromIndis (List<Integer> indisIn, int numOfComboLayers){
		List<String> output = new ArrayList<String>();
		for (int i = 0; i < indisIn.size(); i++) {
			int currIndi = indisIn.get(i);
			if (numOfComboLayers > 0) {
				for (int j = 0; j < indisIn.size(); j++) {
					String str = currIndi + " " + indisIn.get(j);
					output.add(str);
					if (numOfComboLayers > 1) {
						for (int k = 0; k < indisIn.size(); k++) {
							String str2 = str + " " + indisIn.get(k);
							output.add(str2);
						}
					}
				}
			}

		}
		
		
		return output;
	}
}
