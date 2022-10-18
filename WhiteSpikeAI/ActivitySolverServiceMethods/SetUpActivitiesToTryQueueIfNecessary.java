package ActivitySolverServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import MainLLF.Constants;

public class SetUpActivitiesToTryQueueIfNecessary { //come back and change so it only does this once and adds newly-solved activities as they are solved
	public static List<String> setup (List<String> queueIn, int activityIdIn){
		if (queueIn.size() == 0) {
			List<Integer> allSimilarIndis = new ArrayList<Integer>();
			String definition = null;
			try {
				Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
				Statement myState = myConnection.createStatement();
				String sqlCommand = "SELECT SenseDefinition.Definition FROM Activity INNER JOIN Sense ON Activity.AssociatedSense=Sense.id AND Activity.id=" + activityIdIn + " INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id;";
				ResultSet rs1 = myState.executeQuery(sqlCommand);
				rs1.next();
				definition = rs1.getString("SenseDefinition.Definition");
				sqlCommand = "SELECT Activity.id FROM Activity INNER JOIN Sense ON Activity.AssociatedSense=Sense.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id AND SenseDefinition.Definition=\"" + definition + "\" AND Activity.SolvedStatus=1 ORDER BY Activity.id ASC;";
				ResultSet rs = myState.executeQuery(sqlCommand);
				//individual activities
				while (true) {
					try {
						rs.next();
						int currId = rs.getInt("id");
						queueIn.add(String.valueOf(currId));
						allSimilarIndis.add(currId);
					} catch (Exception e) {
						break;
					}
				}
				queueIn.addAll(combosFromIndis(allSimilarIndis, 1));
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
			List<Integer> allIndis = new ArrayList<Integer>();
			try {
				Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
				Statement myState = myConnection.createStatement();
				String sqlCommand = "";
				if (definition == null) {
					sqlCommand = "SELECT id FROM Activity WHERE SolvedStatus=1 ORDER BY id ASC;";
				} else {
					sqlCommand = "SELECT Activity.id FROM Activity INNER JOIN Sense ON Activity.AssociatedSense=Sense.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id AND SenseDefinition.Definition !=\"" + definition + "\" AND Activity.SolvedStatus=1 ORDER BY Activity.id ASC;";
				}
				
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
				queueIn.addAll(combosFromIndis(allIndis, 0)); //need this to go back to 1 at some point (see comment at top of class)

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
					int current = indisIn.get(j);
					if (currIndi != current) {
						String str = currIndi + " " + current;
						output.add(str);
					}
					
				}
			}

		}
		
		
		return output;
	}
}
