package ActivitySolverServiceMethods;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.ExecuteCoreAction;
import EnvAndDatabaseServiceMethods.UpdateEnv;
import MainLLF.Constants;
import Structure.Env;
import Structure.PixelColorRange;
import Structure.PixelOverallChange;
import Structure.Sense;
public class ExecuteActivity {
	public static Env execByDBId(Env envIn, int activityId) {
		List<Sense> prevEnvSenses2 = new ArrayList<Sense>();
		prevEnvSenses2.addAll(envIn.abstractEnv.senses);
		ResultSet rs = DatabaseHandler.getActivityForExecution(activityId);
		try {
			rs.next();
			int coreActivityToExecute = rs.getInt("CoreActivity");
			if (coreActivityToExecute == -1) {
				throw new Exception();
			}
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
					List<Sense> prevEnvSenses = new ArrayList<Sense>();
					prevEnvSenses.addAll(envIn.abstractEnv.senses);
					envIn = UpdateEnv.update(envIn);
					if (currActivityId > Constants.numOfCoreActions) { //ensure the sub-activity isn't a core action to avoid errors in env-closing
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
								String inString = "(";
								for (int j = 0; j < senseIdStringArray.length; j++) {
									inString = inString + senseIdStringArray[j];
									if (j < senseIdStringArray.length - 1) {
										inString = inString + ", ";
									}
								}
								inString = inString + ")";
								sqlCommand = "SELECT SenseDefinition.Definition, Orientation.Height, Orientation.Width, Orientation.Rotation, Orientation.x, Orientation.y, Orientation.color FROM Sense INNER JOIN Orientation ON Sense.Orientation=Orientation.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id WHERE Sense.id IN" + inString + ";";
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
										Color color = createColorFromRange(rs3.getString("Orientation.color"));
										newSense.orientation.color = color;
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
								newConditionEnv.senseRawEnv();
								for (int j = 0; j < prevEnvSenses.size(); j++) {
									Sense currSense = prevEnvSenses.get(j);
									for (int k = 0; k < conditionEnvSenses.size(); k++) {
										Sense currConditionSense = conditionEnvSenses.get(k);
										PixelColorRange currConSensepcr = new PixelColorRange(currConditionSense.orientation.color);
										PixelColorRange currSensepcr = new PixelColorRange(currSense.orientation.color);
										if (currConditionSense.definition.toString().equals(currSense.definition.toString())
												&& currConditionSense.orientation.height == currSense.orientation.height
												&& currConditionSense.orientation.width == currSense.orientation.width
												&& currConditionSense.orientation.rotation == currSense.orientation.rotation
												&& currConditionSense.orientation.position.x == currSense.orientation.position.x
												&& currConditionSense.orientation.position.y == currSense.orientation.position.y
												&& currConSensepcr.color.equals(currSensepcr.color)) {
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
								
								//create senses string
								String conditionSensesString = "";
								for (int l = 0; l < newConditionEnv.abstractEnv.senses.size(); l++) {
									conditionSensesString = conditionSensesString + newConditionEnv.abstractEnv.senses.get(l).dbId + " ";
								}
								//replace ConditionEnv in db for this activity with the new ConditionEnv
								DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
								LocalDateTime localDate = LocalDateTime.now();
								String timestamp = dtf.format(localDate);
								sqlCommand = "UPDATE Activity INNER JOIN Env ON Activity.Env=Env.id SET Env.Senses=" + conditionSensesString + "Env.CpuUsage=" + newConditionEnv.rawEnv.currentCpuUsage + "Env.CreationDateTime=\"" + timestamp + "\" WHERE Activity.id=" + activityId + ";";
							} catch (Exception f) {
								
							}

						} else { //activity solution didn't work
							//create new activity that does the same sense prop change but with the current Env as the condition env
							DatabaseHandler.uploadEnvToDatabase(envIn);
						}
					}		
				}
				
				//attempt to close down current activity's condition env
				if (activityId > Constants.numOfCoreActions) { //ensure the sub-activity isn't a core action to avoid errors in env-closing
					Sense s = GetSenseAssociatedWithActivity.execute(envIn, activityId);
					if (CheckIfActivityWasSolved.execute(s, activityId)) { //activity solution worked
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
							String inString = "(";
							for (int i = 0; i < senseIdStringArray.length; i++) {
								inString = inString + senseIdStringArray[i];
								if (i < senseIdStringArray.length - 1) {
									inString = inString + ", ";
								}
							}
							inString = inString + ")";
							sqlCommand = "SELECT SenseDefinition.Definition, Orientation.Height, Orientation.Width, Orientation.Rotation, Orientation.x, Orientation.y, Orientation.color FROM Sense INNER JOIN Orientation ON Sense.Orientation=Orientation.id INNER JOIN SenseDefinition ON Sense.SenseDefinition=SenseDefinition.id WHERE Sense.id IN" + inString + ";";
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
									Color color = createColorFromRange(rs3.getString("Orientation.color"));
									newSense.orientation.color = color;
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
							newConditionEnv.senseRawEnv();

							for (int j = 0; j < prevEnvSenses2.size(); j++) {
								Sense currSense = prevEnvSenses2.get(j);
								for (int k = 0; k < conditionEnvSenses.size(); k++) {
									Sense currConditionSense = conditionEnvSenses.get(k);
									PixelColorRange currConSensepcr = new PixelColorRange(currConditionSense.orientation.color);
									PixelColorRange currSensepcr = new PixelColorRange(currSense.orientation.color);
									if (currConditionSense.definition.toString().equals(currSense.definition.toString())
											&& currConditionSense.orientation.height == currSense.orientation.height
											&& currConditionSense.orientation.width == currSense.orientation.width
											&& currConditionSense.orientation.rotation == currSense.orientation.rotation
											&& currConditionSense.orientation.position.x == currSense.orientation.position.x
											&& currConditionSense.orientation.position.y == currSense.orientation.position.y
											&& currConSensepcr.color.equals(currSensepcr.color)) {
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
							
							//create senses string
							String conditionSensesString = "";
							for (int l = 0; l < newConditionEnv.abstractEnv.senses.size(); l++) {
								conditionSensesString = conditionSensesString + newConditionEnv.abstractEnv.senses.get(l).dbId + " ";
							}
							//replace ConditionEnv in db for this activity with the new ConditionEnv
							DateTimeFormatter dtf = DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm:ss");
							LocalDateTime localDate = LocalDateTime.now();
							String timestamp = dtf.format(localDate);
							sqlCommand = "UPDATE Activity INNER JOIN Env ON Activity.Env=Env.id SET Env.Senses=" + conditionSensesString + "Env.CpuUsage=" + newConditionEnv.rawEnv.currentCpuUsage + "Env.CreationDateTime=\"" + timestamp + "\" WHERE Activity.id=" + activityId + ";";
						} catch (Exception f) {
							f.printStackTrace();
						}

					} else { //activity solution didn't work
						//create new activity that does the same sense prop change but with the current Env as the condition env
						DatabaseHandler.uploadEnvToDatabase(envIn);
					}
				}	
				
				
				
			} catch (Exception f) {
				f.printStackTrace();
			}		
		}
		return envIn;
		
	}
	
	public static Color createColorFromRange(String rangeIn) {
		Color output = null;
		if (rangeIn.equals("Red")) {
			output = new Color(255, 0, 0);
		} else if (rangeIn.equals("White")) {
			output = new Color(255, 255, 255);
		} else if (rangeIn.equals("Grey")) {
			output = new Color(96, 96, 96);
		} else if (rangeIn.equals("Black")) {
			output = new Color(0, 0, 0);
		} else if (rangeIn.equals("Brown")) {
			output = new Color(102, 51, 0);
		} else if (rangeIn.equals("Orange")) {
			output = new Color(255, 128, 0);
		} else if (rangeIn.equals("Yellow")) {
			output = new Color(255, 255, 0);
		} else if (rangeIn.equals("Green")) {
			output = new Color(0, 255, 0);
		} else if (rangeIn.equals("Teal")) {
			output = new Color(0, 255, 255);
		} else if (rangeIn.equals("Blue")) {
			output = new Color(0, 0, 255);
		} else if (rangeIn.equals("Purple")) {
			output = new Color(102, 0, 204);
		} else if (rangeIn.equals("Pink")) {
			output = new Color(255, 102, 255);
		}
		
		return output;
	}
}
