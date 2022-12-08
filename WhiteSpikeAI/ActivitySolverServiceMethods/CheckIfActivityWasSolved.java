package ActivitySolverServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import MainLLF.Constants;
import Structure.Env;
import Structure.Sense;

public class CheckIfActivityWasSolved {
	public static boolean execute(Sense senseIn, int ActivityDBIdIn, Env envIn, Connection myConnection) {
		boolean output = false;
		//get propId and increaseOrDecreaseValues from DB for this activity id
		int senseId = -999999;
		int propId = -1;
		int increaseOrDecreaseProp = -999;
		String increaseOrDecreasePropAsString = null;
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT AssociatedSense, PropertyId, increaseOrDecreaseProp FROM Activity WHERE id=" + ActivityDBIdIn + ";";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			try {
				propId = rs.getInt("PropertyId");
				String str = rs.getString("increaseOrDecreaseProp");
				senseId = rs.getInt("AssociatedSense");
				try {
					increaseOrDecreaseProp = Integer.valueOf(str);
				} catch (Exception e) {
					increaseOrDecreasePropAsString = str;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (senseId > -1) {
			if (senseIn == null) {
				if (increaseOrDecreaseProp == 0) {
					output = true;
				}	
				return output;
			}
			if (increaseOrDecreasePropAsString == null) {
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
				} else if (propId == 6 && increaseOrDecreaseProp == -1 && envIn.abstractEnv.recentlyRemovedSenses.contains(senseIn)) {
					output = true;
				} else if (propId == 6 && increaseOrDecreaseProp == 1 && envIn.abstractEnv.senses.contains(senseIn)) {
					output = true;
				}
			} else if (propId == 5 && increaseOrDecreasePropAsString.equals(senseIn.orientationChanges.colorChange)) {
				output = true;
			} 
		} else if (senseId == -2){
			if (increaseOrDecreaseProp == envIn.rawEnv.cpuUsageChange) {
				output = true;
			}
		} else if (senseId == -3){
			if (increaseOrDecreaseProp == envIn.rawEnv.mouseXChange) {
				output = true;
			}
		} else if (senseId == -4){
			if (increaseOrDecreaseProp == envIn.rawEnv.mouseYChange) {
				output = true;
			}
		} 
		
		
		
		return output;
	}
}
