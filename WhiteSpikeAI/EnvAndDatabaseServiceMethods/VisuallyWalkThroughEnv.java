package EnvAndDatabaseServiceMethods;

import java.util.ArrayList;
import java.util.List;

import Structure.Env;
import Structure.Sense;

public class VisuallyWalkThroughEnv {
	public static void start(Env envIn) {
		List<Sense> senseList = new ArrayList<Sense>();
		for (int i = 0; i < envIn.abstractEnv.senses.size(); i++) {
			Sense currentSense = envIn.abstractEnv.senses.get(i);
			senseList.add(currentSense);
			VisualOutputOfSensesFromSensesAndImage.execute(senseList, envIn.rawEnv.currentDisplay.getSubimage(700, 300, 300, 300), null);
			senseList.remove(0);
		}
	}
}
