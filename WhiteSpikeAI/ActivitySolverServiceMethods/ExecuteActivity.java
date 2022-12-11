package ActivitySolverServiceMethods;

import java.awt.Color;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import EnvAndDatabaseServiceMethods.CreateDeepCopyOfEnv;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.ExecuteCoreAction;
import EnvAndDatabaseServiceMethods.UpdateConditionEnvInDBToBeThisEnv;
import EnvAndDatabaseServiceMethods.UpdateEnv;
import EnvAndDatabaseServiceMethods.UpdateSenses;
import EnvAndDatabaseServiceMethods.UploadConditionEnvToDB;
import LogsAndTestingUtils.WriteSubActivityExecutionFinishedLog;
import MainLLF.Constants;
import Structure.CheckActivitySolvingWrapper;
import Structure.DBObjectHighestValueResults;
import Structure.Env;
import Structure.ExecuteActivityInputWrapper;
import Structure.ExecuteActivityPrepWrapper;
import Structure.ExecuteCurrentActivityPrepWrapper;
import Structure.ExecuteCurrentActivityWrapper;
import Structure.LFInputWrapper;
import Structure.PixelColorRange;
import Structure.PixelOverallChange;
import Structure.Sense;
public class ExecuteActivity {
	public static Env execByDBId(ExecuteActivityInputWrapper eaiw) throws IOException { 
		
		ExecuteActivityPrepWrapper eapw = ExecuteActivityPrep.exec(eaiw);
		
		try {
			eapw = AttemptExecutionIfCoreActionActivity.exec(eapw, eaiw);
		} catch (Exception e) {
			try {	
				ExecuteCurrentActivityPrepWrapper ecapw = ExecuteCurrentActivityPrep.exec(eaiw, eapw);
		
				for (ecapw.i = 0; ecapw.i < ecapw.arrayOfSubActivityStrings.length; ecapw.i++) {
					
					ExecuteCurrentActivityWrapper ecaw = ExecuteCurrentActivity.exec(eaiw, ecapw);
					eaiw = ecaw.eaiw;
					
					CheckActivitySolvingWrapper casw = CheckActivitySolving.exec(eaiw, eapw, ecaw);
					eaiw = casw.eaiw;
					eapw = casw.eapw;
					ecaw = casw.ecaw;
					
				}
				
				WriteSubActivityExecutionFinishedLog.exec(eaiw);
				
				
				//attempt to close down current activity's condition env
				if (eaiw.activityId > Constants.numOfCoreActions) { //ensure the sub-activity isn't a core action to avoid errors in env-closing
					eaiw.fw.append(" EXECACT: Top Activity was not a Core Action;");
					eaiw.fw.flush();
					Sense s = GetSenseAssociatedWithActivity.execute(eaiw.envIn, eaiw.activityId, eaiw.myConnection);
					if (CheckIfActivityWasSolved.execute(s, eaiw.activityId, eaiw.envIn, eaiw.myConnection)) { //activity solution worked
						eaiw.fw.append(" EXECACT: Top Activity Worked Correctly, narrowing down condition env\n");
						eaiw.fw.flush();
						//create and assign condition env that only contains elements that are common to existing condition env and the current env where it just worked
						try {
							Statement myState = eaiw.myConnection.createStatement();
							int conditionEnvDBId = rs.getInt("ConditionEnv");
							String sqlCommand = "SELECT Senses FROM ConditionEnv WHERE id=" + conditionEnvDBId + ";";
							ResultSet rs2 = myState.executeQuery(sqlCommand);
							rs2.next();
							String senseIdString = "";
							senseIdString = rs2.getString("Senses");
							String[] senseIdStringArray = senseIdString.split(" ");
							String inString = "(";
							for (int i = 0; i < senseIdStringArray.length; i++) {
								inString = inString + senseIdStringArray[i];
								if (i < senseIdStringArray.length - 1) {
									inString = inString + ", ";
								}
							}
							inString = inString + ")";
							sqlCommand = "SELECT ConditionSenseDefinition.Definition, ConditionOrientation.Height, ConditionOrientation.Width, ConditionOrientation.Rotation, ConditionOrientation.x, ConditionOrientation.y, ConditionOrientation.color FROM ConditionSense INNER JOIN ConditionOrientation ON ConditionSense.ConditionOrientation=ConditionOrientation.id INNER JOIN ConditionSenseDefinition ON ConditionSense.ConditionSenseDefinition=ConditionSenseDefinition.id WHERE ConditionSense.id IN" + inString + ";";
							fw.append("SQL IDK: " + sqlCommand);
							fw.flush();
							ResultSet rs3 = myState.executeQuery(sqlCommand);
							List<Sense> conditionEnvSenses = new ArrayList<Sense>();
							while (true) {
								try {
									rs3.next();
									Sense newSense = new Sense();
									newSense.orientation.height = rs3.getInt("ConditionOrientation.Height");
									newSense.orientation.width = rs3.getInt("ConditionOrientation.Width");
									newSense.orientation.rotation = rs3.getInt("ConditionOrientation.Rotation");
									newSense.orientation.position.x = rs3.getInt("ConditionOrientation.x");
									newSense.orientation.position.y = rs3.getInt("ConditionOrientation.y");
									Color color = createColorFromRange(rs3.getString("ConditionOrientation.color"));
									newSense.orientation.color = color;
									List<PixelOverallChange> pocs = new ArrayList<PixelOverallChange>();
									String[] changeDefValues = rs3.getString("ConditionSenseDefinition.Definition").split(";");
									for (int z = 0; z < changeDefValues.length; z++) {
										pocs.add(new PixelOverallChange(changeDefValues[z]));
									}
									newSense.definition.overallChangeDefString = pocs;
									
									conditionEnvSenses.add(newSense);
								} catch (Exception g) {

									break;
								}
							}
							
							// DO THIS match condition env senses to prevEnv ones and compare them in raw env closing instead of comparing the current env to the condition env for some reason???
							eaiw.fw.append("made it here pos 1");
							eaiw.fw.flush();
							double existingConEnvCpuUsage = 0;
							LocalDateTime existingConEnvLDT = LocalDateTime.MAX;
							int existingConEnvMouseX = -5;
							int existingConEnvMouseY = -5;
							try {
								sqlCommand = "SELECT CurrentDateTime, CpuUsage, MouseX, MouseY FROM ConditionEnv WHERE id=" + conditionEnvDBId + ";";
								ResultSet rs5 = myState.executeQuery(sqlCommand);
								rs5.next();
								existingConEnvCpuUsage = rs5.getDouble("CpuUsage");
								existingConEnvLDT = LocalDateTime.parse(rs5.getString("CurrentDateTime"));
								existingConEnvMouseX = rs5.getInt("MouseX");
								existingConEnvMouseY = rs5.getInt("MouseY");
							} catch (Exception f) {
								f.printStackTrace();
							}
							//for each sense in current Env, check to see if the current sense has the same values as any result in the condition env results and if so adds them to a condition env
							Env newConditionEnv = new Env(0);
							//raw env closing
							if (prevEnv.rawEnv.currentDateTime.equals(existingConEnvLDT)) {
								newConditionEnv.rawEnv.currentDateTime = existingConEnvLDT;
							} else {
								newConditionEnv.rawEnv.currentDateTime = LocalDateTime.MIN;
							}
							
							if (prevEnv.rawEnv.currentCpuUsage.compareTo(existingConEnvCpuUsage) == 0) {
								newConditionEnv.rawEnv.currentCpuUsage = existingConEnvCpuUsage;
							} else {
								newConditionEnv.rawEnv.currentCpuUsage = -1.0;
							}
							
							if (prevEnv.rawEnv.mouseLocation.x == existingConEnvMouseX) {
								newConditionEnv.rawEnv.mouseLocation.x = existingConEnvMouseX;
							} else {
								newConditionEnv.rawEnv.mouseLocation.x = -1;
							}
							
							if (prevEnv.rawEnv.mouseLocation.y == existingConEnvMouseY) {
								newConditionEnv.rawEnv.mouseLocation.y = existingConEnvMouseY;
							} else {
								newConditionEnv.rawEnv.mouseLocation.y = -1;
							}
							
							newConditionEnv.rawEnv.currentDisplay = envIn.rawEnv.currentDisplay;
							
							
							//
							prevEnv.abstractEnv.recentlyAddedSenses.clear();
							prevEnv.abstractEnv.recentlyChangedOldSenses.clear();
							List<Sense> prevEnvSensesList = new ArrayList<Sense>();
							prevEnvSensesList.addAll(prevEnv.abstractEnv.senses);
							int conditionEnvSensesSize = conditionEnvSenses.size();
							eaiw.fw.append("conditionEnvSenses size: " + conditionEnvSenses.size() + "\n");
							eaiw.fw.flush();
							prevEnv = UpdateSenses.update(conditionEnvSenses, prevEnv, false, myConnection); 
							//Attempting to fix senses not being removed from condition env bug
							
						
							//
							eaiw.fw.append("made it here pos 2\n");
							eaiw.fw.flush();
							eaiw.fw.append("prevEnv.abstractEnv.recentlyChangedOldSenses.size(): " + prevEnv.abstractEnv.recentlyChangedOldSenses.size() + "\n");
							eaiw.fw.flush();
							for (int i = 0; i < prevEnv.abstractEnv.senses.size(); i++) {
								Sense currSense = prevEnv.abstractEnv.senses.get(i);
								eaiw.fw.append("prevEnv sense " + i + ": " + currSense.orientation.color + "\n");
								eaiw.fw.flush();
							}
							
							//abstract env closing
							HashMap<Integer, Integer> map = GetSenseAssociatedWithActivity.createIdToIndexMap(prevEnv.abstractEnv.senses);
							for (int j = 0; j < prevEnv.abstractEnv.recentlyChangedOldSenses.size(); j++) {
								int currIndex = prevEnv.abstractEnv.recentlyChangedOldSenses.get(j);
								Sense currSense = prevEnv.abstractEnv.senses.get(map.get(currIndex));
								Sense prevEnvSense = prevEnvSensesList.get(map.get(currIndex));
								PixelColorRange currConSensepcr = new PixelColorRange(prevEnvSense.orientation.color);
								PixelColorRange currSensepcr = null;
								if (currSense.orientation.color != null) {
									currSensepcr = new PixelColorRange(currSense.orientation.color);
								}
								if (currSense.definition.overallChangeDefString != null) {
									if (!prevEnvSense.definition.toString().equals(currSense.definition.toString())){
										currSense.definition.overallChangeDefString = null;
									}
								}
								if (prevEnvSense.orientation.height != currSense.orientation.height) {
									currSense.orientation.height = -1;	
								}
								if (prevEnvSense.orientation.width != currSense.orientation.width) {
									currSense.orientation.width = -1;	
								}
								if (prevEnvSense.orientation.rotation != currSense.orientation.rotation) {
									currSense.orientation.rotation = -999599;	
								}
								if (prevEnvSense.orientation.position.x != currSense.orientation.position.x) {
									currSense.orientation.position.x = -1;	
								}
								if (prevEnvSense.orientation.position.y != currSense.orientation.position.y) {
									currSense.orientation.position.y = -1;
								}
								if (currSense.orientation.color != null) {
									if (!currConSensepcr.color.equals(currSensepcr.color)) {
										currSense.orientation.color = null;
									}
								}
								
								newConditionEnv.abstractEnv.senses.add(currSense);
								if (newConditionEnv.abstractEnv.senses.size() == conditionEnvSensesSize) {
									break;
								}
							}
							
							eaiw.fw.append("made it here pos 3");
							eaiw.fw.flush();
							eaiw.fw.append("\nnewConditionEnvSenseListSize: " + newConditionEnv.abstractEnv.senses.size() + "\n");
							eaiw.fw.flush();
							//
							//newConditionEnv = UpdateConditionEnvInDBToBeThisEnv.update(newConditionEnv, activityId, myConnection, fw);
							newConditionEnv = UploadConditionEnvToDB.exec(newConditionEnv, myConnection);
							//
							eaiw.fw.append("dbId after Update Con Env: " + newConditionEnv.dbId + "\n");
							eaiw.fw.flush();
							
							//for testing TODO
							newConditionEnv.dbId = newConditionEnv.dbId + 1;
							//
							
//							for (int j = 0; j < prevEnvSenses2.size(); j++) {
//								Sense currSense = prevEnvSenses2.get(j);
//								for (int k = 0; k < conditionEnvSenses.size(); k++) {
//									Sense currConditionSense = conditionEnvSenses.get(k);
//									PixelColorRange currConSensepcr = new PixelColorRange(currConditionSense.orientation.color);
//									PixelColorRange currSensepcr = new PixelColorRange(currSense.orientation.color);
//									if (currConditionSense.definition.toString().equals(currSense.definition.toString())
//											&& currConditionSense.orientation.height == currSense.orientation.height
//											&& currConditionSense.orientation.width == currSense.orientation.width
//											&& currConditionSense.orientation.rotation == currSense.orientation.rotation
//											&& currConditionSense.orientation.position.x == currSense.orientation.position.x
//											&& currConditionSense.orientation.position.y == currSense.orientation.position.y
//											&& currConSensepcr.color.equals(currSensepcr.color)) {
//										newConditionEnv.abstractEnv.senses.add(currSense);
//										if (newConditionEnv.abstractEnv.senses.size() == conditionEnvSenses.size()) {
//											break;
//										}
//									}
//								}
//								if (newConditionEnv.abstractEnv.senses.size() == conditionEnvSenses.size()) {
//									break;
//								}
//							}
							
							//create senses string
//							String conditionSensesString = "";
//							for (int l = 0; l < newConditionEnv.abstractEnv.senses.size(); l++) {
//								conditionSensesString = conditionSensesString + newConditionEnv.abstractEnv.senses.get(l).dbId + " ";
//							}
							//replace ConditionEnv in db for this activity with the new ConditionEnv
							//sqlCommand = "UPDATE Activity INNER JOIN ConditionEnv ON Activity.ConditionEnv=ConditionEnv.id SET ConditionEnv.Senses=\"" + conditionSensesString + "\" WHERE Activity.id=" + activityId + ";";
							dropConditionEnvUsingActivityId(activityId, myConnection);
							sqlCommand = "UPDATE Activity SET ConditionEnv=" + newConditionEnv.dbId + " WHERE Activity.id=" + activityId + ";";
							eaiw.fw.append("SQL2: " + sqlCommand + "\n");
							eaiw.fw.flush();
							myState.execute(sqlCommand);
						} catch (Exception f) {
							f.printStackTrace();
							eaiw.fw.append("ERRRRRRORRRR regarding Activity " + activityId + ": " + f.getMessage());
							eaiw.fw.flush();
						}

					} else { //activity solution didn't work
						eaiw.fw.append(" EXECACT: Top Activity Didn't Work, creating new activity with this condition env\n");
						eaiw.fw.flush();
						//create new activity that does the same sense prop change but with the current Env as the condition env
						prevEnv = UploadConditionEnvToDB.exec(prevEnv, eaiw.myConnection);
						DBObjectHighestValueResults dbocr = new DBObjectHighestValueResults(eaiw.myConnection);
						String sqlCommand = "";
						if (associatedSense < 0 && associatedSense > (-1 * Constants.numOfRawProps) - 3) {
							sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + (dbocr.conditionEnvCount) + ", " + associatedSense + ", " + propId + ", " + increaseOrDecreaseProp + ", " + coreActivityToExecute + ");";
						} else {
							sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + (dbocr.conditionEnvCount) + ", " + s.dbId + ", " + propId + ", " + increaseOrDecreaseProp + ", " + coreActivityToExecute + ");";
						}

						try {
							Statement myState = eaiw.myConnection.createStatement();
							myState.execute(sqlCommand);
						} catch (Exception f) {
							f.printStackTrace();
						}
					}
				}	
				
				
				
			} catch (Exception f) {
				eaiw.fw.append("ERROR HERE FOR EXECUTION OF ACTIVITY " + activityId + ": " + f.getMessage());
				eaiw.fw.flush();
				f.printStackTrace();
			}		
		}
		return envIn;
		
	}
}
