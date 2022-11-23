package EnvAndDatabaseServiceMethods;

import java.awt.image.BufferedImage;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv;
import EnvAndDatabaseServiceMethods.ChangedPixelsFromOldEnv.junctionList;
import Structure.Blob;
import Structure.Env;
import Structure.Pixel;
import Structure.Sense;
import EnvAndDatabaseServiceMethods.ChangedPixelsToBlobs;
public class UpdateEnv {
	public static Env update(Env envIn, Connection myConnection) {
		envIn.abstractEnv.recentlyChangedOldSenses.clear();
		envIn.abstractEnv.recentlyAddedSenses.clear();
		Env newEnv = new Env(0);
		newEnv.rawEnv = newEnv.senseRawEnv(envIn);
		BufferedImage newImage = newEnv.rawEnv.currentDisplay.getSubimage(0, 100, 1680, 700);
		BufferedImage oldImage = envIn.rawEnv.currentDisplay.getSubimage(0, 100, 1680, 700);
		
		List<junctionList> changedPixelsByThread = ChangedPixelsFromOldEnv.find(newImage, oldImage);
		List<Blob> newBlobs = ChangedPixelsToBlobs.exec(changedPixelsByThread, newImage);
		List<Sense> newishSenses = new ArrayList<Sense>();
		for (int i = 0; i < newBlobs.size(); i++) {
			Sense currentSense = BlobToSense.getSense(newBlobs.get(i), newImage);
			//if (currentSense.orientation.height > 1 && currentSense.orientation.width > 1) {
				//if (currentSense.orientation.height > 3 || currentSense.orientation.width > 3) {
					newishSenses.add(currentSense);
				//}		
			//}		
		}
		//VisualOutputOfSensesFromSensesAndImage.execute(newishSenses, newEnv.rawEnv.currentDisplay, "newishSensesBeforeChange");
		newishSenses = SenseCombiner.exec(newishSenses, envIn.abstractEnv.senses);
		//VisualOutputOfSensesFromSensesAndImage.execute(newishSenses, newEnv.rawEnv.currentDisplay, "newishSensesAfterCombining");
		try {
			envIn.rawEnv = newEnv.rawEnv;
			envIn = UpdateSenses.update(newishSenses, envIn, true, myConnection);
			//VisualOutputOfSensesFromSensesAndImage.execute(envIn.abstractEnv.senses, newEnv.rawEnv.currentDisplay, "EnvSensesAfterUpdateSenses");
			envIn = RemoveOldSensesFromEnv.exec(newishSenses, envIn, myConnection);
			//VisualOutputOfSensesFromSensesAndImage.execute(envIn.abstractEnv.senses, newEnv.rawEnv.currentDisplay, "EnvSensesAfterRemoveOldSenses");
			UploadOrientationChangesToDB.upload(envIn, myConnection);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//VisualOutputOfSensesFromSensesAndImage.execute(envIn.abstractEnv.senses, envIn.rawEnv.currentDisplay.getSubimage(200, 300, 50, 50), "output2");
		
		return envIn;
	}
}
