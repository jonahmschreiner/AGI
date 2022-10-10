package Tests;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import EnvAndDatabaseServiceMethods.DatabaseHandler;
import MainLLF.Constants;
import Structure.Env;
import Structure.PixelOverallChange;
import Structure.Sense;

public class UploadEnvToDBTest {
	public static void main(String[] args) {
		//Reset DB
		DatabaseHandler.main(null);
		
		//negative case (no senses in env)
		Env env = new Env(0);
		env.rawEnv = env.senseRawEnv();
		DatabaseHandler.uploadEnvToDatabase(env);
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT (SELECT Count(*) FROM Env) as envTotal, (SELECT Count(*) FROM Activity) as actTotal, (SELECT Count(*) FROM Sense) as seTotal;";
			ResultSet rs = myState.executeQuery(sqlCommand);
			try {
				rs.next();
				int envTotal = rs.getInt("envTotal");
				System.out.println("Should be one: " + envTotal);
				int actTotal = rs.getInt("actTotal");
				System.out.println("Should be 444: " + actTotal);
				int seTotal = rs.getInt("seTotal");
				System.out.println("Should be zero: " + seTotal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		//Reset DB
		DatabaseHandler.main(null);
		
		//positive case (a sense in env)
		Env env2 = new Env(0);
		env2.rawEnv = env2.senseRawEnv();
		Sense sense1 = new Sense();
		List<PixelOverallChange> pocs = new ArrayList<PixelOverallChange>();
		PixelOverallChange poc1 = new PixelOverallChange("Straight");
		PixelOverallChange poc2 = new PixelOverallChange("RightTurn");
		pocs.add(poc1);
		pocs.add(poc2);
		pocs.add(poc1);
		pocs.add(poc2);
		pocs.add(poc1);
		pocs.add(poc2);
		pocs.add(poc1);
		pocs.add(poc2);
		sense1.definition.overallChangeDefString = pocs;
		
		sense1.orientation.height = 10;
		sense1.orientation.width = 10;
		sense1.orientation.rotation = 10000;
		sense1.orientation.position.x = 10;
		sense1.orientation.position.y = 10;
		sense1.orientation.color = new Color (100, 20, 10);
		env2.abstractEnv.senses.add(sense1);
		DatabaseHandler.uploadEnvToDatabase(env2);
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT (SELECT Count(*) FROM Env) as envTotal, (SELECT Count(*) FROM Activity) as actTotal, (SELECT Count(*) FROM Sense) as seTotal;";
			ResultSet rs = myState.executeQuery(sqlCommand);
			try {
				rs.next();
				int envTotal = rs.getInt("envTotal");
				System.out.println("Should be one: " + envTotal);
				int actTotal = rs.getInt("actTotal");
				System.out.println("Should be 463: " + actTotal);
				int seTotal = rs.getInt("seTotal");
				System.out.println("Should be one: " + seTotal);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
