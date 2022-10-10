package EnvAndDatabaseServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import MainLLF.Constants;
import Structure.Env;
import Structure.PixelColorRange;
import Structure.Sense;

public class UploadOrientationChangesToDB {
	public static void upload(Env envIn) {
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			for (int i = 0; i < envIn.abstractEnv.senses.size(); i++) {
				Sense currSense = envIn.abstractEnv.senses.get(i);
				PixelColorRange pcr5 = new PixelColorRange(currSense.orientation.color);
				String sqlCommand = "SELECT OrientationChange FROM Sense WHERE id=" + currSense.dbId + ";";
				ResultSet rs = myState.executeQuery(sqlCommand);
				rs.next();
				try {
					int orChangeId = rs.getInt("OrientationChange");
					sqlCommand = "UPDATE OrientationChange SET HeightChange=" + currSense.orientationChanges.heightChange + ", WidthChange=" + currSense.orientationChanges.widthChange + ", RotationChange=" + currSense.orientationChanges.rotationChange + ", xChange=" + currSense.orientationChanges.xChange + ", yChange=" + currSense.orientationChanges.yChange + ", colorChange=\"" + pcr5.color + "\" WHERE id=" + orChangeId + ";";
					myState.addBatch(sqlCommand);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			myState.executeBatch();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
