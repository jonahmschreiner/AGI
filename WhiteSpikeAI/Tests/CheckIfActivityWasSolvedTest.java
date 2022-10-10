package Tests;

import java.util.ArrayList;
import java.util.List;

import ActivitySolverServiceMethods.CheckIfActivityWasSolved;
import ActivitySolverServiceMethods.GetSenseAssociatedWithActivity;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.UpdateEnv;
import Structure.Env;
import Structure.Sense;

public class CheckIfActivityWasSolvedTest {
	public static void main(String[] args) {
		//Reset DB
		DatabaseHandler.main(null);
				
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);
		
		env = UpdateEnv.update(env);
		
		Sense s = GetSenseAssociatedWithActivity.execute(env, 464);
		boolean output = CheckIfActivityWasSolved.execute(s, 464);
		
		System.out.println(output);
		
	}
}
