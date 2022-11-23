package EnvAndDatabaseServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import MainLLF.Constants;
import Structure.Env;
import Structure.PixelColorRange;
import Structure.Sense;

public class UpdateSenseToBeSenseIn {
	public static void update (Sense senseToBeUpdated, Sense senseIn, Env envIn, Connection myConnection) {
		senseIn.orientationChanges = UpdateSenses.extractOrientationChanges(senseIn.orientation, senseToBeUpdated.orientation);
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT * FROM Sense WHERE id=" + senseToBeUpdated.dbId + ";";
			senseIn.dbId = senseToBeUpdated.dbId;
			envIn.abstractEnv.recentlyChangedOldSenses.add(envIn.abstractEnv.senses.indexOf(senseToBeUpdated));
			envIn.abstractEnv.recentlyAddedSenses.remove(senseIn);
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			try {
				int senseDefId = rs.getInt("SenseDefinition");
				int orId = rs.getInt("Orientation");
				int orChangeId = rs.getInt("OrientationChange");
				
				sqlCommand = "UPDATE SenseDefinition SET Definition=\"" + senseIn.definition.toString() + "\" WHERE id=" + senseDefId + ";";
				myState.execute(sqlCommand);
				PixelColorRange pcr = new PixelColorRange(senseIn.orientation.color);
				sqlCommand = "UPDATE Orientation SET Height=" + senseIn.orientation.height + ", Width=" + senseIn.orientation.width + ", Rotation=" + senseIn.orientation.rotation + ", x=" + senseIn.orientation.position.x + ", y=" + senseIn.orientation.position.y + ", color=\"" + pcr.color + "\" WHERE id=" + orId + ";";
				myState.execute(sqlCommand);
				sqlCommand = "UPDATE OrientationChange SET HeightChange=" + senseIn.orientationChanges.heightChange + ", WidthChange=" + senseIn.orientationChanges.widthChange + ", RotationChange=" + senseIn.orientationChanges.rotationChange + ", xChange=" + senseIn.orientationChanges.xChange + ", yChange=" + senseIn.orientationChanges.yChange + ", colorChange=\"" + senseIn.orientationChanges.colorChange + "\" WHERE id=" + orChangeId + ";";
				myState.execute(sqlCommand);
				String senseInDef = senseIn.definition.toString();
				if (!senseInDef.equals(senseToBeUpdated.definition.toString())) {
					sqlCommand = "UPDATE OrientationChange SET defChange=\"" + senseInDef + "\" WHERE id=" + orChangeId + ";";
					myState.execute(sqlCommand);
					senseIn.orientationChanges.defChange = senseInDef;
				} else {
					sqlCommand = "UPDATE OrientationChange SET defChange=\"NULL\" WHERE id=" + orChangeId + ";";
					myState.execute(sqlCommand);
					senseIn.orientationChanges.defChange = null;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
