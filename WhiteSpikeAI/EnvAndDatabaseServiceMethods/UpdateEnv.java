package EnvAndDatabaseServiceMethods;

import java.awt.image.BufferedImage;
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
	public static Env update(Env envIn) {
		Env newEnv = new Env(0);
		newEnv.rawEnv = newEnv.senseRawEnv();
		BufferedImage newImage = newEnv.rawEnv.currentDisplay;
		BufferedImage oldImage = envIn.rawEnv.currentDisplay;
		
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
		newishSenses = SenseCombiner.exec(newishSenses);
		try {
			envIn.rawEnv = newEnv.rawEnv;
			envIn = UpdateSenses.update(newishSenses, envIn);
			envIn = RemoveOldSensesFromEnv.exec(newishSenses, envIn);
			UploadOrientationChangesToDB.upload(envIn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return envIn;
	}
}
