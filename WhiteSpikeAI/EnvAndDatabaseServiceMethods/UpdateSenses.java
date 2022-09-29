package EnvAndDatabaseServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import EnvAndDatabaseServiceMethods.ComparisonScoreBasedOnOrientation;
import MainLLF.Constants;
import Structure.Env;
import Structure.Orientation;
import Structure.Sense;
import Structure.OrientationChanges;

public class UpdateSenses {
	public static class ComparisonClass{
		public Map<Integer, Sense> comparisons = new HashMap<Integer, Sense>();
		int highestScore = -99999;
		int secondHighestScore = -99999;
		public ComparisonClass() {}
	}
	
	
	public static Env update(List<Sense> sensesIn, Env oldEnvIn) throws SQLException {
		//start database connection for later
		Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
		Statement myState = myConnection.createStatement();
		String removeForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=0;";
		Statement removeChecksState = myConnection.createStatement();
		removeChecksState.execute(removeForeignKeyChecksCommand);
		//split senses up into lists by senseDef
		Map<String, List<Sense>> sensesBySenseDef = new HashMap<String, List<Sense>>();
		for (int i = 0; i < sensesIn.size(); i++) {
			Sense currSense = sensesIn.get(i);
			String currDef = currSense.definition.toString();
			if (sensesBySenseDef.containsKey(currDef)) {
				sensesBySenseDef.get(currDef).add(currSense);
			} else {
				List<Sense> newList = new ArrayList<Sense>();
				newList.add(currSense);
				sensesBySenseDef.put(currDef, newList);
			}
		}
		
		//connect new senses to potential matches from old env
		for (int i = 0; i < oldEnvIn.abstractEnv.senses.size(); i++) {
			Sense currSense = oldEnvIn.abstractEnv.senses.get(i);
			String currDef = currSense.definition.toString();
			if (sensesBySenseDef.containsKey(currDef)) {
				List<Sense> potentialMatches = sensesBySenseDef.get(currDef);
				for (int j = 0; j < potentialMatches.size(); j++) {
					Sense currPMSense = potentialMatches.get(j);
					ComparisonClass cc = currPMSense.comparisonClass;
					int csbooScore = ComparisonScoreBasedOnOrientation.get(currPMSense.orientation, currSense.orientation);
					if (csbooScore > 0) {
						cc.comparisons.put(csbooScore, currSense);
					}	
				}
			}
		}
		
		
		//decide on matches and set env senses to the appropriate new ones
		Set<String> senseDefKeys = sensesBySenseDef.keySet();
		Iterator<String> iter = senseDefKeys.iterator();
		for (int i = 0; i < senseDefKeys.size(); i++) { //iterate over each senseDef
			List<Sense> currSenseDefSenses = sensesBySenseDef.get(iter.next());
			Map<Sense, Sense> newSenseToOldSenseMatches = new HashMap<Sense, Sense>();
			for (int k = 0; k < currSenseDefSenses.size(); k++) { //iterate over each sense for the type of senseDef
				Sense currSense = currSenseDefSenses.get(k);
				Set<Integer> keys = currSense.comparisonClass.comparisons.keySet();
				Iterator<Integer> iter2 = keys.iterator();
				for (int j = 0; j < keys.size(); j++) { //iterate over each sense's possible matches and assign the highest score
					int currScore = iter2.next();
					if (currScore > currSense.comparisonClass.highestScore) {
						currSense.comparisonClass.secondHighestScore = currSense.comparisonClass.highestScore;
						currSense.comparisonClass.highestScore = currScore;
						
					} else if (currScore > currSense.comparisonClass.secondHighestScore) {
						currSense.comparisonClass.secondHighestScore = currScore;
					}
				}
				Sense oldMatchingSense = currSense.comparisonClass.comparisons.get(currSense.comparisonClass.highestScore);
				try {
					newSenseToOldSenseMatches.put(oldMatchingSense, currSense);
				} catch (Exception e) {
					Sense blockerSense = newSenseToOldSenseMatches.get(oldMatchingSense);
					if (blockerSense.comparisonClass.highestScore < currSense.comparisonClass.highestScore) {
						newSenseToOldSenseMatches.replace(oldMatchingSense, currSense);
					} else {
						Sense secondOldMatchingSense = currSense.comparisonClass.comparisons.get(currSense.comparisonClass.secondHighestScore);
						try {
							newSenseToOldSenseMatches.put(secondOldMatchingSense, currSense);
						} catch (Exception f) {
							Sense blockerSense2 = newSenseToOldSenseMatches.get(secondOldMatchingSense);
							if (blockerSense2.comparisonClass.secondHighestScore < currSense.comparisonClass.secondHighestScore) {
								newSenseToOldSenseMatches.replace(secondOldMatchingSense, currSense);
							}
						}
					}
				}
				
			}
			
			//apply newSenseToOldSenseMatches to env
			Iterator<Sense> iter3 = newSenseToOldSenseMatches.keySet().iterator();
			int size = newSenseToOldSenseMatches.keySet().size();
			for (int j = 0; j < size; j++) {
				Sense oldSense = iter3.next();
				Sense newSense = newSenseToOldSenseMatches.get(oldSense);
				newSense.orientationChanges = extractOrientationChanges(newSense.orientation, oldSense.orientation);
				List<Sense> envSenses = oldEnvIn.abstractEnv.senses;
				
				//update database
				String sqlCommand = "SELECT Sense.id AS SenseID FROM Sense INNER JOIN SenseDefinition ON SenseDefinition.Definition=\"" + oldSense.definition.toString() + "\" AND Sense.SenseDefinition = SenseDefinition.id INNER JOIN Orientation ON Sense.Orientation= Orientation.id AND Orientation.Height=" + oldSense.orientation.height + " AND Orientation.Width=" + oldSense.orientation.width + " AND Orientation.Rotation=" + oldSense.orientation.rotation + " AND Orientation.x=" + oldSense.orientation.position.x + " AND Orientation.y=" + oldSense.orientation.position.y + " AND Orientation.r=" + oldSense.orientation.color.getRed() + " AND Orientation.g=" + oldSense.orientation.color.getGreen() + " AND Orientation.b=" + oldSense.orientation.color.getBlue() + " LIMIT 1;";
				ResultSet output = myState.executeQuery(sqlCommand);
				output.next();
				int senseDBId = output.getInt("SenseID");
				sqlCommand = "UPDATE Sense INNER JOIN Orientation ON Sense.Orientation = Orientation.id SET Orientation.Height=" + newSense.orientation.height + ", Orientation.Width=" + newSense.orientation.width + ", Orientation.r=" + newSense.orientation.color.getRed() + ", Orientation.g=" + newSense.orientation.color.getGreen() + ", Orientation.b=" + newSense.orientation.color.getBlue() + ", Orientation.x=" + newSense.orientation.position.x + ", Orientation.y=" + newSense.orientation.position.y + " WHERE Sense.id=" + senseDBId + ";";
				myState.addBatch(sqlCommand);
				//remove from sensesIn
				sensesIn.remove(newSense);
				//replace oldSense with newSense
				int index = envSenses.indexOf(oldSense);
				envSenses.set(index, newSense);
				oldEnvIn.abstractEnv.recentlyChangedOldSenses.add(index);
			}
			myState.executeBatch();
		}
		//handle new senses that did not replace old ones
		
		
		int totalNumberOfEnvs = -2;
		String sqlSelectCommand = "SELECT COUNT(*) AS total FROM Env;";
		ResultSet rs0 = myState.executeQuery(sqlSelectCommand);
		rs0.next();
		try {
			totalNumberOfEnvs = rs0.getInt("total");
		} catch (Exception e) {
			
		}
		totalNumberOfEnvs++;
		
		
		for (int i = 0; i < sensesIn.size(); i++) {
			Sense currentSense = sensesIn.get(i);
			oldEnvIn.abstractEnv.senses.add(currentSense);
			oldEnvIn.abstractEnv.recentlyAddedSenses.add(currentSense);

			
			String currDef = currentSense.definition.toString();
			String sqlCommand = "SELECT id FROM SenseDefinition WHERE Definition=" + currDef + ";";
			ResultSet rs1 = myState.executeQuery(sqlCommand);
			rs1.next();
			try {
				int senseDefId = rs1.getInt("id");
				sqlCommand = "SELECT COUNT(*) AS total FROM Orientation;";
				ResultSet rs2 = myState.executeQuery(sqlCommand);
				rs2.next();
				try {
					int orientationCount = rs2.getInt("total");
					orientationCount++;
					
					sqlCommand = "SELECT COUNT(*) AS total FROM OrientationChanges;";
					ResultSet rs4 = myState.executeQuery(sqlCommand);
					rs4.next();
					try {
						int orientationChangesCount = rs4.getInt("total");
						orientationChangesCount++;
						
						sqlCommand = "INSERT INTO Orientation (Height, Width, Rotation, x, y, r, g, b) VALUES (" + currentSense.orientation.height + ", " + currentSense.orientation.width + ", " + currentSense.orientation.rotation + ", " + currentSense.orientation.position.x + ", " + currentSense.orientation.position.y + ", " + currentSense.orientation.color.getRed() + ", " + currentSense.orientation.color.getGreen() + ", " + currentSense.orientation.color.getBlue() +");";
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO OrientationChanges (HeightChange, WidthChange, RotationChange, xChange, yChange, rChange, gChange, bChange) VALUES (" + currentSense.orientationChanges.heightChange + ", " + currentSense.orientationChanges.widthChange + ", " + currentSense.orientationChanges.rotationChange + ", " + currentSense.orientationChanges.xChange + ", " + currentSense.orientationChanges.yChange + ", " + currentSense.orientationChanges.rChange + ", " + currentSense.orientationChanges.gChange + ", " + currentSense.orientationChanges.bChange +");";
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Sense (Env, SenseDefinition, Orientation, activitiesExtracted) VALUES (" + totalNumberOfEnvs + ", " + senseDefId + ", " + orientationCount + ", " + orientationChangesCount + ", false);";
						myState.addBatch(sqlCommand);
					} catch (Exception e) {
						
					}
				} catch (Exception e) {
					
				}			

			} catch (Exception e) {
				sqlCommand = "SELECT COUNT(*) AS total FROM SenseDefinition;";
				ResultSet rs2 = myState.executeQuery(sqlCommand);
				rs2.next();
				try {
					int senseDefCount = rs2.getInt("total");
					senseDefCount++;
					sqlCommand = "SELECT COUNT(*) AS total FROM Orientation;";
					ResultSet rs3 = myState.executeQuery(sqlCommand);
					rs3.next();
					try {
						int orientationCount = rs3.getInt("total");
						orientationCount++;
						
						sqlCommand = "SELECT COUNT(*) AS total FROM OrientationChanges;";
						ResultSet rs4 = myState.executeQuery(sqlCommand);
						rs4.next();
						try {
							int orientationChangesCount = rs4.getInt("total");
							orientationChangesCount++;
							
							sqlCommand = "INSERT INTO SenseDefinition (Definition) VALUES (" + currDef + ");";
							myState.addBatch(sqlCommand);
							sqlCommand = "INSERT INTO Orientation (Height, Width, Rotation, x, y, r, g, b) VALUES (" + currentSense.orientation.height + ", " + currentSense.orientation.width + ", " + currentSense.orientation.rotation + ", " + currentSense.orientation.position.x + ", " + currentSense.orientation.position.y + ", " + currentSense.orientation.color.getRed() + ", " + currentSense.orientation.color.getGreen() + ", " + currentSense.orientation.color.getBlue() +");";
							myState.addBatch(sqlCommand);
							sqlCommand = "INSERT INTO OrientationChanges (HeightChange, WidthChange, RotationChange, xChange, yChange, rChange, gChange, bChange) VALUES (" + currentSense.orientationChanges.heightChange + ", " + currentSense.orientationChanges.widthChange + ", " + currentSense.orientationChanges.rotationChange + ", " + currentSense.orientationChanges.xChange + ", " + currentSense.orientationChanges.yChange + ", " + currentSense.orientationChanges.rChange + ", " + currentSense.orientationChanges.gChange + ", " + currentSense.orientationChanges.bChange +");";
							myState.addBatch(sqlCommand);
							sqlCommand = "INSERT INTO Sense (Env, SenseDefinition, Orientation, OrientationChanges, activitiesExtracted) VALUES (" + totalNumberOfEnvs + ", " + senseDefCount + ", " + orientationCount + ", "  + orientationChangesCount + ", false);";
							myState.addBatch(sqlCommand);
						} catch (Exception f) {
							
						}
						

					} catch (Exception f) {
						
					}
				} catch (Exception f) {
					
				}

			}
			sqlCommand = "SELECT COUNT(*) AS total FROM Sense;";
			ResultSet rs3 = myState.executeQuery(sqlCommand);
			rs3.next();
			try {
				int numOfSenses = rs3.getInt("total");
				oldEnvIn.abstractEnv.dbSenseList = oldEnvIn.abstractEnv.dbSenseList + (numOfSenses + 1) + " ";
			} catch (Exception e) {
				
			}
			myState.executeBatch();
		}
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
		LocalDateTime localDate = LocalDateTime.now();
		String timestamp = dtf.format(localDate);
		String createEnvSQLCommand = "INSERT INTO Env (CpuUsage, CreationDateTime, Senses) VALUES (" + oldEnvIn.rawEnv.currentCpuUsage +  ", \"" + timestamp + "\", \"" + oldEnvIn.abstractEnv.dbSenseList + "\");";
		myState.execute(createEnvSQLCommand);
		
		String readdForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=1;";
		Statement readdChecksState = myConnection.createStatement();
		readdChecksState.execute(readdForeignKeyChecksCommand);
		return oldEnvIn;
	}
	
