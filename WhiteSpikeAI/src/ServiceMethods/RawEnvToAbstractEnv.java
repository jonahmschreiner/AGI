package ServiceMethods;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import Structure.*;

public class RawEnvToAbstractEnv {
	public static AbstractEnv extract(RawEnv rawEnvIn) {
		
		//Visual Abstract Senses
		BufferedImage imageToExtractFrom = rawEnvIn.currentDisplay.getSubimage(100, 100, 100, 100);
		//Lowest-Complexity Visual Abstract Senses				//remove subimage when actually running
		List<Blob> rawBlobs = BufferedImageToBlobs.getBlobsFromImage(imageToExtractFrom); 
		List<Sense> senses = new ArrayList<Sense>();
		for (int i = 0; i < rawBlobs.size(); i++) {
			senses.add(BlobToSense.getSense(rawBlobs.get(i)));
		}
		
			
		
		//Higher-Complexity Visual Abstract Senses
		List<Sense> higherSenses = BaseSensesToHigherSenses.extractHigherSenses(senses);
		senses.addAll(higherSenses);
		
		
		//for testing (output picture with the senses outlined
		VisualOutputOfSensesFromSensesAndImage.execute(senses, imageToExtractFrom);
				
		
		return new AbstractEnv(senses);
	}
}
