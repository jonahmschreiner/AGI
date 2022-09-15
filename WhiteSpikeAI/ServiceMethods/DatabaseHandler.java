package ServiceMethods;
import java.sql.*;
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
	static String url = "jdbc:mysql://localhost:3306/whitespikeai?autoReconect=true&userSSL=false&serverTimezone=UTC";
	static String user = "root";
	static String password = "3v3ry0n3l0v3sag1!";
	
	public static void main(String[] args) {
		cleanDatabase();
		System.out.println("database cleaned");
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
	
	//sets up database tables and inserts core action activities
	public static void setUpDatabase() {
		try {
			Connection myConnection = DriverManager.getConnection(url, user, password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "CREATE TABLE IF NOT EXISTS SenseDefinition (id INT PRIMARY KEY AUTO_INCREMENT, Definition TEXT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS CoreActivity (id INT PRIMARY KEY AUTO_INCREMENT, Command VARCHAR(255));";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Env (id INT PRIMARY KEY AUTO_INCREMENT, Senses TEXT, CpuUsage DOUBLE);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Orientation (id INT PRIMARY KEY AUTO_INCREMENT, Height INT, Width INT, Rotation INT, x INT, y INT, r INT, g INT, b INT);";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Sense (id INT PRIMARY KEY AUTO_INCREMENT, Env INT NOT NULL, SenseDefinition INT NOT NULL, Orientation INT NOT NULL, activitiesExtracted BOOLEAN, CONSTRAINT FOREIGN KEY (Orientation) REFERENCES Orientation(id), CONSTRAINT FOREIGN KEY (SenseDefinition) REFERENCES SenseDefinition(id), CONSTRAINT FOREIGN KEY (Env) REFERENCES Env(id));";
			myState.addBatch(sqlCommand);
			sqlCommand = "CREATE TABLE IF NOT EXISTS Activity (id INT PRIMARY KEY AUTO_INCREMENT, CoreActivity INT, ConditionEnv INT, SubActivities TEXT, SolvedStatus INT DEFAULT 0, CONSTRAINT FOREIGN KEY (ConditionEnv) REFERENCES Env(id), CONSTRAINT FOREIGN KEY (CoreActivity) REFERENCES CoreActivity(id));";
			myState.addBatch(sqlCommand);
			myState.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void cleanDatabase() {
		try {
			Connection myConnection = DriverManager.getConnection(url, user, password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "DROP TABLE IF EXISTS SenseDefinition;";
			myState.addBatch(sqlCommand);
			sqlCommand = "DROP TABLE IF EXISTS CoreActivity;";
			myState.addBatch(sqlCommand);
			sqlCommand = "DROP TABLE IF EXISTS Env;";
			myState.addBatch(sqlCommand);
			sqlCommand = "DROP TABLE IF EXISTS Orientation;";
			myState.addBatch(sqlCommand);
			sqlCommand = "DROP TABLE IF EXISTS Sense;";
			myState.addBatch(sqlCommand);
			sqlCommand = "DROP TABLE IF EXISTS Activity;";
			myState.addBatch(sqlCommand);
			myState.executeBatch();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
