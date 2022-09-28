package Tests;

import java.util.ArrayList;
import java.util.List;

import EnvAndDatabaseServiceMethods.BlobToSense;
import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv;
import EnvAndDatabaseServiceMethods.ChangedPixelsToBlobs;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.VisualOutputOfSensesFromSensesAndImage;
import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv.junctionList;
import Structure.Blob;
import Structure.Env;
import Structure.Pixel;
import Structure.Sense;

public class ChangedPixelsToBlobsTest {
	public static void main(String[] args) {
		
		Env env = new Env(0);
		env.senseRawEnv();
		env.rawEnv.currentDisplay = env.rawEnv.currentDisplay.getSubimage(200, 300, 50, 50);
		
		//change pixel info for next screenshot
		Env env2 = new Env(0);
		env2.senseRawEnv();
		env2.rawEnv.currentDisplay = env2.rawEnv.currentDisplay.getSubimage(200, 300, 50, 50);
		
		List<junctionList> cpfoeOutput = ChangedPixelsFromOldEnv.find(env2.rawEnv.currentDisplay, env.rawEnv.currentDisplay);
		
		List<Pixel> pixelsOutput = new ArrayList<Pixel>();
		for (int i = 0; i < cpfoeOutput.size(); i++) {
			pixelsOutput.addAll(cpfoeOutput.get(i).pixels);
		}
		
		
		List<Blob> output = ChangedPixelsToBlobs.exec(cpfoeOutput, env2.rawEnv.currentDisplay);
		List<Sense> outputSenses = new ArrayList<Sense>();
		for (int i = 0; i < output.size(); i++) {
			outputSenses.add(BlobToSense.getSense(output.get(i)));
		}
		env2.abstractEnv.senses = outputSenses;
		VisualOutputOfSensesFromSensesAndImage.execute(outputSenses, env2.rawEnv.currentDisplay, "result");
		VisualOutputOfSensesFromSensesAndImage.execute(new ArrayList<Sense>(), env.rawEnv.currentDisplay, "initialImage");
		System.out.println("Should be 5: " + output.size());
	}
}
