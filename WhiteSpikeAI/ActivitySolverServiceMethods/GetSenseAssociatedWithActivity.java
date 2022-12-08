package ActivitySolverServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;

import MainLLF.Constants;
import Structure.Env;
import Structure.PixelColorRange;
import Structure.Sense;

public class GetSenseAssociatedWithActivity {
	public static Sense execute(Env envIn, int ActivityDBIdIn, Connection myConnection) {
		Sense output = null;
		//db Activity.AssociatedSense.SenseDef && .Orientation are same
		
		//get SenseDef and orientation of AssociatedSense of the activity id passed in
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT SenseDefinition.Definition, Orientation.Height, Orientation.Width, Orientation.Rotation, Orientation.x, Orientation.y, Orientation.color FROM Activity INNER JOIN Sense ON Activity.AssociatedSense=Sense.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id INNER JOIN Orientation ON Sense.Orientation=Orientation.id WHERE Activity.id=" + ActivityDBIdIn + ";";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			try {
				//String definition = rs.getString("SenseDefinition.Definition");
				int height = rs.getInt("Orientation.Height");
				int width = rs.getInt("Orientation.Width");
				int rotation = rs.getInt("Orientation.Rotation");
				int x = rs.getInt("Orientation.x");
				int y = rs.getInt("Orientation.y");
				String color = rs.getString("Orientation.color");
				
				
				//loop through env recentlyChangedOldSenses and recentlyAddedSenses (if not found in the first one) to find the match
				
				//TODO create hashmap of dbIds matching to indexes in abstractEnv.senses
				HashMap<Integer, Integer> dbIdToIndex = createIdToIndexMap(envIn.abstractEnv.senses);
				List<Integer> rcos = envIn.abstractEnv.recentlyChangedOldSenses;
				for (int i = 0; i < rcos.size(); i++) {
					Sense currPotMatch = envIn.abstractEnv.senses.get(dbIdToIndex.get(rcos.get(i)));
					PixelColorRange currPCR = new PixelColorRange(currPotMatch.orientation.color);
					if (currPotMatch.orientation.height == height && currPotMatch.orientation.width == width 
							&& currPotMatch.orientation.rotation == rotation && currPotMatch.orientation.position.x == x 
							&& currPotMatch.orientation.position.y == y && currPCR.color.equals(color)) {
						output = currPotMatch;
						break;
					}
				}
				
				if (output == null) {
					List<Sense> ras = envIn.abstractEnv.recentlyAddedSenses;
					for (int i = 0; i < ras.size(); i++) {
						Sense currPotMatch = ras.get(i);
						PixelColorRange currPCR = new PixelColorRange(currPotMatch.orientation.color);
						if (currPotMatch.orientation.height == height && currPotMatch.orientation.width == width 
								&& currPotMatch.orientation.rotation == rotation && currPotMatch.orientation.position.x == x 
								&& currPotMatch.orientation.position.y == y && currPCR.color.equals(color)) {
							output = currPotMatch;
							break;
						}
					}
				}
				
				if (output == null) {
					List<Sense> rrs = envIn.abstractEnv.recentlyRemovedSenses;
					for (int i = 0; i < rrs.size(); i++) {
						Sense currPotMatch = rrs.get(i);
						PixelColorRange currPCR = new PixelColorRange(currPotMatch.orientation.color);
						if (currPotMatch.orientation.height == height && currPotMatch.orientation.width == width 
								&& currPotMatch.orientation.rotation == rotation && currPotMatch.orientation.position.x == x 
								&& currPotMatch.orientation.position.y == y && currPCR.color.equals(color)) {
							output = currPotMatch;
							break;
						}
					}
				}
				
				if (output == null) {
					List<Sense> senses = envIn.abstractEnv.senses;
					for (int i = 0; i < senses.size(); i++) {
						Sense currPotMatch = senses.get(i);
						PixelColorRange currPCR = new PixelColorRange(currPotMatch.orientation.color);
						if (currPotMatch.orientation.height == height && currPotMatch.orientation.width == width 
								&& currPotMatch.orientation.rotation == rotation && currPotMatch.orientation.position.x == x 
								&& currPotMatch.orientation.position.y == y && currPCR.color.equals(color)) {
							output = currPotMatch;
							break;
						}
					}
				}

			} catch (Exception e) {
				//e.printStackTrace();
			}

		} catch (Exception e) {
			//e.printStackTrace();
		}
		return output;
	}
	
	public static HashMap<Integer, Integer> createIdToIndexMap (List<Sense> sensesIn) {
		HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
		for (int i = 0; i < sensesIn.size(); i++) {
			Sense currSense = sensesIn.get(i);
			map.put(currSense.dbId, i);
		}
		return map;
	}
}
