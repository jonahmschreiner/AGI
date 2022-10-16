package EnvAndDatabaseServiceMethods;

import java.util.ArrayList;

import Structure.Env;
import Structure.Sense;

public class CreateDeepCopyOfEnv {
	public static Env exec(Env envIn) {
		Env output = new Env(0);
		output.abstractEnv.senses = new ArrayList<Sense>();
		output.abstractEnv.senses.addAll(envIn.abstractEnv.senses);
		output.rawEnv.currentCpuUsage = envIn.rawEnv.currentCpuUsage;
		output.rawEnv.currentDisplay = envIn.rawEnv.currentDisplay;
		output.abstractEnv.recentlyAddedSenses = new ArrayList<Sense>();
		output.abstractEnv.recentlyAddedSenses.addAll(envIn.abstractEnv.recentlyAddedSenses);
		output.abstractEnv.recentlyChangedOldSenses = new ArrayList<Integer>();
		output.abstractEnv.recentlyChangedOldSenses.addAll(envIn.abstractEnv.recentlyChangedOldSenses);
		return output;
	}
}

