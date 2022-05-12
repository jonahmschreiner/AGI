package unboundBeliefHandling;
import java.util.*;
import unboundStruct.*;
public class PullHighestConfBelief {
	public static Belief retrieveHighest(List<Belief> beliefInput, List<KeyTag> tagsIn) {
		int maxSum = 0;
		Belief output = new Belief();
		for (int i = 0; i < beliefInput.size(); i++) {
			int currentSum = 0;
			for (int j = 0; j < beliefInput.get(i).keyTags.size(); j++) {
				KeyTag tag = new KeyTag(beliefInput.get(i).keyTags.get(j).label, 0);
				if (tagsIn.contains(tag)) {
					currentSum += beliefInput.get(i).keyTags.get(j).confidenceRating;
				}				
			}
			if (currentSum > maxSum) {
				maxSum = currentSum;
				output = beliefInput.get(i);
			}
		}
		return output;
	}
	
	
	
	public static void main(String[] args) {
		KeyTag Tag1 = new KeyTag("Tag1", 50);
		KeyTag tag2 = new KeyTag("Tag2", 50);
		Belief bel1 = new Belief();
		bel1.beliefName = "BEL1";
		List<KeyTag> keyTags1 = new ArrayList<KeyTag>();
		keyTags1.add(Tag1);
		keyTags1.add(tag2);
		bel1.keyTags = keyTags1;
		KeyTag tag3 = new KeyTag("Tag1", 60);
		KeyTag tag4 = new KeyTag("Tag2", 30);
		Belief bel2 = new Belief();
		bel2.beliefName = "BEL2";
		List<KeyTag> keyTags2 = new ArrayList<KeyTag>();
		keyTags2.add(tag3);
		keyTags2.add(tag4);
		bel2.keyTags = keyTags2;
		List<Belief> beliefs = new ArrayList<Belief>();
		beliefs.add(bel1);
		beliefs.add(bel2);
		List<KeyTag> tagsIn = new ArrayList<KeyTag>();
		tagsIn.add(Tag1);
		tagsIn.add(tag2);
		System.out.println(tagsIn);
		System.out.println(PullHighestConfBelief.retrieveHighest(beliefs, tagsIn).beliefName);
	}
}
