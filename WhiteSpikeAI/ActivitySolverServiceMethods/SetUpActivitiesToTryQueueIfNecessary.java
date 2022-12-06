package ActivitySolverServiceMethods;

import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import MainLLF.Constants;

public class SetUpActivitiesToTryQueueIfNecessary { //come back and change so it only does this once and adds newly-solved activities as they are solved
	public static List<String> setup (List<String> queueIn, int activityIdIn, Connection myConnection, FileWriter fw){
		try {
			fw.append("Set up activities to try queueIn size: " + queueIn.size() + "\n");
			fw.flush();
		} catch (Exception e) {
			try {
				fw.append("queueIn error: " + e.getMessage() + "\n");
				fw.flush();
			} catch(Exception f) {
				
			}
		}

		if (queueIn.size() == 0 || queueIn == null) {
			//exact matches (SenseDef, Orientation, PropId, increaseOrDecrease all match)
			List<Integer> allExactIndis = new ArrayList<Integer>();
			String definition = null;
			int height = 0;
			int width = 0;
			int rotation = -99959;
			int x = -5;
			int y = -5;
			String color = null;
			int propId = -5;
			int increaseOrDecrease = -5;
			try {
				Statement myState = myConnection.createStatement();
				String sqlCommand = "SELECT SenseDefinition.Definition, Orientation.Height, Orientation.Width, Orientation.Rotation, Orientation.x, Orientation.y, Orientation.color, PropertyId, increaseOrDecreaseProp FROM Activity INNER JOIN Sense ON Activity.AssociatedSense=Sense.id AND Activity.id=" + activityIdIn + " INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id INNER JOIN Orientation ON Sense.Orientation=Orientation.id;";
				ResultSet rs1 = myState.executeQuery(sqlCommand);
				rs1.next();
				definition = rs1.getString("SenseDefinition.Definition");
				height = rs1.getInt("Orientation.Height");
				width = rs1.getInt("Orientation.Width");
				rotation = rs1.getInt("Orientation.Rotation");
				x = rs1.getInt("Orientation.x");
				y = rs1.getInt("Orientation.y");
				color = rs1.getString("Orientation.color");
				propId = rs1.getInt("Activity.PropertyId");
				increaseOrDecrease = rs1.getInt("Activity.increaseOrDecreaseProp");
				sqlCommand = "SELECT Activity.id FROM Activity INNER JOIN Sense ON Activity.AssociatedSense=Sense.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id AND SenseDefinition.Definition=\"" + definition + "\" AND Activity.SolvedStatus=1 INNER JOIN Orientation ON Sense.Orientation=Orientation.id AND Orientation.Height=" + height + " AND Orientation.Width=" + width + " AND Orientation.Rotation=" + rotation + " AND Orientation.x=" + x + " AND Orientation.y=" + y + " AND Orientation.color=\"" + color + "\" AND Activity.PropertyId=" + propId + " AND Activity.increaseOrDecreaseProp=" + increaseOrDecrease + " ORDER BY Activity.id ASC;";
				ResultSet rs = myState.executeQuery(sqlCommand);
				//individual activities
				while (true) {
					try {
						rs.next();
						int currId = rs.getInt("Activity.id");
						queueIn.add(String.valueOf(currId));
						allExactIndis.add(currId);
					} catch (Exception e) {
						e.printStackTrace();
						break;
					}
				}
				List<String> testList = combosFromIndis(allExactIndis, 1);
				fw.append("Exact Match Size: " + allExactIndis.size() + "\n");
				fw.flush();
				queueIn.addAll(testList);
			} catch (Exception e) {
				try {
					fw.append("-2 error: " + e.getMessage() + "\n");
					fw.flush();
				} catch (Exception f) {
					
				}
			}
			
			
			//sense matches (SenseDef, PropId, increaseOrDecrease all match)
			List<Integer> allSenseIndis = new ArrayList<Integer>();
			try {
				Statement myState = myConnection.createStatement();
				String sqlCommand = "SELECT Activity.id FROM Activity INNER JOIN Sense ON Activity.AssociatedSense=Sense.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id AND SenseDefinition.Definition=\"" + definition + "\" AND Activity.SolvedStatus=1 INNER JOIN Orientation ON Sense.Orientation=Orientation.id AND (Orientation.Height !=" + height + " OR Orientation.Width !=" + width + " OR Orientation.Rotation !=" + rotation + " OR Orientation.x !=" + x + " OR Orientation.y !=" + y + " OR Orientation.color !=\"" + color + "\") AND Activity.PropertyId=" + propId + " AND Activity.increaseOrDecreaseProp=" + increaseOrDecrease + " ORDER BY Activity.id ASC;";
				ResultSet rs = myState.executeQuery(sqlCommand);
				//individual activities
				while (true) {
					try {
						rs.next();
						int currId = rs.getInt("Activity.id");
						queueIn.add(String.valueOf(currId));
						allSenseIndis.add(currId);
					} catch (Exception e) {
						break;
					}
				}
				List<String> testList = combosFromIndis(allSenseIndis, 1);
				fw.append("Sense Match Size: " + allSenseIndis.size() + "\n");
				fw.flush();
				queueIn.addAll(testList);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					fw.append("-1 error: " + e.getMessage() + "\n");
					fw.flush();
				} catch (Exception f) {
					
				}
			}
			
			
			//location matches (Orientation, PropId, increaseOrDecrease all match)
			List<Integer> allLocationIndis = new ArrayList<Integer>();
			
			try {
				Statement myState = myConnection.createStatement();
			
				String sqlCommand = "SELECT Activity.id FROM Activity INNER JOIN Sense ON Activity.AssociatedSense=Sense.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id AND SenseDefinition.Definition != \"" + definition + "\" AND Activity.SolvedStatus=1 INNER JOIN Orientation ON Sense.Orientation=Orientation.id AND Orientation.Height=" + height + " AND Orientation.Width=" + width + " AND Orientation.Rotation=" + rotation + " AND Orientation.x=" + x + " AND Orientation.y=" + y + " AND Orientation.color=\"" + color + "\" AND Activity.PropertyId=" + propId + " AND Activity.increaseOrDecreaseProp=" + increaseOrDecrease + " ORDER BY Activity.id ASC;";
				ResultSet rs = myState.executeQuery(sqlCommand);
				//individual activities
				while (true) {
					try {
						rs.next();
						int currId = rs.getInt("Activity.id");
						queueIn.add(String.valueOf(currId));
						allLocationIndis.add(currId);
					} catch (Exception e) {
						break;
					}
				}
				List<String> testList = combosFromIndis(allLocationIndis, 1);
				fw.append("Location Match Size: " + allLocationIndis.size() + "\n");
				fw.flush();
				queueIn.addAll(testList);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					fw.append("0 error: " + e.getMessage() + "\n");
					fw.flush();
				} catch (Exception f) {
					
				}
			}
			
