package MainLLF;

import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.VisuallyWalkThroughEnv;
import Structure.Env;

public class LLF {
	public static void main(String[] args) {
		
		DatabaseHandler.doSetupIfNecessary();
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);

	}
}
