package Tests;

import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.UploadConditionEnvToDB;
import Structure.Env;

public class UploadConditionEnvToDBTest {
	public static void main(String[] args) {
		//Reset DB
		DatabaseHandler.main(null);
								
		Env env = new Env();
		UploadConditionEnvToDB.exec(env);
	}
}
