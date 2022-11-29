package EnvAndDatabaseServiceMethods;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import Structure.DBObjectCountResults;
import Structure.Env;
import Structure.PixelColorRange;
import Structure.Sense;

public class UpdateConditionEnvInDBToBeThisEnv {
	public static Env update(Env conditionEnvIn, int activityToUpdate, Connection myConnection, FileWriter fw) {
			try {
				Statement myState = myConnection.createStatement();
				
				int conditionEnvToUpdateId = -1;
				try {
					String mySqlCommand = "SELECT ConditionEnv FROM Activity WHERE id=" + activityToUpdate + ";";
					ResultSet rs2 = myState.executeQuery(mySqlCommand);
					rs2.next();
					conditionEnvToUpdateId = rs2.getInt("ConditionEnv");
					fw.append("UPDATE CON ENV: condition env found of id " + conditionEnvToUpdateId + "\n");
					fw.flush();
					if (conditionEnvToUpdateId == 0) {
						throw new Exception();
					}
				} catch (Exception e) {
					fw.append("UPDATE CON ENV: upload con env to db called \n");
					fw.flush();
					return UploadConditionEnvToDB.exec(conditionEnvIn, myConnection);
				}
				
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
				String createEnvSQLCommand = "UPDATE ConditionEnv SET CurrentDateTime=\"" + conditionEnvIn.rawEnv.currentDateTime.toString() + "\", CpuUsage=" + conditionEnvIn.rawEnv.currentCpuUsage + ", MouseX=" + conditionEnvIn.rawEnv.mouseLocation.x + ", MouseY=" + conditionEnvIn.rawEnv.mouseLocation.y + ", CreationDateTime= \"" + timestamp + "\", Senses=\"";
				int numOfSenseDefMatches = 0;
				int numOfOrientationMatches = 0;
				int numOfOrientationChangeMatches = 0;
				int numOfSenseMatches = 0;
				String EnvSenseListSerializedString = "";
				for (int i = 0; i < conditionEnvIn.abstractEnv.senses.size(); i++) {
					Sense currentSense = conditionEnvIn.abstractEnv.senses.get(i);
					
					//SenseDefinition
					String definitionString = currentSense.definition.toString();
					sqlCommand = "SELECT * FROM ConditionSenseDefinition WHERE Definition=\"" + definitionString + "\";";
					ResultSet indQueryRS = myState.executeQuery(sqlCommand);
					indQueryRS.next();
					int matchingSenseDefId = -1;
					try {
						int idOfMatch = indQueryRS.getInt("id");
						matchingSenseDefId = idOfMatch;
						numOfSenseDefMatches = numOfSenseDefMatches + 1;
					} catch (Exception e) {
						sqlCommand = "INSERT INTO ConditionSenseDefinition (Definition) VALUES (\"" + definitionString + "\");";
						myState.execute(sqlCommand);
						matchingSenseDefId = firstConditionSenseDefId + i - numOfSenseDefMatches;
					}
					
					
		
					//Orientation
					PixelColorRange pcr2 = new PixelColorRange(currentSense.orientation.color);
					sqlCommand = "SELECT * FROM ConditionOrientation WHERE Height=" + currentSense.orientation.height + " AND Width=" + currentSense.orientation.width + " AND Rotation=" + currentSense.orientation.rotation + " AND x=" + currentSense.orientation.position.x + " AND y=" + currentSense.orientation.position.y + " AND color=\"" + pcr2.color + "\";";
					ResultSet indQueryOrientationRS = myState.executeQuery(sqlCommand);
					indQueryOrientationRS.next();
					int matchingOrientationId = -1;
					try {
						int idOfMatch = indQueryOrientationRS.getInt("id");
						matchingOrientationId = idOfMatch;
						numOfOrientationMatches = numOfOrientationMatches + 1;
					} catch (Exception e) {
						PixelColorRange pcr = new PixelColorRange(currentSense.orientation.color);
						sqlCommand = "INSERT INTO ConditionOrientation (Height, Width, Rotation, x, y, color) VALUES (" + currentSense.orientation.height + ", " + currentSense.orientation.width + ", " + currentSense.orientation.rotation + ", " + currentSense.orientation.position.x + ", " + currentSense.orientation.position.y + ", \"" + pcr.color + "\");";
						myState.execute(sqlCommand);
						matchingOrientationId = firstConditionOrientationId + i - numOfOrientationMatches;
					}
					
					
					//Orientation Change
					if (currentSense.orientationChanges.colorChange == null) {
						currentSense.orientationChanges.colorChange = "null";
					}
					
					sqlCommand = "SELECT * FROM ConditionOrientationChange WHERE HeightChange=" + currentSense.orientationChanges.heightChange + " AND WidthChange=" + currentSense.orientationChanges.widthChange + " AND RotationChange=" + currentSense.orientationChanges.rotationChange + " AND xChange=" + currentSense.orientationChanges.xChange + " AND yChange=" + currentSense.orientationChanges.yChange + " AND defChange=\"" + currentSense.orientationChanges.defChange + "\"";
					if (!currentSense.orientationChanges.colorChange.isEmpty()) {
						sqlCommand = sqlCommand + " AND colorChange=\"" + currentSense.orientationChanges.colorChange + "\"";
					}
					sqlCommand = sqlCommand + ";";
					ResultSet indQueryOChangeRS = myState.executeQuery(sqlCommand);
					indQueryOChangeRS.next();
					int matchingOChangeId = -1;
					try {
						int idOfMatch = indQueryOChangeRS.getInt("id");
						matchingOChangeId = idOfMatch;
						numOfOrientationChangeMatches = numOfOrientationChangeMatches + 1;
					} catch (Exception e) {
						sqlCommand = "INSERT INTO ConditionOrientationChange (HeightChange, WidthChange, RotationChange, xChange, yChange, defChange, colorChange) VALUES (" + currentSense.orientationChanges.heightChange + ", " + currentSense.orientationChanges.widthChange + ", " + currentSense.orientationChanges.rotationChange + ", " + currentSense.orientationChanges.xChange + ", " + currentSense.orientationChanges.yChange + ", \"" + currentSense.orientationChanges.defChange + "\", \"" + currentSense.orientationChanges.colorChange + "\");";
						myState.execute(sqlCommand);
						matchingOChangeId = firstConditionOrientationChangeId + i - numOfOrientationChangeMatches;
					}
					
					
					
					//Sense
					PixelColorRange pcr = new PixelColorRange(currentSense.orientation.color);
					sqlCommand = "SELECT ConditionSense.id FROM ConditionSense INNER JOIN ConditionOrientation ON ConditionSenseDefinition=" + matchingSenseDefId + " AND ConditionOrientation.id = ConditionSense.ConditionOrientation" + " AND ConditionOrientation.Height=" + currentSense.orientation.height + " AND ConditionOrientation.Width=" + currentSense.orientation.width + " AND ConditionOrientation.Rotation=" + currentSense.orientation.rotation + " AND ConditionOrientation.color=\"" + pcr.color + "\" LIMIT 1;";
					ResultSet activityRS = myState.executeQuery(sqlCommand);
					int senseFound = -1;
					try {
						activityRS.next();
						@SuppressWarnings("unused")
						int foundSense = activityRS.getInt("id");
						sqlCommand = "SELECT ConditionSense.id FROM ConditionSense INNER JOIN ConditionOrientation ON ConditionSenseDefinition=" + matchingSenseDefId + " AND ConditionOrientation.id = ConditionSense.ConditionOrientation" + " AND ConditionOrientation.Height=" + currentSense.orientation.height + " AND ConditionOrientation.Width=" + currentSense.orientation.width + " AND ConditionOrientation.Rotation=" + currentSense.orientation.rotation + " AND ConditionOrientation.x=" + currentSense.orientation.position.x + " AND ConditionOrientation.y=" + currentSense.orientation.position.y + " AND ConditionOrientation.color=\"" + pcr.color + "\" LIMIT 1;";
						ResultSet activityRSX = myState.executeQuery(sqlCommand);
						try {
							activityRSX.next();
							senseFound = activityRSX.getInt("id");
						} catch (Exception e) {
							
						}
					} catch (Exception e) {
						
					}
					
					
					
					if (senseFound == -1) {
						sqlCommand = "INSERT INTO ConditionSense (ConditionEnv, ConditionSenseDefinition, ConditionOrientation, ConditionOrientationChange) VALUES (" + conditionEnvId + ", " + matchingSenseDefId + ", " + matchingOrientationId + ", " + matchingOChangeId + ");";
						myState.execute(sqlCommand);
						DBObjectCountResults dbocr = new DBObjectCountResults(myConnection);
						currentSense.dbId = dbocr.conditionSenseCount;
						//myState.addBatch(sqlCommand);
						
						EnvSenseListSerializedString = EnvSenseListSerializedString + (firstConditionSenseId + i - numOfSenseMatches) + " ";
						conditionEnvIn.abstractEnv.dbSenseList = conditionEnvIn.abstractEnv.dbSenseList + (firstConditionSenseId + i - numOfSenseMatches) + " ";
					} else {
						EnvSenseListSerializedString = EnvSenseListSerializedString + (senseFound) + " ";
						currentSense.dbId = senseFound;//recently added (might break shit)
						conditionEnvIn.abstractEnv.dbSenseList = conditionEnvIn.abstractEnv.dbSenseList + (senseFound) + " ";
						numOfSenseMatches++;
					}
					
				}
				createEnvSQLCommand = createEnvSQLCommand + EnvSenseListSerializedString + "\" WHERE id=" + conditionEnvToUpdateId + ";";
				fw.append("SQL: " + createEnvSQLCommand +  "\n");
				fw.flush();
				myState.execute(createEnvSQLCommand);
				
				conditionEnvIn.dbId = conditionEnvToUpdateId;
				
				fw.append("dbId In UPDATE CON ENV: " + conditionEnvIn.dbId + "\n");
				fw.flush();
				removeForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=1;";
				removeChecksState.execute(removeForeignKeyChecksCommand);
			} catch (Exception e) {
				
			}	
			return conditionEnvIn;
	}
}
