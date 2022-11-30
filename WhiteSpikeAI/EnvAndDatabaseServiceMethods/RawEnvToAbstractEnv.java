package EnvAndDatabaseServiceMethods;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Structure.*;

public class RawEnvToAbstractEnv {
	public static AbstractEnv extract(RawEnv rawEnvIn) {
		
		//Visual Abstract Senses
		BufferedImage imageToExtractFrom = rawEnvIn.currentDisplay.getSubimage(605, 271, 468, 398);
		//Lowest-Complexity Visual Abstract Senses				//remove subimage when actually running .getSubimage(600, 400, 200, 200)
		
		long blobStart = System.currentTimeMillis();
		//List<Blob> rawBlobs = BufferedImageToBlobs.getBlobsFromImage(imageToExtractFrom); 
		List<Blob> rawBlobs = BufferedImageToBlobsInParallel.getBlobsFromImage(imageToExtractFrom);
		long blobEnd = System.currentTimeMillis();
		System.out.println("Total Blob Creation Time: " + (blobEnd-blobStart));
		
		List<Sense> senses = new ArrayList<Sense>();
		
		
		//long baseSenseStart = System.currentTimeMillis();
		
		
		for (int i = 0; i < rawBlobs.size(); i++) {
			Sense currentSense = BlobToSense.getSense(rawBlobs.get(i), rawEnvIn.currentDisplay, i);
			//if (currentSense.orientation.height > 1 && currentSense.orientation.width > 1) {
				//if (currentSense.orientation.height > 3 || currentSense.orientation.width > 3) {
					senses.add(currentSense);
				//}		
			//}			
		}
		senses = SenseCombiner.exec(senses, null);
		//long baseSenseEnd = System.currentTimeMillis();
		//System.out.println("Total Raw Sense Creation Time: " + (baseSenseEnd-baseSenseStart));
		
		
		//for testing (output picture with the senses outlined
		//VisualOutputOfSensesFromSensesAndImage.execute(senses, imageToExtractFrom, null);
		
		//Higher-Complexity Visual Abstract Senses
		//long higherSenseStart = System.currentTimeMillis();
		//HERE!!!!!!!!!!!
		//List<Sense> higherSenses = BaseSensesToHigherSenses.extractHigherSenses(senses);
		//senses.addAll(higherSenses);
		
		//long higherSenseEnd = System.currentTimeMillis();
		//System.out.println("Total Higher Sense Creation Time: " + (higherSenseEnd-higherSenseStart));
		
		//for testing (output picture with the senses outlined
		//VisualOutputOfSensesFromSensesAndImage.execute(senses, imageToExtractFrom);
		
		//Imaginary Senses
		//long imaginarySenseStart = System.currentTimeMillis();
		
		//HERE!!!!
		//List<Sense> imaginarySenses = ImaginaryHigherSensesFromSenses.extract(senses);
		//senses.addAll(imaginarySenses);
		
		
		//long imaginarySenseEnd = System.currentTimeMillis();
		//System.out.println("Total Imaginary Sense Creation Time: " + (imaginarySenseEnd-imaginarySenseStart));
		//for testing (output picture with the senses outlined
		//VisualOutputOfSensesFromSensesAndImage.execute(imaginarySenses, imageToExtractFrom);
				
		
		return new AbstractEnv(senses);
	}
}
