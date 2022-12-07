package EnvAndDatabaseServiceMethods;
import java.awt.Color;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import MainLLF.Constants;
import Structure.DBObjectCountResults;
import Structure.Env;
import Structure.PixelColorRange;
import Structure.PixelOverallChange;
import Structure.Sense;
public class DatabaseHandler {
	/*
	 * CREATE TABLE tablename(columnLabel1 INT PRIMARY KEY AUTO_INCREMENT, columnLabel2 VARCHAR(255), columnLabel3 VARCHAR(255) NOT NULL UNIQUE, columnLabel4 TEXT);
	 * CREATE DATABASE dbname;
	 * INSERT INTO tablename (columnLabel1, columnLabel2, columnLabel3, columnLabel4) VALUES (5, 'hello', 'test', 'hey'), VALUES (10, 'hello2', 'test2', 'hey2');
	 * SELECT columnLabel1, columnLabel2 FROM tablename WHERE columnLabel1 = 2 AND columnLabel2 = 'hello' AND columnLabel3 LIKE 'te%' ORDER BY id ASC LIMIT 2;
	 * 
	 * 
	 * CREATE TABLE tablename (id INT AUTO_INCREMENT, foreignkeyexamplecolumn INT NOT NULL, PRIMARY KEY (id), FOREIGN KEY (foreignkeyexamplecolumn) REFERENCES tablenameofforeignobject(idColumnOfForeignObject)
	 * 
	 * SELECT tablecolumn AS exampleAlias FROM tablename INNER JOIN othertablename ON othertablename.othertablecolumn = tablename.tablecolumn;
	 * 
	 * DROP TABLE tablename;
	 */
	
	public static void main(String[] args) {
		setUpDatabase();
	}
	
	public static void doSetupIfNecessary() {
		if (DatabaseHandler.checkDatabaseIsNotSetup()) {
			DatabaseHandler.setUpDatabase();
			System.out.println("database refreshed");
		}
	}
	
