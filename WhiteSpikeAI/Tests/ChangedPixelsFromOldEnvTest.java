package Tests;

import EnvAndDatabaseServiceMethods.DatabaseHandler;
import Structure.Env;
import Structure.Pixel;

import java.util.List;

import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv;
import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv.junctionList;

public class ChangedPixelsFromOldEnvTest {
	public static void main (String[] args) {
		
		Env env = new Env(0);
		env.senseRawEnv();
		
		//change pixel info for next screenshot
		Env env2 = new Env(0);
		env2.senseRawEnv();
		
		List<junctionList> output1 = ChangedPixelsFromOldEnv.find(env2.rawEnv.currentDisplay, env.rawEnv.currentDisplay);
		System.out.println();
	}
}
