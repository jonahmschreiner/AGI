package unboundBeliefHandling;
import java.io.*;
import java.util.*;
import unboundStruct.*;
public class BeliefParser {
	public static List<Belief> parseBeliefs(List<String> beliefPathsIn, int typeIn) throws FileNotFoundException{
		List<Belief> parsedBeliefs = new ArrayList<Belief>();		
		for (int i = 0; i < beliefPathsIn.size(); i++) {
			File source = new File(beliefPathsIn.get(i));
			Scanner scan = new Scanner(source);
			scan.useDelimiter(",,,");		
			//tags
			String tagString = scan.next();
			Scanner tagReader = new Scanner(tagString);
			List<KeyTag> tags = new ArrayList<KeyTag>();
			while (tagReader.hasNext()) {
				tagReader.useDelimiter(":");
				String tag = tagReader.next();
				if (tag.contains(" ")) {
					tag = tag.substring(1);
				}
				tagReader.useDelimiter(" ");
				String confidenceRatingString = tagReader.next();
				if (confidenceRatingString.charAt(0) == ':') {
					confidenceRatingString = confidenceRatingString.substring(1);
				}
				int confidenceRating = Integer.valueOf(confidenceRatingString);
				KeyTag tagOut = new KeyTag(tag, confidenceRating);
				tags.add(tagOut);
			}
			tagReader.close();		
			//parent class
			Scanner parentClassScanner = new Scanner(beliefPathsIn.get(i));
			parentClassScanner.useDelimiter("/");
			String parentClass = "";
			while (parentClassScanner.hasNext()) {
				parentClass = parentClassScanner.next();
			}
			parentClassScanner.close();
			//instructions
			List<Instruction> currentBeliefInstructions = new ArrayList<Instruction>();
			String instructionString = scan.next();
			scan.close();
			Scanner instScanner = new Scanner(instructionString);
			String currentInstructionString = "";
			int instructionNum = 0;
			while (instScanner.hasNextLine()) {
				currentInstructionString = instScanner.nextLine();
				if (!currentInstructionString.isEmpty()) {
					Instruction inst = new Instruction(parentClass, instructionNum, currentInstructionString);
					currentBeliefInstructions.add(inst);
					instructionNum++;
				}
			}	
			instScanner.close();		
			Belief currentBelief = new Belief(parentClass, typeIn, currentBeliefInstructions, tags);
			parsedBeliefs.add(currentBelief);
		}	
		return parsedBeliefs;
	}
}
