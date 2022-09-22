package ActivitySolverServiceMethods;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import MainLLF.Constants;
import Structure.Env;
import Structure.Sense;

public class GetSenseAssociatedWithActivity {
	public static Sense execute(Env envIn, int ActivityDBIdIn) {
		Sense output = null;
		//db Activity.AssociatedSense.SenseDef && .Orientation are same
		
		//get SenseDef and orientation of AssociatedSense of the activity id passed in
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT SenseDefinition.Definition, Orientation.Height, Orientation.Width, Orientation.Rotation, Orientation.x, Orientation.y, Orientation.r, Orientation.g, Orientation.b FROM Activity INNER JOIN Sense ON Activity.AssociatedSense=Sense.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id INNER JOIN Orientation ON Sense.Orientation=Orientation.id WHERE Activity.id=" + ActivityDBIdIn + ";";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			try {
				String definition = rs.getString("SenseDefinition.Definition");
				int height = rs.getInt("Orientation.Height");
				int width = rs.getInt("Orientation.Width");
				int rotation = rs.getInt("Orientation.Rotation");
				int x = rs.getInt("Orientation.x");
				int y = rs.getInt("Orientation.y");
				int r = rs.getInt("Orientation.r");
				int g = rs.getInt("Orientation.g");
				int b = rs.getInt("Orientation.b");
				
				
				//loop through env recentlyChangedOldSenses and recentlyAddedSenses (if not found in the first one) to find the match
				
				List<Integer> rcos = envIn.abstractEnv.recentlyChangedOldSenses;
				for (int i = 0; i < rcos.size(); i++) {
					Sense currPotMatch = envIn.abstractEnv.senses.get(rcos.get(i));
					if (currPotMatch.orientation.height == height && currPotMatch.orientation.width == width 
							&& currPotMatch.orientation.rotation == rotation && currPotMatch.orientation.position.x == x 
							&& currPotMatch.orientation.position.y == y && currPotMatch.orientation.color.getRed() == r 
							&& currPotMatch.orientation.color.getBlue() == b && currPotMatch.orientation.color.getGreen() == g) {
						output = currPotMatch;
						break;
					}
				}
				
				if (output == null) {
					List<Sense> ras = envIn.abstractEnv.recentlyAddedSenses;
					for (int i = 0; i < rcos.size(); i++) {
						Sense currPotMatch = ras.get(i);
						if (currPotMatch.orientation.height == height && currPotMatch.orientation.width == width 
								&& currPotMatch.orientation.rotation == rotation && currPotMatch.orientation.position.x == x 
								&& currPotMatch.orientation.position.y == y && currPotMatch.orientation.color.getRed() == r 
								&& currPotMatch.orientation.color.getBlue() == b && currPotMatch.orientation.color.getGreen() == g) {
							output = currPotMatch;
							break;
						}
					}
				}

			} catch (Exception e) {
				
			}

		} catch (Exception e) {
			
		}
		return output;
	}
}
