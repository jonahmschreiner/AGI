package com.whitespike.vision;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.whitespike.vision.ChangedPixelsFromOldEnv;
import com.whitespike.vision.ChangedPixelsFromOldEnv.junctionList;
import com.whitespike.visionstructure.*;

import com.whitespike.vision.ChangedPixelsToBlobs;
public class UpdateEnv {
	public static Env update(Env envIn) {
		envIn.abstractEnv.recentlyChangedOldSenses.clear();
		envIn.abstractEnv.recentlyAddedSenses.clear();
		envIn.abstractEnv.recentlyRemovedSenses.clear();
		Env newEnv = new Env(0);
		newEnv.rawEnv = newEnv.senseRawEnv(envIn);
		BufferedImage newImage = newEnv.rawEnv.currentDisplay.getSubimage(605, 271, 468, 398);
		BufferedImage oldImage = envIn.rawEnv.currentDisplay.getSubimage(605, 271, 468, 398);
		
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
			List<Sense> newishSensesCopy = new ArrayList<Sense>();
			newishSensesCopy.addAll(newishSenses);
			envIn = UpdateSenses.update(newishSenses, envIn);
			//VisualOutputOfSensesFromSensesAndImage.execute(envIn.abstractEnv.senses, newEnv.rawEnv.currentDisplay, "EnvSensesAfterUpdateSenses");
			envIn = RemoveOldSensesFromEnv.exec(newishSensesCopy, envIn);
			//VisualOutputOfSensesFromSensesAndImage.execute(envIn.abstractEnv.senses, newEnv.rawEnv.currentDisplay, "EnvSensesAfterRemoveOldSenses");
		} catch (Exception e) {
			e.printStackTrace();
		}
		//VisualOutputOfSensesFromSensesAndImage.execute(envIn.abstractEnv.senses, envIn.rawEnv.currentDisplay.getSubimage(200, 300, 50, 50), "output2");
		
		return envIn;
	}
}
