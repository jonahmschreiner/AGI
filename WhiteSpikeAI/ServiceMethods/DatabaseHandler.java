package ServiceMethods;
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
			Connection myConnection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "CREATE TABLE IF NOT EXISTS SenseDefinition (id INT PRIMARY KEY AUTO_INCREMENT, Definition TEXT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Env (id INT PRIMARY KEY AUTO_INCREMENT, Senses TEXT, CpuUsage DOUBLE, CreationDateTime DATETIME);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Orientation (id INT PRIMARY KEY AUTO_INCREMENT, Height INT, Width INT, Rotation DOUBLE, x INT, y INT, r INT, g INT, b INT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Sense (id INT PRIMARY KEY AUTO_INCREMENT, Env INT NOT NULL, SenseDefinition INT NOT NULL, Orientation INT NOT NULL, activitiesExtracted BOOLEAN, numOfActivityExtractionAttempts INT, CONSTRAINT FOREIGN KEY (Orientation) REFERENCES Orientation(id), CONSTRAINT FOREIGN KEY (SenseDefinition) REFERENCES SenseDefinition(id), CONSTRAINT FOREIGN KEY (Env) REFERENCES Env(id));";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Activity (id INT PRIMARY KEY AUTO_INCREMENT, CoreActivity INT, ConditionEnv INT, SubActivities TEXT, SolvedStatus INT DEFAULT 0, CONSTRAINT FOREIGN KEY (ConditionEnv) REFERENCES Env(id));";
			myState.addBatch(sqlCommand);
			myState.executeBatch();
			
			Statement CAActivityInsertState = myConnection.createStatement();
			for (int i = 0; i < 444; i++) {
				sqlCommand = "INSERT INTO Activity (CoreActivity) VALUES (" + i + ");";
				CAActivityInsertState.addBatch(sqlCommand);
			}
			CAActivityInsertState.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanDatabase() {
		try {
			Connection myConnection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "DROP TABLE IF EXISTS Sense;";
			myState.addBatch(sqlCommand);
			sqlCommand = "DROP TABLE IF EXISTS Activity;";
			myState.addBatch(sqlCommand);
			sqlCommand = "DROP TABLE IF EXISTS SenseDefinition;";
			myState.addBatch(sqlCommand);
			sqlCommand = "DROP TABLE IF EXISTS Env;";
			myState.addBatch(sqlCommand);
			sqlCommand = "DROP TABLE IF EXISTS Orientation;";
			myState.addBatch(sqlCommand);
			myState.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static boolean checkDatabaseIsNotSetup() {
		boolean output = true;
		try {
			Connection myConnection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT COUNT(*) AS total FROM information_schema.tables WHERE TABLE_SCHEMA = 'whitespikeai';";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			if (rs.getInt("total") == 5) {
				output = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return output;
	}
	
	
	public static void uploadEnvToDatabase(Env envIn) {
		try {
			Connection myConnection = DriverManager.getConnection(Constants.url, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
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
			for (int i = 0; i < envIn.abstractEnv.senses.size(); i++) {
				Sense currentSense = envIn.abstractEnv.senses.get(i);
				
				//SenseDefinition
				String definitionString = "";
				for (int j = 0; j < currentSense.definition.overallChangeDefString.size(); j++) {
					definitionString = definitionString + currentSense.definition.overallChangeDefString.get(j).changeType + ";";
				}
				
				sqlCommand = "INSERT INTO SenseDefinition (Definition) VALUES (\"" + definitionString + "\");";
				//myState.execute(sqlCommand);
				myState.addBatch(sqlCommand);
				
				//Orientation
				sqlCommand = "INSERT INTO Orientation (Height, Width, Rotation, x, y, r, g, b) VALUES (" + currentSense.orientation.height + ", " + currentSense.orientation.width + ", " + currentSense.orientation.rotation + ", " + currentSense.orientation.position.x + ", " + currentSense.orientation.position.y + ", " + currentSense.orientation.color.getRed() + ", " + currentSense.orientation.color.getGreen() + ", " + currentSense.orientation.color.getBlue() +");";
				//myState.execute(sqlCommand);
				myState.addBatch(sqlCommand);
				
				//Sense
				sqlCommand = "INSERT INTO Sense (Env, SenseDefinition, Orientation, activitiesExtracted, numOfActivityExtractionAttempts) VALUES (" + EnvId + ", " + (firstSenseId + i) + ", " + (firstSenseId + i) + ", false, 0);";
				//myState.execute(sqlCommand);
				myState.addBatch(sqlCommand);
				
				EnvSenseListSerializedString = EnvSenseListSerializedString + (firstSenseId + i) + " ";
			}
			createEnvSQLCommand = createEnvSQLCommand + EnvSenseListSerializedString + "\");";
			//myState.execute(createEnvSQLCommand);
			myState.addBatch(createEnvSQLCommand);
			myState.executeBatch();
			
			//resume foreign key checks
			String readdForeignKeyChecksCommand = "SET FOREIGN_KEY_CHECKS=1;";
			Statement readdChecksState = myConnection.createStatement();
			readdChecksState.execute(readdForeignKeyChecksCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
