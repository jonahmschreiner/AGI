package Tests;

import java.util.ArrayList;
import java.util.List;

import ActivitySolverServiceMethods.GetSenseAssociatedWithActivity;
import ActivitySolverServiceMethods.SetUpActivitiesToSolveQueueIfNecessary;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import Structure.Env;
import Structure.Sense;

public class GetSenseAssociatedWithActivityTest {
	public static void main(String[] args) {
		//Reset DB
		DatabaseHandler.main(null);
				
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);
		
		List<Integer> activitiesToSolveQueue = new ArrayList<Integer>();
		activitiesToSolveQueue = SetUpActivitiesToSolveQueueIfNecessary.setup(activitiesToSolveQueue);
		int currActId = activitiesToSolveQueue.get(0);
		@SuppressWarnings("unused")
		Sense s = GetSenseAssociatedWithActivity.execute(env, currActId);
		System.out.println();
		
	}
}
