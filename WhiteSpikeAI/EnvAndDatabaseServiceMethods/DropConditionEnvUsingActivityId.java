package EnvAndDatabaseServiceMethods;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class DropConditionEnvUsingActivityId {
	public static void exec(int activityIdIn, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT ConditionEnv FROM Activity WHERE id=" + activityIdIn + ";";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			int conditionEnvId = rs.getInt("ConditionEnv");
			sqlCommand = "SET FOREIGN_KEY_CHECKS=0;";
			myState.execute(sqlCommand);
			sqlCommand = "SELECT Senses FROM ConditionEnv WHERE id=" + conditionEnvId + ";";
			rs = myState.executeQuery(sqlCommand);
			rs.next();
			String sensesString = rs.getString("Senses");
			String[] sensesStringArray = sensesString.split(" ");
			for (int i = 0; i < sensesStringArray.length; i++) {
				int currSenseId = Integer.valueOf(sensesStringArray[i]);
				sqlCommand = "SELECT ConditionOrientation, ConditionSenseDefinition, ConditionOrientationChange FROM ConditionSense WHERE id=" + currSenseId + ";";
				rs = myState.executeQuery(sqlCommand);
				rs.next();
				int currConOrId = rs.getInt("ConditionOrientation");
				int currConSenseDefId = rs.getInt("ConditionSenseDefinition");
				int currConOrChangeId = rs.getInt("ConditionOrientationChange");
				
				sqlCommand = "DELETE FROM ConditionOrientation WHERE id=" + currConOrId + ";";
				myState.execute(sqlCommand);
				sqlCommand = "DELETE FROM ConditionSenseDefinition WHERE id=" + currConSenseDefId + ";";
				myState.execute(sqlCommand);
				sqlCommand = "DELETE FROM ConditionOrientationChange WHERE id=" + currConOrChangeId + ";";
				myState.execute(sqlCommand);
				sqlCommand = "DELETE FROM ConditionSense WHERE id=" + currSenseId + ";";
				myState.execute(sqlCommand);
			}
			sqlCommand = "DELETE FROM ConditionEnv WHERE id=" + conditionEnvId + ";";
			myState.execute(sqlCommand);
			sqlCommand = "SET FOREIGN_KEY_CHECKS=1;";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
