package ServiceMethods;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Structure.*;

public class RawEnvToAbstractEnv {
	public static AbstractEnv extract(RawEnv rawEnvIn) {
		
		//Visual Abstract Senses
		BufferedImage imageToExtractFrom = rawEnvIn.currentDisplay.getSubimage(600, 400, 100, 100);
		//Lowest-Complexity Visual Abstract Senses				//remove subimage when actually running
		List<Blob> rawBlobs = BufferedImageToBlobs.getBlobsFromImage(imageToExtractFrom); 
		List<Sense> senses = new ArrayList<Sense>();
		for (int i = 0; i < rawBlobs.size(); i++) {
			Sense currentSense = BlobToSense.getSense(rawBlobs.get(i));
			if (currentSense.orientation.height > 1 && currentSense.orientation.width > 1) {
				if (currentSense.orientation.height > 3 || currentSense.orientation.width > 3) {
					senses.add(currentSense);
				}		
			}			
		}
		
		//for testing (output picture with the senses outlined
		//VisualOutputOfSensesFromSensesAndImage.execute(senses, imageToExtractFrom);
		
		//Higher-Complexity Visual Abstract Senses
		List<Sense> higherSenses = BaseSensesToHigherSenses.extractHigherSenses(senses);
		senses.addAll(higherSenses);
		
		//for testing (output picture with the senses outlined
		//VisualOutputOfSensesFromSensesAndImage.execute(senses, imageToExtractFrom);
		
		//Imaginary Senses
		List<Sense> imaginarySenses = ImaginaryHigherSensesFromSenses.extract(senses);
		senses.addAll(imaginarySenses);
		
		//for testing (output picture with the senses outlined
		//VisualOutputOfSensesFromSensesAndImage.execute(imaginarySenses, imageToExtractFrom);
				
		
		return new AbstractEnv(senses);
	}
}
