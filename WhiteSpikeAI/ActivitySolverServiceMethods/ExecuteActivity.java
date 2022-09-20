package ActivitySolverServiceMethods;

import java.sql.ResultSet;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.ExecuteCoreAction;
public class ExecuteActivity {
	public static void exec(int activityId) {
		ResultSet rs = DatabaseHandler.getActivityForExecution(activityId);
		try {
			rs.next();
			int coreActivityToExecute = rs.getInt("CoreActivity");
			ExecuteCoreAction.exec(coreActivityToExecute);
		} catch (Exception e) {
			try {
				String listOfSubActivities = rs.getString("SubActivities");
				String[] arrayOfSubActivityStrings = listOfSubActivities.split(" ");
				for (int i = 0; i < arrayOfSubActivityStrings.length; i++) {
					String currStr = arrayOfSubActivityStrings[i];
					int currActivityId = Integer.valueOf(currStr);
					ExecuteActivity.exec(currActivityId);
				}
			} catch (Exception f) {
				
			}
			
		}
		
		
	}
}
