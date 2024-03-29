package ActivitySolverServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import MainLLF.Constants;

public class SetUpActionQueueIfNecessary {
	public static List<Integer> setup (List<Integer> actionQueueIn, List<String> activitiesToTryQueueIn, Connection myConnection){
		if (actionQueueIn.size() == 0) {
			List<Integer> output = new ArrayList<Integer>();
			String newActivityToPullInString = activitiesToTryQueueIn.get(0);
			String[] natpisArray = newActivityToPullInString.split(" ");
			for (int i = 0; i < natpisArray.length; i++) {
				int newActivityToPullInId =  Integer.valueOf(natpisArray[i]);
				//output.addAll(extractCoreActionListFromActivity(newActivityToPullInId, myConnection));
				output.add(newActivityToPullInId);
			}
			
			return output;
		}
		return actionQueueIn;
	}
	
	public static List<Integer> extractCoreActionListFromActivity (int activityIdIn, Connection myConnection) {
		List<Integer> output = new ArrayList<Integer>();
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT CoreActivity, SubActivities FROM Activity WHERE id=" + activityIdIn + ";";
			ResultSet rs = myState.executeQuery(sqlCommand);
			try {
				rs.next(); //if the activity is a core action activity
				int caId = rs.getInt("CoreActivity");
				if (caId == -1) {
					throw new Exception();
				}
				output.add(caId);
			} catch (Exception e) { //if the activity is a higher level activity
				String subActivityString = rs.getString("SubActivities");
				String[] subActivityArray = subActivityString.split(" ");
				for (int i = 0; i < subActivityArray.length; i++) {
					output.addAll(extractCoreActionListFromActivity(Integer.valueOf(subActivityArray[i]), myConnection));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return output;
	}
}
