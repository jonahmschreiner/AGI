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
		total = total + "ENDERRORS\n";
		//Hypothesis Info
		for (int i = 0; i < contextIn.hypothesi.size(); i++) {
			Hypothesis hypo = contextIn.hypothesi.get(i);
			total = total + hypo.actionFile.getAbsolutePath();
			total = total + "," + hypo.actionName + ",";
			//Add rating info
			for (int j = 0; j < hypo.ratings.size(); j++) {
				total = total + hypo.ratings.get(j).goalFile.getAbsolutePath();
				total = total + " " + hypo.ratings.get(j).goalName + " ";
				total = total + hypo.ratings.get(j).rating;
				if (j+1 < hypo.ratings.size()) {
					total = total + " ";
				} else {
					total = total + "\n";
				}
			}
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
	public static void main(String[] args) throws Exception {
		Context context = ContextParser.parse();
		Hypothesis hypo1 = new Hypothesis("/home/agi/Desktop/eclipse/UnboundAI/BeliefStorage/Actions/TestBelief", "TestBelief");
		context.hypothesi.add(hypo1);
		ContextWriter.writeContext(context, false);
	}
}
