package EnvAndDatabaseServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import MainLLF.Constants;
import Structure.DBObjectHighestValueResults;
import Structure.Env;
import Structure.PixelColorRange;
import Structure.Sense;

public class UploadConditionEnvToDB {
	public static Env exec(Env conditionEnvIn, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT COUNT(*) AS total FROM ConditionEnv;";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			int conditionEnvId = rs.getInt("total") + 1;
			sqlCommand = "SELECT COUNT(*) AS total FROM ConditionSense;";
			rs = myState.executeQuery(sqlCommand);
			rs.next();
			int firstConditionSenseId = rs.getInt("total") + 1;
			sqlCommand = "SELECT COUNT(*) AS total FROM ConditionOrientation;";
			rs = myState.executeQuery(sqlCommand);
			rs.next();
			int firstConditionOrientationId = rs.getInt("total") + 1;
			sqlCommand = "SELECT COUNT(*) AS total FROM ConditionOrientationChange;";
			rs = myState.executeQuery(sqlCommand);
			rs.next();
			int firstConditionOrientationChangeId = rs.getInt("total") + 1;
			sqlCommand = "SELECT COUNT(*) AS total FROM ConditionSenseDefinition;";
			rs = myState.executeQuery(sqlCommand);
			rs.next();
			int firstConditionSenseDefId = rs.getInt("total") + 1;
			String removeForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=0;";
			Statement removeChecksState = myConnection.createStatement();
			removeChecksState.execute(removeForeignKeyChecksCommand);
			
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
			LocalDateTime localDate = LocalDateTime.now();
			String timestamp = dtf.format(localDate);
			String createEnvSQLCommand = "INSERT INTO ConditionEnv (CurrentDateTime, CpuUsage, MouseX, MouseY, CreationDateTime, Senses) VALUES (\"" + conditionEnvIn.rawEnv.currentDateTime.toString() + "\", " + conditionEnvIn.rawEnv.currentCpuUsage + ", " + conditionEnvIn.rawEnv.mouseLocation.x + ", " + conditionEnvIn.rawEnv.mouseLocation.y +  ", \"" + timestamp + "\", \"";
			String EnvSenseListSerializedString = "";
			for (int i = 0; i < conditionEnvIn.abstractEnv.senses.size(); i++) {
				Sense currentSense = conditionEnvIn.abstractEnv.senses.get(i);
				
				//SenseDefinition
				String definitionString = currentSense.definition.toString();
				int matchingSenseDefId = -1;
				sqlCommand = "INSERT INTO ConditionSenseDefinition (Definition) VALUES (\"" + definitionString + "\");";
				myState.execute(sqlCommand);
				matchingSenseDefId = firstConditionSenseDefId + i;
				
				
				
	
				//Orientation
				int matchingOrientationId = -1;
				PixelColorRange pcr = new PixelColorRange(currentSense.orientation.color);
				sqlCommand = "INSERT INTO ConditionOrientation (Height, Width, Rotation, x, y, color) VALUES (" + currentSense.orientation.height + ", " + currentSense.orientation.width + ", " + currentSense.orientation.rotation + ", " + currentSense.orientation.position.x + ", " + currentSense.orientation.position.y + ", \"" + pcr.color + "\");";
				myState.execute(sqlCommand);
				matchingOrientationId = firstConditionOrientationId + i;
				
				
				
				//Orientation Change
				if (currentSense.orientationChanges.colorChange == null) {
					currentSense.orientationChanges.colorChange = "null";
				}
				int matchingOChangeId = -1;
				sqlCommand = "INSERT INTO ConditionOrientationChange (HeightChange, WidthChange, RotationChange, xChange, yChange, defChange, colorChange) VALUES (" + currentSense.orientationChanges.heightChange + ", " + currentSense.orientationChanges.widthChange + ", " + currentSense.orientationChanges.rotationChange + ", " + currentSense.orientationChanges.xChange + ", " + currentSense.orientationChanges.yChange + ", \"" + currentSense.orientationChanges.defChange + "\", \"" + currentSense.orientationChanges.colorChange + "\");";
				myState.execute(sqlCommand);
				matchingOChangeId = firstConditionOrientationChangeId + i;
				
				
				
				
				//Sense
				sqlCommand = "INSERT INTO ConditionSense (ConditionEnv, ConditionSenseDefinition, ConditionOrientation, ConditionOrientationChange) VALUES (" + conditionEnvId + ", " + matchingSenseDefId + ", " + matchingOrientationId + ", " + matchingOChangeId + ");";
				myState.execute(sqlCommand);
				DBObjectHighestValueResults dbocr = new DBObjectHighestValueResults(myConnection);
				//currentSense.dbId = dbocr.conditionSenseCount;
				EnvSenseListSerializedString = EnvSenseListSerializedString + (firstConditionSenseId + i) + " ";
				conditionEnvIn.abstractEnv.dbSenseList = conditionEnvIn.abstractEnv.dbSenseList + (firstConditionSenseId + i) + " ";
				
			}
			
			createEnvSQLCommand = createEnvSQLCommand + EnvSenseListSerializedString + "\");";
			myState.execute(createEnvSQLCommand);
			DBObjectHighestValueResults dbocr = new DBObjectHighestValueResults(myConnection);
			conditionEnvIn.dbId = dbocr.conditionEnvCount;
			System.out.println("huh: " + conditionEnvIn.dbId);
			
			removeForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=1;";
			removeChecksState.execute(removeForeignKeyChecksCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return conditionEnvIn;
	}
}
