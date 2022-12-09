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
import MainLLF.Constants;
import Structure.DBObjectHighestValueResults;
import Structure.Env;
import Structure.PixelColorRange;
import Structure.PixelOverallChange;
import Structure.Sense;
public class ExecuteActivity {
	public static Env execByDBId(Env envIn, int activityId, FileWriter fw, Connection myConnection) throws IOException { 
		Env prevEnv = CreateDeepCopyOfEnv.exec(envIn);
		fw.append(" EXECACT: CreateDeepCopyOfEnv Finished\n");
		fw.flush();
		List<Sense> prevEnvSenses2 = new ArrayList<Sense>();
		prevEnvSenses2.addAll(envIn.abstractEnv.senses);
		ResultSet rs = DatabaseHandler.getActivityForExecution(activityId, myConnection);
		fw.append(" EXECACT: Activity Gotten For Execution\n");
		fw.flush();
		int propId = -1;
		int increaseOrDecreaseProp = -1;
		int coreActivityToExecute = -1;
		int associatedSense = -99999;
		try {
			rs.next();	
			try {
				propId = rs.getInt("PropertyId");
				increaseOrDecreaseProp = rs.getInt("increaseOrDecreaseProp");
				associatedSense = rs.getInt("AssociatedSense");
			} catch (Exception e) {
				e.printStackTrace();
			}
			coreActivityToExecute = rs.getInt("CoreActivity");
			if (coreActivityToExecute == -1) {
				throw new Exception();
			}
			fw.append(" EXECACT: Core Activity Found\n");
			fw.flush();
			ExecuteCoreAction.exec(coreActivityToExecute, fw, envIn.rawEnv); //TODO remove the envIn.rawEnv when done testing
		} catch (Exception e) {
			fw.append(" EXECACT: Activity with subactivities found\n");
			fw.flush();
			try {
				String listOfSubActivities = rs.getString("SubActivities");
				String[] arrayOfSubActivityStrings = listOfSubActivities.split(" ");
				for (int i = 0; i < arrayOfSubActivityStrings.length; i++) {
					String currStr = arrayOfSubActivityStrings[i];
					int currActivityId = Integer.valueOf(currStr);
					Env currPrevEnv = new Env(0);
					currPrevEnv.rawEnv.currentCpuUsage = envIn.rawEnv.currentCpuUsage;
					currPrevEnv.rawEnv.currentDisplay = envIn.rawEnv.currentDisplay;
					envIn = ExecuteActivity.execByDBId(envIn, currActivityId, fw, myConnection);
					fw.append(" EXECACT: Execution of a subactivity finished\n");
					fw.flush();
					//insert activity-worked-checking and environment-closing here
					//List<Sense> prevEnvSenses = new ArrayList<Sense>();
					currPrevEnv.abstractEnv.senses.addAll(envIn.abstractEnv.senses);
					envIn = UpdateEnv.update(envIn, myConnection);
					fw.append(" EXECACT: Updating Env after Sub-Activity Execution Finished\n");
					fw.flush();

					if (currActivityId > (Constants.numOfCoreActions + Constants.numOfRawPropActivities)) { //ensure the sub-activity isn't a core action to avoid errors in env-closing
						fw.append(" EXECACT: Executed Sub-Activity is not a core action\n");
						fw.flush();
						Sense s = GetSenseAssociatedWithActivity.execute(envIn, currActivityId, myConnection);
						if (CheckIfActivityWasSolved.execute(s, currActivityId, envIn, myConnection)) { //activity solution worked
							//create and assign condition env that only contains elements that are common to existing condition env and the current env where it just worked
							fw.append(" EXECACT: Sub-Activity Worked Correctly\n");
							fw.flush();
							try {
								Statement myState = myConnection.createStatement();
								int conditionEnvDBId = rs.getInt("ConditionEnv");
								String sqlCommand = "SELECT Senses FROM ConditionEnv WHERE id=" + conditionEnvDBId + ";";
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
								sqlCommand = "SELECT ConditionSenseDefinition.Definition, ConditionOrientation.Height, ConditionOrientation.Width, ConditionOrientation.Rotation, ConditionOrientation.x, ConditionOrientation.y, ConditionOrientation.color FROM ConditionSense INNER JOIN ConditionOrientation ON ConditionSense.ConditionOrientation=ConditionOrientation.id INNER JOIN ConditionSenseDefinition ON ConditionSense.ConditionSenseDefinition=ConditionSenseDefinition.id WHERE ConditionSense.id IN" + inString + ";";
								fw.append("SQL IDK2: " + sqlCommand);
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
								fw.append(" EXECACT: Re-creating condition env from db finished\n");
								fw.flush();
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
								currPrevEnv.abstractEnv.recentlyAddedSenses.clear();
								currPrevEnv.abstractEnv.recentlyChangedOldSenses.clear();
								List<Sense> currPrevEnvSensesList = new ArrayList<Sense>();
								currPrevEnvSensesList.addAll(currPrevEnv.abstractEnv.senses);
								int conditionEnvSensesSize = conditionEnvSenses.size();
								currPrevEnv = UpdateSenses.update(conditionEnvSenses, currPrevEnv, false, myConnection); 
								
								
								//recentlyChangedOldSenses should contain the indexes of the existing db conditionEnv senses that are present in this condition env
								//loop through them and compare to currPrevEnv.abstractEnv.senses. any differences make that property irrelevant
								
								HashMap<Integer, Integer> map = GetSenseAssociatedWithActivity.createIdToIndexMap(prevEnv.abstractEnv.senses);
								for (int j = 0; j < currPrevEnv.abstractEnv.recentlyChangedOldSenses.size(); j++) {
									int currIndex = currPrevEnv.abstractEnv.recentlyChangedOldSenses.get(j);
									Sense currSense = prevEnv.abstractEnv.senses.get(map.get(currIndex));
									Sense currPrevEnvSense = currPrevEnvSensesList.get(map.get(currIndex));
									PixelColorRange currConSensepcr = new PixelColorRange(currPrevEnvSense.orientation.color);
									PixelColorRange currSensepcr = null;
									if (currSense.orientation.color != null) {
										currSensepcr = new PixelColorRange(currSense.orientation.color);
									}
									if (currSense.definition.overallChangeDefString != null) {
										if (!currPrevEnvSense.definition.toString().equals(currSense.definition.toString())){
											currSense.definition.overallChangeDefString = null;
										}
									}
									if (currPrevEnvSense.orientation.height != currSense.orientation.height) {
										currSense.orientation.height = -1;
									}
									if (currPrevEnvSense.orientation.width != currSense.orientation.width) {
										currSense.orientation.width = -1;	
									}
									if (currPrevEnvSense.orientation.rotation != currSense.orientation.rotation) {
										currSense.orientation.rotation = -999599;	
									}
									if (currPrevEnvSense.orientation.position.x != currSense.orientation.position.x) {
										currSense.orientation.position.x = -1;	
									}
									if (currPrevEnvSense.orientation.position.y != currSense.orientation.position.y) {
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
								
								
								
								
								
								
//								for (int j = 0; j < currPrevEnv.abstractEnv.senses.size(); j++) {
//									Sense currSense = currPrevEnv.abstractEnv.senses.get(j);
//									for (int k = 0; k < conditionEnvSenses.size(); k++) {
//										Sense currConditionSense = conditionEnvSenses.get(k);
//										PixelColorRange currConSensepcr = new PixelColorRange(currConditionSense.orientation.color);
//										PixelColorRange currSensepcr = new PixelColorRange(currSense.orientation.color);
//										if (currConditionSense.definition.toString().equals(currSense.definition.toString())
//												&& currConditionSense.orientation.height == currSense.orientation.height
//												&& currConditionSense.orientation.width == currSense.orientation.width
//												&& currConditionSense.orientation.rotation == currSense.orientation.rotation
//												&& currConditionSense.orientation.position.x == currSense.orientation.position.x
//												&& currConditionSense.orientation.position.y == currSense.orientation.position.y
//												&& currConSensepcr.color.equals(currSensepcr.color)) {
//											newConditionEnv.abstractEnv.senses.add(currSense);
//											if (newConditionEnv.abstractEnv.senses.size() == conditionEnvSenses.size()) {
//												break;
//											}
//										}
//									}
//									if (newConditionEnv.abstractEnv.senses.size() == conditionEnvSenses.size()) {
//										break;
//									}
//								}
								
								newConditionEnv = UploadConditionEnvToDB.exec(newConditionEnv, myConnection);
								//newConditionEnv = UpdateConditionEnvInDBToBeThisEnv.update(newConditionEnv, currActivityId, myConnection, fw);
								//
								//for testing TODO
								newConditionEnv.dbId = newConditionEnv.dbId + 1;
								//
								
								fw.append(" EXECACT: narrowed-down condition env uploading to db finished\n");
								fw.flush();
//								String conditionSensesString = "";
//								for (int l = 0; l < newConditionEnv.abstractEnv.senses.size(); l++) {
//									conditionSensesString = conditionSensesString + newConditionEnv.abstractEnv.senses.get(l).dbId + " ";
//								}
								//replace ConditionEnv in db for this activity with the new ConditionEnv
								dropConditionEnvUsingActivityId(activityId, myConnection);
								sqlCommand = "UPDATE Activity SET ConditionEnv=" + newConditionEnv.dbId + "WHERE Activity.id=" + activityId + ";";
								fw.append("SQL3: " + sqlCommand + "\n");
								fw.flush();
								myState.execute(sqlCommand);
							} catch (Exception f) {
								f.printStackTrace();
							}

						} else { //activity solution didn't work
							//create new activity that does the same sense prop change but with the current Env as the condition env
							fw.append(" EXECACT: Sub-Activity Did Not Work Correctly\n");
							fw.flush();
							UploadConditionEnvToDB.exec(currPrevEnv, myConnection);
							fw.append(" EXECACT: Condition Env Uploading To DB Finished\n");
							fw.flush();
							DBObjectHighestValueResults dbocr = new DBObjectHighestValueResults(myConnection);
							fw.append("Condition Env Id of New Activity: " + dbocr.conditionEnvCount + ". New Activity Id: " + (dbocr.activityCount + 1));
							fw.flush();
							String sqlCommand = "";
							if (associatedSense < 0 && associatedSense > (-1 * Constants.numOfRawProps) - 3) {
								sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + (dbocr.conditionEnvCount) + ", " + associatedSense + ", " + propId + ", " + increaseOrDecreaseProp + ", " + coreActivityToExecute + ");";
							} else {
								sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + (dbocr.conditionEnvCount) + ", " + s.dbId + ", " + propId + ", " + increaseOrDecreaseProp + ", " + coreActivityToExecute + ");";
							}
							try {
								Statement myState = myConnection.createStatement();
								myState.execute(sqlCommand);
							} catch (Exception f) {
								f.printStackTrace();
							}
							
						}
					}		
				}
				fw.append(" EXECACT: Sub-Activity Execution Finished. Evaluating and Closing Down Condition Env of Top-Level Action\n");
				fw.flush();
				fw.append("JSD envIn senses size: " + envIn.abstractEnv.senses.size() + "\n");
				fw.flush();
				//attempt to close down current activity's condition env
				if (activityId > Constants.numOfCoreActions) { //ensure the sub-activity isn't a core action to avoid errors in env-closing
					fw.append(" EXECACT: Top Activity was not a Core Action;");
					fw.flush();
					Sense s = GetSenseAssociatedWithActivity.execute(envIn, activityId, myConnection);
					if (CheckIfActivityWasSolved.execute(s, activityId, envIn, myConnection)) { //activity solution worked
						fw.append(" EXECACT: Top Activity Worked Correctly, narrowing down condition env\n");
						fw.flush();
						//create and assign condition env that only contains elements that are common to existing condition env and the current env where it just worked
						try {
							Statement myState = myConnection.createStatement();
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
							fw.append("made it here pos 1");
							fw.flush();
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
							fw.append("conditionEnvSenses size: " + conditionEnvSenses.size() + "\n");
							fw.flush();
							prevEnv = UpdateSenses.update(conditionEnvSenses, prevEnv, false, myConnection); 
							//Attempting to fix senses not being removed from condition env bug
							
						
							//
							fw.append("made it here pos 2\n");
							fw.flush();
							fw.append("prevEnv.abstractEnv.recentlyChangedOldSenses.size(): " + prevEnv.abstractEnv.recentlyChangedOldSenses.size() + "\n");
							fw.flush();
							for (int i = 0; i < prevEnv.abstractEnv.senses.size(); i++) {
								Sense currSense = prevEnv.abstractEnv.senses.get(i);
								fw.append("prevEnv sense " + i + ": " + currSense.orientation.color + "\n");
								fw.flush();
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
							
							fw.append("made it here pos 3");
							fw.flush();
							fw.append("\nnewConditionEnvSenseListSize: " + newConditionEnv.abstractEnv.senses.size() + "\n");
							fw.flush();
							//
							//newConditionEnv = UpdateConditionEnvInDBToBeThisEnv.update(newConditionEnv, activityId, myConnection, fw);
							newConditionEnv = UploadConditionEnvToDB.exec(newConditionEnv, myConnection);
							//
							fw.append("dbId after Update Con Env: " + newConditionEnv.dbId + "\n");
							fw.flush();
							
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
							fw.append("SQL2: " + sqlCommand + "\n");
							fw.flush();
							myState.execute(sqlCommand);
						} catch (Exception f) {
							f.printStackTrace();
							fw.append("ERRRRRRORRRR regarding Activity " + activityId + ": " + f.getMessage());
							fw.flush();
						}

					} else { //activity solution didn't work
						fw.append(" EXECACT: Top Activity Didn't Work, creating new activity with this condition env\n");
						fw.flush();
						//create new activity that does the same sense prop change but with the current Env as the condition env
						prevEnv = UploadConditionEnvToDB.exec(prevEnv, myConnection);
						DBObjectHighestValueResults dbocr = new DBObjectHighestValueResults(myConnection);
						String sqlCommand = "";
						if (associatedSense < 0 && associatedSense > (-1 * Constants.numOfRawProps) - 3) {
							sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + (dbocr.conditionEnvCount) + ", " + associatedSense + ", " + propId + ", " + increaseOrDecreaseProp + ", " + coreActivityToExecute + ");";
						} else {
							sqlCommand = "INSERT INTO Activity (ConditionEnv, AssociatedSense, PropertyId, increaseOrDecreaseProp, CoreActivity) VALUES (" + (dbocr.conditionEnvCount) + ", " + s.dbId + ", " + propId + ", " + increaseOrDecreaseProp + ", " + coreActivityToExecute + ");";
						}

						try {
							Statement myState = myConnection.createStatement();
							myState.execute(sqlCommand);
						} catch (Exception f) {
							f.printStackTrace();
						}
					}
				}	
				
				
				
			} catch (Exception f) {
				fw.append("ERROR HERE FOR EXECUTION OF ACTIVITY " + activityId + ": " + f.getMessage());
				fw.flush();
				f.printStackTrace();
			}		
		}
		return envIn;
		
	}
	
	
	public static void dropConditionEnvUsingActivityId(int activityIdIn, Connection myConnection) {
		try {
			Statement myState = myConnection.createStatement();
			String sqlCommand = "SELECT ConditionEnv FROM Activity WHERE id=" + activityIdIn + ";";
			ResultSet rs = myState.executeQuery(sqlCommand);
			rs.next();
			int conditionEnvId = rs.getInt("ConditionEnv");
			sqlCommand = "SET FOREIGN_KEY_CHECKS=0;";
			myState.execute(sqlCommand);
			sqlCommand = "SELECT Senses FROM ConditionEnv WHERE id=" + conditionEnvId + ";";
			rs = myState.executeQuery(sqlCommand);
			rs.next();
			String sensesString = rs.getString("Senses");
			String[] sensesStringArray = sensesString.split(" ");
			for (int i = 0; i < sensesStringArray.length; i++) {
				int currSenseId = Integer.valueOf(sensesStringArray[i]);
				sqlCommand = "SELECT ConditionOrientation, ConditionSenseDefinition, ConditionOrientationChange FROM ConditionSense WHERE id=" + currSenseId + ";";
				rs = myState.executeQuery(sqlCommand);
				rs.next();
				int currConOrId = rs.getInt("ConditionOrientation");
				int currConSenseDefId = rs.getInt("ConditionSenseDefinition");
				int currConOrChangeId = rs.getInt("ConditionOrientationChange");
				
				sqlCommand = "DELETE FROM ConditionOrientation WHERE id=" + currConOrId + ";";
				myState.execute(sqlCommand);
				sqlCommand = "DELETE FROM ConditionSenseDefinition WHERE id=" + currConSenseDefId + ";";
				myState.execute(sqlCommand);
				sqlCommand = "DELETE FROM ConditionOrientationChange WHERE id=" + currConOrChangeId + ";";
				myState.execute(sqlCommand);
				sqlCommand = "DELETE FROM ConditionSense WHERE id=" + currSenseId + ";";
				myState.execute(sqlCommand);
			}
			sqlCommand = "DELETE FROM ConditionEnv WHERE id=" + conditionEnvId + ";";
			myState.execute(sqlCommand);
			sqlCommand = "SET FOREIGN_KEY_CHECKS=1;";
			myState.execute(sqlCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
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
