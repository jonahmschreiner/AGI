package EnvAndDatabaseServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import MainLLF.Constants;
import Structure.DBObjectHighestValueResults;
import Structure.Sense;

public class InsertSenseIntoLatestEnvInDB {
	public static void insert(Sense currentSense, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			DBObjectHighestValueResults dbocr = new DBObjectHighestValueResults(myConnection);
			
			//Orientation
			String sqlCommand = "SELECT * FROM Orientation WHERE Height=" + currentSense.orientation.height + " AND Width=" + currentSense.orientation.width + " AND Rotation=" + currentSense.orientation.rotation + " AND x=" + currentSense.orientation.position.x + " AND y=" + currentSense.orientation.position.y + " AND r=" + currentSense.orientation.color.getRed() + " AND g=" + currentSense.orientation.color.getGreen() + " AND b=" + currentSense.orientation.color.getBlue() + ";";
			ResultSet indQueryOrientationRS = myState.executeQuery(sqlCommand);
			indQueryOrientationRS.next();
			int matchingOrientationId = -1;
			try {
				int idOfMatch = indQueryOrientationRS.getInt("id");
				matchingOrientationId = idOfMatch;
			} catch (Exception e) {
				sqlCommand = "INSERT INTO Orientation (Height, Width, Rotation, x, y, r, g, b) VALUES (" + currentSense.orientation.height + ", " + currentSense.orientation.width + ", " + currentSense.orientation.rotation + ", " + currentSense.orientation.position.x + ", " + currentSense.orientation.position.y + ", " + currentSense.orientation.color.getRed() + ", " + currentSense.orientation.color.getGreen() + ", " + currentSense.orientation.color.getBlue() +");";
				myState.execute(sqlCommand);
				matchingOrientationId = dbocr.orCount + 1;
			}
			
			//SenseDefinition
			String definitionString = currentSense.definition.toString();
			sqlCommand = "SELECT * FROM SenseDefinition WHERE Definition=\"" + definitionString + "\";";
			ResultSet indQueryRS = myState.executeQuery(sqlCommand);
			indQueryRS.next();
			int matchingSenseDefId = -1;
			try {
				int idOfMatch = indQueryRS.getInt("id");
				matchingSenseDefId = idOfMatch;
			} catch (Exception e) {
				sqlCommand = "INSERT INTO SenseDefinition (Definition) VALUES (\"" + definitionString + "\");";
				myState.execute(sqlCommand);
				//myState.addBatch(sqlCommand);
				matchingSenseDefId = dbocr.senseDefCount + 1;
			}
			
			
			
			sqlCommand = "INSERT INTO Sense (Env, SenseDefinition, Orientation) VALUES (" + dbocr.envCount + ", " + matchingSenseDefId + ", " + matchingOrientationId + ");";
			myState.execute(sqlCommand);
			//update env record in db
			sqlCommand = "UPDATE Env SET Senses=Senses + \"" + (dbocr.senseCount + 1) + " \";";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			
		}
	}
}