			//end result matches (PropId, increaseOrDecrease all match)
			List<Integer> allResultIndis = new ArrayList<Integer>();
			
			try {
				Statement myState = myConnection.createStatement();
			
				String sqlCommand = "SELECT Activity.id FROM Activity WHERE Activity.SolvedStatus=1 AND Activity.PropertyId=" + propId + " AND Activity.increaseOrDecreaseProp=" + increaseOrDecrease + " ORDER BY Activity.id ASC;";
				ResultSet rs = myState.executeQuery(sqlCommand);
				//individual activities
				while (true) {
					try {
						rs.next();
						int currId = rs.getInt("Activity.id");
						queueIn.add(String.valueOf(currId));
						allResultIndis.add(currId);
					} catch (Exception e) {
						break;
					}
				}
				List<String> testList = combosFromIndis(allResultIndis, 1);
				fw.append("End Result Match Size: " + allResultIndis.size() + "\n");
				fw.flush();
				queueIn.addAll(testList);
			} catch (Exception e) {
				e.printStackTrace();
				try {
					fw.append("1 error: " + e.getMessage() + "\n");
					fw.flush();
				} catch (Exception f) {
					
				}
			}
			
			
			
			
			
			//everything else TODO update so it excludes stuff already found
			List<Integer> allIndis = new ArrayList<Integer>();
			try {
				Statement myState = myConnection.createStatement();
				String sqlCommand = "";
				if (definition == null) {
					sqlCommand = "SELECT id FROM Activity WHERE SolvedStatus=1 ORDER BY id ASC;";
				} else {
					sqlCommand = "SELECT Activity.id FROM Activity WHERE SolvedStatus=1 AND (PropertyId !=" + propId + " OR Activity.increaseOrDecreaseProp !=" + increaseOrDecrease + ") ORDER BY Activity.id ASC;";
					fw.append("went here. PropertyId is " + propId + ". IncreaseOrDecrease value is " + increaseOrDecrease + ".");
					fw.flush();
				}
				
				ResultSet rs = myState.executeQuery(sqlCommand);
				//individual activities
				while (true) {
					try {
						rs.next();
						int currId = rs.getInt("id");
						queueIn.add(String.valueOf(currId));
						allIndis.add(currId);
					} catch (Exception e) {
						break;
					}
				}
				
				if (definition != null) {
					sqlCommand = "SELECT id FROM Activity WHERE AssociatedSense IS NULL;";
				
				
				ResultSet rs2 = myState.executeQuery(sqlCommand);
				//individual activities
				while (true) {
					try {
						rs2.next();
						int currId = rs2.getInt("id");
						queueIn.add(String.valueOf(currId));
						allIndis.add(currId);
					} catch (Exception e) {
						break;
					}
				}
				}
				List<String> testList = combosFromIndis(allIndis, 1);
				fw.append("Everything Size: " + allIndis.size() + "\n");
				fw.flush();
				queueIn.addAll(testList); //need this to go back to 1 at some point (see comment at top of class)

			} catch (Exception e) {
				try {
					fw.append("2 error: " + e.getMessage() + "\n");
					fw.flush();
				} catch (Exception f) {
					
				}
			}
		}
		return queueIn;
	}
	
	public static List<String> combosFromIndis (List<Integer> indisIn, int numOfComboLayers){
		List<String> output = new ArrayList<String>(); //TODO uncomment this. only doing this for testing atm
		for (int i = 0; i < indisIn.size(); i++) {
			int currIndi = indisIn.get(i);
			if (numOfComboLayers > 0) {
				for (int j = 0; j < indisIn.size(); j++) {
					int current = indisIn.get(j);
					if (currIndi != current) {
						String str = currIndi + " " + current;
						output.add(str);
					}
					
				}
			}

		}
		
		
		return output;
	}
}