	//sets up database tables and inserts core action activities
	public static void setUpDatabase() {
		try {
			cleanDatabase();
			//create database
//			Runtime run = Runtime.getRuntime();
//			String command = "";
//			run.exec(command);
			Connection createDBConnection = DriverManager.getConnection(Constants.homeurl, Constants.user, Constants.password);
			Statement createDBState = createDBConnection.createStatement();
			String createCommand = "CREATE DATABASE whitespikeai;";
			createDBState.execute(createCommand);
			createCommand = "use whitespikeai;";
			createDBState.execute(createCommand);
			
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			

			
			//create tables
			String sqlCommand = "CREATE TABLE IF NOT EXISTS SenseDefinition (id INT PRIMARY KEY AUTO_INCREMENT, Definition TEXT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Env (id INT PRIMARY KEY AUTO_INCREMENT, Senses TEXT, CurrentDateTime TEXT, CpuUsage DOUBLE, MouseX INT, MouseY INT, CreationDateTime DATETIME);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Orientation (id INT PRIMARY KEY AUTO_INCREMENT, Height INT, Width INT, Rotation DOUBLE, x INT, y INT, color TEXT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS OrientationChange (id INT PRIMARY KEY AUTO_INCREMENT, HeightChange INT, WidthChange INT, RotationChange DOUBLE, xChange INT, yChange INT, colorChange TEXT, defChange TEXT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Sense (id INT PRIMARY KEY AUTO_INCREMENT, Env INT NOT NULL, SenseDefinition INT NOT NULL, Orientation INT NOT NULL, OrientationChange INT, CONSTRAINT FOREIGN KEY (Orientation) REFERENCES Orientation(id), CONSTRAINT FOREIGN KEY (OrientationChange) REFERENCES OrientationChange(id), CONSTRAINT FOREIGN KEY (SenseDefinition) REFERENCES SenseDefinition(id), CONSTRAINT FOREIGN KEY (Env) REFERENCES Env(id));";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Activity (id INT PRIMARY KEY AUTO_INCREMENT, CoreActivity INT, ConditionEnv INT, AssociatedSense INT, PropertyId INT, SubActivities TEXT, SolvedStatus INT DEFAULT 0, numOfSolveAttempts INT DEFAULT 0, increaseOrDecreaseProp TEXT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS ConditionSenseDefinition (id INT PRIMARY KEY AUTO_INCREMENT, Definition TEXT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS ConditionOrientation (id INT PRIMARY KEY AUTO_INCREMENT, Height INT, Width INT, Rotation DOUBLE, x INT, y INT, color TEXT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS ConditionOrientationChange (id INT PRIMARY KEY AUTO_INCREMENT, HeightChange INT, WidthChange INT, RotationChange DOUBLE, xChange INT, yChange INT, colorChange TEXT, defChange TEXT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS ConditionEnv (id INT PRIMARY KEY AUTO_INCREMENT, Senses TEXT, CurrentDateTime TEXT, CpuUsage DOUBLE, MouseX INT, MouseY INT, CreationDateTime DATETIME);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS ConditionSense (id INT PRIMARY KEY AUTO_INCREMENT, ConditionEnv INT NOT NULL, ConditionSenseDefinition INT NOT NULL, ConditionOrientation INT NOT NULL, ConditionOrientationChange INT, CONSTRAINT FOREIGN KEY (ConditionOrientation) REFERENCES ConditionOrientation(id), CONSTRAINT FOREIGN KEY (ConditionOrientationChange) REFERENCES OrientationChange(id), CONSTRAINT FOREIGN KEY (ConditionSenseDefinition) REFERENCES ConditionSenseDefinition(id), CONSTRAINT FOREIGN KEY (ConditionEnv) REFERENCES ConditionEnv(id));";
			myState.addBatch(sqlCommand);
			myState.executeBatch();
			
			//insert core action activities
			Statement CAActivityInsertState = myConnection.createStatement();
			for (int i = 1; i < Constants.numOfCoreActions + 1; i++) {
				sqlCommand = "INSERT INTO Activity (CoreActivity, SolvedStatus, numOfSolveAttempts) VALUES (" + i + ", 1, NULL);";
				CAActivityInsertState.addBatch(sqlCommand);
			}
			
			//insert rawEnv activities
//			sqlCommand = "INSERT INTO Activity (AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (-2, 0, 1);"; //cpuUsage increase
//			CAActivityInsertState.addBatch(sqlCommand);
//			sqlCommand = "INSERT INTO Activity (AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (-2, 0, -1);";//cpuUsage decrease
//			CAActivityInsertState.addBatch(sqlCommand);
			sqlCommand = "INSERT INTO Activity (CoreActivity, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (-1, -3, 0, 1);";//mouseX increase
			CAActivityInsertState.addBatch(sqlCommand);
			sqlCommand = "INSERT INTO Activity (CoreActivity, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (-1, -3, 0, -1);";//mouseX decrease
			CAActivityInsertState.addBatch(sqlCommand);
			sqlCommand = "INSERT INTO Activity (CoreActivity, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (-1, -4, 0, 1);";//mouseY increase
			CAActivityInsertState.addBatch(sqlCommand);
			sqlCommand = "INSERT INTO Activity (CoreActivity, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (-1, -4, 0, -1);";//mouseY decrease
			CAActivityInsertState.addBatch(sqlCommand);
			CAActivityInsertState.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanDatabase() {
		try {
			Connection myConnection = DriverManager.getConnection(Constants.homeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "DROP DATABASE IF EXISTS whitespikeai;";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkDatabaseIsNotSetup() {
		boolean output = true;
		try {
			Connection myConnection = DriverManager.getConnection(Constants.homeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT COUNT(*) AS total FROM information_schema.tables WHERE TABLE_SCHEMA = 'whitespikeai';";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			if (rs.getInt("total") == 11) {
				output = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	
	public static void uploadEnvToDatabase(Env envIn, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			Statement indQueryStatement = myConnection.createStatement();
			String indQueryCommand = "";
			String sqlCommand = "SELECT COUNT(*) AS total FROM Env;";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			int EnvId = rs.getInt("total") + 1;
			sqlCommand = "SELECT COUNT(*) AS total FROM Sense;";
			rs = myState.executeQuery(sqlCommand);
			rs.next();
			int firstSenseId = rs.getInt("total") + 1;
			String removeForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=0;";
			Statement removeChecksState = myConnection.createStatement();
			removeChecksState.execute(removeForeignKeyChecksCommand);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
			LocalDateTime localDate = LocalDateTime.now();
			String timestamp = dtf.format(localDate);
			String createEnvSQLCommand = "INSERT INTO Env (CurrentDateTime, CpuUsage, MouseX, MouseY, CreationDateTime, Senses) VALUES (\"" + envIn.rawEnv.currentDateTime.toString() + "\", " + envIn.rawEnv.currentCpuUsage + ", " + envIn.rawEnv.mouseLocation.x + ", " + envIn.rawEnv.mouseLocation.y +  ", \"" + timestamp + "\", ";
			String EnvSenseListSerializedString = "\"";
			//Env INT NOT NULL, SenseDefinition INT NOT NULL, Orientation INT NOT NULL, activitiesExtracted BOOLEAN, CONSTRAINT FOREIGN KEY (Orientation) REFERENCES Orientation(id), CONSTRAINT FOREIGN KEY (SenseDefinition) REFERENCES SenseDefinition(id), CONSTRAINT FOREIGN KEY (Env) REFERENCES Env(id)
			int numOfSenseDefMatches = 0;
			int numOfOrientationMatches = 0;
			int numOfSenseMatches = 0;
			for (int i = 0; i < envIn.abstractEnv.senses.size(); i++) {
				Sense currentSense = envIn.abstractEnv.senses.get(i);
				
				//SenseDefinition
				String definitionString = currentSense.definition.toString();
//				for (int j = 0; j < currentSense.definition.overallChangeDefString.size(); j++) {
//					definitionString = definitionString + currentSense.definition.overallChangeDefString.get(j).changeType + ";";
//				}
				indQueryCommand = "SELECT * FROM SenseDefinition WHERE Definition=\"" + definitionString + "\";";
				ResultSet indQueryRS = indQueryStatement.executeQuery(indQueryCommand);
				indQueryRS.next();
				int matchingSenseDefId = -1;
				try {
					int idOfMatch = indQueryRS.getInt("id");
					matchingSenseDefId = idOfMatch;
					numOfSenseDefMatches = numOfSenseDefMatches + 1;
				} catch (Exception e) {
					sqlCommand = "INSERT INTO SenseDefinition (Definition) VALUES (\"" + definitionString + "\");";
					myState.execute(sqlCommand);
					//myState.addBatch(sqlCommand);
					matchingSenseDefId = firstSenseId + i - numOfSenseDefMatches;
				}

				PixelColorRange pcr2 = new PixelColorRange(currentSense.orientation.color);
				//Orientation
				indQueryCommand = "SELECT * FROM Orientation WHERE Height=" + currentSense.orientation.height + " AND Width=" + currentSense.orientation.width + " AND Rotation=" + currentSense.orientation.rotation + " AND x=" + currentSense.orientation.position.x + " AND y=" + currentSense.orientation.position.y + " AND color=\"" + pcr2.color + "\";";
				ResultSet indQueryOrientationRS = indQueryStatement.executeQuery(indQueryCommand);
				indQueryOrientationRS.next();
				int matchingOrientationId = -1;
				try {
					int idOfMatch = indQueryOrientationRS.getInt("id");
					matchingOrientationId = idOfMatch;
					numOfOrientationMatches = numOfOrientationMatches + 1;
				} catch (Exception e) {
					PixelColorRange pcr = new PixelColorRange(currentSense.orientation.color);
					sqlCommand = "INSERT INTO Orientation (Height, Width, Rotation, x, y, color) VALUES (" + currentSense.orientation.height + ", " + currentSense.orientation.width + ", " + currentSense.orientation.rotation + ", " + currentSense.orientation.position.x + ", " + currentSense.orientation.position.y + ", \"" + pcr.color + "\");";
					myState.execute(sqlCommand);
					matchingOrientationId = firstSenseId + i - numOfOrientationMatches;
				}
				
				//Empty OrientationChange
				sqlCommand = "SELECT COUNT(*) AS total FROM OrientationChange;";
				ResultSet rs4 = myState.executeQuery(sqlCommand);
				rs4.next();
				int orChangeId = -1;
				try {
					orChangeId = rs4.getInt("total");
					orChangeId++;
				} catch (Exception e) {
					
				}
				sqlCommand = "INSERT INTO OrientationChange (HeightChange, WidthChange, RotationChange, xChange, yChange, defChange, colorChange) VALUES (" + currentSense.orientationChanges.heightChange + ", " + currentSense.orientationChanges.widthChange + ", " + currentSense.orientationChanges.rotationChange + ", " + currentSense.orientationChanges.xChange + ", " + currentSense.orientationChanges.yChange + ", \"" + currentSense.orientationChanges.defChange + "\", \"" + currentSense.orientationChanges.colorChange +"\");";
				myState.execute(sqlCommand);
				
				//Activity
				PixelColorRange pcr = new PixelColorRange(currentSense.orientation.color);
				sqlCommand = "SELECT Sense.id FROM Sense INNER JOIN Orientation ON SenseDefinition=" + matchingSenseDefId + " AND Orientation.id = Sense.Orientation" + " AND Orientation.Height=" + currentSense.orientation.height + " AND Orientation.Width=" + currentSense.orientation.width + " AND Orientation.Rotation=" + currentSense.orientation.rotation + " AND Orientation.color=\"" + pcr.color + "\" LIMIT 1;";
				//SELECT Sense.id, Height FROM Sense LEFT JOIN Orientation ON Sense.Orientation = Orientation.Height AND Sense.SenseDefinition = 1;
				ResultSet activityRS = myState.executeQuery(sqlCommand);				
				int senseFound = -1;
				try {
					activityRS.next();
					int foundSense = activityRS.getInt("id");
					sqlCommand = "SELECT Sense.id FROM Sense INNER JOIN Orientation ON SenseDefinition=" + matchingSenseDefId + " AND Orientation.id = Sense.Orientation" + " AND Orientation.Height=" + currentSense.orientation.height + " AND Orientation.Width=" + currentSense.orientation.width + " AND Orientation.Rotation=" + currentSense.orientation.rotation + " AND Orientation.x=" + currentSense.orientation.position.x + " AND Orientation.y=" + currentSense.orientation.position.y + " AND Orientation.color=\"" + pcr.color + "\" LIMIT 1;";
					ResultSet activityRSX = myState.executeQuery(sqlCommand);
					try {
						activityRSX.next();
						senseFound = activityRSX.getInt("id");
					} catch (Exception e) {
						
					}
					sqlCommand = "SELECT id FROM Activity WHERE AssociatedSense= " + foundSense + " LIMIT 1;";
					ResultSet activityRS2 = myState.executeQuery(sqlCommand);
					int activityFound = -1;
					try {
						boolean throwError = false;
						while (true) {
							try {	
								activityRS2.next();
								activityFound = activityRS2.getInt("id");
								sqlCommand = "SELECT ConditionEnv FROM Activity WHERE id=" + activityFound + ";";
								ResultSet rs3 = myState.executeQuery(sqlCommand);
								try {
									rs3.next();
									int conditionEnvId = rs3.getInt("ConditionEnv");
									if (!checkIfConditionEnvIsContainedInEnv(conditionEnvId, envIn, myConnection)) {
										throwError = true;
									}
								} catch (Exception e) {
									
								}
							} catch (Exception e) {
								break;
							}

						}
						if (throwError) {
							throw new Exception();
						}
						//if this line is reached, no activities need to be created
						
					} catch (Exception e) {
						//create new activity
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 0, 1, -1);"; //height prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 1, 1, -1);"; //width prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 2, 1, -1);"; //rotation prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 3, 1, -1);"; //x prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 4, 1, -1);"; //y prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Red\", -1);"; //color prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Brown\", -1);"; //color prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Orange\", -1);"; //color prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Yellow\", -1);"; //color prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Green\", -1);"; //color prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Teal\", -1);"; //color prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Blue\", -1);"; //color prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Purple\", -1);"; //color prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Pink\", -1);"; //color prop activity +
						myState.addBatch(sqlCommand);
						
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 0, -1, -1);"; //height prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 1, -1, -1);"; //width prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 2, -1, -1);"; //rotation prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 3, -1, -1);"; //x prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 4, -1, -1);"; //y prop activity -
						myState.addBatch(sqlCommand);
						myState.executeBatch();
					}
				} catch (Exception e) {
					//create new activity
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 0, 1, -1);"; //height prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 1, 1, -1);"; //width prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 2, 1, -1);"; //rotation prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 3, 1, -1);"; //x prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 4, 1, -1);"; //y prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Red\", -1);"; //color prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Brown\", -1);"; //color prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Orange\", -1);"; //color prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Yellow\", -1);"; //color prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Green\", -1);"; //color prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Teal\", -1);"; //color prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Blue\", -1);"; //color prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Purple\", -1);"; //color prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, \"Pink\", -1);"; //color prop activity +
					myState.addBatch(sqlCommand);
					
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 0, -1, -1);"; //height prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 1, -1, -1);"; //width prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 2, -1, -1);"; //rotation prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 3, -1, -1);"; //x prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 4, -1, -1);"; //y prop activity -
					myState.addBatch(sqlCommand);
					myState.executeBatch();
				}
				
				
				
				//Sense
				if (senseFound == -1) {
					sqlCommand = "INSERT INTO Sense (Env, SenseDefinition, Orientation, OrientationChange) VALUES (" + EnvId + ", " + matchingSenseDefId + ", " + matchingOrientationId + ", " + orChangeId + ");";
					myState.execute(sqlCommand);
					DBObjectCountResults dbocr = new DBObjectCountResults(myConnection);
					currentSense.dbId = dbocr.senseCount;
					//myState.addBatch(sqlCommand);
					
					EnvSenseListSerializedString = EnvSenseListSerializedString + (firstSenseId + i - numOfSenseMatches) + " ";
					envIn.abstractEnv.dbSenseList = envIn.abstractEnv.dbSenseList + (firstSenseId + i - numOfSenseMatches) + " ";
				} else {
					EnvSenseListSerializedString = EnvSenseListSerializedString + (senseFound) + " ";
					envIn.abstractEnv.dbSenseList = envIn.abstractEnv.dbSenseList + (senseFound) + " ";
					numOfSenseMatches++;
				}

					
			}
			createEnvSQLCommand = createEnvSQLCommand + EnvSenseListSerializedString + "\");";
			myState.execute(createEnvSQLCommand);
			DBObjectCountResults dbocr = new DBObjectCountResults(myConnection);
			envIn.dbId = dbocr.envCount;
			//myState.addBatch(createEnvSQLCommand);
			//myState.executeBatch();
			
			
			
			
			//resume foreign key checks
			String readdForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=1;";
			Statement readdChecksState = myConnection.createStatement();
			readdChecksState.execute(readdForeignKeyChecksCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ResultSet getActivityForExecution(int idIn, Connection myConnection) {
		ResultSet output = null;
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT CoreActivity, AssociatedSense, SubActivities, ConditionEnv, PropertyId, increaseOrDecreaseProp FROM Activity WHERE id=" + idIn + ";";
			output = myState.executeQuery(sqlCommand);
		} catch (Exception e) {
			
		}
		return output;
	}
	
	
	public static boolean checkIfConditionEnvIsContainedInEnvUsingIds(int conditionEnvId, int envId, Connection myConnection) { //assumes all senses in db are unique
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT Senses FROM Env WHERE id=" + conditionEnvId + ";";
			ResultSet rs = myState.executeQuery(sqlCommand);
			try {
				rs.next();
				String conditionEnvSenseString = rs.getString("Senses");
				sqlCommand = "SELECT Senses FROM Env WHERE id=" + envId + ";";
				ResultSet rs2 = myState.executeQuery(sqlCommand);
				try {
					rs2.next();
					String envSenseString = rs2.getString("Senses");
					String[] conditionEnvSenseArray = conditionEnvSenseString.split(" ");
					String[] envSenseArray = envSenseString.split(" ");
					return Arrays.asList(envSenseArray).containsAll(Arrays.asList(conditionEnvSenseArray));
				} catch (Exception e) {
					
				}
			} catch (Exception e) {
				
			}
		} catch (Exception e) {
			
		}
		return false;
	}
	
	public static boolean checkIfConditionEnvIsContainedInEnv(int conditionEnvId, Env env, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT Senses FROM Env WHERE id=" + conditionEnvId + ";";
			ResultSet rs = myState.executeQuery(sqlCommand);
			try {
				rs.next();
				String conditionEnvSenseString = rs.getString("Senses");
				String[] conditionEnvSenseArray = conditionEnvSenseString.split(" ");
				return env.abstractEnv.senses.containsAll(createSenseListFromIdList(conditionEnvSenseArray, myConnection));
			} catch (Exception e) {
				
			}
		} catch (Exception e) {
			
		}
		return false;
	}
	
	public static List<Sense> createSenseListFromIdList(String[] senseIds, Connection myConnection){
		List<Sense> output = new ArrayList<Sense>();
		try {
			Statement myState = myConnection.createStatement();
			for (int i = 0; i < senseIds.length; i++) {
				Sense newSense = new Sense();
				int currId = Integer.valueOf(senseIds[i]);
				String sqlCommand = "SELECT SenseDefinition, Orientation FROM Sense WHERE id=" + currId + ";";
				ResultSet rs = myState.executeQuery(sqlCommand);
				
				try {
					rs.next();
					int senseDefId = rs.getInt("SenseDefinition");
					int orId = rs.getInt("Orientation");
					
					sqlCommand = "SELECT Definition FROM SenseDefinition WHERE id=" + senseDefId + ";";
					ResultSet rs2 = myState.executeQuery(sqlCommand);
					try {
						rs2.next();
						String defString = rs2.getString("Definition");
						String[] defArray = defString.split(";");
						for (int j = 0; j < defArray.length; j++) {
							PixelOverallChange poc = new PixelOverallChange(defArray[j]);
							newSense.definition.overallChangeDefString.add(poc);
						}
						
						sqlCommand = "SELECT Height, Width, Rotation, x, y, r, g, b FROM Orientation WHERE id=" + orId + ";";
						ResultSet rs3 = myState.executeQuery(sqlCommand);
						try {
							rs3.next();
							int height = rs3.getInt("Height");
							int width = rs3.getInt("Width");
							int rotation = rs3.getInt("Rotation");
							int x = rs3.getInt("x");
							int y = rs3.getInt("y");
							int r = rs3.getInt("r");
							int g = rs3.getInt("g");
							int b = rs3.getInt("b");
							newSense.orientation.height = height;
							newSense.orientation.width = width;
							newSense.orientation.rotation = rotation;
							newSense.orientation.position.x = x;
							newSense.orientation.position.y = y;
							newSense.orientation.color = new Color (r, g, b);
							
							output.add(newSense);
						} catch (Exception e) {
							
						}
					} catch (Exception e) {
						
					}
				} catch (Exception e) {
					
				}
			}

		} catch (Exception e) {
			
		}
		
		
		return output;
	}
}
