package unboundContextHandling;
import unboundStruct.*;
import java.io.*;

import javax.imageio.ImageIO;
public class ContextWriter {
	public static void writeContext(Context contextIn, boolean writeToPrev) throws Exception {
		String init = contextIn.satisfaction + ",,,,," + contextIn.prevExecBeliefName + ",,,,,";
		String env = contextIn.env.mouseLocation.x + "," + contextIn.env.mouseLocation.y + "," + contextIn.env.keyPressed + ","
				+ contextIn.env.numOfMouseButtons + "," + contextIn.env.leftMouseButtonPressed + ","
				+ contextIn.env.middleMouseButtonPressed + "," + contextIn.env.rightMouseButtonPressed + ","
				+ contextIn.env.calendar.toString() + "," + contextIn.env.currentCpuUsage + ",,,,,\n";		
		String total = init + env;
		
		//errors
		for (int i = 0; i < contextIn.env.errorLocations.size(); i++) {
			total = total + contextIn.env.errorLocations.get(i).parentClass + ",,," + contextIn.env.errorLocations.get(i).instructionNumber + ",,,"
					+ contextIn.env.errorLocations.get(i).instruction + "\n";
		}
		
		//write it
		File contextFile = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/contextSource.txt");
		FileWriter writer = new FileWriter(contextFile, false);
		writer.write(total);
		writer.close();	
		if (writeToPrev) {
			File prevDisplayOut = new File("/home/agi/Desktop/eclipse/UnboundAI/bin/PrevDisplayVisuals.jpg");
			ImageIO.write(contextIn.env.currentDisplay, "jpg", prevDisplayOut);
		}
		
	}
}
