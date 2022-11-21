package MainLLF;

import java.awt.Frame;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
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
import EnvAndDatabaseServiceMethods.VisuallyWalkThroughEnv;
import Structure.Env;
import Structure.Sense;

public class LLF {
	public static void main(String[] args) throws IOException {
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
		System.out.println("db setup done");
		fw.append(" db setup done\n");
		fw.flush();
		Env env = new Env();
		System.out.println("initial env created");
		fw.append(" initial env created\n");
		fw.flush();
		DatabaseHandler.uploadEnvToDatabase(env);
		System.out.println("env uploaded to db");
		fw.append(" env uploaded to db\n");
		fw.flush();
		List<Integer> activitiesToSolveQueue = new ArrayList<Integer>();
		List<String> activitiesToTryQueue = new ArrayList<String>();
		List<Integer> actionQueue = new ArrayList<Integer>();
		while (true) {
			System.out.println("new loop");
			fw.append(" start of new life loop iteration\n");
			fw.flush();
			activitiesToSolveQueue = SetUpActivitiesToSolveQueueIfNecessary.setup(activitiesToSolveQueue);
			System.out.println("activities to solve set up if necessary");
			fw.append(" activities to solve queue set up (count: " + activitiesToSolveQueue.size() + ")\n");
			fw.flush();
			activitiesToTryQueue = SetUpActivitiesToTryQueueIfNecessary.setup(activitiesToTryQueue, activitiesToSolveQueue.get(0));
			System.out.println("activities to try set up if necessary");
			fw.append(" activities to try queue set up (count: " + activitiesToTryQueue.size() + ")\n");
			fw.flush();
			actionQueue = SetUpActionQueueIfNecessary.setup(actionQueue, activitiesToTryQueue);
			System.out.println("action queue set up if necessary");
			fw.append(" action queue set up (count: " + actionQueue.size() + ")\n");
			fw.flush();
			Env conditionEnv = CreateDeepCopyOfEnv.exec(env);
			System.out.println("deep copy of env created");
			fw.append(" deep copy of env created\n");
			fw.flush();
			while (actionQueue.size() > 0) {
				env = ExecuteActivity.execByDBId(env, actionQueue.get(0));
				actionQueue.remove(0);
				System.out.println("individual action executed");
				fw.append(" individual action finished executing from actionQueue\n");
				fw.flush();
			}
			System.out.println("actions executed");
			fw.append(" action queue finished executing\n");
			fw.flush();
			//get sense obj that who is associated with the activity we are currently trying to solve
			int currentActivityToSolveID = activitiesToSolveQueue.get(0);
			Sense s = GetSenseAssociatedWithActivity.execute(env, currentActivityToSolveID);
			System.out.println("sense associated with activity gotten");
			fw.append(" sense associated with activity gotten\n");
			fw.flush();
			//check if activity was solved and clear ActivityToTryQueue and ActionQueue if so and update the solved's db entry
			boolean solved = false;
			if (CheckIfActivityWasSolved.execute(s, currentActivityToSolveID, env)) {
				fw.append(" activity " + currentActivityToSolveID + " was solved\n");
				fw.flush();
				//update the solved's db entry
				try {
					Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
					Statement myState = myConnection.createStatement();
					
					conditionEnv = UploadConditionEnvToDB.exec(conditionEnv);
					
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
				fw.append(" activity " + currentActivityToSolveID + " was not solved\n");
				fw.flush();
				activitiesToTryQueue.remove(0);
				if (activitiesToTryQueue.size() == 0) {
					activitiesToSolveQueue.remove(0);
					//update numOfSolveAttempts in database
					try {
						Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
						Statement myState = myConnection.createStatement();
						String sqlCommand = "UPDATE Activity SET numOfSolveAttempts=numOfSolveAttempts + 1 WHERE id=" + currentActivityToSolveID + ";";
						myState.execute(sqlCommand);
					} catch (Exception e) {
						
					}		
				}
			}
		}

	}
}
