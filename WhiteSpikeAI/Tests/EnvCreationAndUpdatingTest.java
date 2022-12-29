package Tests;

import java.sql.Connection;
import java.sql.DriverManager;

import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.UpdateEnv;
import MainLLF.Constants;
import Structure.Env;

public class EnvCreationAndUpdatingTest {
	public static void main(String[] args) {
		try {
			Connection myConnection = DriverManager.getConnection(Constants.whitespikeurl, Constants.user, Constants.password);
			Env env = new Env();
			DatabaseHandler.uploadEnvToDatabase(env, myConnection);
			System.out.println();
			UpdateEnv.update(env, myConnection);
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
