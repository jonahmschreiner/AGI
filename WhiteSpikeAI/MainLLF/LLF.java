package MainLLF;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
	public static void main(String[] args) {
		
		DatabaseHandler.doSetupIfNecessary();
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);
		List<Integer> activitiesToSolveQueue = new ArrayList<Integer>();
		List<String> activitiesToTryQueue = new ArrayList<String>();
		List<Integer> actionQueue = new ArrayList<Integer>();
		while (true) {
			System.out.println("new loop");
			activitiesToSolveQueue = SetUpActivitiesToSolveQueueIfNecessary.setup(activitiesToSolveQueue);
			System.out.println("activities to solve set up if necessary");
			activitiesToTryQueue = SetUpActivitiesToTryQueueIfNecessary.setup(activitiesToTryQueue, activitiesToSolveQueue.get(0));
			System.out.println("activities to try set up if necessary");
			actionQueue = SetUpActionQueueIfNecessary.setup(actionQueue, activitiesToTryQueue);
			System.out.println("action queue set up if necessary");
			
			Env conditionEnv = CreateDeepCopyOfEnv.exec(env);
			
			while (actionQueue.size() > 0) {
				env = ExecuteActivity.execByDBId(env, actionQueue.get(0));
				actionQueue.remove(0);
			}
			System.out.println("actions executed");
			//get sense obj that who is associated with the activity we are currently trying to solve
			int currentActivityToSolveID = activitiesToSolveQueue.get(0);
			Sense s = GetSenseAssociatedWithActivity.execute(env, currentActivityToSolveID);
			System.out.println("sense associated with activity gotten");
			//check if activity was solved and clear ActivityToTryQueue and ActionQueue if so and update the solved's db entry
			boolean solved = false;
			if (CheckIfActivityWasSolved.execute(s, currentActivityToSolveID, env)) {

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
