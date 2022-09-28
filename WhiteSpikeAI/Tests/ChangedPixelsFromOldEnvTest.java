package Tests;

import Structure.Env;
import Structure.Pixel;

import java.util.ArrayList;
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
		List<Pixel> pixelsOutput = new ArrayList<Pixel>();
		for (int i = 0; i < output1.size(); i++) {
			pixelsOutput.addAll(output1.get(i).pixels);
		}
		System.out.println();
	}
}
