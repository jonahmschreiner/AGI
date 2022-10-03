package Tests;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import EnvAndDatabaseServiceMethods.BlobToSense;
import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv;
import EnvAndDatabaseServiceMethods.ChangedPixelsToBlobs;
import EnvAndDatabaseServiceMethods.DatabaseHandler;
import EnvAndDatabaseServiceMethods.SenseCombiner;
import EnvAndDatabaseServiceMethods.UpdateSenses;
import EnvAndDatabaseServiceMethods.VisualOutputOfSensesFromSensesAndImage;
import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv.junctionList;
import Structure.Blob;
import Structure.Env;
import Structure.Pixel;
import Structure.Sense;

public class UpdateSensesTest {
	public static void main(String[] args) {
		//Reset DB
		DatabaseHandler.main(null);
		
		Env env = new Env();
		DatabaseHandler.uploadEnvToDatabase(env);
		
		Env newEnv = new Env(0);
		newEnv.rawEnv = newEnv.senseRawEnv();
		BufferedImage newImage = newEnv.rawEnv.currentDisplay.getSubimage(200, 300, 50, 50);
		BufferedImage oldImage = env.rawEnv.currentDisplay.getSubimage(200, 300, 50, 50);
		
		List<junctionList> changedPixelsByThread = ChangedPixelsFromOldEnv.find(newImage, oldImage);
		List<Blob> newBlobs = ChangedPixelsToBlobs.exec(changedPixelsByThread, newImage);
		List<Sense> newishSenses = new ArrayList<Sense>();
		for (int i = 0; i < newBlobs.size(); i++) {
			Sense currentSense = BlobToSense.getSense(newBlobs.get(i));
			//if (currentSense.orientation.height > 1 && currentSense.orientation.width > 1) {
				//if (currentSense.orientation.height > 3 || currentSense.orientation.width > 3) {
					newishSenses.add(currentSense);
				//}		
			//}		
		}
		try {
			newishSenses = SenseCombiner.exec(newishSenses);
			Env outputEnv = UpdateSenses.update(newishSenses, env);
			outputEnv.rawEnv = newEnv.rawEnv;
			VisualOutputOfSensesFromSensesAndImage.execute(outputEnv.abstractEnv.senses, outputEnv.rawEnv.currentDisplay.getSubimage(200, 300, 50, 50), "outputEnv");
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
