package com.whitespike.vision;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import com.whitespike.visionstructure.*;

/**
 * Service method that creates an abstract env from a raw env.
 * @author Jonah Schreiner
 *
 */
public class RawEnvToAbstractEnv {
	/**
	 * Service method that creates an abstract env from a raw env.
	 * @param rawEnvIn The raw env to create an abstract env from.
	 * @param includeHigherSenses Should higher senses be found?
	 * @param includeImaginarySenses Should imaginary senses be found?
	 * @return The abstract env created.
	 */
	public static AbstractEnv extract(RawEnv rawEnvIn, boolean includeHigherSenses, boolean includeImaginarySenses) {
		
		BufferedImage imageToExtractFrom = rawEnvIn.currentDisplay.getSubimage(605, 271, 468, 398);
			//TODO remove subimage when actually running .getSubimage(600, 400, 200, 200)
		
		List<Blob> rawBlobs = BufferedImageToBlobsInParallel.getBlobsFromImage(imageToExtractFrom);
		
		List<Sense> senses = new ArrayList<Sense>();
		
		
		for (int i = 0; i < rawBlobs.size(); i++) {
			Sense currentSense = BlobToSense.getSense(rawBlobs.get(i), rawEnvIn.currentDisplay);
			//if (currentSense.orientation.height > 1 && currentSense.orientation.width > 1) {
				//if (currentSense.orientation.height > Constants.thresholdSenseHeight || currentSense.orientation.width > Constants.thresholdSenseWidth) {
					senses.add(currentSense);
				//}		
			//}			
		}
		senses = SenseCombiner.exec(senses, null);
		
		if (includeHigherSenses) {
			List<Sense> higherSenses = BaseSensesToHigherSenses.extractHigherSenses(senses);
			senses.addAll(higherSenses);
		}
		
		if (includeImaginarySenses) {
			List<Sense> imaginarySenses = ImaginaryHigherSensesFromSenses.extract(senses);
			senses.addAll(imaginarySenses);
		}
		
		return new AbstractEnv(senses);
	}
}
