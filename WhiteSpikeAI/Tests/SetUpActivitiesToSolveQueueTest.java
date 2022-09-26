package Tests;

import java.util.ArrayList;
import java.util.List;

import ActivitySolverServiceMethods.SetUpActivitiesToSolveQueueIfNecessary;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import Structure.Env;

public class SetUpActivitiesToSolveQueueTest {
	public static void main(String[] args) {
		//empty test case
		DatabaseHandler.main(null);
		List<Integer> input = new ArrayList<Integer>();
		input = SetUpActivitiesToSolveQueueIfNecessary.setup(input);
		System.out.println("Should be zero: " + input.size());
		
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);
		List<Integer> input2 = new ArrayList<Integer>();
		input = SetUpActivitiesToSolveQueueIfNecessary.setup(input2);
		System.out.println("Should be ten: " + input2.size());
	}
}
