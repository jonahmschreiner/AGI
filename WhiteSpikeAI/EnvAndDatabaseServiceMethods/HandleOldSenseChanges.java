package EnvAndDatabaseServiceMethods;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import Structure.AbstractEnv;
import Structure.Blob;
import Structure.BoundingBox;
import Structure.Env;
import Structure.Pixel;
import Structure.Sense;
import EnvAndDatabaseServiceMethods.UpdateSenses;
import MainLLF.Constants;

public class HandleOldSenseChanges {
	public static Env exec(List<Sense> newSensesIn, Env oldEnvIn){
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String removeForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=0;";
			Statement removeChecksState = myConnection.createStatement();
			removeChecksState.execute(removeForeignKeyChecksCommand);
			for (int i = 0; i < oldEnvIn.abstractEnv.senses.size(); i++) {
				Sense currSense = oldEnvIn.abstractEnv.senses.get(i);
				if (aBoxOverlaps(newSensesIn, currSense)) {
					Blob newBlob = new Blob();
					for (int j = 0; j < currSense.blob.pixels.size(); j++) {
						Pixel currPixel = currSense.blob.pixels.get(j);
						if (currPixel.color.equals(new Color(oldEnvIn.rawEnv.currentDisplay.getRGB(currPixel.position.x, currPixel.position.y)))) {
							newBlob.pixels.add(currPixel);
						}
					}
					if (newBlob.pixels.size() == 0) {
						removeSenseFromAbstractEnvDBSenseListAndDB(currSense.dbId, oldEnvIn.abstractEnv);
						oldEnvIn.abstractEnv.senses.remove(currSense);
						//remove it from abstractEnv dbSenseList
						
						
					} else {
						Sense newSense = BlobToSense.getSense(newBlob);
						newSense.blob = newBlob;
						newSense.dbId = currSense.dbId;
						newSense.orientationChanges = UpdateSenses.extractOrientationChanges(newSense.orientation, currSense.orientation);
						oldEnvIn.abstractEnv.senses.set(i, newSense);

						//update database
						String sqlCommand = "SELECT Sense.id AS SenseID FROM Sense INNER JOIN SenseDefinition ON SenseDefinition.Definition=\"" + currSense.definition.toString() + "\" AND Sense.SenseDefinition = SenseDefinition.id INNER JOIN Orientation ON Sense.Orientation= Orientation.id AND Orientation.Height=" + currSense.orientation.height + " AND Orientation.Width=" + currSense.orientation.width + " AND Orientation.Rotation=" + currSense.orientation.rotation + " AND Orientation.x=" + currSense.orientation.position.x + " AND Orientation.y=" + currSense.orientation.position.y + " AND Orientation.r=" + currSense.orientation.color.getRed() + " AND Orientation.g=" + currSense.orientation.color.getGreen() + " AND Orientation.b=" + currSense.orientation.color.getBlue() + " LIMIT 1;";
						ResultSet output = myState.executeQuery(sqlCommand);
						output.next();
						int senseDBId = output.getInt("SenseID");
						sqlCommand = "UPDATE Sense INNER JOIN Orientation ON Sense.Orientation = Orientation.id SET Orientation.Height=" + newSense.orientation.height + ", Orientation.Width=" + newSense.orientation.width + ", Orientation.r=" + newSense.orientation.color.getRed() + ", Orientation.g=" + newSense.orientation.color.getGreen() + ", Orientation.b=" + newSense.orientation.color.getBlue() + ", Orientation.x=" + newSense.orientation.position.x + ", Orientation.y=" + newSense.orientation.position.y + " WHERE Sense.id=" + senseDBId + ";";
						myState.addBatch(sqlCommand);
					}
				}
			}
			String updateEnvSQLCommand = "UPDATE Env (Senses) VALUES (" + oldEnvIn.abstractEnv.dbSenseList + "\");";
			myState.execute(updateEnvSQLCommand);
			String readdForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=1;";
			Statement readdChecksState = myConnection.createStatement();
			readdChecksState.execute(readdForeignKeyChecksCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return oldEnvIn;
	}
	
	public static void removeSenseFromAbstractEnvDBSenseListAndDB (int dbIdIn, AbstractEnv aeIn) {
		//remove from env db sense list
		String[] array = aeIn.dbSenseList.split(" ");
		List<String> strList = Arrays.asList(array);
		strList.remove("" + dbIdIn);
		String composite = "";
		for (int i = 0; i < strList.size(); i++) {
			composite = composite + strList.get(i) + " ";
		}
		aeIn.dbSenseList = composite;
		
		//remove from db
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "DROP Sense WHERE id=" + dbIdIn + ";";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean aBoxOverlaps (List<Sense> sensesIn, Sense currSense) {
		boolean output = false;
		for (int i = 0; i < sensesIn.size(); i++) {
			output = boxesOverlap(sensesIn.get(i).orientation.boundingBox, currSense.orientation.boundingBox);
			if (output) {
				break;
			}
		}
		return output;
	}
	
	public static boolean boxesOverlap (BoundingBox newSense, BoundingBox potOldSense) {
		boolean output = false;
		if (xInRange(newSense, potOldSense) && yInRange(newSense, potOldSense)) {
			output = true;
		}
		return output;
	}
	
	public static boolean xInRange(BoundingBox newSense, BoundingBox potOldSense) {
		boolean output = false;
		if ((newSense.maxX >= potOldSense.minX && newSense.maxX <= potOldSense.maxX)
				|| newSense.minX >= potOldSense.minX && newSense.minX <= potOldSense.maxX
				|| newSense.minX < potOldSense.minX && newSense.maxX > potOldSense.maxX) {
			output = true;
		}
		return output;
	}
	
	public static boolean yInRange(BoundingBox newSense, BoundingBox potOldSense) {
		boolean output = false;
		if ((newSense.maxY >= potOldSense.minY && newSense.maxY <= potOldSense.maxY)
				|| newSense.minY >= potOldSense.minY && newSense.minY <= potOldSense.maxY
				|| newSense.minY < potOldSense.minY && newSense.maxY > potOldSense.maxY) {
			output = true;
		}
		return output;
	}
}
