package ServiceMethods;
import java.util.ArrayList;
import java.util.List;

import Structure.*;

public class RawEnvToAbstractEnv {
	public static AbstractEnv extract(RawEnv rawEnvIn) {
		
		//Visual Abstract Senses
		
		//Lowest-Complexity Visual Abstract Senses
		List<Blob> rawBlobs = BufferedImageToBlobs.getBlobsFromImage(rawEnvIn.currentDisplay.getSubimage(100, 100, 100, 100));
		List<Sense> senses = new ArrayList<Sense>();
		for (int i = 0; i < rawBlobs.size(); i++) {
			senses.add(BlobToSense.getSense(rawBlobs.get(i)));
		}
		
		//Higher-Complexity Visual Abstract Senses
		
		return new AbstractEnv(senses);
	}
}
