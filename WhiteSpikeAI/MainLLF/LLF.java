package MainLLF;

import ServiceMethods.DatabaseHandler;
import ServiceMethods.VisuallyWalkThroughEnv;
import Structure.Env;

public class LLF {
	public static void main(String[] args) {
		
		DatabaseHandler.doSetupIfNecessary();
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);
		//VisuallyWalkThroughEnv.start(env);
	}
}
