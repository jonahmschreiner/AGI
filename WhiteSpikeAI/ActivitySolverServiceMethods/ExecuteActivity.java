package ActivitySolverServiceMethods;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.ExecuteCoreAction;
import EnvAndDatabaseServiceMethods.UpdateEnv;
import MainLLF.Constants;
import Structure.Env;
import Structure.PixelOverallChange;
import Structure.Sense;
public class ExecuteActivity {
	public static Env execByDBId(Env envIn, int activityId) {
		ResultSet rs = DatabaseHandler.getActivityForExecution(activityId);
		try {
			rs.next();
			int coreActivityToExecute = rs.getInt("CoreActivity");
			ExecuteCoreAction.exec(coreActivityToExecute);
		} catch (Exception e) {
			try {
				String listOfSubActivities = rs.getString("SubActivities");
				String[] arrayOfSubActivityStrings = listOfSubActivities.split(" ");
				for (int i = 0; i < arrayOfSubActivityStrings.length; i++) {
					String currStr = arrayOfSubActivityStrings[i];
					int currActivityId = Integer.valueOf(currStr);
					ExecuteActivity.execByDBId(envIn, currActivityId);
					//insert activity-worked-checking and environment-closing here
					List<Sense> prevEnvSenses = envIn.abstractEnv.senses;
					envIn = UpdateEnv.update(envIn);
					Sense s = GetSenseAssociatedWithActivity.execute(envIn, currActivityId);
					if (CheckIfActivityWasSolved.execute(s, currActivityId)) { //activity solution worked
						//create and assign condition env that only contains elements that are common to existing condition env and the current env where it just worked
						try {
							Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
							Statement myState = myConnection.createStatement();
							int conditionEnvDBId = rs.getInt("ConditionEnv");
							String sqlCommand = "SELECT Senses FROM Env WHERE id=" + conditionEnvDBId + ";";
							ResultSet rs2 = myState.executeQuery(sqlCommand);
							rs2.next();
							String senseIdString = rs2.getString("Senses");
							String[] senseIdStringArray = senseIdString.split(" ");
							sqlCommand = "SELECT SenseDefinition.Definition, Orientation.Height, Orientation.Width, Orientation.Rotation, Orientation.x, Orientation.y, Orientation.r, Orientation.g, Orientation.b FROM Sense INNER JOIN Orientation ON Sense.Orientation=Orientation.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id WHERE id IN" + senseIdStringArray + ";";
							ResultSet rs3 = myState.executeQuery(sqlCommand);
							List<Sense> conditionEnvSenses = new ArrayList<Sense>();
							while (true) {
								try {
									rs3.next();
									Sense newSense = new Sense();
									newSense.orientation.height = rs3.getInt("Orientation.Height");
									newSense.orientation.width = rs3.getInt("Orientation.Width");
									newSense.orientation.rotation = rs3.getInt("Orientation.Rotation");
									newSense.orientation.position.x = rs3.getInt("Orientation.x");
									newSense.orientation.position.y = rs3.getInt("Orientation.y");
									newSense.orientation.color = new Color(rs3.getInt("Orientation.r"), rs3.getInt("Orientation.g"), rs3.getInt("Orientation.b"));
									List<PixelOverallChange> pocs = new ArrayList<PixelOverallChange>();
									String[] changeDefValues = rs3.getString("SenseDefinition.Definition").split(";");
									for (int z = 0; z < changeDefValues.length; z++) {
										pocs.add(new PixelOverallChange(changeDefValues[z]));
									}
									newSense.definition.overallChangeDefString = pocs;
									
									conditionEnvSenses.add(newSense);
								} catch (Exception g) {
									break;
								}
							}
							//for each sense in current Env, check to see if the current sense has the same values as any result in the condition env results and if so adds them to a condition env
							Env newConditionEnv = new Env(0);
							for (int j = 0; j < prevEnvSenses.size(); j++) {
								Sense currSense = prevEnvSenses.get(j);
								for (int k = 0; k < conditionEnvSenses.size(); k++) {
									Sense currConditionSense = conditionEnvSenses.get(k);
									if (currConditionSense.definition.toString().equals(currSense.definition.toString())
											&& currConditionSense.orientation.height == currSense.orientation.height
											&& currConditionSense.orientation.width == currSense.orientation.width
											&& currConditionSense.orientation.rotation == currSense.orientation.rotation
											&& currConditionSense.orientation.position.x == currSense.orientation.position.x
											&& currConditionSense.orientation.position.y == currSense.orientation.position.y
											&& currConditionSense.orientation.color.getRed() == currSense.orientation.color.getRed()
											&& currConditionSense.orientation.color.getGreen() == currSense.orientation.color.getGreen()
											&& currConditionSense.orientation.color.getBlue() == currSense.orientation.color.getBlue()) {
										newConditionEnv.abstractEnv.senses.add(currSense);
										if (newConditionEnv.abstractEnv.senses.size() == conditionEnvSenses.size()) {
											break;
										}
									}
								}
								if (newConditionEnv.abstractEnv.senses.size() == conditionEnvSenses.size()) {
									break;
								}
							}
							
							//replace ConditionEnv in db for this activity with the new ConditionEnv
							sqlCommand = "UPDATE Env SET Senses=" +  + "WHERE id=" + activityId + ";";
						} catch (Exception f) {
							
						}

					} else { //activity solution didn't work
						//create new activity that does the same sense prop change but with the current Env as the condition env
						
					}
					
				}
			} catch (Exception f) {
				
			}
			
		}
		return envIn;
		
	}
}
