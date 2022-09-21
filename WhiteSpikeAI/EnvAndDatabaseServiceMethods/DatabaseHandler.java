package EnvAndDatabaseServiceMethods;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import MainLLF.Constants;
import Structure.Env;
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
//		try {
//			Connection myConnection = DriverManager.getConnection(url, user, password);
//			Statement myState = myConnection.createStatement();
//			String sqlCommand = "select * from whitespikeai.TABLENAME";
//			ResultSet rs = myState.executeQuery(sqlCommand);
//			while (rs.next()) {
//				System.out.println(rs.getString("COLUMNLABEL"));
//			}
//		} catch (Exception e) {
//			
//		}
		
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
			Connection createDBConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/", Constants.user, Constants.password);
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
			sqlCommand = "CREATE TABLE IF NOT EXISTS Env (id INT PRIMARY KEY AUTO_INCREMENT, Senses TEXT, CpuUsage DOUBLE, CreationDateTime DATETIME);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Orientation (id INT PRIMARY KEY AUTO_INCREMENT, Height INT, Width INT, Rotation DOUBLE, x INT, y INT, r INT, g INT, b INT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS OrientationChange (id INT PRIMARY KEY AUTO_INCREMENT, HeightChange INT, WidthChange INT, RotationChange DOUBLE, xChange INT, yChange INT, rChange INT, gChange INT, bChange INT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Sense (id INT PRIMARY KEY AUTO_INCREMENT, Env INT NOT NULL, SenseDefinition INT NOT NULL, Orientation INT NOT NULL, OrientationChange INT, activitiesExtracted BOOLEAN, CONSTRAINT FOREIGN KEY (Orientation) REFERENCES Orientation(id), CONSTRAINT FOREIGN KEY (OrientationChange) REFERENCES OrientationChange(id), CONSTRAINT FOREIGN KEY (SenseDefinition) REFERENCES SenseDefinition(id), CONSTRAINT FOREIGN KEY (Env) REFERENCES Env(id));";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Activity (id INT PRIMARY KEY AUTO_INCREMENT, CoreActivity INT, ConditionEnv INT, AssociatedSense INT, PropertyId INT, SubActivities TEXT, SolvedStatus INT DEFAULT 0, numOfSolveAttempts INT DEFAULT 0, increaseOrDecreaseProp INT, CONSTRAINT FOREIGN KEY (ConditionEnv) REFERENCES Env(id), CONSTRAINT FOREIGN KEY (AssociatedSense) REFERENCES Sense(id));";
			myState.addBatch(sqlCommand);
			myState.executeBatch();
			
			//insert core action activities
			Statement CAActivityInsertState = myConnection.createStatement();
			for (int i = 0; i < 444; i++) {
				sqlCommand = "INSERT INTO Activity (CoreActivity, SolvedStatus, numOfSolveAttempts) VALUES (" + i + ", 1, NULL);";
				CAActivityInsertState.addBatch(sqlCommand);
			}
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
			if (rs.getInt("total") == 6) {
				output = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	
	public static void uploadEnvToDatabase(Env envIn) {
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
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
			String createEnvSQLCommand = "INSERT INTO Env (CpuUsage, CreationDateTime, Senses) VALUES (" + envIn.rawEnv.currentCpuUsage +  ", \"" + timestamp + "\", ";
			String EnvSenseListSerializedString = "\"" + firstSenseId + " ";
			//Env INT NOT NULL, SenseDefinition INT NOT NULL, Orientation INT NOT NULL, activitiesExtracted BOOLEAN, CONSTRAINT FOREIGN KEY (Orientation) REFERENCES Orientation(id), CONSTRAINT FOREIGN KEY (SenseDefinition) REFERENCES SenseDefinition(id), CONSTRAINT FOREIGN KEY (Env) REFERENCES Env(id)
			int numOfSenseDefMatches = 0;
			int numOfOrientationMatches = 0;
			for (int i = 0; i < envIn.abstractEnv.senses.size(); i++) {
				Sense currentSense = envIn.abstractEnv.senses.get(i);
				
				//SenseDefinition
				String definitionString = "";
				for (int j = 0; j < currentSense.definition.overallChangeDefString.size(); j++) {
					definitionString = definitionString + currentSense.definition.overallChangeDefString.get(j).changeType + ";";
				}
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

				
				//Orientation
				indQueryCommand = "SELECT * FROM Orientation WHERE Height=" + currentSense.orientation.height + " AND Width=" + currentSense.orientation.width + " AND Rotation=" + currentSense.orientation.rotation + " AND x=" + currentSense.orientation.position.x + " AND y=" + currentSense.orientation.position.y + " AND r=" + currentSense.orientation.color.getRed() + " AND g=" + currentSense.orientation.color.getGreen() + " AND b=" + currentSense.orientation.color.getBlue() + ";";
				ResultSet indQueryOrientationRS = indQueryStatement.executeQuery(indQueryCommand);
				indQueryOrientationRS.next();
				int matchingOrientationId = -1;
				try {
					int idOfMatch = indQueryOrientationRS.getInt("id");
					matchingOrientationId = idOfMatch;
					numOfOrientationMatches = numOfOrientationMatches + 1;
				} catch (Exception e) {
					sqlCommand = "INSERT INTO Orientation (Height, Width, Rotation, x, y, r, g, b) VALUES (" + currentSense.orientation.height + ", " + currentSense.orientation.width + ", " + currentSense.orientation.rotation + ", " + currentSense.orientation.position.x + ", " + currentSense.orientation.position.y + ", " + currentSense.orientation.color.getRed() + ", " + currentSense.orientation.color.getGreen() + ", " + currentSense.orientation.color.getBlue() +");";
					myState.execute(sqlCommand);
					matchingOrientationId = firstSenseId + i - numOfOrientationMatches;
				}
				
				//Activity
				sqlCommand = "SELECT Sense.id FROM Sense INNER JOIN Orientation ON SenseDefinition=" + matchingSenseDefId + " AND Orientation.Height=" + currentSense.orientation.height + " AND Orientation.Width=" + currentSense.orientation.width + " AND Orientation.Rotation=" + currentSense.orientation.rotation + " AND Orientation.r=" + currentSense.orientation.color.getRed() + " AND Orientation.g=" + currentSense.orientation.color.getGreen() + " AND Orientation.b=" + currentSense.orientation.color.getBlue() + " LIMIT 1;";
				//SELECT Sense.id, Height FROM Sense LEFT JOIN Orientation ON Sense.Orientation = Orientation.Height AND Sense.SenseDefinition = 1;
				ResultSet activityRS = myState.executeQuery(sqlCommand);				
				int senseFound = -1;
				try {
					activityRS.next();
					senseFound = activityRS.getInt("id");
					sqlCommand = "SELECT id FROM Activity WHERE AssociatedSense= " + senseFound + " LIMIT 1;";
					ResultSet activityRS2 = myState.executeQuery(sqlCommand);
					@SuppressWarnings("unused")
					int activityFound = -1;
					try {
						activityRS2.next();
						activityFound = activityRS2.getInt("id");
						//if this line is reached, no activities need to be created
						
					} catch (Exception e) {
						//create new activity
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 0, 1);"; //height prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 1, 1);"; //width prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 2, 1);"; //rotation prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 3, 1);"; //x prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 4, 1);"; //y prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, 1);"; //r prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 6, 1);"; //g prop activity +
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 7, 1);"; //b prop activity +
						myState.addBatch(sqlCommand);
						
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 0, 0);"; //height prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 1, 0);"; //width prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 2, 0);"; //rotation prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 3, 0);"; //x prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 4, 0);"; //y prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, 0);"; //r prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 6, 0);"; //g prop activity -
						myState.addBatch(sqlCommand);
						sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 7, 0);"; //b prop activity -
						myState.addBatch(sqlCommand);
						myState.executeBatch();
					}
				} catch (Exception e) {
					//create new activity
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 0, 1);"; //height prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 1, 1);"; //width prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 2, 1);"; //rotation prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 3, 1);"; //x prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 4, 1);"; //y prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, 1);"; //r prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 6, 1);"; //g prop activity +
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 7, 1);"; //b prop activity +
					myState.addBatch(sqlCommand);
					
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 0, 0);"; //height prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 1, 0);"; //width prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 2, 0);"; //rotation prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 3, 0);"; //x prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 4, 0);"; //y prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 5, 0);"; //r prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 6, 0);"; //g prop activity -
					myState.addBatch(sqlCommand);
					sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", 7, 0);"; //b prop activity -
					myState.addBatch(sqlCommand);
					myState.executeBatch();
				}
				
				
				
				//Sense
				sqlCommand = "INSERT INTO Sense (Env, SenseDefinition, Orientation, activitiesExtracted) VALUES (" + EnvId + ", " + matchingSenseDefId + ", " + matchingOrientationId + ", false);";
				myState.execute(sqlCommand);
				//myState.addBatch(sqlCommand);
				
				EnvSenseListSerializedString = EnvSenseListSerializedString + (firstSenseId + i) + " ";
					
			}
			createEnvSQLCommand = createEnvSQLCommand + EnvSenseListSerializedString + "\");";
			myState.execute(createEnvSQLCommand);
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
	
	public static ResultSet getActivityForExecution(int idIn) {
		ResultSet output = null;
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT CoreActivity, SubActivities FROM Activity WHERE id=" + idIn + ";";
			output = myState.executeQuery(sqlCommand);
		} catch (Exception e) {
			
		}
		return output;
	}
}
