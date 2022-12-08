package MainLLF;

import java.awt.Frame;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import ActivitySolverServiceMethods.CheckIfActivityWasSolved;
import ActivitySolverServiceMethods.ExecuteActivity;
import ActivitySolverServiceMethods.SetUpActionQueueIfNecessary;
import ActivitySolverServiceMethods.SetUpActivitiesToSolveQueueIfNecessary;
import ActivitySolverServiceMethods.SetUpActivitiesToTryQueueIfNecessary;
import ActivitySolverServiceMethods.GetSenseAssociatedWithActivity;
import EnvAndDatabaseServiceMethods.CreateDeepCopyOfEnv;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.UpdateEnv;
import EnvAndDatabaseServiceMethods.UploadConditionEnvToDB;
import EnvAndDatabaseServiceMethods.Util;
import EnvAndDatabaseServiceMethods.VisualOutputOfSensesFromSensesAndImage;
import EnvAndDatabaseServiceMethods.VisuallyWalkThroughEnv;
import Structure.Env;
import Structure.Sense;

public class LLF {
	public static void main(String[] args) {
		try {
		Frame frame = new Frame();
		JOptionPane.showMessageDialog(frame, "White Spike was initialized");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		File logFile = new File("/home/agi/Downloads/log.txt");
		FileWriter fw = new FileWriter(logFile);
		LocalDateTime ldt = LocalDateTime.now();
		fw.append(ldt.toString() + "\n-------\n");
		DatabaseHandler.doSetupIfNecessary();
		Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
		Statement myStatement = myConnection.createStatement();
		System.out.println("db setup done");
		fw.append(" db setup done\n");
		fw.flush();
		Env env = new Env();
		System.out.println("initial env created");
		fw.append(" initial env created (Num of Senses: " + env.abstractEnv.senses.size() + ")\n");
		fw.flush();
		DatabaseHandler.uploadEnvToDatabase(env, myConnection);
		System.out.println("env uploaded to db");
		fw.append(" env uploaded to db\n");
		fw.flush();
		List<Integer> activitiesToSolveQueue = new ArrayList<Integer>();
		List<String> activitiesToTryQueue = new ArrayList<String>();
		List<Integer> actionQueue = new ArrayList<Integer>();
		
		//for testing
		int testCount = 0;
		//
		try {
			boolean continueLooping = true;
		while (continueLooping) {
			//for testing
			testCount++;
//			if (testCount == 5) {
//				Point testPoint = MouseInfo.getPointerInfo().getLocation();
//				System.out.println("");
//				try {
//					Thread.sleep(4000);
//					Util util = new Util();
//					util.moveMouseToCoordinates(testPoint.x, testPoint.y);
//				} catch (Exception e) {
//					
//				}
//			}
			if (activitiesToSolveQueue.size() > 0) {
				if (activitiesToSolveQueue.get(0) == 11) {
					Point testPoint = MouseInfo.getPointerInfo().getLocation();
					System.out.println("");
					try {
						Thread.sleep(4000);
						Util util = new Util();
						util.moveMouseToCoordinates(testPoint.x, testPoint.y);
					} catch (Exception e) {
				
					}
				}
			}
			if (testCount == 51) {
				System.out.println();
			}
			//
			
			
			System.out.println("new loop");
			fw.append("\n\n.....................\n\n");
			fw.append(" start of new life loop iteration\n");
			fw.flush();
			activitiesToSolveQueue = SetUpActivitiesToSolveQueueIfNecessary.setup(activitiesToSolveQueue, fw, myConnection);
			System.out.println("activities to solve set up if necessary");
			fw.append(" activities to solve queue set up (count: " + activitiesToSolveQueue.size() + ")\n");
			fw.flush();
			activitiesToTryQueue = SetUpActivitiesToTryQueueIfNecessary.setup(activitiesToTryQueue, activitiesToSolveQueue.get(0), myConnection, fw);
			System.out.println("activities to try set up if necessary");
			fw.append(" activities to try queue set up (count: " + activitiesToTryQueue.size() + ")\n");
			fw.flush();
			actionQueue = SetUpActionQueueIfNecessary.setup(actionQueue, activitiesToTryQueue, myConnection);
			System.out.println("action queue set up if necessary");
			fw.append(" action queue set up (count: " + actionQueue.size() + ")\n");
			fw.flush();
			Env conditionEnv = CreateDeepCopyOfEnv.exec(env);
			System.out.println("deep copy of env created");
			fw.append(" deep copy of env created\n");
			fw.flush();
			boolean coreActionIsTopAction;
			if (actionQueue.get(0) <= Constants.numOfCoreActions) {
				if (actionQueue.size() > 1) {
					if (actionQueue.get(1) <= Constants.numOfCoreActions) {
						coreActionIsTopAction = true;
					} else {
						coreActionIsTopAction = false;
					}
				} else {
					coreActionIsTopAction = true;
				}
			} else {
				coreActionIsTopAction = false;
			}
			while (actionQueue.size() > 0) {
				env = ExecuteActivity.execByDBId(env, actionQueue.get(0), fw, myConnection);
				System.out.println("individual action executed");
				fw.append(" individual action " + actionQueue.get(0) + " finished executing from actionQueue\n");
				fw.flush();
				actionQueue.remove(0);
			}
			if (coreActionIsTopAction) {
				env = UpdateEnv.update(env, myConnection);
			}
			
			System.out.println("actions executed");
			fw.append(" action queue finished executing\n");
			fw.flush();
			//get sense obj that who is associated with the activity we are currently trying to solve
			int currentActivityToSolveID = activitiesToSolveQueue.get(0);
			
			//for testing
			if (currentActivityToSolveID == 11) {
				System.out.println("");
			}
			//
			Sense s = GetSenseAssociatedWithActivity.execute(env, currentActivityToSolveID, myConnection);
			System.out.println("sense associated with activity gotten");
			fw.append(" sense associated with activity gotten\n");
			fw.flush();
			//check if activity was solved and clear ActivityToTryQueue and ActionQueue if so and update the solved's db entry
			boolean solved = false;
			if (s != null) { //the sense to solve a prop for is currently present in the env
			if (CheckIfActivityWasSolved.execute(s, currentActivityToSolveID, env, myConnection)) {
				fw.append(" activity " + currentActivityToSolveID + " was solved\n");
				fw.flush();
				//update the solved's db entry
				try {
					Statement myState = myConnection.createStatement();
					
					conditionEnv = UploadConditionEnvToDB.exec(conditionEnv, myConnection);
					
					String sqlCommand = "UPDATE Activity SET ConditionEnv=" + conditionEnv.dbId + ", SubActivities=\"" + activitiesToTryQueue.get(0) + "\", SolvedStatus=1, numOfSolveAttempts=numOfSolveAttempts + 1 WHERE id=" + currentActivityToSolveID + ";";
					myState.execute(sqlCommand);
				} catch (Exception e) {
					
				}
				solved = true;
				activitiesToTryQueue.clear();
				actionQueue.clear();
				activitiesToSolveQueue.remove(0);
			}		
			if (!solved) {
				fw.append(" activity " + currentActivityToSolveID + " was not solved using activity or activity combo: " + activitiesToTryQueue.get(0) + "\n");
				fw.flush();
				activitiesToTryQueue.remove(0);
				try {
					if (activitiesToTryQueue.size() == 0) {
						activitiesToSolveQueue.remove(0);
						//update numOfSolveAttempts in database
						try {
							Statement myState = myConnection.createStatement();
							String sqlCommand = "UPDATE Activity SET numOfSolveAttempts=numOfSolveAttempts + 1 WHERE id=" + currentActivityToSolveID + ";";
							myState.execute(sqlCommand);
						} catch (Exception e) {
							fw.append(e.getMessage() + "\n");
							fw.flush();
						}		
					}
				} catch (Exception e) {
					fw.append(e.getMessage() + "\n");
					fw.flush();
				}
			}
			} else {//the sense to solve a prop for isn't currently present in the env
				fw.append(" activity " + currentActivityToSolveID + " was not solved using activity or activity combo: " + activitiesToTryQueue.get(0) + " because the associated sense was not present in the env\n");
				fw.flush();
				try {
					Statement myState = myConnection.createStatement();
					String sqlCommand = "UPDATE Activity SET numOfSolveAttempts=numOfSolveAttempts + 1 WHERE id=" + currentActivityToSolveID + ";";
					myState.execute(sqlCommand);
				} catch (Exception e) {
					fw.append(e.getMessage() + "\n");
					fw.flush();
				}		
				activitiesToTryQueue.clear();
				actionQueue.clear();
				activitiesToSolveQueue.remove(0);
			}
			ResultSet rs = myStatement.executeQuery("SELECT * FROM Activity WHERE SolvedStatus=0 LIMIT 1;");
			try {
				rs.next();
				@SuppressWarnings("unused")
				String id = rs.getString("id");
			} catch (Exception e) {
				continueLooping = false;
			}
		}
		} catch (Exception e) {
			StackTraceElement[] ste = e.getStackTrace();
			for (int i = 0; i < ste.length; i++) {
				fw.append("Stack: " + ste[i].toString() + "\n");
				fw.flush();
			}
			fw.append("Stack: " + e.getMessage() + "\n");
			fw.flush();
			
		}
		fw.append("end\n");
		fw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