	public static OrientationChanges extractOrientationChanges(Orientation newSenseOrIn, Orientation oldSenseOrIn) {
		OrientationChanges oc = new OrientationChanges();
		//height
		int heightChange = newSenseOrIn.height - oldSenseOrIn.height;
		if (heightChange > 0) {
			oc.heightChange = 1;
		} else if (heightChange < 0) {
			oc.heightChange = -1;
		} else {
			oc.heightChange = 0;
		}
		
		//width
		int widthChange = newSenseOrIn.width - oldSenseOrIn.width;
		if (widthChange > 0) {
			oc.widthChange = 1;
		} else if (widthChange < 0) {
			oc.widthChange = -1;
		} else {
			oc.widthChange = 0;
		}
		//rotation
		double rotationChange = newSenseOrIn.rotation - oldSenseOrIn.rotation;
		if (rotationChange > 0) {
			oc.rotationChange = 1;
		} else if (rotationChange < 0) {
			oc.rotationChange = -1;
		} else {
			oc.rotationChange = 0;
		}
		//r
		int rChange = newSenseOrIn.color.getRed() - oldSenseOrIn.color.getRed();
		if (rChange > 0) {
			oc.rChange = 1;
		} else if (rChange < 0) {
			oc.rChange = -1;
		} else {
			oc.rChange = 0;
		}
		//g
		int gChange = newSenseOrIn.color.getGreen() - oldSenseOrIn.color.getGreen();
		if (gChange > 0) {
			oc.gChange = 1;
		} else if (gChange < 0) {
			oc.gChange = -1;
		} else {
			oc.gChange = 0;
		}
		//b
		int bChange = newSenseOrIn.color.getBlue() - oldSenseOrIn.color.getBlue();
		if (bChange > 0) {
			oc.bChange = 1;
		} else if (bChange < 0) {
			oc.bChange = -1;
		} else {
			oc.bChange = 0;
		}
		//x
		int xChange = newSenseOrIn.position.x - oldSenseOrIn.position.x;
		if (xChange > 0) {
			oc.xChange = 1;
		} else if (xChange < 0) {
			oc.xChange = -1;
		} else {
			oc.xChange = 0;
		}
		//y
		int yChange = newSenseOrIn.position.y - oldSenseOrIn.position.y;
		if (yChange > 0) {
			oc.yChange = 1;
		} else if (yChange < 0) {
			oc.yChange = -1;
		} else {
			oc.yChange = 0;
		}
		
		return oc;
	}
}
