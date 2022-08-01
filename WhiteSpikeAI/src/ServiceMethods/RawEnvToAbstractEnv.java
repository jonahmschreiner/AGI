package ServiceMethods;
import java.util.ArrayList;
import java.util.List;

import Structure.*;

public class RawEnvToAbstractEnv {
	public static AbstractEnv extract(RawEnv rawEnvIn) {
		
		//comment out this code for testing
		
		//Visual Abstract Senses
		List<Blob> rawBlobs = BufferedImageToBlobs.getBlobsFromImage(rawEnvIn.currentDisplay);
		List<Sense> senses = new ArrayList<Sense>();
		for (int i = 0; i < rawBlobs.size(); i++) {
			senses.add(BlobToSense.getSense(rawBlobs.get(i)));
		}
		return new AbstractEnv(senses);
		
		
		
		//remove this later, currently in place for testing
		//return new AbstractEnv();
	}
}
