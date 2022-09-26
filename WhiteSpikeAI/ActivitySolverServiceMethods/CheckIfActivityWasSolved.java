package ActivitySolverServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import MainLLF.Constants;
import Structure.Env;
import Structure.Sense;

public class CheckIfActivityWasSolved {
	public static boolean execute(Sense senseIn, int ActivityDBIdIn) {
		boolean output = false;
		//get propId and increaseOrDecreaseValues from DB for this activity id
		int propId = -1;
		int increaseOrDecreaseProp = -999;
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT PropertyId, increaseOrDecreaseProp FROM Activity WHERE id=" + ActivityDBIdIn + ";";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			try {
				propId = rs.getInt("PropertyId");
				increaseOrDecreaseProp = rs.getInt("increaseOrDecreaseProp");
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (senseIn == null) {
			if (increaseOrDecreaseProp == 0) {
				output = true;
			}	
			return output;
		}
		
		if (propId == 0 && increaseOrDecreaseProp == senseIn.orientationChanges.heightChange) {
			output = true;
		} else if (propId == 1 && increaseOrDecreaseProp == senseIn.orientationChanges.widthChange) {
			output = true;
		} else if (propId == 2 && increaseOrDecreaseProp == senseIn.orientationChanges.rotationChange) {
			output = true;
		} else if (propId == 3 && increaseOrDecreaseProp == senseIn.orientationChanges.xChange) {
			output = true;
		} else if (propId == 4 && increaseOrDecreaseProp == senseIn.orientationChanges.yChange) {
			output = true;
		} else if (propId == 5 && increaseOrDecreaseProp == senseIn.orientationChanges.rChange) {
			output = true;
		} else if (propId == 6 && increaseOrDecreaseProp == senseIn.orientationChanges.gChange) {
			output = true;
		} else if (propId == 7 && increaseOrDecreaseProp == senseIn.orientationChanges.bChange) {
			output = true;
		}
		
		return output;
	}
}
