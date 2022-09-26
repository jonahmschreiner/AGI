package Tests;

import java.util.ArrayList;
import java.util.List;

import ActivitySolverServiceMethods.SetUpActivitiesToTryQueueIfNecessary;
import EnvAndDatabaseServiceMethods.DatabaseHandler;

public class SetUpActivitiesToTryQueueTest {
	public static void main(String[] args) {
		DatabaseHandler.main(null);
		List<String> input = new ArrayList<String>();
		input = SetUpActivitiesToTryQueueIfNecessary.setup(input);
		System.out.println("Should be 444: " + input.size());
		
		List<String> input2 = new ArrayList<String>();
		input2.add("testNotSizeZero");
		input2 = SetUpActivitiesToTryQueueIfNecessary.setup(input2);
		System.out.println("Should be 1: " + input2.size());
	}
}
